package br.com.senac.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Categoria;
import br.com.senac.repositorio.CategoriaRepositorio;
import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepositorio repoCat;
	
	public Categoria buscar(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> objCategoria = repoCat.findById(id);
		return objCategoria.orElseThrow(() -> new ObjectNotFoundException(
				"Categoria não encotrada! Id: " + id + ",  Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria inserir(Categoria objCategoria) {
		objCategoria.setId(null);
		return repoCat.save(objCategoria);
	}
	
	public Categoria alterar(Categoria objCategoria) throws ObjectNotFoundException {
		Categoria obCategoriaEncontrado = buscar(objCategoria.getId());
		obCategoriaEncontrado.setNome(objCategoria.getNome());
		return repoCat.save(obCategoriaEncontrado);
	}
	
	public void excluir(Integer id) {
		repoCat.deleteById(id);
	}
	
	public List<Categoria> listaCategorias() {
		return repoCat.findAll();
	}
	
}
