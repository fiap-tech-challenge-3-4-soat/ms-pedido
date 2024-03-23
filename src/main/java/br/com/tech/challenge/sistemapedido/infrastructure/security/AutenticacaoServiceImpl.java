package br.com.tech.challenge.sistemapedido.infrastructure.security;

import br.com.tech.challenge.sistemapedido.application.repository.UsuarioRepository;
import br.com.tech.challenge.sistemapedido.domain.Usuario;
import br.com.tech.challenge.sistemapedido.application.service.AutenticacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AutenticacaoServiceImpl implements AutenticacaoService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;


    @Override
    public String autenticar(String cpf, String senha) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                cpf, senha));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        var usuarioComSenhaEncriptada = new Usuario(usuario.getNome(),
                usuario.getCpf(), usuario.getEmail(), passwordEncoder.encode(usuario.getSenha()), usuario.getPapeis());
        return usuarioRepository.save(usuarioComSenhaEncriptada);
    }
}
