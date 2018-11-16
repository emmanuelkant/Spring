package br.com.senac.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Aluno;
import br.com.senac.repositorio.AlunoRepositorio;
import br.com.senac.repositorio.EnderecoRepositorio;
import br.com.senac.servicos.exception.ObjectNotFoundException;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepositorio repoAluno;

	public Aluno buscar(Integer id) throws ObjectNotFoundException {
		Optional<Aluno> objAluno = repoAluno.findById(id);
		return objAluno.orElseThrow(() -> new ObjectNotFoundException(
				"Categoria não encotrada! Id: " + id + ",  Tipo: " + Aluno.class.getName()));
	}
	
	public Aluno inserir(Aluno objAluno) {
		objAluno.setId(null);
		return repoAluno.save(objAluno);
	}
	
	public Aluno alterar(Aluno objAluno) throws ObjectNotFoundException {
		Aluno objAlunoEncontrado = buscar(objAluno.getId());
		objAlunoEncontrado.setNome(objAluno.getNome());
		return repoAluno.save(objAlunoEncontrado);
	}
	
	public void excluir(Integer id) {
		repoAluno.deleteById(id);
	}
	
	public List<Aluno> listaAlunos() {
		return repoAluno.findAll();
	}
	
}
