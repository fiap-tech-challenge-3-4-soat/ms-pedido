package br.com.tech.challenge.sistemapedido.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fila_restaurante")
public class FilaRestauranteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private PedidoModel pedido;
    @JoinColumn(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
}
