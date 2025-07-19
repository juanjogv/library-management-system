package co.com.juanjogv.lms.application.dto.user;

import java.util.UUID;

public record FindAllUsersResponse(
        UUID id,
        String name,
        String email
) {}
