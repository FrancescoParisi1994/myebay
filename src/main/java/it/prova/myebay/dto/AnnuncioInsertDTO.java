package it.prova.myebay.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;

public class AnnuncioInsertDTO {

	private Long id;
	@NotNull(message = "{annuncio.testoAnnuncio.notnull}")
	private String testoAnnuncio;
	@NotNull(message = "{annuncio.prezzo.notnull}")
	@Min(1)
	private Integer prezzo;
	private Set<Categoria> categorie = new HashSet<>();

	public AnnuncioInsertDTO(Long id, String testoAnnuncio,
			@NotNull(message = "{annuncio.prezzo.notnull}") @Min(1) Integer prezzo, Set<Categoria> categorie) {
		super();
		this.id = id;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.categorie = categorie;
	}

	public AnnuncioInsertDTO() {
		super();
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

	public Set<Categoria> getCategorie() {
		return categorie;
	}

	public void setCategorie(Set<Categoria> categorie) {
		this.categorie = categorie;
	}

	public Annuncio buildAnnuncioModel() {
		Annuncio result = new Annuncio(this.id, this.testoAnnuncio, this.prezzo, this.categorie);

		return result;
	}

	public static AnnuncioInsertDTO buildAnnuncioDTOFromModel(Annuncio annuncio) {
		AnnuncioInsertDTO result = new AnnuncioInsertDTO(annuncio.getId(), annuncio.getTestoAnnuncio(),
				annuncio.getPrezzo(), annuncio.getCategorie());

		return result;
	}

}
