package co.com.juanjogv.lms.application.dto.user;

import co.com.juanjogv.lms.domain.model.Role;

import java.util.UUID;

public record FindUserByIdResponse(
        UUID id,
        String name,
        String email,
        Role role
) {}
