package com.laptrinhjava.LichHoc.controller;

import com.laptrinhjava.LichHoc.entity.ERole;
import com.laptrinhjava.LichHoc.entity.ResponseObject;
import com.laptrinhjava.LichHoc.entity.Role;
import com.laptrinhjava.LichHoc.entity.User;
import com.laptrinhjava.LichHoc.payload.request.LoginRequest;
import com.laptrinhjava.LichHoc.payload.request.SignupRequest;
import com.laptrinhjava.LichHoc.payload.response.JwtResponse;
import com.laptrinhjava.LichHoc.payload.response.MessageResponse;
import com.laptrinhjava.LichHoc.repository.RoleRepository;
import com.laptrinhjava.LichHoc.security.jwt.JwtUtils;
import com.laptrinhjava.LichHoc.service.RoleService;
import com.laptrinhjava.LichHoc.service.UserService;
import com.laptrinhjava.LichHoc.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<ResponseObject> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        List<User> foundUserName = userService.findAllByUserName(loginRequest.getUserName());
        Boolean foundPassword = null;
        if (!foundUserName.isEmpty()) {
            User user = userService.findUserName(loginRequest.getUserName());
            foundPassword = encoder.matches(loginRequest.getPassword(), user.getPassword());
        }
        if (foundUserName.isEmpty() || foundPassword == false) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Ten dang nhap hoac mat khau khong dung", "")
            );
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userService.findUserName(loginRequest.getUserName()); // get fullName

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Login successfully!", new JwtResponse(jwt,
                                                                            userDetails.getId(),
                                                                            userDetails.getUsername(),
                                                                            user.getFullName(),
                                                                            roles))
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseObject> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userService.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Username already exists!", "")
                    );
        }

        // Create new user's account
        User user = new User(signUpRequest.getUserName(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getFullName());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "user":
                        Role userRole = roleService.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        break;
                }
            });
        }

        user.setRoles(roles);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "User registered successfully!", userService.save(user))
        );
    }


//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> registerUser(@RequestParam(name = "username") String userName, @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
//        User foundUser = userRepository.findByUserName(userName);
//        if (foundUser == null) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Username not exists!"));
//        } else {
//            if (forgotPasswordRequest.getNewPassword().equals(forgotPasswordRequest.getConfirmPassword())) {
//                foundUser.setPassword(encoder.encode(forgotPasswordRequest.getNewPassword()));
//                userRepository.save(foundUser);
//                return ResponseEntity.ok("Update password successfully!");
//            } else {
//                return ResponseEntity.badRequest().body(new MessageResponse("Confirm password does not match!"));
//            }
//        }
//    }
}
