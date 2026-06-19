package com.example.demo.dto;

import com.example.demo.entity.Membre;

public class MembreResponseDTO {
    private Long id;
    private String nomFamille;
    private String prenom;
    private String adresseMail;
    private String ville;
    private String pays;
    private int niveauExpertise;
    private String role;

    public MembreResponseDTO() {}

    public MembreResponseDTO(Membre m) {
        this.id = m.getId();
        this.nomFamille = m.getNomFamille();
        this.prenom = m.getPrenom();
        this.adresseMail = m.getAdresseMail();
        this.ville = m.getVille();
        this.pays = m.getPays();
        this.niveauExpertise = m.getNiveauExpertise();
        this.role = m.getRole() != null ? m.getRole().name() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomFamille() { return nomFamille; }
    public void setNomFamille(String nomFamille) { this.nomFamille = nomFamille; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getAdresseMail() { return adresseMail; }
    public void setAdresseMail(String adresseMail) { this.adresseMail = adresseMail; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }
    public int getNiveauExpertise() { return niveauExpertise; }
    public void setNiveauExpertise(int niveauExpertise) { this.niveauExpertise = niveauExpertise; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}