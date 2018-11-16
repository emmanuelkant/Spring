package br.com.senac.controles;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Aluno;
import br.com.senac.dominio.Endereco;
import br.com.senac.servicos.EnderecoService;
import br.com.senac.servicos.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@PostMapping("/validar")
	public String login(Aluno aluno, HttpSession session) {
		Aluno foundedAluno = loginService.login(aluno);
		if (foundedAluno != null) {
			List<Endereco> o = enderecoService.buscar(foundedAluno);
			session.setAttribute("user", foundedAluno);
			return "redirect:/menu";
		}
		return "404.html";
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/paginaLogin");
		mv.addObject("usuario", new Aluno());
		return mv;
	}
}
