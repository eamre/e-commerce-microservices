package com.kodlamaio.commonpackage.events.order;

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
public class OrderCreatedEvent implements Event {
    private UUID productId;
    private int requestQuantity;
}
