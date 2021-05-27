package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;
    private String name;
    private String description;
    @OneToOne(
            cascade = {CascadeType.ALL}
    )
    private Users users;
    @OneToOne(
            cascade = {CascadeType.ALL}
    )
    private Doctors doctors;

    public History(int id) {
        this.id = id;
    }

    public History() {
    }

    public String toString() {
        return "History{id=" + this.id + ", name='" + this.name + '\'' + ", description='" + this.description + '\'' + ", users=" + this.users + ", doctors=" + this.doctors + '}';
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Users getUsers() {
        return this.users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Doctors getDoctors() {
        return this.doctors;
    }

    public void setDoctors(Doctors doctors) {
        this.doctors = doctors;
    }
}