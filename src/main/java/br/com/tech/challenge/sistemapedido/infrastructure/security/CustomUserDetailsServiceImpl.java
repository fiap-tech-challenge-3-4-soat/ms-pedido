package br.com.tech.challenge.sistemapedido.infrastructure.security;

import br.com.tech.challenge.sistemapedido.application.repository.UsuarioRepository;
import br.com.tech.challenge.sistemapedido.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCpfOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

        Set<GrantedAuthority> authorities = usuario
                .getPapeis()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNome())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(usuario.getCpf(),
                usuario.getSenha(),
                authorities);
    }
}
