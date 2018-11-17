package br.com.senac.controles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Aluno;
import br.com.senac.dominio.Curso;
import br.com.senac.dominio.Item;
import br.com.senac.dominio.ItemPedido;
import br.com.senac.dominio.Pagamento;
import br.com.senac.dominio.PagamentoComBoleto;
import br.com.senac.dominio.Pedido;
import br.com.senac.dominio.enums.StatusPagamento;
import br.com.senac.servicos.AlunoService;
import br.com.senac.servicos.CursoService;
import br.com.senac.servicos.ItemPedidoService;
import br.com.senac.servicos.PagamentoService;
import br.com.senac.servicos.PedidoService;
import javassist.tools.rmi.ObjectNotFoundException;

@Controller
public class CarrinhoController {

	@Autowired
	private CursoService cursoService;

	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private ItemPedidoService itemPedidoService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PagamentoService pagamentoService;
	

	@GetMapping("/carrinho/comprar/{id}")
	public String comprar(@PathVariable("id") Integer id, HttpSession session) throws ObjectNotFoundException {
		if (session.getAttribute("cart") == null) {
			List<Item> listaCarrinho = new ArrayList<Item>();
			listaCarrinho.add(new Item(cursoService.buscar(id), 1));
			session.setAttribute("cart", listaCarrinho);
		} else {
			List<Item> listaCarrinho = (List<Item>) session.getAttribute("cart");
			int index = isExists(id, listaCarrinho);
			if (index == -1) {
				listaCarrinho.add(new Item(cursoService.buscar(id), 1));
			} else {
				int quantidade = listaCarrinho.get(index).getQuantidade() + 1;
				listaCarrinho.get(index).setQuantidade(quantidade);
			}
			session.setAttribute("cart", listaCarrinho);
		}
		return "redirect:/indexCarrinho";
	}

	private int isExists(int id, List<Item> listaCarrinho) {
		for (int i = 0; i < listaCarrinho.size(); i++) {
			if (listaCarrinho.get(i).getCurso().getId() == id) {
				return i;
			}
		}
		return -1;
	}

	@GetMapping("/indexCarrinho")
	public ModelAndView index(HttpSession session) {
		ModelAndView mv = new ModelAndView("/carrinho/index");

		return mv;
	}

	@GetMapping("/carrinho/remover/{id}")
	public String remover(@PathVariable("id") Integer id, HttpSession session) {
		List<Item> listaCarrinho = (List<Item>) session.getAttribute("cart");
		int index = isExists(id, listaCarrinho);
		listaCarrinho.remove(index);
		session.setAttribute("cart", listaCarrinho);
		return "redirect:/indexCarrinho";
	}

	@GetMapping("/closeCart")
	public String preparToCloseCart(HttpSession session) throws ParseException, ObjectNotFoundException {
		
		try {
			// Criando novo pedido.
			Pedido newOrder = new Pedido();
			
			// Buscando o cliente da sessão.
			Aluno client = (Aluno) session.getAttribute("user");
			// Resgatando o cliente e todos os seus dados.
			client = alunoService.buscar(client.getId());
			// Inserindo cliente no pedido.
			newOrder.setAluno(client);
			// Inserindo o endereço de compra para o cliente.
			newOrder.setEnderecoDeEntrega(client.getEnderecos().get(0));
			// Resgatando o carrinho de compras da sessão.
			List<Item> cart = (List<Item>) session.getAttribute("cart");
			
			Set<ItemPedido> orderItens = new HashSet<ItemPedido>();
			
			Set<Curso> courses = new HashSet<Curso>();
			// A cada iteração um (1) item.
			for (Item item : cart) {
				// Resgatando o curso e todos os seus dados.
				item.setCurso(cursoService.buscar(item.getCurso().getId()));
				// Guardando a referência de cada curso do item, ou seja, cada curso comprado. 
				courses.add(item.getCurso());
				// Inserindo um novo @{ItemPedido} na lista de @{ItemPedido}.
				orderItens.add(new ItemPedido(newOrder, item.getCurso(), 0.0D, item.getQuantidade(), item.getCurso().getPreco()));
			}
			// Cada iteração será um curso armazenado anteriormente.
			for (Curso c : courses) {
				// Verifica se o @{Curso} já tem algum @{ItemPedido}.
				if (c.getItens().isEmpty()) {
					// Se tiver só insere a lista.
					c.setItens(orderItens);
				} else {
					// Se não tiver itera para quantos @{ItemPedido}s tiverem 
					for (ItemPedido ip : orderItens) {
						// Adiciona a lista existente.
						c.getItens().add(ip);				
					}				
				}
			}
			// Insere a listade @{ItemPedido}s no pedido
			newOrder.setItens(orderItens);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			// Insere a data (atual) que foi feita a compra.
			newOrder.setDataPedido(new Date());
			
			// Marretando por enquanto o pagamento
			Pagamento pag = new PagamentoComBoleto(null, StatusPagamento.PENDENTE, newOrder, sdf.parse("30/06/2018 00:00"),
					sdf.parse("29/06/2018 00:00"));
			// Insere a forma de pagamento no pedido
			newOrder.setPagamento(pag);
			
			// A ordem importa muito na hora da inserção
			pedidoService.inserir(newOrder);
			pagamentoService.inserir(pag);
			itemPedidoService.inserirVarios(orderItens);
			// Limpando o Carrinho
			resetCart(session);
			
			return "compraSucesso.html";
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "compraFalha.html";
		}
		
	}
	
	private void resetCart(HttpSession session) {
		session.setAttribute("cart", null);
	}

}
