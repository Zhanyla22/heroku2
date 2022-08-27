package com.example.SpringZhanyl.RestController;

import com.example.SpringZhanyl.Entity.User;
import com.example.SpringZhanyl.Model.AuthenticationRequestModel;
import com.example.SpringZhanyl.Repository.UserRepository;
import com.example.SpringZhanyl.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationRestController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?>  authenticate(@RequestBody AuthenticationRequestModel requestModel){
        try {
            String email = requestModel.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestModel.getEmail(),requestModel.getPassword()));
            User users = userRepository.findByEmail(requestModel.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User doesnt exist"));
            String token = jwtTokenProvider.createToken(requestModel.getEmail(), users.getRole().name());
            Map<Object,Object> response = new HashMap<>();
            response.put("email", requestModel.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);


        } catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid email password", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request,response,null);
    }
}

