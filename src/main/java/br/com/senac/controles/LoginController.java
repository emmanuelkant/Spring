package br.com.senac.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Aluno;
import br.com.senac.servicos.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping("/validar")
	public String login(Aluno aluno) {
		boolean decision = loginService.login(aluno);
		if (decision)
			return "redirect:/menu";
		return "404.html";
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/paginaLogin");
		mv.addObject("usuario", new Aluno());
		return mv;
	}
}
