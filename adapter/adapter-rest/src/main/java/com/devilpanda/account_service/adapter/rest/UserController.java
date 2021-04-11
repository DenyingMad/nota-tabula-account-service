package com.devilpanda.account_service.adapter.rest;

import com.devilpanda.account_service.app.api.UserService;
import com.devilpanda.account_service.domain.User;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDtoMapper mapper;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDto.class),
            @ApiResponse(code = 409, message = "Action rejected due to business rules / validations")
    })
    @PostMapping
    public UserDto createUser(@RequestBody UserFormDto userFormDto) {
        User user = mapper.mapUserFromUserDto(userFormDto);
        return mapper.mapDtoFromUser(userService.createUser(user));
    }

    @GetMapping("/{login}")
    public UserDto getUser(@PathVariable String login) {
        User user = userService.getUserByLogin(login);
        return mapper.mapDtoFromUser(user);
    }
}
