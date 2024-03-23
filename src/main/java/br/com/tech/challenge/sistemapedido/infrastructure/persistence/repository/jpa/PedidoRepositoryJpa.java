package br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa;

import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PedidoRepositoryJpa extends JpaRepository<PedidoModel, Long> {

    @Query("SELECT p FROM PedidoModel p WHERE p.status IN (:statusList) ORDER BY p.status desc, p.dataCriacao asc")
    List<PedidoModel> findAllByStatus(@Param("statusList") List<StatusPedido> statusList);
}
