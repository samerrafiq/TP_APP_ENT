package com.example.demo.services;

import com.example.demo.entity.Badge;
import com.example.demo.entity.Presence;
import java.util.List;

public interface BadgeService {
    Badge creerBadge();
    Badge associerBadge(Long badgeId, Long secretaireId, Long membreId);
    Badge dissocierBadge(Long badgeId, Long secretaireId);
    Presence badger(String numeroBadge, Long coursId);
    List<Presence> getPresencesParMembre(Long membreId);
    List<Presence> getPresencesParCours(Long coursId);
}