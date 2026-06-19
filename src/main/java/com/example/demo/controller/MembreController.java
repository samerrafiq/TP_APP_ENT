package com.example.demo.controller;

import com.example.demo.entity.Membre;
import com.example.demo.dto.MembreDTO;
import com.example.demo.dto.MembreResponseDTO;
import com.example.demo.services.MembreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/membres")
public class MembreController {

    private final MembreService membreService;

    public MembreController(MembreService membreService) {
        this.membreService = membreService;
    }

    @PostMapping
    public ResponseEntity<MembreResponseDTO> creer(@RequestBody MembreDTO dto) {
        return ResponseEntity.ok(new MembreResponseDTO(membreService.creerMembre(dto)));
    }

    @PostMapping("/init-secretaire")
    public ResponseEntity<MembreResponseDTO> initSecretaire(@RequestBody MembreDTO dto) {
        return ResponseEntity.ok(new MembreResponseDTO(membreService.initialiserPremierSecretaire(dto)));
    }

    @GetMapping
    public ResponseEntity<List<MembreResponseDTO>> lister() {
        return ResponseEntity.ok(membreService.listerMembres().stream()
                .map(MembreResponseDTO::new)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembreResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new MembreResponseDTO(membreService.getMembre(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembreResponseDTO> modifier(@PathVariable Long id, @RequestBody MembreDTO dto) {
        return ResponseEntity.ok(new MembreResponseDTO(membreService.modifierMembre(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        membreService.supprimerMembre(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<MembreResponseDTO> changerRole(@PathVariable Long id,
                                                         @RequestParam Long secretaireId,
                                                         @RequestParam String role) {
        return ResponseEntity.ok(new MembreResponseDTO(membreService.changerRole(id, secretaireId, role)));
    }

    @PutMapping("/{id}/niveau")
    public ResponseEntity<MembreResponseDTO> modifierNiveau(@PathVariable Long id,
                                                            @RequestParam Long secretaireId,
                                                            @RequestParam int niveau) {
        return ResponseEntity.ok(new MembreResponseDTO(membreService.modifierNiveau(id, secretaireId, niveau)));
    }
}