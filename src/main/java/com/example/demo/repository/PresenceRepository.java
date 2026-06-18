package com.example.demo.repository;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Membre;
import com.example.demo.entity.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, Long> {
    List<Presence> findByMembre(Membre membre);
    List<Presence> findByCours(Cours cours);
    Optional<Presence> findByMembreAndCours(Membre membre, Cours cours);
}