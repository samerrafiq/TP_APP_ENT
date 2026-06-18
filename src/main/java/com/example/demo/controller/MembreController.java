package com.example.demo.controller;

import com.example.demo.entity.Membre;
import com.example.demo.dto.MembreDTO;
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
    public ResponseEntity<Membre> creer(@RequestBody MembreDTO dto) {
        return ResponseEntity.ok(membreService.creerMembre(dto));
    }

    @GetMapping
    public ResponseEntity<List<Membre>> lister() {
        return ResponseEntity.ok(membreService.listerMembres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membre> getById(@PathVariable Long id) {
        return ResponseEntity.ok(membreService.getMembre(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Membre> modifier(@PathVariable Long id, @RequestBody MembreDTO dto) {
        return ResponseEntity.ok(membreService.modifierMembre(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        membreService.supprimerMembre(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<Membre> changerRole(@PathVariable Long id,
                                              @RequestParam Long secretaireId,
                                              @RequestParam String role) {
        return ResponseEntity.ok(membreService.changerRole(id, secretaireId, role));
    }

    @PutMapping("/{id}/niveau")
    public ResponseEntity<Membre> modifierNiveau(@PathVariable Long id,
                                                 @RequestParam Long secretaireId,
                                                 @RequestParam int niveau) {
        return ResponseEntity.ok(membreService.modifierNiveau(id, secretaireId, niveau));
    }
}