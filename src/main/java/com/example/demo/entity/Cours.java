package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private int niveauCible;

    @Column(nullable = false)
    private LocalDateTime creneauSemaine;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Membre enseignant;

    private String lieu;
    private int duree;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public int getNiveauCible() { return niveauCible; }
    public void setNiveauCible(int niveauCible) { this.niveauCible = niveauCible; }
    public LocalDateTime getCreneauSemaine() { return creneauSemaine; }
    public void setCreneauSemaine(LocalDateTime creneauSemaine) { this.creneauSemaine = creneauSemaine; }
    public Membre getEnseignant() { return enseignant; }
    public void setEnseignant(Membre enseignant) { this.enseignant = enseignant; }
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
}