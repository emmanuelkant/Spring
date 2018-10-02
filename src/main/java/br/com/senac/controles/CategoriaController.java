package br.com.senac.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Categoria;
import br.com.senac.servicos.CategoriaService;

@Controller
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/listarCategorias")
	public ModelAndView listaCategorias() {
		ModelAndView mv = new ModelAndView("/paginaCategorias");
		mv.addObject("categorias", categoriaService.listaCategorias());
		return mv;
	}
	
	@GetMapping("/formInserirCategoria")
	public ModelAndView inserir() {
		ModelAndView mv = new ModelAndView("/paginaInserir");
		mv.addObject("categorias", categoriaService.listaCategorias());
		return mv;
		
	}
	
	@PostMapping("/inserirCategoria")
	public ModelAndView inserir(Categoria categoria) {
		ModelAndView mv = new ModelAndView("/paginaInserir");
		mv.addObject("categorias", categoriaService.inserir(categoria));
		return listaCategorias();
	}
	
	
}
