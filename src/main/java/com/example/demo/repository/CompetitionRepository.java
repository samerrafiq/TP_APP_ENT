package com.example.demo.repository;

import com.example.demo.entity.Competition;
import com.example.demo.entity.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    List<Competition> findByNiveauCible(int niveauCible);
    List<Competition> findByEnseignant(Membre enseignant);
}