package com.example.demo.controller;

import com.example.demo.entity.Membre;
import com.example.demo.services.StatistiquesService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatistiquesController {

    private final StatistiquesService statistiquesService;

    public StatistiquesController(StatistiquesService statistiquesService) {
        this.statistiquesService = statistiquesService;
    }

    @GetMapping("/cours")
    public ResponseEntity<Map<String, Object>> nombreCoursEtMoyenne() {
        return ResponseEntity.ok(statistiquesService.getNombreCoursEtMoyennePresents());
    }

    @GetMapping("/cours/{coursId}/eleves")
    public ResponseEntity<List<Membre>> elevesPresents(@PathVariable Long coursId) {
        return ResponseEntity.ok(statistiquesService.getElevesPresentsACours(coursId));
    }

    @GetMapping("/membres/{membreId}/cours")
    public ResponseEntity<List<Map<String, Object>>> coursEleve(
            @PathVariable Long membreId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(statistiquesService.getCoursEleveAvecPresences(membreId, debut, fin));
    }

    @GetMapping("/competitions/niveau/{niveau}")
    public ResponseEntity<Integer> nbCompetitionsParNiveau(@PathVariable int niveau) {
        return ResponseEntity.ok(statistiquesService.getNombreCompetitionsParNiveau(niveau));
    }

    @GetMapping("/membres/{membreId}/competitions")
    public ResponseEntity<List<Map<String, Object>>> competitionsEleve(
            @PathVariable Long membreId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(statistiquesService.getCompetitionsEleveAvecResultats(membreId, debut, fin));
    }
}