package com.devilpanda.user_service.adapter.rest;

import com.devilpanda.user_service.adapter.rest.dto.UserAuthDto;
import com.devilpanda.user_service.adapter.rest.dto.UserDtoMapper;
import com.devilpanda.user_service.adapter.rest.dto.UserCreationRequestDto;
import com.devilpanda.user_service.app.api.UserService;
import com.devilpanda.user_service.domain.User;
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
            @ApiResponse(code = 200, message = "OK", response = UserAuthDto.class),
            @ApiResponse(code = 409, message = "Action rejected due to business rules / validations")
    })
    @PostMapping
    public void createUser(@RequestBody UserCreationRequestDto userCreationRequestDto) {
        User user = mapper.mapUserFromUserDto(userCreationRequestDto);
        userService.createUser(user);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserAuthDto.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping("/login")
    public UserAuthDto getUserByLogin(@RequestParam String login) {
        User user = userService.getUserByLogin(login);
        return mapper.mapDtoFromUser(user);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserAuthDto.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping("/email")
    public UserAuthDto getUserByEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        return mapper.mapDtoFromUser(user);
    }
}
