package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model;


import java.util.List;
import java.util.stream.Stream;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class Users extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(
            cascade = {CascadeType.ALL}
    )
    private Passwords passwords;

    public Users() {
    }

    public Users(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public Users(int id, String name, String lastname, String email) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public Users(String nickname, String name, String lastname) {
        this.nickname = nickname;
        this.name = name;
        this.lastname = lastname;
    }

    public Users(String nickname, String name, String lastname, String email) {
        this.nickname = nickname;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public Users(String f) {
        this.nickname = f;
        this.name = f;
        this.lastname = f;
    }

    public Users(String nickname, String name, String lastname, String email, Passwords passwords) {
        this.nickname = nickname;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.passwords = passwords;
    }

    public Users(String email, String name, String lastname, String nickname, String permissions, Passwords passwords) {
        this.nickname = nickname;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.permissions = permissions;
        this.passwords = passwords;
    }

    public String toString() {
        return "Users{id=" + this.id + ", passwords=" + this.passwords + ", nickname='" + this.nickname + '\'' + ", name='" + this.name + '\'' + ", email='" + this.email + '\'' + ", lastname='" + this.lastname + '\'' + ", permissions='" + this.permissions + '\'' + '}';
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

    private static <T> Stream<T> convertListToStream(List<T> list) {
        return list.stream();
    }
}