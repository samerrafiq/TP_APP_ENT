package com.example.demo.controller;

import com.example.demo.entity.Badge;
import com.example.demo.entity.Presence;
import com.example.demo.services.BadgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/badges")
public class BadgeController {

    private final BadgeService badgeService;

    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @PostMapping
    public ResponseEntity<Badge> creer() {
        return ResponseEntity.ok(badgeService.creerBadge());
    }

    @PutMapping("/{badgeId}/associer/{membreId}")
    public ResponseEntity<Badge> associer(@PathVariable Long badgeId,
                                          @RequestParam Long secretaireId,
                                          @PathVariable Long membreId) {
        return ResponseEntity.ok(badgeService.associerBadge(badgeId, secretaireId, membreId));
    }

    @PutMapping("/{badgeId}/dissocier")
    public ResponseEntity<Badge> dissocier(@PathVariable Long badgeId,
                                           @RequestParam Long secretaireId) {
        return ResponseEntity.ok(badgeService.dissocierBadge(badgeId, secretaireId));
    }

    @PostMapping("/badger")
    public ResponseEntity<Presence> badger(@RequestParam String numero,
                                           @RequestParam Long coursId) {
        return ResponseEntity.ok(badgeService.badger(numero, coursId));
    }

    @GetMapping("/presences/membre/{membreId}")
    public ResponseEntity<List<Presence>> parMembre(@PathVariable Long membreId) {
        return ResponseEntity.ok(badgeService.getPresencesParMembre(membreId));
    }

    @GetMapping("/presences/cours/{coursId}")
    public ResponseEntity<List<Presence>> parCours(@PathVariable Long coursId) {
        return ResponseEntity.ok(badgeService.getPresencesParCours(coursId));
    }
}