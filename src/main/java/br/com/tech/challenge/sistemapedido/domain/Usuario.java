package br.com.tech.challenge.sistemapedido.domain;

import java.util.Set;

public class Usuario {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private Set<Papel> papeis;

    public Usuario(String nome, String cpf, String email, String senha, Set<Papel> papeis) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.papeis = papeis;
    }

    public Usuario(Long id, String nome, String cpf, String email, String senha, Set<Papel> papeis) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.papeis = papeis;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Set<Papel> getPapeis() {
        return papeis;
    }
}
