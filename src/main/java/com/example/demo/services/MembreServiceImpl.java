package com.example.demo.services;

import com.example.demo.dto.MembreDTO;
import com.example.demo.entity.Membre;
import com.example.demo.repository.MembreRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MembreServiceImpl implements MembreService {

    private final MembreRepository membreRepository;

    public MembreServiceImpl(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    @Override
    public Membre creerMembre(MembreDTO dto) {
        if (membreRepository.existsByIdentifiant(dto.getIdentifiant())) {
            throw new RuntimeException("Identifiant déjà utilisé");
        }
        if (membreRepository.existsByAdresseMail(dto.getAdresseMail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        Membre membre = new Membre();
        membre.setNomFamille(dto.getNomFamille());
        membre.setPrenom(dto.getPrenom());
        membre.setAdresseMail(dto.getAdresseMail());
        membre.setIdentifiant(dto.getIdentifiant());
        membre.setMotDePasse(dto.getMotDePasse());
        membre.setVille(dto.getVille());
        membre.setPays(dto.getPays());
        membre.setNiveauExpertise(dto.getNiveauExpertise());
        return membreRepository.save(membre);
    }

    @Override
    public List<Membre> listerMembres() {
        return membreRepository.findAll();
    }

    @Override
    public Membre getMembre(Long id) {
        return membreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
    }

    @Override
    public Membre modifierMembre(Long id, MembreDTO dto) {
        Membre membre = getMembre(id);
        membre.setNomFamille(dto.getNomFamille());
        membre.setPrenom(dto.getPrenom());
        membre.setVille(dto.getVille());
        membre.setPays(dto.getPays());
        membre.setNiveauExpertise(dto.getNiveauExpertise());
        return membreRepository.save(membre);
    }

    @Override
    public void supprimerMembre(Long id) {
        membreRepository.deleteById(id);
    }
}