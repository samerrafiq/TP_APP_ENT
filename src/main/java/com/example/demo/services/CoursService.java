package com.example.demo.services;

import com.example.demo.dto.CoursDTO;
import com.example.demo.entity.Cours;
import java.util.List;

public interface CoursService {
    Cours creerCours(CoursDTO dto);
    List<Cours> listerCours();
    List<Cours> getCoursParNiveau(int niveau);
    List<Cours> getCoursParEnseignant(Long enseignantId);
    Cours getCours(Long id);
    Cours modifierCours(Long id, CoursDTO dto);
    void supprimerCours(Long id);
}