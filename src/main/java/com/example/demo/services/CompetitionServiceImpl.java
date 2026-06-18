package com.example.demo.services;

import com.example.demo.dto.CompetitionDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final MembreRepository membreRepository;
    private final ResultatCompetitionRepository resultatRepository;

    public CompetitionServiceImpl(CompetitionRepository competitionRepository,
                                  MembreRepository membreRepository,
                                  ResultatCompetitionRepository resultatRepository) {
        this.competitionRepository = competitionRepository;
        this.membreRepository = membreRepository;
        this.resultatRepository = resultatRepository;
    }

    @Override
    public Competition creerCompetition(CompetitionDTO dto) {
        if (dto.getCreneauSemaine().isBefore(LocalDateTime.now().plusDays(7))) {
            throw new RuntimeException("La compétition doit être planifiée au moins 7 jours à l'avance");
        }
        Membre enseignant = membreRepository.findById(dto.getEnseignantId())
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));
        if (enseignant.getRole() != Membre.Role.ENSEIGNANT && enseignant.getRole() != Membre.Role.PRESIDENT) {
            throw new RuntimeException("Ce membre n'est pas enseignant");
        }
        Competition competition = new Competition();
        competition.setTitre(dto.getTitre());
        competition.setNiveauCible(dto.getNiveauCible());
        competition.setCreneauSemaine(dto.getCreneauSemaine());
        competition.setEnseignant(enseignant);
        competition.setLieu(dto.getLieu());
        competition.setDuree(dto.getDuree());
        return competitionRepository.save(competition);
    }

    @Override
    public List<Competition> listerCompetitions() {
        return competitionRepository.findAll();
    }

    @Override
    public List<Competition> getCompetitionsParNiveau(int niveau) {
        return competitionRepository.findByNiveauCible(niveau);
    }

    @Override
    public Competition getCompetition(Long id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compétition introuvable"));
    }

    @Override
    public ResultatCompetition ajouterResultat(Long competitionId, Long membreId, double note, Long enseignantId) {
        Membre enseignant = membreRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));
        if (enseignant.getRole() != Membre.Role.ENSEIGNANT && enseignant.getRole() != Membre.Role.PRESIDENT) {
            throw new RuntimeException("Seul un enseignant peut saisir un résultat");
        }
        if (note < 0 || note > 10) {
            throw new RuntimeException("La note doit être entre 0 et 10");
        }
        Competition competition = getCompetition(competitionId);
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        ResultatCompetition resultat = new ResultatCompetition();
        resultat.setCompetition(competition);
        resultat.setMembre(membre);
        resultat.setNote(Math.round(note * 10.0) / 10.0);
        return resultatRepository.save(resultat);
    }

    @Override
    public List<ResultatCompetition> getResultatsCompetition(Long competitionId) {
        Competition competition = getCompetition(competitionId);
        return resultatRepository.findByCompetition(competition);
    }
}