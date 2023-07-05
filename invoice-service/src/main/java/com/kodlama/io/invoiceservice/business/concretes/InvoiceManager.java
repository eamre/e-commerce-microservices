package com.kodlama.io.invoiceservice.business.concretes;

import com.kodlama.io.invoiceservice.business.abstracts.InvoiceService;
import com.kodlama.io.invoiceservice.business.dto.requests.CreateInvoiceRequest;
import com.kodlama.io.invoiceservice.business.dto.requests.UpdateInvoiceRequest;
import com.kodlama.io.invoiceservice.business.dto.responses.CreateInvoiceResponse;
import com.kodlama.io.invoiceservice.business.dto.responses.GetAllInvoicesResponse;
import com.kodlama.io.invoiceservice.business.dto.responses.GetInvoiceResponse;
import com.kodlama.io.invoiceservice.business.dto.responses.UpdateInvoiceResponse;
import com.kodlama.io.invoiceservice.entities.Invoice;
import com.kodlama.io.invoiceservice.repository.InvoiceRepository;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
    private final InvoiceRepository repository;
    private final ModelMapperService mapper;
    @Override
    public List<GetAllInvoicesResponse> getAll() {
        var invoices = repository.findAll();
        var responses = invoices
                .stream()
                .map(invoice -> mapper.forResponse().map(invoice, GetAllInvoicesResponse.class))
                .toList();

        return responses;
    }

    @Override
    public GetInvoiceResponse getById(String id) {
        var invoice = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(invoice, GetInvoiceResponse.class);

        return response;
    }

    @Override
    public CreateInvoiceResponse add(CreateInvoiceRequest request) {
        var invoice = mapper.forRequest().map(request, Invoice.class);
        repository.save(invoice);

        var response = mapper.forResponse().map(invoice, CreateInvoiceResponse.class);
        return response;
    }

    @Override
    public UpdateInvoiceResponse update(String id, UpdateInvoiceRequest request) {
        var invoice = mapper.forRequest().map(request, Invoice.class);
        invoice.setId(id);
        repository.save(invoice);

        var response = mapper.forResponse().map(invoice, UpdateInvoiceResponse.class);
        return response;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
