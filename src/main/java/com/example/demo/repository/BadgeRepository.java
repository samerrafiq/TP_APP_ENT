package com.example.demo.repository;

import com.example.demo.entity.Badge;
import com.example.demo.entity.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Optional<Badge> findByNumero(String numero);
    Optional<Badge> findByMembre(Membre membre);
}