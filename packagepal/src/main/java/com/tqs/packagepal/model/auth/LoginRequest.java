package com.tqs.packagepal.model.auth;

import jakarta.annotation.sql.DataSourceDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
