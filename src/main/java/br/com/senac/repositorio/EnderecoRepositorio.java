package br.com.senac.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senac.dominio.Aluno;
import br.com.senac.dominio.Endereco;

@Repository
public interface EnderecoRepositorio extends JpaRepository<Endereco, Integer> {

	List<Endereco> findByAluno(Aluno idAluno);

}
