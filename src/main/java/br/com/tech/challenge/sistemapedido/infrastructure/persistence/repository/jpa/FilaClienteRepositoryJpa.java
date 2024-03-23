package br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa;

import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.FilaClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilaClienteRepositoryJpa extends JpaRepository<FilaClienteModel, Long> {
    @Query("SELECT f FROM FilaClienteModel f WHERE f.pedido.status IN (:status) ORDER BY f.pedido.status desc, f.pedido.dataCriacao asc")
    List<FilaClienteModel> findAllByPedidoStatusIn(@Param("status") List<StatusPedido> status);
}
