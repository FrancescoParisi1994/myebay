package it.prova.myebay.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.prova.myebay.model.Utente;
import it.prova.myebay.validation.ValidationWithPassword;

public class UtenteResetPasswordDTO {

	private Long id;

	private String username;

	private String vecchiaPassword;
	@NotBlank(message = "{password.notblank}", groups = ValidationWithPassword.class)
	@Size(min = 8, max = 15, message = "Il valore inserito deve essere lungo tra {min} e {max} caratteri")
	private String nuovaPassword;

	private String confermaPassword;

	private String nome;

	private String cognome;

	public UtenteResetPasswordDTO() {
		super();
	}

	public UtenteResetPasswordDTO(Long id, String username, String vecchiaPassword, String nuovaPassword,
			String confermaPassword, String nome, String cognome) {
		super();
		this.id = id;
		this.username = username;
		this.vecchiaPassword = vecchiaPassword;
		this.nuovaPassword = nuovaPassword;
		this.confermaPassword = confermaPassword;
		this.nome = nome;
		this.cognome = cognome;
	}

	public UtenteResetPasswordDTO(String username, String vecchiaPassword, String nome, String cognome) {
		super();
		this.username = username;
		this.vecchiaPassword = vecchiaPassword;
		this.nome = nome;
		this.cognome = cognome;
	}

	public UtenteResetPasswordDTO(String username, String nome, String cognome) {
		super();
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVecchiaPassword() {
		return vecchiaPassword;
	}

	public void setVecchiaPassword(String vecchiaPassword) {
		this.vecchiaPassword = vecchiaPassword;
	}

	public String getNuovaPassword() {
		return nuovaPassword;
	}

	public void setNuovaPassword(String nuovaPassword) {
		this.nuovaPassword = nuovaPassword;
	}

	public String getConfermaPassword() {
		return confermaPassword;
	}

	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public static UtenteResetPasswordDTO daModelADTO(Utente utente) {
		UtenteResetPasswordDTO resultDto = new UtenteResetPasswordDTO(utente.getUsername(),
				utente.getNome(), utente.getCognome());
		return resultDto;
	}

	public Utente daDTOAModel() {
		Utente result = new Utente();
		return result;
	}
}
