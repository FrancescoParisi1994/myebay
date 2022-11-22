package it.prova.myebay.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.myebay.model.Categoria;
import it.prova.myebay.repository.categoria.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	@Autowired
	private CategoriaRepository repository;

	@Override
	public List<Categoria> findAll() {
		// TODO Auto-generated method stub
		return (List<Categoria>) repository.findAll();
	}

	@Override
	public Categoria findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	public void insert(Categoria annuncio) {
		// TODO Auto-generated method stub
		repository.save(annuncio);
	}

	@Override
	public void update(Categoria annuncio) {
		// TODO Auto-generated method stub
		repository.save(annuncio);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	public List<Categoria> findByDescrizioneECodice(String term){
		return repository.findByDescrizioneIgnoreCaseContainingOrCodiceIgnoreCaseContainingOrderByDescrizioneAsc(term, term);
	}
}
