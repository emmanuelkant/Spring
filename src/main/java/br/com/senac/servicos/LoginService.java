package br.com.senac.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Aluno;
import br.com.senac.repositorio.AlunoRepositorio;

@Service
public class LoginService {

	@Autowired
	private AlunoRepositorio alunoCat;

	public boolean login(Aluno aluno) {
		Aluno alunoEncontrado = alunoCat.findByEmailAndNome(aluno.getEmail(), aluno.getNome());
		if (alunoEncontrado == null)
			return false;
		return true;

	}

}
