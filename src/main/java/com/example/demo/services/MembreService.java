package com.example.demo.services;

import com.example.demo.entity.Membre;
import com.example.demo.dto.MembreDTO;
import java.util.List;

public interface MembreService {
    Membre creerMembre(MembreDTO dto);
    List<Membre> listerMembres();
    Membre getMembre(Long id);
    Membre modifierMembre(Long id, MembreDTO dto);
    void supprimerMembre(Long id);
    Membre changerRole(Long membreId, Long secretaireId, String nouveauRole);
    Membre modifierNiveau(Long membreId, Long secretaireId, int nouveauNiveau);
    Membre initialiserPremierSecretaire(MembreDTO dto);
}