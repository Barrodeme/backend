package sn.barro.dev.backend.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.*;

@SuppressWarnings("ALL")
@Entity
@Table(name = "auteurs")
public class AuteurModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "maisonedition")
    private String maisonedition;

    @OneToMany(mappedBy = "auteurModel")
    List<LivreModel> livreModels= new ArrayList<>();

    public AuteurModel() {
        super();
    }

    public AuteurModel(String name, String maisonedition) {
        super();
        this.name = name;
        this.maisonedition = maisonedition;
    }

    public List<LivreModel> getLivreModels() {
        return livreModels;
    }

    public void setLivreModels(List<LivreModel> livreModels) {
        this.livreModels = livreModels;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaisonedition() {
        return maisonedition;
    }

    public void setMaisonedition(String maisonedition) {
        this.maisonedition = maisonedition;
    }

    @Override
    public String toString() {
        return "AuteurModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maisonedition='" + maisonedition + '\'' +
                ", livreModels=" + livreModels +
                '}';
    }
}
