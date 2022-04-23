package sn.barro.dev.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;

@SuppressWarnings("ALL")
@Entity
@Table(name = "livres")
public class LivreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "nbpages")
    private String nbpages;

    @Column(name = "description" , length = 500)
    private String description;

    /*
     * @ManyToOne
     * private AuteurModel auteurmodel
     * 
     * L’annotation @ManyToOne implique que la table Livre contient une colonne qui
     * est une clé étrangère contenant
     * la clé d’un auteur. Par défaut, JPA s’attend à ce que cette colonne se nomme
     * AUTEUR_ID,
     * mais il est possible de changer ce nom grâce à l’annotation @JoinColumn.
     * Plutôt que par une colonne,
     * il est également possible d’indiquer à JPA qu’il doit passer par
     * une table d’association pour établir la relation entre les deux entités avec
     * l’annotation @JoinTable
     * comm-suit:
     * // déclaration d'une table d'association
    @JoinTable(name = "auteur_livre", joinColumns = @JoinColumn(name = "livre_id"), inverseJoinColumns = @JoinColumn(name = "auteur_id"))
     */
    // @ManyToOne(fetch = FetchType.LAZY) // plusieurs livres sont ecrits par un auteur ainsi un chargement paresseux
    // private AuteurModel auteurmodel;


    @ManyToOne// plusieurs livres sont ecrits par un auteur
    @JoinColumn(name = "auteur_id", nullable = false)// redefinition du nom de la cle etrangere
    @JsonIgnore
    private AuteurModel auteurModel;// Ceci ajoutera a la table livre une cle etrangere auteur_model_id

    public LivreModel() {

    }

    public LivreModel(String titre, String nbpages, String description, AuteurModel auteurModel) {
        this.titre = titre;
        this.nbpages = nbpages;
        this.description = description;
        this.auteurModel = new AuteurModel();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuteurModel getAuteurModel() {
        return auteurModel;
    }

    public void setAuteurModel(AuteurModel auteurModel) {
        this.auteurModel = auteurModel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNbpages() {
        return nbpages;
    }

    public void setNbpages(String nbpages) {
        this.nbpages = nbpages;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", titre:'" + titre + '\'' +
                ", nbpages:'" + nbpages + '\'' +
                ", description:'" + description + '\'' +
                ", auteur_id:'" + auteurModel.getId() + '\'' +
                ", name_auteur:'" + auteurModel.getName() + '\'' +
                '}';
    }

    public  String json(){
       String json ="{" +
                " \"id\": " + id+
               " \"titre\": " + titre+
               " \"nbpages\": " + nbpages+
               " \"description\": " + description+
               " \"auteur_id\": " + auteurModel.getId()+
               " \"name_auteur\": " + auteurModel.getName()+
               "}";
       return json;
    }
}
