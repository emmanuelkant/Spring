package br.com.senac.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senac.dominio.Pedido;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Integer> {

}
