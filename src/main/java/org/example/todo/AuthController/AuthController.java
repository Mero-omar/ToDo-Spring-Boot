package org.example.todo.AuthController;

import lombok.RequiredArgsConstructor;
import org.example.todo.AuthService.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    public final AuthenticationService authenticationService;

    @PostMapping("/register")
        public ResponseEntity<AuthenticationResponse>  Register(@RequestBody RegisterRequest regiter){
        return ResponseEntity.ok(authenticationService.register(regiter));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse>  Register(@RequestBody AuthRequest login){
        return ResponseEntity.ok(authenticationService.login(login));
    }

}
