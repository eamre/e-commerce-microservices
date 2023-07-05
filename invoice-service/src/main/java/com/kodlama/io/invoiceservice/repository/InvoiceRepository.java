package com.kodlama.io.invoiceservice.repository;

import com.kodlama.io.invoiceservice.entities.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice,String> {
}
