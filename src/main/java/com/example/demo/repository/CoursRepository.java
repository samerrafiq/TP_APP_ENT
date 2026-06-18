package com.example.demo.repository;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    List<Cours> findByNiveauCible(int niveauCible);
    List<Cours> findByEnseignant(Membre enseignant);
}