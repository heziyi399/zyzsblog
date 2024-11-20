package com.zysblog.zysblog.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data

public class LoginAdminRequest {
    @NotNull
    private String userName;
    @NotNull
    private String passWord;
}
