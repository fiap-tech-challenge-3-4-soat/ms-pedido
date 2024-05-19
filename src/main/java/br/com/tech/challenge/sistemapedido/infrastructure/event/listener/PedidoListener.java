package br.com.tech.challenge.sistemapedido.infrastructure.event.listener;

import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import br.com.tech.challenge.sistemapedido.domain.event.AlteracaoStatusPedidoEvent;
import br.com.tech.challenge.sistemapedido.domain.event.PedidoPagoEvent;
import br.com.tech.challenge.sistemapedido.infrastructure.mapper.PedidoModelMapper;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.FilaClienteModel;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.FilaRestauranteModel;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.FilaClienteRepositoryJpa;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa.FilaRestauranteRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;

@Component
@RequiredArgsConstructor
public class PedidoListener {
    private final FilaRestauranteRepositoryJpa filaRestauranteRepository;
    private final FilaClienteRepositoryJpa filaClienteRepositoryJpa;
    private final PedidoModelMapper pedidoDataMapper;

    @EventListener
    public void adicionarNaFilaRestaurante(PedidoPagoEvent event) {
        var fila = FilaRestauranteModel.builder()
                .pedido(pedidoDataMapper.toModel(event.pedido()))
                .dataAtualizacao(LocalDateTime.now())
                .build();

        filaRestauranteRepository.save(fila);
    }

    @EventListener
    public void adicionarNaFilaCliente(AlteracaoStatusPedidoEvent event) {
        var statusFila = new LinkedList<>(Arrays.asList(StatusPedido.EM_PREPARACAO, StatusPedido.PRONTO));

        if (statusFila.contains(event.pedido().getStatus())) {
            var fila = FilaClienteModel.builder()
                    .pedido(pedidoDataMapper.toModel(event.pedido()))
                    .dataAtualizacao(LocalDateTime.now())
                    .build();

            filaClienteRepositoryJpa.save(fila);
        }
    }
}
