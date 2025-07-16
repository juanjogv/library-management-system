package co.com.juanjogv.lms.application.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
    String token
) {}
