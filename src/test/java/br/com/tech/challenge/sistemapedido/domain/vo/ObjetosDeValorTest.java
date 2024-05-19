package br.com.tech.challenge.sistemapedido.domain.vo;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ObjetosDeValorTest {
    @Test
    void naoDeveriaPermitirCriarPrecoComValorZero() {
        assertThrows(ConstraintViolationException.class, () ->
                new Preco(BigDecimal.ZERO));
    }

    @Test
    void naoDeveriaPermitirCriarPrecoComValorNulo() {
        assertThrows(ConstraintViolationException.class, () ->
                new Preco(null));
    }

    @Test
    void naoDeveriaPermitirCriarPrecoComValorInvalido() {
        var valor = new BigDecimal("2.33333");
        assertThrows(ConstraintViolationException.class, () ->
                new Preco(valor));
    }

    @Test
    void naoDeveriaPermitirCriarNomeEmBranco() {
        assertThrows(ConstraintViolationException.class, () ->
                new Nome(""));
    }

    @Test
    void naoDeveriaPermitirCriarNomeNulo() {
        assertThrows(ConstraintViolationException.class, () ->
                new Nome(null));
    }

    @Test
    void naoDeveriaPermitirCriarDescricaoEmBranco() {
        assertThrows(ConstraintViolationException.class, () ->
                new Descricao(""));
    }

    @Test
    void naoDeveriaPermitirCriarDescricaoNulo() {
        assertThrows(ConstraintViolationException.class, () ->
                new Descricao(null));
    }

    @Test
    void naoDeveriaCriarObservacaoComMaisde200Caracteres() {
        var observacao = "S".repeat(201);

        assertThrows(ConstraintViolationException.class, () ->
                new Observacao(observacao));
    }

    @Test
    void naoDeveriaCriarQuantidadeMenorQueZero() {
        assertThrows(ConstraintViolationException.class, () ->
                new Quantidade(-1));
    }
}