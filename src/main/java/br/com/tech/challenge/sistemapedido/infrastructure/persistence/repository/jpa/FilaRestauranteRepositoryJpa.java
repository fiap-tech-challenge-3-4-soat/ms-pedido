package br.com.tech.challenge.sistemapedido.infrastructure.persistence.repository.jpa;

import br.com.tech.challenge.sistemapedido.domain.StatusPedido;
import br.com.tech.challenge.sistemapedido.infrastructure.persistence.model.FilaRestauranteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilaRestauranteRepositoryJpa extends JpaRepository<FilaRestauranteModel, Long> {

    @Query("SELECT f FROM FilaRestauranteModel f WHERE f.pedido.status IN (:status) ORDER BY f.pedido.status desc, f.pedido.dataCriacao asc")
    List<FilaRestauranteModel> findAllByPedidoStatusIn(@Param("status") List<StatusPedido> status);
}
