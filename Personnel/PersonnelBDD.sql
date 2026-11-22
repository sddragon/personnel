CREATE DATABASE gestion_ligues;
USE gestion_ligues;

CREATE TABLE ROLE (
    id_role INT PRIMARY KEY AUTO_INCREMENT,
    libelle_role VARCHAR(50)
);

CREATE TABLE LIGUE (
    id_ligue INT PRIMARY KEY AUTO_INCREMENT,
    nom_ligue VARCHAR(50),
    id_employe INT
);

CREATE TABLE EMPLOYE (
    id_employe INT PRIMARY KEY AUTO_INCREMENT,
    prenom_employe VARCHAR(50),
    nom_employe VARCHAR(50),
    date_arrive DATE,
    date_depart DATE,
    id_ligue INT,
    id_role INT,
    FOREIGN KEY (id_ligue) REFERENCES LIGUE(id_ligue),
    FOREIGN KEY (id_role) REFERENCES ROLE(id_role)
);

ALTER TABLE LIGUE
ADD FOREIGN KEY (id_employe) REFERENCES EMPLOYE(id_employe);
