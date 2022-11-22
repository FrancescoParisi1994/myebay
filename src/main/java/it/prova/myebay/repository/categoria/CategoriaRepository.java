package it.prova.myebay.repository.categoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import it.prova.myebay.model.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long>, JpaSpecificationExecutor<Categoria> {

	public List<Categoria> findByDescrizioneIgnoreCaseContainingOrCodiceIgnoreCaseContainingOrderByDescrizioneAsc(
			String descrizione, String codice);
}
