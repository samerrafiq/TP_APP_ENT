package com.example.demo.repository;

import com.example.demo.entity.Membre;
import com.example.demo.entity.Competition;
import com.example.demo.entity.ResultatCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResultatCompetitionRepository extends JpaRepository<ResultatCompetition, Long> {
    List<ResultatCompetition> findByMembre(Membre membre);
    List<ResultatCompetition> findByCompetition(Competition competition);
}