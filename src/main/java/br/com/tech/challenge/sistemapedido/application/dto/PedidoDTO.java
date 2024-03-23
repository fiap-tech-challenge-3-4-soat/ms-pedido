package br.com.tech.challenge.sistemapedido.application.dto;

import br.com.tech.challenge.sistemapedido.domain.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDTO(Long id,
                        StatusPedido status,
                        LocalDateTime dataCriacao,
                        LocalDateTime dataAtualizacao,
                        BigDecimal total,
                        Boolean pago,
                        String cliente,
                        List<ItemPedidoDTO> itens) {}
