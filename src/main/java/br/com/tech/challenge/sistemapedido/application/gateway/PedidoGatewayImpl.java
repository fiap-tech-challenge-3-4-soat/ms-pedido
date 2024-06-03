package br.com.tech.challenge.sistemapedido.application.gateway;

import br.com.tech.challenge.sistemapedido.application.events.AlteracaoStatusPedidoEventPublisher;
import br.com.tech.challenge.sistemapedido.application.events.PedidoPagoEventPublisher;
import br.com.tech.challenge.sistemapedido.application.repository.FilaRepository;
import br.com.tech.challenge.sistemapedido.application.repository.PedidoRepository;
import br.com.tech.challenge.sistemapedido.domain.Pedido;
import br.com.tech.challenge.sistemapedido.domain.event.AlteracaoStatusPedidoEvent;
import br.com.tech.challenge.sistemapedido.domain.event.PedidoPagoEvent;
import br.com.tech.challenge.sistemapedido.usecase.gateway.PedidoGateway;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoGatewayImpl implements PedidoGateway {
    private final PedidoRepository pedidoRepository;
    private final FilaRepository filaRepository;
    private final PedidoPagoEventPublisher pedidoPagoPublisher;
    private final AlteracaoStatusPedidoEventPublisher alteracaoStatusPedidoEventPublisher;


    public PedidoGatewayImpl(PedidoRepository pedidoRepository,
                             FilaRepository filaRepository,
                             PedidoPagoEventPublisher pedidoPagoPublisher,
                             AlteracaoStatusPedidoEventPublisher alteracaoStatusPedidoEventPublisher) {
        this.pedidoRepository = pedidoRepository;
        this.filaRepository = filaRepository;
        this.pedidoPagoPublisher = pedidoPagoPublisher;
        this.alteracaoStatusPedidoEventPublisher = alteracaoStatusPedidoEventPublisher;
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public void pagar(Pedido pedido) {
        this.salvar(pedido);
        pedidoPagoPublisher.publicar(new PedidoPagoEvent(pedido));
    }

    @Override
    public void alterarStatus(Pedido pedido) {
        pedidoRepository.save(pedido);

        alteracaoStatusPedidoEventPublisher.publicar(new AlteracaoStatusPedidoEvent(pedido));
    }

    @Override
    public List<Pedido> buscarFilaRestaurante() {
        return filaRepository.buscarFilaRestaurante();
    }

    @Override
    public List<Pedido> buscarFilaCliente() {
        return filaRepository.buscarFilaCliente();
    }
}
