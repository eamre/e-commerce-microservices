package com.kodlamaio.commonpackage.events.shipping;

import com.kodlamaio.commonpackage.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingCreatedEvent implements Event {
    private UUID orderId;
    private String fullName;
    private String address;
}
