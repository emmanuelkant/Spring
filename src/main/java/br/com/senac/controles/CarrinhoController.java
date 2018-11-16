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
		Pedido newOrder = new Pedido();
		List<Item> cart = (List<Item>) session.getAttribute("cart");
		Set<ItemPedido> orderItem = new HashSet<ItemPedido>();
		
		Set<Curso> courses = new HashSet<Curso>();
		
		
		
		for (Item item : cart) {
			item.setCurso(cursoService.buscar(item.getCurso().getId()));
			courses.add(item.getCurso());
			orderItem.add(new ItemPedido(newOrder, item.getCurso(), 0.0D, item.getQuantidade(), item.getCurso().getPreco()));
		}
		
		for (Curso c : courses) {
			if (c.getItens().isEmpty()) {
				c.setItens(orderItem);
			} else {
				for (ItemPedido ip : orderItem) {
					c.getItens().add(ip);				
				}				
			}
		}
		
		newOrder.setItens(orderItem);
		
		Aluno client = (Aluno) session.getAttribute("user");
		client = alunoService.buscar(client.getId());
		newOrder.setAluno(client);
		newOrder.setEnderecoDeEntrega(client.getEnderecos().get(0));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		newOrder.setDataPedido(new Date());
		
		// Marretando por enquanto
		Pagamento pag = new PagamentoComBoleto(null, StatusPagamento.PENDENTE, newOrder, sdf.parse("30/06/2018 00:00"),
				sdf.parse("29/06/2018 00:00"));
		
		newOrder.setPagamento(pag);
		
		newOrder.setId(null);
		
		
		itemPedidoService.inserirVarios(orderItem);
		pagamentoService.inserir(pag);
		pedidoService.inserir(newOrder);
		


		return "redirect:/indexCarrinho";
	}

}
