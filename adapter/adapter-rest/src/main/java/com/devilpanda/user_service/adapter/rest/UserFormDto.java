package com.devilpanda.user_service.adapter.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFormDto {
    private String login;
    private String email;
    private String password;
    private Boolean isSubscribed;
}
