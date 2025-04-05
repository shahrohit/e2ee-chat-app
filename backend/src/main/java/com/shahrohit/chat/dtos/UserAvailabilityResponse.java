package com.shahrohit.chat.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAvailabilityResponse {
    private boolean available;
    private String message;
}
