package com.kodlamaio.shippingservice.business.concretes;

import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.shippingservice.business.abstacts.ShippingService;
import com.kodlamaio.commonpackage.utils.dto.CreateShippingRequest;
import com.kodlamaio.shippingservice.business.dto.requests.UpdateShippingRequest;
import com.kodlamaio.shippingservice.business.dto.responses.CreateShippingResponse;
import com.kodlamaio.shippingservice.business.dto.responses.GetAllShippingResponse;
import com.kodlamaio.shippingservice.business.dto.responses.GetShippingResponse;
import com.kodlamaio.shippingservice.business.dto.responses.UpdateShippingResponse;
import com.kodlamaio.shippingservice.entities.Shipping;
import com.kodlamaio.shippingservice.repository.ShippingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ShippingManager implements ShippingService {
    private final ShippingRepository repository;
    private final ModelMapperService mapper;
    @Override
    public List<GetAllShippingResponse> getAll() {
        var shipping = repository.findAll();
        var responses = shipping
                .stream()
                .map(order -> mapper.forResponse().map(order, GetAllShippingResponse.class))
                .toList();

        return responses;
    }

    @Override
    public GetShippingResponse getById(UUID id) {
        var shipping = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(shipping, GetShippingResponse.class);

        return response;
    }

    @Override
    public CreateShippingResponse add(CreateShippingRequest request) {
        var shipping = mapper.forRequest().map(request, Shipping.class);
        shipping.setId(null);
        shipping.setShippingCode(UUID.randomUUID().toString());
        shipping.setShippingDate(LocalDateTime.now());

        repository.save(shipping);

        var response = mapper.forResponse().map(shipping, CreateShippingResponse.class);
        return response;
    }

    @Override
    public UpdateShippingResponse update(UUID id, UpdateShippingRequest request) {
        var shipping = mapper.forRequest().map(request, Shipping.class);
        shipping.setId(id);
        shipping.setShippingCode(UUID.randomUUID().toString());
        shipping.setShippingDate(LocalDateTime.now());

        repository.save(shipping);

        var response = mapper.forResponse().map(shipping, UpdateShippingResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
