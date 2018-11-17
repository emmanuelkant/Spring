package br.com.senac.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Pagamento;
import br.com.senac.repositorio.PagamentoRepositorio;

@Service
public class PagamentoService {

	@Autowired
	private PagamentoRepositorio pagamentoPedido;

	public Pagamento inserir(Pagamento objPagamento) {
		return pagamentoPedido.save(objPagamento);
	}

}
