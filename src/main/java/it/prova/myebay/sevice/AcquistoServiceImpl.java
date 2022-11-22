package it.prova.myebay.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.repository.acquisto.AcquistoRepositorry;

@Service
public class AcquistoServiceImpl implements AcquistoService {

	@Autowired
	private AcquistoRepositorry acquistoRepositorry;

	@Override
	@Transactional(readOnly = true)
	public Acquisto findById(Long id) {
		return acquistoRepositorry.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void insert(Acquisto acquisto) {
		acquistoRepositorry.save(acquisto);
	}

	@Override
	@Transactional
	public void update(Acquisto acquisto) {
		acquistoRepositorry.save(acquisto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		acquistoRepositorry.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Acquisto> listAll() {
		return (List<Acquisto>) acquistoRepositorry.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Acquisto> acquistiDellUtente(Long id) {
		// TODO Auto-generated method stub
		return acquistoRepositorry.findByUtenteAcquirente_Id(id);
	}

}
