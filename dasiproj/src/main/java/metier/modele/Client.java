/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author ncardenas
 */
@Entity
@Table(name = "Clients")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String nom;

    protected String prenom;

    @Column(unique = true)
    protected String mail;

    protected String motDePasse;

    protected String noTel;

    @Temporal(TemporalType.DATE)
    protected Date dateNaissance;

    protected String adressePostale;

    protected Genre genre;

    @Embedded
    protected ProfilAstral profilAstral;
    
    @OneToMany(mappedBy="client")
    private List<Consultation> consultations; 

    public Client(String nom, String prenom, String mail, String motDePasse, String adressePostale, Date date_naissance, String no_tel, Genre genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.adressePostale = adressePostale;
        this.dateNaissance = date_naissance;
        this.noTel = no_tel;
        this.genre = genre;
        this.profilAstral = new ProfilAstral();
        this.consultations = new ArrayList<>();
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    
    public Client() {
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getNoTel() {
        return noTel;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getAdressePostale() {
        return adressePostale;
    }


    public Genre getGenre() {
        return genre;
    }

    public void initProfilAstral(List<String> profil) {
        this.profilAstral = new ProfilAstral(profil.get(0), profil.get(1), profil.get(2), profil.get(3));
    }

    public String getSigneChinois() {
        return profilAstral.signeChinois;
    }

    public String getSigneZodiaque() {
        return profilAstral.signeZodiaque;
    }

    public String getCouleur() {
        return profilAstral.couleur;
    }

    public String getAnimal() {
        return profilAstral.animal;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setNoTel(String noTel) {
        this.noTel = noTel;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }
    
    public Integer appendConsultation(Consultation c) {
        consultations.add(c);
        return consultations.size();
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", motDePasse=" + motDePasse + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        return Objects.equals(this.id, other.id);
    }

}
