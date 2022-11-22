package it.prova.myebay.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.myebay.dto.AcquistoDTO;
import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.model.Acquisto;
import it.prova.myebay.sevice.AcquistoService;

@Controller
@RequestMapping(value = "/acquisto")
public class AcquistoController {

	@Autowired
	private AcquistoService acquistoService;

	@GetMapping
	public ModelAndView listAllAcquisti(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		UtenteDTO utenteInSessione = (UtenteDTO) request.getSession().getAttribute("userInfo");
		List<Acquisto> acquisti = acquistoService.acquistiDellUtente(utenteInSessione.getId());
		mv.addObject("acquisti_list_attribute", AcquistoDTO.createAcquistoDTOListFromModelList(acquisti));
		mv.setViewName("acquisto/list");
		return mv;
	}

}
