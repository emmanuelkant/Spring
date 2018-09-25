package br.com.senac.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.servicos.CategoriaService;
import br.com.senac.servicos.MenuService;

@Controller
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@GetMapping("/menu")
	public ModelAndView menu() {
		ModelAndView mv = new ModelAndView("/paginaMenu");
		
		return mv;
	}
	
	
}
