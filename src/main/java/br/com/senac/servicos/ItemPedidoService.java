package br.com.senac.servicos;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.ItemPedido;
import br.com.senac.repositorio.ItemPedidoRepositorio;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepositorio itemPedidoRepo;
	
	public ItemPedido inserir(ItemPedido objItemPedido) {
		return itemPedidoRepo.save(objItemPedido);
	}
	
	public Set<ItemPedido> inserirVarios(Set<ItemPedido> objItemPedido) {
		Set<ItemPedido> toReturn = new HashSet<ItemPedido>();
		for (ItemPedido ip : objItemPedido) {
			toReturn.add(inserir(ip));			
		}
		return toReturn;
		
	}
}
