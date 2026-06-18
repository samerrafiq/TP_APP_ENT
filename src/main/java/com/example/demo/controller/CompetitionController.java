package com.example.demo.controller;

import com.example.demo.dto.CompetitionDTO;
import com.example.demo.entity.Competition;
import com.example.demo.entity.ResultatCompetition;
import com.example.demo.services.CompetitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @PostMapping
    public ResponseEntity<Competition> creer(@RequestBody CompetitionDTO dto) {
        return ResponseEntity.ok(competitionService.creerCompetition(dto));
    }

    @GetMapping
    public ResponseEntity<List<Competition>> lister() {
        return ResponseEntity.ok(competitionService.listerCompetitions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competition> getById(@PathVariable Long id) {
        return ResponseEntity.ok(competitionService.getCompetition(id));
    }

    @GetMapping("/niveau/{niveau}")
    public ResponseEntity<List<Competition>> parNiveau(@PathVariable int niveau) {
        return ResponseEntity.ok(competitionService.getCompetitionsParNiveau(niveau));
    }

    @PostMapping("/{id}/resultats")
    public ResponseEntity<ResultatCompetition> ajouterResultat(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        Long membreId = Long.valueOf(body.get("membreId").toString());
        double note = Double.parseDouble(body.get("note").toString());
        Long enseignantId = Long.valueOf(body.get("enseignantId").toString());
        return ResponseEntity.ok(competitionService.ajouterResultat(id, membreId, note, enseignantId));
    }

    @GetMapping("/{id}/resultats")
    public ResponseEntity<List<ResultatCompetition>> getResultats(@PathVariable Long id) {
        return ResponseEntity.ok(competitionService.getResultatsCompetition(id));
    }
}