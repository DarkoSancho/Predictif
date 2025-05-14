/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author ncardenas
 */

@Entity
@Table(name = "Consultations")
public class Consultation implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public Consultation(Client client, Medium medium, Employe selected_emp) {
        this.employe = selected_emp;
        this.medium = medium;
        this.client = client;
        this.etat = Etat.demande;
        this.date = new Date();
    }
    
    public enum Etat {demande, enCours, effectuee, annulee};
    
    protected Etat etat;
    
    protected String commentaire_employe;
    
    /**
     * Date generee automatiquement avec la consultation
     */
    @Temporal(TemporalType.DATE)
    protected final Date date;
    
    protected Integer scoreAmour;
    
    protected Integer scoreTravail;
    
    protected Integer scoreSante;
    
    protected String resume;
    
    @ManyToOne
    protected Medium medium;
    
    @ManyToOne
    protected Employe employe;
    
    @ManyToOne
    protected Client client;

    public Consultation() {
        this.date = new Date();
    }

    public Consultation(Client client, Medium medium) {
        this.employe = null;
        this.medium = null;
        this.client = client;
        this.etat = Etat.demande;
        this.date = new Date();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }
    
    public Date getDate() {
        return date;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public String getCommentaire_employe() {
        return commentaire_employe;
    }

    public void setCommentaire_employe(String commentaire_employe) {
        this.commentaire_employe = commentaire_employe;
    }

    public int getScore_amour() {
        return scoreAmour;
    }

    public void setScoreAmour(Integer scoreAmour) {
        this.scoreAmour = scoreAmour;
    }

    public int getScore_travail() {
        return scoreTravail;
    }

    public void setScoreTravail(Integer scoreTravail) {
        this.scoreTravail = scoreTravail;
    }

    public int getScore_sante() {
        return scoreSante;
    }

    public void setScoreSante(Integer scoreSante) {
        this.scoreSante = scoreSante;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Consultation :\n");
        sb.append("[ Client ").append(client.getNom()).append(" ").append(client.getPrenom()).append("\n");
        sb.append("[ Medium ").append(medium.getDenomination()).append(" ").append("\n");
        sb.append("[ Employe ").append(employe.getNom()).append(" ").append(employe.getPrenom()).append("\n");
        sb.append("[ Date ").append(date).append("\n");
        sb.append("[ Etat ").append(etat).append("\n");
        return sb.toString();
    }

}
