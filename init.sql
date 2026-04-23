CREATE DATABASE IF NOT EXISTS odoru;
USE odoru;

-- Table de base pour tous les utilisateurs
CREATE TABLE membre (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nom_famille VARCHAR(100) NOT NULL,
                        prenom VARCHAR(100) NOT NULL,
                        adresse_mail VARCHAR(150) NOT NULL UNIQUE,
                        identifiant VARCHAR(100) NOT NULL UNIQUE,
                        mot_de_passe VARCHAR(255) NOT NULL,
                        ville VARCHAR(100),
                        pays VARCHAR(100),
                        niveau_expertise INT CHECK (niveau_expertise BETWEEN 1 AND 5),
                        role ENUM('MEMBRE', 'ENSEIGNANT', 'SECRETAIRE', 'PRESIDENT') DEFAULT 'MEMBRE'
);

-- Badge
CREATE TABLE badge (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       numero VARCHAR(100) NOT NULL UNIQUE,
                       membre_id BIGINT UNIQUE,
                       FOREIGN KEY (membre_id) REFERENCES membre(id)
);

-- Cours
CREATE TABLE cours (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       titre VARCHAR(150) NOT NULL,
                       niveau_cible INT NOT NULL,
                       creneau_semaine DATETIME NOT NULL,
                       lieu VARCHAR(150),
                       duree INT,
                       enseignant_id BIGINT,
                       FOREIGN KEY (enseignant_id) REFERENCES membre(id)
);

-- Compétition
CREATE TABLE competition (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             titre VARCHAR(150) NOT NULL,
                             niveau_cible INT NOT NULL,
                             creneau_semaine DATETIME NOT NULL,
                             lieu VARCHAR(150),
                             duree INT,
                             enseignant_id BIGINT,
                             FOREIGN KEY (enseignant_id) REFERENCES membre(id)
);

-- Résultat d'un élève à une compétition
CREATE TABLE resultat_competition (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      membre_id BIGINT NOT NULL,
                                      competition_id BIGINT NOT NULL,
                                      note DECIMAL(4,1) CHECK (note BETWEEN 0 AND 10),
                                      FOREIGN KEY (membre_id) REFERENCES membre(id),
                                      FOREIGN KEY (competition_id) REFERENCES competition(id)
);

-- Présence d'un élève à un cours (badging)
CREATE TABLE presence (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          membre_id BIGINT NOT NULL,
                          cours_id BIGINT NOT NULL,
                          date_presence DATETIME NOT NULL,
                          FOREIGN KEY (membre_id) REFERENCES membre(id),
                          FOREIGN KEY (cours_id) REFERENCES cours(id)
);