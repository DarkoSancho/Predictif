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
public class Spirite extends Medium{
    protected String support;

    public Spirite(String denomination, String presentation, Genre genre, String support) {
        super(denomination, presentation, genre);
        this.support = support;
    }

    public Spirite() {
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getSupport() {
        return support;
    }

    
}
