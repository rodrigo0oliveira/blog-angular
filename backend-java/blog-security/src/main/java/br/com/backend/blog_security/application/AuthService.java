package br.com.backend.blog_security.application;

import br.com.backend.blog_security.domain.Role;
import br.com.backend.blog_security.domain.TokenResponse;
import br.com.backend.blog_security.domain.User;
import br.com.backend.blog_security.domain.dto.LoginDto;
import br.com.backend.blog_security.domain.dto.UserRequiredDto;
import br.com.backend.blog_security.infra.repository.RoleRepository;
import br.com.backend.blog_security.infra.repository.UserRepository;
import br.com.backend.blog_security.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider provider;

    public String signup(UserRequiredDto userRequiredDto) {

        Role role = roleRepository.findByName("ROLE_USER");

        User user = User.builder()
                .username(userRequiredDto.userName())
                .email(userRequiredDto.email())
                .password(passwordEncoder.encode(userRequiredDto.password()))
                .roles(Collections.singletonList(role))
                .build();

        userRepository.save(user);

        return "Conta criada com sucesso";
    }

    public TokenResponse login(LoginDto login) throws Exception {
        log.info("entrou no login");
        try {
            log.info("tentanto criar autenticacao com "+login.userName()+login.password());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.userName(), login.password()));

            log.info("criou objeto e vai gerar token");
            return provider.generateToken(authentication);
        }
        catch (AuthenticationException e) {
            throw new Exception("Credenciais inválidas : "+e.getMessage());
        }
        catch (Exception e) {
            throw new Exception("Erro ao autenticar: "+e.getMessage());
        }
    }




}
