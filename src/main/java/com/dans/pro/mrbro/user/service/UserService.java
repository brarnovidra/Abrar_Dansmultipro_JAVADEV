package com.dans.pro.mrbro.user.service;

import com.dans.pro.mrbro.common.constant.ResultCode;
import com.dans.pro.mrbro.common.constant.ResultType;
import com.dans.pro.mrbro.common.response.RestSingleResponse;
import com.dans.pro.mrbro.security.jwt.JwtUtils;
import com.dans.pro.mrbro.security.services.UserDetailsImpl;
import com.dans.pro.mrbro.user.models.ERole;
import com.dans.pro.mrbro.user.models.Role;
import com.dans.pro.mrbro.user.models.User;
import com.dans.pro.mrbro.user.payload.request.LoginRequest;
import com.dans.pro.mrbro.user.payload.request.SignupRequest;
import com.dans.pro.mrbro.user.payload.response.JwtV;
import com.dans.pro.mrbro.user.repository.RoleRepository;
import com.dans.pro.mrbro.user.repository.UserRepository;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    private final Gson gson;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public RestSingleResponse<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return RestSingleResponse.create(ResultCode.SUCCESS, ResultType.READ, new JwtV.JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles), "SIGNIN ");
    }

    public RestSingleResponse<User> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return RestSingleResponse.create(ResultCode.FAIL, ResultType.CREATE, null, "Username is already taken! ");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return RestSingleResponse.create(ResultCode.FAIL, ResultType.CREATE, null, "Email is already in use! ");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return RestSingleResponse.create(ResultCode.SUCCESS, ResultType.CREATE, user, "User registered successfully! ");

    }

}
