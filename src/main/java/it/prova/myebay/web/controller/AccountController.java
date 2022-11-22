package it.prova.myebay.web.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.UtenteResetPasswordDTO;
import it.prova.myebay.sevice.UtenteService;

@Controller
@RequestMapping(value = "/reset")
public class AccountController {

	@Autowired
	private UtenteService utenteService;
	
	@GetMapping
	public String resetPassword(Principal principal,Model model) {
		model.addAttribute("utente_attr", UtenteResetPasswordDTO.daModelADTO(utenteService.findByUsername(principal.getName())));
		return "reset/resetPassword";
	}
	
	@PostMapping(value = "/executeReset")
	public String executeReset(@Valid @ModelAttribute("utente_attr") UtenteResetPasswordDTO utenteResetPasswordDTO,BindingResult result, RedirectAttributes redirectAttributes,Principal principal,HttpServletRequest request) {
		if (!utenteResetPasswordDTO.getNuovaPassword().equals(utenteResetPasswordDTO.getConfermaPassword())) {
			redirectAttributes.addFlashAttribute("errorMessage", "Errore nella validazione delle password");
			return "reset/resetPassword";
		}
		try {
			utenteService.cambioPassword(utenteResetPasswordDTO, principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Vecchia password errata");
			return "reset/resetPassword";
		}
		
		return "redirect:/executeLogout";
	}
	
}
