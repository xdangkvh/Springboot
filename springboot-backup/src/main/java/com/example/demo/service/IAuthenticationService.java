package com.example.demo.service;

import java.text.ParseException;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.request.IntrospectRequest;
import com.example.demo.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

public interface IAuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    String authenticate(UserDTO request);

    // String generateToken(String userName);

}
