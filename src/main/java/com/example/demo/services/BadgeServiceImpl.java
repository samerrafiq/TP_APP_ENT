package com.example.demo.services;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;
    private final MembreRepository membreRepository;
    private final CoursRepository coursRepository;
    private final PresenceRepository presenceRepository;

    public BadgeServiceImpl(BadgeRepository badgeRepository,
                            MembreRepository membreRepository,
                            CoursRepository coursRepository,
                            PresenceRepository presenceRepository) {
        this.badgeRepository = badgeRepository;
        this.membreRepository = membreRepository;
        this.coursRepository = coursRepository;
        this.presenceRepository = presenceRepository;
    }

    @Override
    public Badge creerBadge() {
        Badge badge = new Badge();
        return badgeRepository.save(badge);
    }
    @Override
    public List<Badge> listerBadges() {
        return badgeRepository.findAll();
    }

    @Override
    public Badge associerBadge(Long badgeId, Long secretaireId, Long membreId) {
        Membre secretaire = membreRepository.findById(secretaireId)
                .orElseThrow(() -> new RuntimeException("Secrétaire introuvable"));
        if (secretaire.getRole() != Membre.Role.SECRETAIRE) {
            throw new RuntimeException("Seul le secrétaire peut associer un badge");
        }
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new RuntimeException("Badge introuvable"));
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        badge.setMembre(membre);
        return badgeRepository.save(badge);
    }

    @Override
    public Badge dissocierBadge(Long badgeId, Long secretaireId) {
        Membre secretaire = membreRepository.findById(secretaireId)
                .orElseThrow(() -> new RuntimeException("Secrétaire introuvable"));
        if (secretaire.getRole() != Membre.Role.SECRETAIRE) {
            throw new RuntimeException("Seul le secrétaire peut dissocier un badge");
        }
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new RuntimeException("Badge introuvable"));
        badge.setMembre(null);
        return badgeRepository.save(badge);
    }

    @Override
    public Presence badger(String numeroBadge, Long coursId) {
        Badge badge = badgeRepository.findByNumero(numeroBadge)
                .orElseThrow(() -> new RuntimeException("Badge introuvable"));
        if (badge.getMembre() == null) {
            throw new RuntimeException("Badge non associé à un membre");
        }
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));
        presenceRepository.findByMembreAndCours(badge.getMembre(), cours)
                .ifPresent(p -> { throw new RuntimeException("Présence déjà enregistrée"); });
        Presence presence = new Presence();
        presence.setMembre(badge.getMembre());
        presence.setCours(cours);
        presence.setDatePresence(LocalDateTime.now());
        return presenceRepository.save(presence);
    }

    @Override
    public List<Presence> getPresencesParMembre(Long membreId) {
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        return presenceRepository.findByMembre(membre);
    }

    @Override
    public List<Presence> getPresencesParCours(Long coursId) {
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));
        return presenceRepository.findByCours(cours);
    }
}