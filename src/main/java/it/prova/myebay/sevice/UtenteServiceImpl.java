package it.prova.myebay.sevice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.dto.UtenteResetPasswordDTO;
import it.prova.myebay.model.Ruolo;
import it.prova.myebay.model.StatoUtente;
import it.prova.myebay.model.Utente;
import it.prova.myebay.repository.utente.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<Utente> listAllUtenti() {
		return (List<Utente>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Utente caricaSingoloUtente(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Utente caricaSingoloUtenteConRuoli(Long id) {
		return repository.findByIdConRuoli(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Utente utenteInstance) {
		// deve aggiornare solo nome, cognome, username, ruoli
		Utente utenteReloaded = repository.findById(utenteInstance.getId()).orElse(null);
		if (utenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		utenteReloaded.setNome(utenteInstance.getNome());
		utenteReloaded.setCognome(utenteInstance.getCognome());
		utenteReloaded.setUsername(utenteInstance.getUsername());
		utenteReloaded.setRuoli(utenteInstance.getRuoli());
		repository.save(utenteReloaded);
	}

	@Override
	@Transactional
	public void inserisciNuovo(Utente utenteInstance) {
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		utenteInstance.setDateCreated(new Date());
		repository.save(utenteInstance);
	}

	@Transactional
	public void rimuovi(Long idToDelete) {
		repository.deleteById(idToDelete);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Utente> findByExample(Utente example) {
		Specification<Utente> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getNome()))
				predicates.add(cb.like(cb.upper(root.get("nome")), "%" + example.getNome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCognome()))
				predicates.add(cb.like(cb.upper(root.get("cognome")), "%" + example.getCognome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getUsername()))
				predicates
						.add(cb.like(cb.upper(root.get("username")), "%" + example.getPassword().toUpperCase() + "%"));

			if (example.getDateCreated() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dateCreated"), example.getDateCreated()));

			if (example.getStato() != null)
				predicates.add(cb.equal(root.get("stato"), example.getStato()));

			if (example.getRuoli() != null) {
				for (Ruolo item : example.getRuoli()) {
					predicates.add(cb.equal(root.get("ruoli"), item));
				}
			}

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		return repository.findAll(specificationCriteria);

	}

	@Transactional(readOnly = true)
	public Utente eseguiAccesso(String username, String password) {
		return repository.findByUsernameAndPasswordAndStato(username, password, StatoUtente.ATTIVO);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	@Transactional
	public void changeUserAbilitation(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if (utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");

		if (utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if (utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if (utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
	}

	@Transactional(readOnly = true)
	public Utente findByUsername(String username) {
		return repository.findByUsername(username).orElse(null);

	}

	@Transactional(readOnly = true)
	public List<Utente> findByUsernameNomeCognome(String term) {
		return repository
				.findAllByUsernameIgnoreCaseContainingOrNomeIgnoreCaseContainingOrCognomeIgnoreCaseContainingOrderByNomeAsc(
						term, term, term);
	}

	@Transactional
	public void cambioPassword(UtenteResetPasswordDTO utenteResetPasswordDTO,String username) {
		Utente utente=repository.findByUsername(username).orElse(null);
		String vecchiaPassword=utenteResetPasswordDTO.getVecchiaPassword();
		if (!passwordEncoder.matches(vecchiaPassword, utente.getPassword())) {
			throw new RuntimeException("Le password non sono uguali");
		}
		utente.setPassword(passwordEncoder.encode(utenteResetPasswordDTO.getNuovaPassword()));
		repository.save(utente);
	}
	
}
