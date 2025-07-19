package co.com.juanjogv.lms.application.dto.authentication;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
    String token
) {}
