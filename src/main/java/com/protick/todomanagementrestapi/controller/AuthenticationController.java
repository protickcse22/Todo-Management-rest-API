package com.protick.todomanagementrestapi.controller;

import com.protick.todomanagementrestapi.dto.user.LoginDto;
import com.protick.todomanagementrestapi.dto.user.SignUpRequestDto;
import com.protick.todomanagementrestapi.dto.user.SignUpResponseDto;
import com.protick.todomanagementrestapi.dto.user.TokenDto;
import com.protick.todomanagementrestapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUpUser(@RequestBody SignUpRequestDto signUpRequestDto) {
        var createdUser = userService.registerUser(signUpRequestDto);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) throws Exception {
        var response = userService.login(loginDto);
        return ResponseEntity.ok(response);
    }
}
