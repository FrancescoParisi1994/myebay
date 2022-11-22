package it.prova.myebay.repository.annuncio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.myebay.model.Annuncio;

public interface AnnuncioRepository extends CrudRepository<Annuncio, Long>, JpaSpecificationExecutor<Annuncio> {

	@Query("from Annuncio a left join fetch a.categorie c left join fetch a.utenteInserimento u where a.id=?1")
	public Annuncio findEagerCategorieAndUtenteInserimento(Long id);

	public List<Annuncio> findByAperto(boolean input);

	public List<Annuncio> findByApertoAndUtenteInserimento_Id(boolean input, Long id);
	
	
}
