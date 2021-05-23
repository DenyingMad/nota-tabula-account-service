package com.devilpanda.user_service.adapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequestDto {
    private String login;
    private String email;
    private String password;
    private Boolean isSubscribed;
}
