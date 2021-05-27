package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PASSWORDS")
public class Passwords {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;
    private String password;

    public Passwords() {
    }

    public Passwords(String password) {
        this.password = password;
    }

    public Passwords(int id, String password) {
        this.id = id;
        this.password = password;
    }

    public String toString() {
        return "Passwords{id=" + this.id + ", password='" + this.password + '\'' + '}';
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}