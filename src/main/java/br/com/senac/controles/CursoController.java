package br.com.senac.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Categoria;
import br.com.senac.dominio.Curso;
import br.com.senac.servicos.CategoriaService;
import br.com.senac.servicos.CursoService;

@Controller
public class CursoController {

	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private CategoriaService catService;
	
	@GetMapping("/listarCursos")
	public ModelAndView listaCursos() {
		ModelAndView mv = new ModelAndView("/paginaListarCursos");
		mv.addObject("cursos", cursoService.listaCursos());
		return mv;
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/index");
		mv.addObject("cursos", cursoService.listaCursos());
		return mv;
	}
	
	
	@GetMapping("/formInserirCurso")
	public ModelAndView inserir() {
		ModelAndView mv = new ModelAndView("/paginaFormCurso");
		mv.addObject("curso", new Curso());
		mv.addObject("categorias", catService.listaCategorias());
		return mv;
	}
	
	@PostMapping("/inserirCurso")
	public ModelAndView inserir(Curso curso) {
		ModelAndView mv = new ModelAndView("/paginaInserir");
		Curso insertedCourse = cursoService.inserir(curso);
		if (insertedCourse != null)
			return listaCursos();
		return listaCursos();
	}
	
	
}
