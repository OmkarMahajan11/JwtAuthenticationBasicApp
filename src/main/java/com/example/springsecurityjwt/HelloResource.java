package com.example.springsecurityjwt;

import com.example.springsecurityjwt.models.AuthenticationRequest;
import com.example.springsecurityjwt.models.AuthenticationResponse;
import com.example.springsecurityjwt.services.MyUserDetailsService;
import com.example.springsecurityjwt.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HelloResource {
  
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private MyUserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtTokenUtil;

  @GetMapping("/hello")
  public String hello() {
    return "Hello World";
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
    );

    final UserDetails UserDetails = userDetailsService
      .loadUserByUsername(authenticationRequest.getUsername());
    final String jwt = jwtTokenUtil.generateToken(UserDetails);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }
}
