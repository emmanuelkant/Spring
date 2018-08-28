package br.com.senac.inicializacao;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.com.senac.dominio.Aluno;
import br.com.senac.dominio.Cidade;
import br.com.senac.dominio.Estado;
import br.com.senac.repositorio.AlunoRepositorio;
import br.com.senac.repositorio.CidadeRepositorio;
import br.com.senac.repositorio.EstadoRepositorio;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	AlunoRepositorio alunoRepositorio;

	@Autowired
	CidadeRepositorio cidadeRepositorio;

	@Autowired
	EstadoRepositorio estadoRepositorio;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		Aluno aluno = new Aluno();
		aluno.setNome("Lucas");
		aluno.setEmail("lucas@gmail.com");
		
		alunoRepositorio.save(aluno);
		
		Aluno alunoGravado = alunoRepositorio.findByEmail("lucas@gmail.com");
		
		Estado estado1 = new Estado();
		Estado estado2 = new Estado();
		estado1.setNome("Rio de Janeiro");
		estado2.setNome("São Paulo");

		Cidade cidade1 = new Cidade();
		Cidade cidade2 = new Cidade();
		Cidade cidade3 = new Cidade();
		cidade1.setNome("Angra do Reis");
		cidade2.setNome("Cabo Frio");
		cidade3.setNome("Mogi das Cruzes");

		cidade1.setEstado(estado1);
		cidade2.setEstado(estado1);
		cidade3.setEstado(estado2);

		estadoRepositorio.saveAll(Arrays.asList(estado1, estado2));

		cidadeRepositorio.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
	}

}
