package com.services;

import com.odoru.entity.Membre;
import com.dto.MembreDTO;
import java.util.List;

public interface MembreService {
    Membre creerMembre(MembreDTO dto);
    List<Membre> listerMembres();
    Membre getMembre(Long id);
    Membre modifierMembre(Long id, MembreDTO dto);
    void supprimerMembre(Long id);
}