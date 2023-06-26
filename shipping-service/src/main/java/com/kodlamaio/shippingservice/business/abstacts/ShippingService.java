package com.kodlamaio.shippingservice.business.abstacts;

import com.kodlamaio.commonpackage.utils.dto.CreateShippingRequest;
import com.kodlamaio.shippingservice.business.dto.requests.UpdateShippingRequest;
import com.kodlamaio.shippingservice.business.dto.responses.CreateShippingResponse;
import com.kodlamaio.shippingservice.business.dto.responses.GetAllShippingResponse;
import com.kodlamaio.shippingservice.business.dto.responses.GetShippingResponse;
import com.kodlamaio.shippingservice.business.dto.responses.UpdateShippingResponse;

import java.util.List;
import java.util.UUID;

public interface ShippingService {
    List<GetAllShippingResponse> getAll();
    GetShippingResponse getById(UUID id);
    CreateShippingResponse add(CreateShippingRequest request);
    UpdateShippingResponse update(UUID id, UpdateShippingRequest request);
    void delete(UUID id);
}
