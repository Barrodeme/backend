package sn.barro.dev.backend.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import sn.barro.dev.backend.exception.ResourceNotFoundException;
import sn.barro.dev.backend.model.AuteurModel;
import sn.barro.dev.backend.repository.AuteurRepository;

import javax.annotation.security.RolesAllowed;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api")
public class AuteurController {
    
    /**
     *
     */
    private static final String AUTEUR_INTROUVABLE_AVEC_ID = "Auteur introuvable avec id :";
    @Autowired
	private AuteurRepository auteurRepository;

    // create auteur rest api
	@PostMapping("/auteurs")
	@RolesAllowed("admin")
	public AuteurModel createAuteur(@RequestBody AuteurModel auteur) {
		return auteurRepository.save(auteur);
	}

    // get all auteur
	@GetMapping("/auteurs")
	@RolesAllowed({"admin", "user"})// pour ajouter multi role
	public List<AuteurModel> getAllAllAuteurs(){
		return auteurRepository.findAll();
	}
    
    // get auteur by id rest api
	@GetMapping("/auteurs/{id}")
	@RolesAllowed({"admin", "user"})// pour ajouter multi role
	public ResponseEntity<AuteurModel> getAuteurById(@PathVariable Long id) {
		AuteurModel auteur = auteurRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(AUTEUR_INTROUVABLE_AVEC_ID + id));
		return ResponseEntity.ok(auteur);
	}

    // update auteur rest api
	@PutMapping("/auteurs/{id}")
	@RolesAllowed("admin")
	public ResponseEntity<AuteurModel> updateAuteurModel(@PathVariable Long id, @RequestBody AuteurModel auteur){
		AuteurModel auteurmodel = auteurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AUTEUR_INTROUVABLE_AVEC_ID + id));
		
		auteurmodel.setName(auteur.getName());
		auteurmodel.setMaisonedition(auteur.getMaisonedition());
		
		AuteurModel updatedAuteurmodel = auteurRepository.save(auteurmodel);
		return ResponseEntity.ok(updatedAuteurmodel);
	}

    // delete auteur rest api
	@DeleteMapping("/auteurs/{id}")
	@RolesAllowed("admin")
	public ResponseEntity<Map<String, Boolean>> deleteAuteur(@PathVariable Long id){
		AuteurModel auteurmodel = auteurRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(AUTEUR_INTROUVABLE_AVEC_ID + id));
		
		auteurRepository.delete(auteurmodel);
		Map<String, Boolean> response = new HashMap<>();
		response.put("supprimé avec succes!", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
