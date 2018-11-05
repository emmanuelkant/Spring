package br.com.senac.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.senac.dominio.Aluno;
import br.com.senac.servicos.AlunoService;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/alunos")
public class AlunoResource {

	@Autowired
	private AlunoService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public String testar() {
		return "Está Funcionando";
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Aluno> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Aluno objAluno = service.buscar(id);
		return ResponseEntity.ok().body(objAluno);
	}
	
}
