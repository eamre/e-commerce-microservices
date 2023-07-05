package com.kodlamaio.orderservice.business.concretes;

import com.kodlamaio.commonpackage.events.invoice.InvoiceCreatedEvent;
import com.kodlamaio.commonpackage.events.order.OrderCreatedEvent;
import com.kodlamaio.commonpackage.events.shipping.ShippingCreatedEvent;
import com.kodlamaio.commonpackage.kafka.producer.KafkaProducer;
import com.kodlamaio.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.kodlamaio.commonpackage.utils.dto.GetProductResponse;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.orderservice.api.clients.ProductClient;
import com.kodlamaio.orderservice.business.abstracts.OrderService;
import com.kodlamaio.orderservice.business.dto.requests.create.CreateOrderRequest;
import com.kodlamaio.orderservice.business.dto.requests.update.UpdateOrderRequest;
import com.kodlamaio.orderservice.business.dto.responses.create.CreateOrderResponse;
import com.kodlamaio.orderservice.business.dto.responses.get.GetAllOrdersResponse;
import com.kodlamaio.orderservice.business.dto.responses.get.GetOrderResponse;
import com.kodlamaio.orderservice.business.dto.responses.update.UpdateOrderResponse;
import com.kodlamaio.orderservice.business.rules.OrderBusinessRules;
import com.kodlamaio.orderservice.entities.Order;
import com.kodlamaio.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@Service
public class OrderManager implements OrderService {
    private final OrderRepository repository;
    private final ModelMapperService mapper;
    private final KafkaProducer producer;
    private final OrderBusinessRules rules;
    private final ProductClient productClient;
    @Override
    public List<GetAllOrdersResponse> getAll() {
        var orders = repository.findAll();
        var responses = orders
                .stream()
                .map(order -> mapper.forResponse().map(order, GetAllOrdersResponse.class))
                .toList();

        return responses;
    }

    @Override
    public GetOrderResponse getById(UUID id) {
        var order = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(order, GetOrderResponse.class);

        return response;
    }

    @Override
    public CreateOrderResponse add(CreateOrderRequest request) {
        rules.ensureProductIsActive(request.getProductId());
        rules.ensureProductHaveEnoughStock(request.getProductId(), request.getQuantity());

        var order = mapper.forRequest().map(request, Order.class);
        order.setId(UUID.randomUUID());
        order.setTotalPrice(getTotalPrice(order));
        order.setSaleDate(LocalDateTime.now());

        var rentalPaymentRequest = new CreateRentalPaymentRequest();
        mapper.forRequest().map(request.getPaymentRequest(),rentalPaymentRequest);
        rentalPaymentRequest.setPrice(order.getTotalPrice());
        rules.ensurePaymentProcess(rentalPaymentRequest);

        var createdOrder = repository.save(order);
        sendKafkaOrderCreatedEvent(order.getProductId(),order.getQuantity());

        ShippingCreatedEvent shippingCreatedEvent = mapper.forResponse().map(createdOrder, ShippingCreatedEvent.class);
        shippingCreatedEvent.setFullName(request.getShippingRequest().getFullName());
        shippingCreatedEvent.setAddress(request.getShippingRequest().getAddress());
        shippingCreatedEvent.setOrderId(createdOrder.getId());
        sendKafkaShippingCreatedEvent(shippingCreatedEvent);

        InvoiceCreatedEvent invoiceCreatedEvent = new InvoiceCreatedEvent();
        createInvoice(order, invoiceCreatedEvent ,request);
        sendKafkaInvoiceCreatedEvent(invoiceCreatedEvent);

        var response = mapper.forResponse().map(order,CreateOrderResponse.class);
        return response;
    }

    @Override
    public UpdateOrderResponse update(UUID id, UpdateOrderRequest request) {
        rules.checkIfOrderExists(id);
        var order = mapper.forRequest().map(request, Order.class);
        order.setId(id);
        repository.save(order);

        var response = mapper.forResponse().map(order, UpdateOrderResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private double getTotalPrice(Order order) {
        return order.getPrice() * order.getQuantity();
    }
    private void sendKafkaOrderCreatedEvent(UUID carId, int requestQuantity){
        producer.sendMessage(new OrderCreatedEvent(carId, requestQuantity), "order-created");
    }

    private void sendKafkaShippingCreatedEvent(ShippingCreatedEvent event){
        producer.sendMessage(event, "shipping-created");
    }

    private void sendKafkaInvoiceCreatedEvent(InvoiceCreatedEvent event) {
        producer.sendMessage(event, "invoice-created");
    }
    private void createInvoice(Order order, InvoiceCreatedEvent event, CreateOrderRequest orderRequest){
        GetProductResponse response = productClient.getById(orderRequest.getProductId());

        event.setPrice(orderRequest.getPrice());
        event.setQuantity(orderRequest.getQuantity());
        event.setProductName(response.getName());
        event.setOrderDate(order.getSaleDate());
        event.setCardHolder(orderRequest.getPaymentRequest().getCardHolder());
        event.setTotalPrice(order.getTotalPrice());
    }
}
