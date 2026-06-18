package com.example.demo.controller;

import com.example.demo.dto.CoursDTO;
import com.example.demo.entity.Cours;
import com.example.demo.services.CoursService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    private final CoursService coursService;

    public CoursController(CoursService coursService) {
        this.coursService = coursService;
    }

    @PostMapping
    public ResponseEntity<Cours> creer(@RequestBody CoursDTO dto) {
        return ResponseEntity.ok(coursService.creerCours(dto));
    }

    @GetMapping
    public ResponseEntity<List<Cours>> lister() {
        return ResponseEntity.ok(coursService.listerCours());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cours> getById(@PathVariable Long id) {
        return ResponseEntity.ok(coursService.getCours(id));
    }

    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Cours>> parNiveau(@PathVariable int niveau) {
        return ResponseEntity.ok(coursService.getCoursParNiveau(niveau));
    }

    @GetMapping("/enseignant/{enseignantId}")
    public ResponseEntity<List<Cours>> parEnseignant(@PathVariable Long enseignantId) {
        return ResponseEntity.ok(coursService.getCoursParEnseignant(enseignantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cours> modifier(@PathVariable Long id, @RequestBody CoursDTO dto) {
        return ResponseEntity.ok(coursService.modifierCours(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        coursService.supprimerCours(id);
        return ResponseEntity.noContent().build();
    }
}