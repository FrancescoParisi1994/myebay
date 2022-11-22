package it.prova.myebay.sevice;

import java.util.List;

import it.prova.myebay.model.Acquisto;

public interface AcquistoService {

	public List<Acquisto> listAll();
	
	public Acquisto findById(Long id);
	
	public void insert(Acquisto acquisto);
	
	public void update(Acquisto acquisto);
	
	public void delete(Long id);
	
	public List<Acquisto> acquistiDellUtente(Long id);
}
