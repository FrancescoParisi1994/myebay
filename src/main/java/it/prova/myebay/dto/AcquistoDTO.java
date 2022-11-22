package it.prova.myebay.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Utente;

public class AcquistoDTO {
	private Long id;
	private String descrizione;
	private Date data;
	private Integer prezzo;
	@NotNull
	private Utente utenteAcquirente;

	public AcquistoDTO(Long id, String descrizione, Date data, Integer prezzo, Utente utenteAcquirente) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.data = data;
		this.prezzo = prezzo;
		this.utenteAcquirente = utenteAcquirente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}

	public Utente getUtenteAcquirente() {
		return utenteAcquirente;
	}

	public void setUtenteAcquirente(Utente utenteAcquirente) {
		this.utenteAcquirente = utenteAcquirente;
	}

	public Acquisto buildAnnuncioModel() {
		Acquisto result = new Acquisto(this.id, this.descrizione, this.data, this.prezzo, this.utenteAcquirente);

		return result;
	}

	public static AcquistoDTO buildAcquistoDTOFromModel(Acquisto acquisto) {
		AcquistoDTO result = new AcquistoDTO(acquisto.getId(), acquisto.getDescrizione(), acquisto.getData(),
				acquisto.getPrezzo(), acquisto.getUtenteAcquirente());

		return result;
	}

	public static List<AcquistoDTO> createAcquistoDTOListFromModelList(List<Acquisto> acquisti) {
		return acquisti.stream().map(i -> {
			return AcquistoDTO.buildAcquistoDTOFromModel(i);
		}).collect(Collectors.toList());
	}

}
