package com.example.demo.services;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class StatistiquesServiceImpl implements StatistiquesService {

    private final CoursRepository coursRepository;
    private final PresenceRepository presenceRepository;
    private final MembreRepository membreRepository;
    private final CompetitionRepository competitionRepository;
    private final ResultatCompetitionRepository resultatRepository;

    public StatistiquesServiceImpl(CoursRepository coursRepository,
                                   PresenceRepository presenceRepository,
                                   MembreRepository membreRepository,
                                   CompetitionRepository competitionRepository,
                                   ResultatCompetitionRepository resultatRepository) {
        this.coursRepository = coursRepository;
        this.presenceRepository = presenceRepository;
        this.membreRepository = membreRepository;
        this.competitionRepository = competitionRepository;
        this.resultatRepository = resultatRepository;
    }

    @Override
    public Map<String, Object> getNombreCoursEtMoyennePresents() {
        List<Cours> tousLesCours = coursRepository.findAll();
        int nombreCours = tousLesCours.size();
        double moyenne = tousLesCours.stream()
                .mapToInt(c -> presenceRepository.findByCours(c).size())
                .average()
                .orElse(0.0);
        Map<String, Object> result = new HashMap<>();
        result.put("nombreCours", nombreCours);
        result.put("moyenneElevesPresents", moyenne);
        return result;
    }

    @Override
    public List<Membre> getElevesPresentsACours(Long coursId) {
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new RuntimeException("Cours introuvable"));
        return presenceRepository.findByCours(cours)
                .stream()
                .map(Presence::getMembre)
                .toList();
    }

    @Override
    public List<Map<String, Object>> getCoursEleveAvecPresences(Long membreId,
                                                                LocalDateTime debut,
                                                                LocalDateTime fin) {
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        List<Cours> cours = coursRepository.findByNiveauCible(membre.getNiveauExpertise());
        List<Presence> presences = presenceRepository.findByMembre(membre);

        return cours.stream()
                .filter(c -> {
                    if (debut != null && c.getCreneauSemaine().isBefore(debut)) return false;
                    if (fin != null && c.getCreneauSemaine().isAfter(fin)) return false;
                    return true;
                })
                .map(c -> {
                    boolean present = presences.stream()
                            .anyMatch(p -> p.getCours().getId().equals(c.getId()));
                    Map<String, Object> map = new HashMap<>();
                    map.put("cours", c);
                    map.put("present", present);
                    return map;
                })
                .toList();
    }

    @Override
    public int getNombreCompetitionsParNiveau(int niveau) {
        return competitionRepository.findByNiveauCible(niveau).size();
    }

    @Override
    public List<Map<String, Object>> getCompetitionsEleveAvecResultats(Long membreId,
                                                                       LocalDateTime debut,
                                                                       LocalDateTime fin) {
        Membre membre = membreRepository.findById(membreId)
                .orElseThrow(() -> new RuntimeException("Membre introuvable"));
        return resultatRepository.findByMembre(membre)
                .stream()
                .filter(r -> {
                    LocalDateTime date = r.getCompetition().getCreneauSemaine();
                    if (debut != null && date.isBefore(debut)) return false;
                    if (fin != null && date.isAfter(fin)) return false;
                    return true;
                })
                .map(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("competition", r.getCompetition());
                    map.put("note", r.getNote());
                    return map;
                })
                .toList();
    }
}