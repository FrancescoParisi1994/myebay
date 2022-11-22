package it.prova.myebay.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.prova.myebay.model.Categoria;
import it.prova.myebay.sevice.CategoriaService;

@Controller
@RequestMapping(value = "/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping(value = "/searchCategorieAjax", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String searchCategoria(@RequestParam String term) {

		List<Categoria> listaCategoriaByTerm = categoriaService.findByDescrizioneECodice(term);
		return buildJsonResponse(listaCategoriaByTerm);
	}

	private String buildJsonResponse(List<Categoria> listaCategoria) {
		JsonArray ja = new JsonArray();

		for (Categoria categoriaItem : listaCategoria) {
			JsonObject jo = new JsonObject();
			jo.addProperty("value", categoriaItem.getId());
			jo.addProperty("label", categoriaItem.getDescrizione() + " " + categoriaItem.getCodice());
			ja.add(jo);
		}

		return new Gson().toJson(ja);
	}

}
