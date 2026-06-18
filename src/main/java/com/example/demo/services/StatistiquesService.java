package com.example.demo.services;

import com.example.demo.entity.Membre;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatistiquesService {
    Map<String, Object> getNombreCoursEtMoyennePresents();
    List<Membre> getElevesPresentsACours(Long coursId);
    List<Map<String, Object>> getCoursEleveAvecPresences(Long membreId, LocalDateTime debut, LocalDateTime fin);
    int getNombreCompetitionsParNiveau(int niveau);
    List<Map<String, Object>> getCompetitionsEleveAvecResultats(Long membreId, LocalDateTime debut, LocalDateTime fin);
}