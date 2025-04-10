package com.shahrohit.chat.dtos;

import lombok.Builder;

@Builder
public record UserAvailabilityResponse(
    boolean available,
    String message
){}
