package com.example.demo.repository;

import com.example.demo.entity.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {
    Optional<Membre> findByIdentifiant(String identifiant);
    Optional<Membre> findByAdresseMail(String adresseMail);
    boolean existsByIdentifiant(String identifiant);
    boolean existsByAdresseMail(String adresseMail);
}