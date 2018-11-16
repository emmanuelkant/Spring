package br.com.senac.repositorio;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senac.dominio.Aluno;

@Repository
public interface AlunoRepositorio extends JpaRepository<Aluno, Integer> {
	
	Aluno findByEmailAndNome(String email, String nome);
	Aluno findByNome(String nome);
//	
//	@Query("SELECT telefones FROM aluno a INNER JOIN telefone t ON a.id = t.aluno_id WHERE t.aluno_id = ?1")
//	List<String> findByAlunoId(Integer idAluno);
	
	// @Query("select a from Aluno a where a.nome = ?1")
	// Aluno findByNome(String nome);

}
