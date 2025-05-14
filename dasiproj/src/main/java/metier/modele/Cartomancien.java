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
public class Cartomancien extends Medium{

    public Cartomancien(String denomination, String presentation, Genre genre) {
        super(denomination, presentation, genre);
    }

    public Cartomancien() {
    }
    
}
