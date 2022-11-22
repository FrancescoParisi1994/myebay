package it.prova.myebay.sevice;

import java.util.List;

import it.prova.myebay.model.Categoria;

public interface CategoriaService {

	public List<Categoria> findAll();

	public Categoria findById(Long id);

	public void insert(Categoria acquisto);

	public void update(Categoria acquisto);

	public void delete(Long id);
	
	public List<Categoria> findByDescrizioneECodice(String term);
}
