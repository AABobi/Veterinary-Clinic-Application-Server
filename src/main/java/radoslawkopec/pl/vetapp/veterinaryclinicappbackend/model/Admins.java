package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model;


import javax.persistence.*;

@Entity
@Table(name = "admins")
public class Admins extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(
            cascade = {CascadeType.ALL}
    )
    private Passwords passwords;

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

    public Admins() {
    }

    public Admins(int id, String nickname, String name, String lastname) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.lastname = lastname;
    }

    public String toString() {
        return "Admins{id=" + this.id + ", passwords=" + this.passwords + ", nickname='" + this.nickname + '\'' + ", name='" + this.name + '\'' + ", email='" + this.email + '\'' + ", lastname='" + this.lastname + '\'' + ", permissions='" + this.permissions + '\'' + '}';
    }
}
