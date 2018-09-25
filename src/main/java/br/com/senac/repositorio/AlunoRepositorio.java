package br.com.senac.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senac.dominio.Aluno;

@Repository
public interface AlunoRepositorio extends JpaRepository<Aluno, Integer> {
	
	Aluno findByEmailAndNome(String email, String nome);
	Aluno findByNome(String nome);
	
	// @Query("select a from Aluno a where a.nome = ?1")
	// Aluno findByNome(String nome);

}
