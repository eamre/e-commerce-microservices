package com.kodlamaio.paymentservice.business.concretes;

import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import com.kodlamaio.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.dto.requests.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.requests.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.responses.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.GetAllPaymentsResponse;
import com.kodlamaio.paymentservice.business.dto.responses.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.UpdatePaymentResponse;
import com.kodlamaio.paymentservice.business.rules.PaymentBusinessRules;
import com.kodlamaio.paymentservice.entites.Payment;
import com.kodlamaio.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private final PaymentRepository repository;
    private final ModelMapperService mapper;
    private final PaymentBusinessRules rules;

    @Override
    public List<GetAllPaymentsResponse> getAll() {
        var payments = repository.findAll();
        var responses = payments
                .stream()
                .map(payment -> mapper.forResponse().map(payment, GetAllPaymentsResponse.class))
                .toList();

        return responses;
    }

    @Override
    public GetPaymentResponse getById(UUID id) {
        var payment = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(payment, GetPaymentResponse.class);

        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        var payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(null);
        repository.save(payment);

        var response = mapper.forResponse().map(payment, CreatePaymentResponse.class);
        return response;
    }

    @Override
    public UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request) {
        var payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(id);
        repository.save(payment);

        var response = mapper.forResponse().map(payment, UpdatePaymentResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public ClientResponse paymentProcess(CreateRentalPaymentRequest request){
        var response = new ClientResponse();
        checkPaymentAvailability(request,response);
        return response;
    }

    private void checkPaymentAvailability(CreateRentalPaymentRequest request, ClientResponse response){
        try {
            rules.checkIfPaymentValid(request);
            var payment = repository.findByCardNumber(request.getCardNumber());
            rules.checkIfBalanceIsEnough(payment.getBalance(), request.getPrice());
            payment.setBalance(payment.getBalance()-request.getPrice());
            repository.save(payment);
            response.setSuccess(true);
        }catch (BusinessException e){
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
    }
}
