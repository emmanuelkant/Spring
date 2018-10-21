package br.com.senac.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Curso;
import br.com.senac.repositorio.CursoRepositorio;
import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CursoService {

	@Autowired
	private CursoRepositorio repoCurso;
	
	public Curso buscar(Integer id) throws ObjectNotFoundException {
		Optional<Curso> objCurso = repoCurso.findById(id);
		return objCurso.orElseThrow(() -> new ObjectNotFoundException(
				"Categoria não encotrada! Id: " + id + ",  Tipo: " + Curso.class.getName()));
	}
	
	public Curso inserir(Curso objCurso) {
		objCurso.setId(null);
		return repoCurso.save(objCurso);
	}
	
	public Curso alterar(Curso objCurso) throws ObjectNotFoundException {
		Curso obCursoEncontrado = buscar(objCurso.getId());
		obCursoEncontrado.setNome(objCurso.getNome());
		obCursoEncontrado.setDescricao(objCurso.getDescricao());
		obCursoEncontrado.setCategorias(objCurso.getCategorias());
		return repoCurso.save(obCursoEncontrado);
	}
	
	public void excluir(Integer id) {
		repoCurso.deleteById(id);
	}
	
	public List<Curso> listaCursos() {
		return repoCurso.findAll();
	}
	
}
