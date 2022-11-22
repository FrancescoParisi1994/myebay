package it.prova.myebay.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Utente;

public class AnnuncioDTO {

	private Long id;

	private String testoAnnuncio;
	@NotNull(message = "{annuncio.prezzo.notnull}")
	@Min(1)
	private Integer prezzo;
	@NotNull(message = "{annuncio.data.notnull}")
	private Date data;
	@NotNull(message = "{annuncio.aperto.notnull}")
	private boolean aperto;
	@NotNull(message = "{annuncio.utenteInserimento.notempty}")
	private Utente utenteInserimento;

	private Set<Categoria> categorie = new HashSet<>();

	public AnnuncioDTO() {
		super();
	}

	public AnnuncioDTO(Long id, String testoAnnuncio, Integer prezzo, Date data, boolean aperto,
			Utente utenteInserimento, Set<Categoria> categorie) {
		super();
		this.id = id;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.data = data;
		this.aperto = aperto;
		this.utenteInserimento = utenteInserimento;
		this.categorie = categorie;
	}

	public AnnuncioDTO(Long id, Integer prezzo, Date data, boolean aperto, Utente utenteInserimento,
			Set<Categoria> categorie) {
		super();
		this.id = id;
		this.prezzo = prezzo;
		this.data = data;
		this.aperto = aperto;
		this.utenteInserimento = utenteInserimento;
		this.categorie = categorie;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestoAnnuncio() {
		return testoAnnuncio;
	}

	public void setTestoAnnuncio(String testoAnnuncio) {
		this.testoAnnuncio = testoAnnuncio;
	}

	public Integer getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isAperto() {
		return aperto;
	}

	public void setAperto(boolean aperto) {
		this.aperto = aperto;
	}

	public Utente getUtenteInserimento() {
		return utenteInserimento;
	}

	public void setUtenteInserimento(Utente utenteInserimento) {
		this.utenteInserimento = utenteInserimento;
	}

	public Set<Categoria> getCategorie() {
		return categorie;
	}

	public void setCategorie(Set<Categoria> categorie) {
		this.categorie = categorie;
	}

	public Annuncio buildAnnuncioModel() {
		Annuncio result = new Annuncio(this.id, this.testoAnnuncio, this.prezzo, this.data, this.aperto,this.utenteInserimento, this.categorie);
		
		return result;
	}

	public static AnnuncioDTO buildAnnuncioDTOFromModel(Annuncio annuncio) {
		AnnuncioDTO result = new AnnuncioDTO(annuncio.getId(), annuncio.getTestoAnnuncio(), annuncio.getPrezzo(),
				annuncio.getData(), annuncio.isAperto(), annuncio.getUtenteInserimento(), annuncio.getCategorie());

		return result;
	}

	public static List<AnnuncioDTO> createAnnuncioDTOListFromModelList(List<Annuncio> annunci) {
		return annunci.stream().map(i -> {
			return AnnuncioDTO.buildAnnuncioDTOFromModel(i);
		}).collect(Collectors.toList());
	}
}
