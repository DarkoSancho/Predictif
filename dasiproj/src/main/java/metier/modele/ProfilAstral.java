/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author ncardenas
 */
@Embeddable
public class ProfilAstral implements Serializable {

    protected String signeChinois;
    protected String signeZodiaque;

    public void setSigneChinois(String signeChinois) {
        this.signeChinois = signeChinois;
    }

    public void setSigneZodiaque(String signeZodiaque) {
        this.signeZodiaque = signeZodiaque;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
    protected String couleur;
    protected String animal;

    public String getSigneChinois() {
        return signeChinois;
    }

    public String getSigneZodiaque() {
        return signeZodiaque;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getAnimal() {
        return animal;
    }

    public ProfilAstral() {
        this.signeChinois = "";
        this.signeZodiaque = "";
        this.couleur = "";
        this.animal = "";
    }

    public ProfilAstral(String signeZodiaque, String signeChinois, String couleur, String animal) {
        this.signeChinois = signeChinois;
        this.signeZodiaque = signeZodiaque;
        this.couleur = couleur;
        this.animal = animal;
    }

}
