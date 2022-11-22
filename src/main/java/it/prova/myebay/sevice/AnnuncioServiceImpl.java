package it.prova.myebay.sevice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Utente;
import it.prova.myebay.repository.annuncio.AnnuncioRepository;

@Service
public class AnnuncioServiceImpl implements AnnuncioService {

	@Autowired
	private AnnuncioRepository repository;
	@Autowired
	private UtenteService utenteService;
	@Autowired
	private AcquistoService acquistoService;

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> findAll() {
		// TODO Auto-generated method stub
		return (List<Annuncio>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Annuncio findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void insert(Annuncio annuncio,String username) {
		// TODO Auto-generated method stub
		annuncio.setAperto(true);
		annuncio.setData(new Date());
		annuncio.setUtenteInserimento(utenteService.findByUsername(username));
		repository.save(annuncio);
	}

	@Override
	@Transactional
	public void update(Annuncio annuncio) {
		repository.save(annuncio);
	}
	
	@Override
	@Transactional
	public void update(Annuncio annuncio, String username) {
		annuncio.setUtenteInserimento(utenteService.findByUsername(username));
		repository.save(annuncio);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> findByExample(Annuncio annuncio) {
		Specification<Annuncio> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (annuncio.getPrezzo() != null && annuncio.getPrezzo() < 0)
				predicates.add(cb.greaterThanOrEqualTo(root.get("prezzo"), annuncio.getPrezzo()));

			if (annuncio.getData() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("data"), annuncio.getData()));

			if (annuncio.getUtenteInserimento() != null && annuncio.getUtenteInserimento().getId() != null)
				predicates.add(cb.equal(root.get("utenteInserimento"), annuncio.getUtenteInserimento().getId()));

			if (!annuncio.getCategorie().isEmpty()) {
				for (Categoria item : annuncio.getCategorie()) {
					predicates.add(cb.equal(root.get("categorie"), item));
				}
			}

			predicates.add(cb.equal(root.get("aperto"), true));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		return repository.findAll(specificationCriteria);

	}

	@Override
	@Transactional(readOnly = true)
	public Annuncio findEagerCategorie(Long id) {
		// TODO Auto-generated method stub
		return repository.findEagerCategorieAndUtenteInserimento(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> trovaAnnunciAperti() {
		// TODO Auto-generated method stub
		return repository.findByAperto(true);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> trovaAnnunciApertiDellUtente(Long id) {
		// TODO Auto-generated method stub
		return repository.findByApertoAndUtenteInserimento_Id(true, id);
	}

	@Override
	@Transactional
	public void buy(String username,Long id) {
		Utente utente=utenteService.findByUsername(username);
		Annuncio annuncio=repository.findById(id).orElse(null);
		if (annuncio.getPrezzo()>utente.getCreditoResiduo()) {
			throw new RuntimeException("utente senza soldi");
		}
		Acquisto acquisto = new Acquisto();
		acquisto.setData(new Date());
		acquisto.setDescrizione(annuncio.getTestoAnnuncio());
		acquisto.setPrezzo(annuncio.getPrezzo());
		acquisto.setUtenteAcquirente(utente);
		acquistoService.insert(acquisto);
		utente.setCreditoResiduo(utente.getCreditoResiduo()-annuncio.getPrezzo());
		utenteService.aggiorna(utente);
		annuncio.setAperto(false);
		repository.save(annuncio);
	}

}
