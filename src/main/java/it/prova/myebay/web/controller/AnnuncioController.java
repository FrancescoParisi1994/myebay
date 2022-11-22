package it.prova.myebay.web.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.dto.AnnuncioInsertDTO;
import it.prova.myebay.dto.CategoriaDTO;
import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Utente;
import it.prova.myebay.repository.utente.UtenteRepository;
import it.prova.myebay.sevice.AcquistoService;
import it.prova.myebay.sevice.AnnuncioService;
import it.prova.myebay.sevice.CategoriaService;
import it.prova.myebay.sevice.UtenteService;

@Controller
@RequestMapping(value = "/annunci")
public class AnnuncioController {

	@Autowired
	private AnnuncioService annuncioService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private UtenteService utenteService;
	@Autowired
	private AcquistoService acquistoService;

	@GetMapping
	public ModelAndView listAllAnnunci() {
		ModelAndView mv = new ModelAndView();
		List<Annuncio> annunci = annuncioService.trovaAnnunciAperti();
		mv.addObject("annunci_list_attribute", AnnuncioDTO.createAnnuncioDTOListFromModelList(annunci));
		mv.setViewName("annunci/list");
		return mv;
	}

	@GetMapping("/insert")
	public String createFilm(Model model) {
		model.addAttribute("insert_annunci_attr", new AnnuncioDTO());
		model.addAttribute("insert_categoriaList_attr",
				CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.findAll()));
		return "annunci/insert";
	}

	// inietto la request perché ci potrebbe tornare utile per ispezionare i
	// parametri
	@PostMapping("/save")
	public String saveFilm(@Valid @ModelAttribute("insert_annunci_attr") AnnuncioInsertDTO annuncioDTO,
			BindingResult result, RedirectAttributes redirectAttrs, Model model, HttpServletRequest request,
			Principal principal) {

		if ((annuncioDTO.getPrezzo() == null || annuncioDTO.getPrezzo() < 1)
				&& annuncioDTO.getTestoAnnuncio().isBlank()) {
			result.rejectValue("testoAnnuncio", "testoAnnuncio.save.error");
			model.addAttribute("insert_categoriaList_attr",
					CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.findAll()));
			return "annunci/insert";
		}

		if (result.hasErrors()) {
			System.out.println("sono qui");
			model.addAttribute("insert_categoriaList_attr",
					CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.findAll()));
			return "annunci/insert";
		}

		annuncioService.insert(annuncioDTO.buildAnnuncioModel(), principal.getName());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/annunci";
	}

	@GetMapping("/public/search")
	public String searchAnnunci(Model model) {
		model.addAttribute("annunci_list_attribute",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.findAll()));
		return "annunci/search";
	}

	@PostMapping("/public/list")
	public String listAnnunci(AnnuncioDTO annuncioDTO, ModelMap model) {
		List<Annuncio> annunci = annuncioService.findByExample(annuncioDTO.buildAnnuncioModel());
		model.addAttribute("annunci_list_attribute", AnnuncioDTO.createAnnuncioDTOListFromModelList(annunci));
		return "annunci/list";
	}

	@GetMapping("/public/show/{idAnnuncio}")
	public String showAnnuncio(@PathVariable(required = true) Long idAnnuncio, Model model) {
		model.addAttribute("show_annunci_attr",
				AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioService.findEagerCategorie(idAnnuncio)));
		return "annunci/show";
	}

	@PostMapping("/buy")
	public String buyAnnuncio(@RequestParam(required = true) Long idAnnuncio, Model model, HttpServletRequest request) {
		UtenteDTO utenteInSessione = (UtenteDTO) request.getSession().getAttribute("userInfo");
		Utente utente = utenteService.caricaSingoloUtente(utenteInSessione.getId());
		Annuncio annuncio = annuncioService.findById(idAnnuncio);

		if (annuncio.getPrezzo() > utente.getCreditoResiduo()) {
			model.addAttribute("show_annunci_attr",
					AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioService.findEagerCategorie(idAnnuncio)));
			model.addAttribute("errorMessage", "Credito insufficente");
			return "annunci/show";
		}
		utente.setCreditoResiduo(utente.getCreditoResiduo() - annuncio.getPrezzo());
		utenteService.aggiorna(utente);

		Acquisto acquisto = new Acquisto();
		acquisto.setData(new Date());
		acquisto.setDescrizione(annuncio.getTestoAnnuncio());
		acquisto.setPrezzo(annuncio.getPrezzo());
		acquisto.setUtenteAcquirente(utente);
		acquistoService.insert(acquisto);

		annuncio.setAperto(false);
		annuncioService.update(annuncio);

		return "redirect:/acquisto";
	}

	@GetMapping("/edit/{idAnnuncio}")
	public String editAnnuncio(@PathVariable(required = true) Long idAnnuncio, Model model) {
		model.addAttribute("edit_annunci_attr",
				AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioService.findEagerCategorie(idAnnuncio)));

		return "annunci/edit";
	}

	@PostMapping("/update")
	public String updateAnnucio(@Valid @ModelAttribute("edit_annunci_attr") AnnuncioDTO annuncioDTO,
			BindingResult result, RedirectAttributes redirectAttrs, Model model, HttpServletRequest request,
			Principal principal) {

		if (result.hasErrors()) {
			return "annunci/edit";
		}
		annuncioService.update(annuncioDTO.buildAnnuncioModel(), principal.getName());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/annunci";
	}

	@GetMapping("/delete/{idAnnuncio}")
	public String deleteAnnuncio(@PathVariable(required = true) Long idAnnuncio, Model model) {
		model.addAttribute("delete_annunci_attr",
				AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioService.findEagerCategorie(idAnnuncio)));
		return "annunci/delete";
	}

	@PostMapping("/cancel")
	public String cancelAnnuncio(@RequestParam(required = true) Long idAnnuncio, RedirectAttributes redirectAttrs) {

		annuncioService.delete(idAnnuncio);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/annunci";
	}

	@GetMapping("/gestione")
	public String gestioneAnnunci(Model model, HttpServletRequest request) {
		UtenteDTO utenteInSessione = (UtenteDTO) request.getSession().getAttribute("userInfo");
		model.addAttribute("annunci_gestione_attribute", AnnuncioDTO.createAnnuncioDTOListFromModelList(
				annuncioService.trovaAnnunciApertiDellUtente(utenteInSessione.getId())));
		return "annunci/gestione";
	}

//	@GetMapping("/error/{idRegista}")
//	public String errorList(@PathVariable(required = true) Long idRegista, Model model) {
//		List<Film> result = new ArrayList<>();
//		registaService.caricaSingoloElementoConFilms(idRegista).getFilms().forEach(i -> result.add(i));
//		model.addAttribute("film_list_attribute", FilmDTO.createFilmDTOListFromModelList(result, true));
//		model.addAttribute("id_regista_attr", idRegista);
//		return "film/listRegista";
//	}
//
//	@PostMapping("/deleteFilmRegista")
//	public String deleteFilmRegista(@RequestParam(required = true) Long idRegista, RedirectAttributes redirectAttrs) {
//
//		registaService.caricaSingoloElementoConFilms(idRegista).getFilms().forEach(i -> filmService.rimuovi(i.getId()));
//
//		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
//		return "redirect:/regista";
//	}

}
