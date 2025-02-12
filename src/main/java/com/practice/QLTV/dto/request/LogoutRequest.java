package com.practice.QLTV.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogoutRequest {
    String token;
}
