package com.controller;

import com.dto.MembreDTO;
import com.odoru.entity.Membre;
import com.services.MembreService;
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
}