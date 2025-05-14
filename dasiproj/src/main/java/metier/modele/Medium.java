/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author ncardenas
 */
@Entity
@Table(name = "Mediums")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_medium")
public class Medium implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String denomination;

    protected String presentation;

    protected Genre genre;

    public Medium(String denomination, String presentation, Genre genre) {
        this.denomination = denomination;
        this.presentation = presentation;
        this.genre = genre;
    }

    public Medium() {
    }

    public long getId() {
        return id;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getDenomination() {
        return denomination;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Medium{" + "id=" + id + ", nom=" + denomination + '}';
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
        final Medium other = (Medium) obj;
        return Objects.equals(this.id, other.id);
    }

}
