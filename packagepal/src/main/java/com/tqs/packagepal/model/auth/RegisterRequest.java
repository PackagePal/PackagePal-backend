package com.tqs.packagepal.model.auth;

import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    @Nullable
    private String role = "ROLE_NOT_VERIFIED";
}
