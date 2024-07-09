package com.example.demo.api;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.request.IntrospectRequest;
import com.example.demo.dto.response.IntrospectResponse;
// import com.example.demo.dto.AuthenticationRequest;
// import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.service.impl.AuthenticationService;
import com.nimbusds.jose.JOSEException;

// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
public class Authentication {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/log-in")
    public String authentication(@RequestBody UserDTO request) {
        String result = authenticationService.authenticate(request);
        return result;
    }

    @PostMapping(value = "/introspect")
    public IntrospectResponse verified(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        var result = authenticationService.introspect(request);
        return result;
    }

}
