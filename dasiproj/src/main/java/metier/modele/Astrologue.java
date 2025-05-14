/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author ncardenas
 */
@Entity
public class Astrologue extends Medium{
    protected String formation;
    protected String promotion;

    public Astrologue(String denomination, String presentation, Genre genre, String formation, String promotion) {
        super(denomination, presentation, genre);
        this.formation = formation;
        this.presentation = presentation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getFormation() {
        return formation;
    }

    public String getPromotion() {
        return promotion;
    }

    public Astrologue() {
    }
}
