package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model;


import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class User {
    protected String nickname;
    protected String name;
    protected String email;
    protected String lastname;
    protected String permissions;

    public User() {
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPermissions() {
        return this.permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}