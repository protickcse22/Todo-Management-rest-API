package com.protick.todomanagementrestapi.service;

import com.protick.todomanagementrestapi.dto.user.LoginDto;
import com.protick.todomanagementrestapi.dto.user.SignUpRequestDto;
import com.protick.todomanagementrestapi.dto.user.TokenDto;
import com.protick.todomanagementrestapi.model.User;
import com.protick.todomanagementrestapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public static final String TAG = UserService.class.getName();
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;

    private final AuthenticationManager authenticationManager;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public UserService(UserRepository userRepository, JwtTokenService jwtTokenService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;

    }

    public User registerUser(SignUpRequestDto signUpRequestDto) {
        logger.info(TAG+"signUpDto: "+ signUpRequestDto);
        var user = new User();
        user.setUsername(signUpRequestDto.userName());
        user.setEmail(signUpRequestDto.userEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequestDto.password()));
        user.setRoles(List.of("USER"));
        logger.info(TAG+"user: "+user);
        return userRepository.save(user);
    }

    public TokenDto login(LoginDto loginDto) throws Exception{
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.userName(),
                            loginDto.password()
                    )
            );
            var jwtToken = jwtTokenService.generateToken(authentication);
            logger.info("Token: "+jwtToken);
            return new TokenDto(jwtToken);
        } catch (BadCredentialsException e) {
            logger.info("Exception found: ");
            throw new Exception("Invalid credential",e);
        }
    }


}
