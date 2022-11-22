package it.prova.myebay.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;

public class CategoriaDTO {

	private Long id;
	private String descrizione;
	private String codice;
	private Set<Annuncio> annunci = new HashSet<>();

	public CategoriaDTO(Long id, String descrizione, String codice) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.codice = codice;
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

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Set<Annuncio> getAnnunci() {
		return annunci;
	}

	public void setAnnunci(Set<Annuncio> annunci) {
		this.annunci = annunci;
	}

	public Categoria buildCategoriaModel() {
		Categoria result = new Categoria(this.id, this.descrizione, this.codice);

		return result;
	}

	public static CategoriaDTO buildCategoriaDTOFromModel(Categoria categoria) {
		CategoriaDTO result = new CategoriaDTO(categoria.getId(), categoria.getCodice(), categoria.getDescrizione());

		return result;
	}

	public static List<CategoriaDTO> createCategoriaDTOListFromModelList(List<Categoria> categorie) {
		return categorie.stream().map(i -> {
			return CategoriaDTO.buildCategoriaDTOFromModel(i);
		}).collect(Collectors.toList());
	}

}
