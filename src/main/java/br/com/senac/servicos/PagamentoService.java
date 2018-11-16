package br.com.senac.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Pagamento;
import br.com.senac.repositorio.PagamentoRepositorio;
import br.com.senac.repositorio.PedidoRepositorio;
import br.com.senac.servicos.exception.ObjectNotFoundException;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepositorio pagamentoPedido;

	public Pagamento inserir(Pagamento objPagamento) {
		objPagamento.setId(null);
		return pagamentoPedido.save(objPagamento);
	}

}
