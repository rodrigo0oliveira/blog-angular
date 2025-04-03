package br.com.backend.blog_security.application;

import br.com.backend.blog_security.domain.TokenResponse;
import br.com.backend.blog_security.domain.dto.LoginDto;
import br.com.backend.blog_security.domain.dto.UserRequiredDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserRequiredDto userRequiredDto) {
        String  message = authService.signup(userRequiredDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginDto loginDto){

        try{
            TokenResponse tokenResponse = authService.login(loginDto);

            return ResponseEntity.ok(tokenResponse);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
