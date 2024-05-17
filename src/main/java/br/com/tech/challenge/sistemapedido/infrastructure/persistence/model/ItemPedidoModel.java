package br.com.tech.challenge.sistemapedido.infrastructure.persistence.model;

import br.com.tech.challenge.sistemapedido.domain.Categoria;
import br.com.tech.challenge.sistemapedido.domain.vo.Descricao;
import br.com.tech.challenge.sistemapedido.domain.vo.Nome;
import br.com.tech.challenge.sistemapedido.domain.vo.Preco;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    private Long idProduto;
    private String nome;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidade;
    private String observacao;
    @ManyToOne
    @Setter
    @JoinColumn(name = "id_pedido", nullable = false)
    private PedidoModel pedido;
}
