package br.com.senac.controles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Aluno;

@Controller
public class MenuController {
	
	@GetMapping("/menu")
	public ModelAndView menu() {
		ModelAndView mv = new ModelAndView("/paginaMenu");
		
		return mv;
	}
	
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/paginaLogin");
		mv.addObject("usuario", new Aluno());
		return mv;
	}
	
	
}
