package com.example.demo.dto;

import java.time.LocalDateTime;

public class CoursDTO {
    private String titre;
    private int niveauCible;
    private LocalDateTime creneauSemaine;
    private Long enseignantId;
    private String lieu;
    private int duree;

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public int getNiveauCible() { return niveauCible; }
    public void setNiveauCible(int niveauCible) { this.niveauCible = niveauCible; }
    public LocalDateTime getCreneauSemaine() { return creneauSemaine; }
    public void setCreneauSemaine(LocalDateTime creneauSemaine) { this.creneauSemaine = creneauSemaine; }
    public Long getEnseignantId() { return enseignantId; }
    public void setEnseignantId(Long enseignantId) { this.enseignantId = enseignantId; }
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
}