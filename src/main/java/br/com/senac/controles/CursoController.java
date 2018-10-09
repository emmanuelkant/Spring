package br.com.senac.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Categoria;
import br.com.senac.dominio.Curso;
import br.com.senac.servicos.CategoriaService;
import br.com.senac.servicos.CursoService;
import javassist.tools.rmi.ObjectNotFoundException;

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
		Curso insertedCourse = cursoService.inserir(curso);
		if (insertedCourse != null)
			return listaCursos();
		return listaCursos();
	}
	
	@GetMapping("/excluirCurso/{id}")
	public ModelAndView excluir(@PathVariable("id") Integer idCurso) {
		cursoService.excluir(idCurso);
		return listaCursos();
	}
	
	@GetMapping("/alterarCurso/{id}")
	public ModelAndView alterar(@PathVariable("id") Integer idCurso) throws ObjectNotFoundException {
		ModelAndView mv = new ModelAndView("/paginaAlterarCurso");
		mv.addObject("curso", cursoService.buscar(idCurso));
		mv.addObject("categorias", catService.listaCategorias());
		return mv;
	}
	
	@PostMapping("/alterarCurso")
	public ModelAndView alterar( Curso objCurso) throws ObjectNotFoundException {
		cursoService.alterar(objCurso);
		return listaCursos();
	}
	
	
}
