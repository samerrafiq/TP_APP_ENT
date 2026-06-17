package com.odoru.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "membre")
public class Membre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomFamille;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    private String adresseMail;

    @Column(nullable = false, unique = true)
    private String identifiant;

    @Column(nullable = false)
    private String motDePasse;

    private String ville;
    private String pays;

    @Column(nullable = false)
    private int niveauExpertise; // entre 1 et 5

    @Enumerated(EnumType.STRING)
    private Role role = Role.MEMBRE;

    public enum Role {
        MEMBRE, ENSEIGNANT, SECRETAIRE, PRESIDENT
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}