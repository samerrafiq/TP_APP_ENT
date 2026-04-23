package com.dto;

public class MembreDTO {
    private String nomFamille;
    private String prenom;
    private String adresseMail;
    private String identifiant;
    private String motDePasse;
    private String ville;
    private String pays;
    private int niveauExpertise;

    // Getters & Setters
    public String getNomFamille() { return nomFamille; }
    public void setNomFamille(String nomFamille) { this.nomFamille = nomFamille; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getAdresseMail() { return adresseMail; }
    public void setAdresseMail(String adresseMail) { this.adresseMail = adresseMail; }

    public String getIdentifiant() { return identifiant; }
    public void setIdentifiant(String identifiant) { this.identifiant = identifiant; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }

    public int getNiveauExpertise() { return niveauExpertise; }
    public void setNiveauExpertise(int niveauExpertise) { this.niveauExpertise = niveauExpertise; }
}