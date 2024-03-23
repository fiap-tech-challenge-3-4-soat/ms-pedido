package br.com.tech.challenge.sistemapedido.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "itens_pedido")
public class ItemPedidoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private ProdutoModel produto;
    @ManyToOne
    @Setter
    @JoinColumn(name = "id_pedido", nullable = false)
    private PedidoModel pedido;
}
