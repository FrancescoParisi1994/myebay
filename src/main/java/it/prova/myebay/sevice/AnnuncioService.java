package it.prova.myebay.sevice;

import java.util.List;

import it.prova.myebay.model.Annuncio;

public interface AnnuncioService {

	public List<Annuncio> findAll();

	public Annuncio findById(Long id);

	public void insert(Annuncio annuncio, String username);

	public void update(Annuncio annuncio);

	public void update(Annuncio annuncio, String username);

	public void delete(Long id);

	public List<Annuncio> findByExample(Annuncio annuncio);

	public Annuncio findEagerCategorie(Long id);

	public List<Annuncio> trovaAnnunciAperti();

	public List<Annuncio> trovaAnnunciApertiDellUtente(Long id);
}
