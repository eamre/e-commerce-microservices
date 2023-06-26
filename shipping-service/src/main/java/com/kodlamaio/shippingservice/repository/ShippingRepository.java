package com.kodlamaio.shippingservice.repository;

import com.kodlamaio.shippingservice.entities.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShippingRepository extends JpaRepository<Shipping, UUID> {
}
