package com.example.demo.service.impl;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.request.IntrospectRequest;
import com.example.demo.dto.response.IntrospectResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.AppException;
import com.example.demo.exception.Error;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IAuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService implements IAuthenticationService {
    @Autowired
    UserRepository userRepository;
    @NonFinal
    protected static final String SIGNER_KEY = "6NTkXT+FMt8vBvq1xu7Np/Z+TLUTj+oPrWzGmKdJa3CIdtkfv6/pAVovSabww8m6";

    // @Autowired
    // private UserConverter userConverter;
    @Override
    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();

    }

    @Override
    public String authenticate(UserDTO request) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // System.out.println(request.getUserName());
        // System.out.println(userRepository.findByUserName(request.getUserName()));
        var user = userRepository
                .findByUserName(request.getUserName())
                .orElseThrow(() -> new AppException(Error.USER_NOT_EXISTED));
        if (userRepository.findByUserName(request.getUserName()) == null) {
            throw new AppException(Error.USER_NOT_EXISTED);
        } else {
            // UserDTO userDTO = new UserDTO();
            // String passWordRequest = passwordEncoder.encode(request.getPassword());
            // UserEntity userEntity = new UserEntity();
            // userEntity = userRepository.findByUserName(request.getUserName());

            UserEntity userEntity = userRepository.findByUserName(request.getUserName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if (passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
                var token = generateToken(request.getUserName());
                return token;

            } else {
                throw new AppException(Error.UNAUTHENTICATED);
            }
        }
    }

    private String generateToken(String userName) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userName)
                .issuer("dang.com")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .claim("customClaim", "Custom")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        // Create the HMAC signer with the 256-bit secret key
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            return null;

        }
    }
}
