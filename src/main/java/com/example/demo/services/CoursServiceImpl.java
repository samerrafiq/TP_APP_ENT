package com.example.demo.services;

import com.example.demo.dto.CoursDTO;
import com.example.demo.entity.Cours;
import com.example.demo.entity.Membre;
import com.example.demo.repository.CoursRepository;
import com.example.demo.repository.MembreRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CoursServiceImpl implements CoursService {

    private final CoursRepository coursRepository;
    private final MembreRepository membreRepository;

    public CoursServiceImpl(CoursRepository coursRepository, MembreRepository membreRepository) {
        this.coursRepository = coursRepository;
        this.membreRepository = membreRepository;
    }

    @Override
    public Cours creerCours(CoursDTO dto) {
        if (dto.getCreneauSemaine().isBefore(LocalDateTime.now().plusDays(7))) {
            throw new RuntimeException("Le cours doit être planifié au moins 7 jours à l'avance");
        }
        Membre enseignant = membreRepository.findById(dto.getEnseignantId())
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));
        if (enseignant.getRole() != Membre.Role.ENSEIGNANT && enseignant.getRole() != Membre.Role.PRESIDENT) {
            throw new RuntimeException("Ce membre n'est pas enseignant");
        }
        if (enseignant.getNiveauExpertise() < dto.getNiveauCible()) {
            throw new RuntimeException("Enseignant non apte pour ce niveau");
        }
        Cours cours = new Cours();
        cours.setTitre(dto.getTitre());
        cours.setNiveauCible(dto.getNiveauCible());
        cours.setCreneauSemaine(dto.getCreneauSemaine());
        cours.setEnseignant(enseignant);
        cours.setLieu(dto.getLieu());
        cours.setDuree(dto.getDuree());
        return coursRepository.save(cours);
    }

    @Override
    public List<Cours> listerCours() {
        return coursRepository.findAll();
    }

    @Override
    public List<Cours> getCoursParNiveau(int niveau) {
        return coursRepository.findByNiveauCible(niveau);
    }

    @Override
    public List<Cours> getCoursParEnseignant(Long enseignantId) {
        Membre enseignant = membreRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));
        return coursRepository.findByEnseignant(enseignant);
    }

    @Override
    public Cours getCours(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));
    }

    @Override
    public Cours modifierCours(Long id, CoursDTO dto) {
        Cours cours = getCours(id);
        cours.setTitre(dto.getTitre());
        cours.setNiveauCible(dto.getNiveauCible());
        cours.setCreneauSemaine(dto.getCreneauSemaine());
        cours.setLieu(dto.getLieu());
        cours.setDuree(dto.getDuree());
        return coursRepository.save(cours);
    }

    @Override
    public void supprimerCours(Long id) {
        coursRepository.deleteById(id);
    }
}