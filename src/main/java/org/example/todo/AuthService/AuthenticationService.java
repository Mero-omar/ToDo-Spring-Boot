package org.example.todo.AuthService;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.todo.AuthController.AuthenticationResponse;
import org.example.todo.AuthController.AuthRequest;
import org.example.todo.AuthController.RegisterRequest;
import org.example.todo.DAO.UserDAO;
import org.example.todo.Entity.Role;
import org.example.todo.Entity.User;
import org.example.todo.SecurityConfig.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final  UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JWTService service;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest register) {
       var user= new User();
       user.setFirstname(register.getFirstname());
       user.setLastname(register.getLastname());
       user.setEmail(register.getEmail());
       user.setRole(Role.USER);
       user.setPassword(passwordEncoder.encode(register.getPassword()));

       userDAO.save(user);
       var jwtToken= service.createToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public AuthenticationResponse login(AuthRequest login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );

        var user=userDAO.findByEmail(login.getEmail()).orElseThrow(()->new UsernameNotFoundException("user not found"));

        var jwtToken= service.createToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();

    }
}
