package br.com.tech.challenge.sistemapedido.domain;

public class Papel {

    private Long id;
    private String nome;

    public Papel(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Papel(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }


}
