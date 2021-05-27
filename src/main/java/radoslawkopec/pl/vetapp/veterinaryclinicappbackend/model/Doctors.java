package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model;


import javax.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctors extends User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;
    @OneToOne(
            cascade = {CascadeType.ALL}
    )
    private Passwords passwords;

    public Doctors() {
    }

    public String toString() {
        return "Doctors{id=" + this.id + ", passwords=" + this.passwords + ", nickname='" + this.nickname + '\'' + ", name='" + this.name + '\'' + ", email='" + this.email + '\'' + ", lastname='" + this.lastname + '\'' + ", permissions='" + this.permissions + '\'' + '}';
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Passwords getPasswords() {
        return this.passwords;
    }

    public void setPasswords(Passwords passwords) {
        this.passwords = passwords;
    }
}