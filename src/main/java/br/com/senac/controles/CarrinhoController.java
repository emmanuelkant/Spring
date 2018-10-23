package br.com.senac.controles;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Item;
import br.com.senac.servicos.CursoService;
import javassist.tools.rmi.ObjectNotFoundException;

@Controller
public class CarrinhoController {

	@Autowired
	private CursoService cursoService;

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
		for(int i = 0; i < listaCarrinho.size(); i++) {
			if(listaCarrinho.get(i).getCurso().getId() == id) {
				return i;
			}
		}
		return -1;
	}

	@GetMapping("/indexCarrinho")
	public ModelAndView index() {
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
	
}
