package it.prova.myebay.repository.acquisto;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import it.prova.myebay.model.Acquisto;

public interface AcquistoRepositorry extends CrudRepository<Acquisto, Long>,JpaSpecificationExecutor<Acquisto>{

	@EntityGraph(attributePaths = {"utenteAcquirente"})
	public List<Acquisto> findByUtenteAcquirente_Id(Long id);
}
