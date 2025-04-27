package io.sleepyhoon.project1.event;

import io.sleepyhoon.project1.entity.Order;

public record OrderCreatedEvent(Order order) {}
