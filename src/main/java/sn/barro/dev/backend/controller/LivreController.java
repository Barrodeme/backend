package sn.barro.dev.backend.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import sn.barro.dev.backend.exception.ResourceNotFoundException;
import sn.barro.dev.backend.model.*;
import sn.barro.dev.backend.repository.*;

import javax.annotation.security.RolesAllowed;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api")
public class LivreController {

	private static final String LIVRE_INTROUVABLE_AVEC_ID = "Livre introuvable avec id :";
	@Autowired
	private LivreRepository livreRepository;
	@Autowired
	private AuteurRepository auteurRepository;

    // create livre rest api
	@PostMapping("/auteurs/{auteurId}/livres")
	@RolesAllowed("admin")// pour ajouter multi role
	public ResponseEntity<LivreModel> createLivre(@PathVariable(value = "auteurId") Long auteurId,
												  @RequestBody LivreModel livreModel) {
		// recherche de l'auteur par Id en le mappant a auteur dans la fonction ci-dessous
		LivreModel livreModel1 = auteurRepository.findById(auteurId).map(auteur -> {
			livreModel.setAuteurModel(auteur);// sauvegarde de l'objet auteur (id  = ur_id dans la table livre)
			return livreRepository.save(livreModel);// ajouter du livre y compris auteur_id
		}).orElseThrow(() -> new ResourceNotFoundException(LIVRE_INTROUVABLE_AVEC_ID + " pour auteur"));
		return new ResponseEntity<>(livreModel1, HttpStatus.CREATED);
	}

    // get all livre
	@GetMapping("/livres")
	@RolesAllowed({"admin", "user"})// pour ajouter multi role
	public List<Object> getAllAllLivre(){
		return livreRepository.findAllLivre();
	}

    // get livre by id rest api with auteur as response
	@GetMapping("/livres/{id}/auteur")
	public ResponseEntity<Object> getAuteurLivre(@PathVariable Long id) {
		LivreModel livreModel = new LivreModel();
		LivreModel livre = livreRepository.findById(id).map(auteur ->{
						livreModel.setAuteurModel(auteur.getAuteurModel());
			return livreModel;
				}).orElseThrow(() -> new ResourceNotFoundException(LIVRE_INTROUVABLE_AVEC_ID + id));

		return ResponseEntity.ok(livre.getAuteurModel());
	}

	// get livre by id auteur
	@GetMapping("/livres/{id}")
	@RolesAllowed({"admin", "user"})// pour ajouter multi role
	public ResponseEntity<Object> getLivreByid(@PathVariable Long id) {
		LivreModel livre = livreRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(LIVRE_INTROUVABLE_AVEC_ID + id));
		return ResponseEntity.ok(livre);
	}
    // update livre rest api
	@PutMapping("/livres/{id}")
	@RolesAllowed("admin")
	public ResponseEntity<LivreModel> updateLivreModel(@PathVariable Long id, @RequestBody LivreModel livre){
		LivreModel livremodel = livreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LIVRE_INTROUVABLE_AVEC_ID + id));
		
		livremodel.setTitre(livre.getTitre());
		livremodel.setNbpages(livre.getNbpages());
		livremodel.setDescription(livre.getDescription());

		LivreModel updatedLivremodel = livreRepository.save(livremodel);
		return ResponseEntity.ok(updatedLivremodel);
	}

    // delete livres rest api
	@DeleteMapping("/livres/{id}")
	@RolesAllowed("admin")
	public ResponseEntity<Map<String, Boolean>> deleteLivre(@PathVariable Long id){
		LivreModel livremodel = livreRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(LIVRE_INTROUVABLE_AVEC_ID + id));
		
		livreRepository.delete(livremodel);
		Map<String, Boolean> response = new HashMap<>();
		response.put("supprimé avec succes!", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
