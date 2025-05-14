/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author ncardenas
 */
@Entity
@Table(name = "Employes")
public class Employe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String nom;

    protected String prenom;

    @Column(unique = true)
    protected String mail;

    protected String motDePasse;

    protected String noTel;

    protected String noTelPro;

    protected Boolean disponible;

    protected Integer nbConsultations;

    protected Genre genre;

    @OneToMany(mappedBy = "employe")
    private List<Consultation> consultations;

    public Employe(String nom, String prenom, String mail, String motDePasse, String no_tel, String no_tel_pro, Genre genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.noTel = no_tel;
        this.noTelPro = no_tel_pro;
        this.disponible = true;
        this.nbConsultations = 0;
        this.genre = genre;
        this.consultations = new ArrayList<>();
    }

    public Employe() {
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

    public String getNoTelPro() {
        return noTelPro;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Integer getNbConsultations() {
        return nbConsultations;
    }

    public Genre getGenre() {
        return genre;
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

    public void setNoTelPro(String noTelPro) {
        this.noTelPro = noTelPro;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setNbConsultations(Integer nbConsultations) {
        this.nbConsultations = nbConsultations;
    }

    public void incrementerNbConsultations() {
        this.nbConsultations++;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public Consultation getConsultationCourante() {
        Consultation res = null;
        for (Consultation courante : consultations) {
            if (courante.etat == Consultation.Etat.demande) {
                res = courante;
                break;
            }
        }
        return res;
    }

    public Consultation getConsultationEnCours() {
        Consultation res = null;
        for (Consultation courante : consultations) {
            if (courante.etat == Consultation.Etat.enCours) {
                res = courante;
                break;
            }
        }
        return res;
    }

    public Integer appendConsultation(Consultation c) {
        consultations.add(c);
        return consultations.size();
    }

    @Override
    public String toString() {
        return "Employe{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", motDePasse=" + motDePasse + '}';
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
        final Employe other = (Employe) obj;
        return Objects.equals(this.id, other.id);
    }

}
