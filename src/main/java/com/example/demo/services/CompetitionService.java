package com.example.demo.services;

import com.example.demo.dto.CompetitionDTO;
import com.example.demo.entity.Competition;
import com.example.demo.entity.ResultatCompetition;
import java.util.List;

public interface CompetitionService {
    Competition creerCompetition(CompetitionDTO dto);
    List<Competition> listerCompetitions();
    List<Competition> getCompetitionsParNiveau(int niveau);
    Competition getCompetition(Long id);
    ResultatCompetition ajouterResultat(Long competitionId, Long membreId, double note, Long enseignantId);
    List<ResultatCompetition> getResultatsCompetition(Long competitionId);
}