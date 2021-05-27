package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.controller;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.*;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository.*;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.service.MailService;


@CrossOrigin(
        origins = {"http://localhost:4200"}
)
@RequestMapping({"/"})
@RestController
public class RegistrationController<T> {
    @Autowired
    public PasswordsRepository passwordsRepository;
    @Autowired
    public UsersRepository usersRepository;
    @Autowired
    private MailService notificationService;
    @Autowired
    public AdminsRepository adminsRepository;
    @Autowired
    public DoctorsRepository doctorsRepository;

    public RegistrationController() {
    }

    @RequestMapping({"send-mail"})
    public String send(Users users) {
        try {
            this.notificationService.sendEmail(users);
        } catch (MailException var3) {
            System.out.println(var3);
        }

        return "Congratulations! Your mail has been send to the user.";
    }

    public void send2(Users users) {
        try {
            this.notificationService.sendEmail(users);
            System.out.println("ggg");
        } catch (MailException var3) {
            System.out.println(var3);
        }

        System.out.println("gg");
    }

    @PostMapping({"/userLogInPath"})
    public Users userLogIn(@RequestBody Users users) {
        List<Users> correctUsers = new ArrayList(this.usersRepository.findByNickname(users.getNickname()));
        Users tmpFalseUser = new Users("false", "false");
        if (correctUsers.size() != 0) {
            if (((Users)correctUsers.get(0)).getPasswords().getPassword().equals(users.getPasswords().getPassword()) && !((Users)correctUsers.get(0)).getPermissions().equals("Not confirmed")) {
                users.setId(((Users)correctUsers.get(0)).getId());
                System.out.println("true");
                return users;
            }

            if (((Users)correctUsers.get(0)).getPasswords().getPassword().equals(users.getPasswords().getPassword()) && ((Users)correctUsers.get(0)).getPermissions().equals("Not confirmed")) {
                System.out.println("Not confirmed");
                tmpFalseUser.setName("NC");
                return tmpFalseUser;
            }
        }

        List<Admins> correctAdmins = new ArrayList(this.adminsRepository.findByNickname(users.getNickname()));
        if (correctAdmins.size() == 1) {
            if (((Admins)correctAdmins.get(0)).getPasswords().getPassword().equals(users.getPasswords().getPassword()) && !((Admins)correctAdmins.get(0)).getPermissions().equals("Not confirmed")) {
                users.setId(((Admins)correctAdmins.get(0)).getId());
                System.out.println("true");
                return users;
            }

            if (((Admins)correctAdmins.get(0)).getPasswords().getPassword().equals(users.getPasswords().getPassword()) && ((Admins)correctAdmins.get(0)).getPermissions().equals("Not confirmed")) {
                System.out.println("Not confirmed");
                tmpFalseUser.setName("NC");
                return tmpFalseUser;
            }
        }

        List<Doctors> correctDoctors = new ArrayList(this.doctorsRepository.findByNickname(users.getNickname()));
        if (correctDoctors.size() == 1) {
            if (((Doctors)correctDoctors.get(0)).getPasswords().getPassword().equals(users.getPasswords().getPassword()) && !((Doctors)correctDoctors.get(0)).getPermissions().equals("Not confirmed")) {
                users.setId(((Doctors)correctDoctors.get(0)).getId());
                System.out.println("true");
                return users;
            }

            if (((Doctors)correctDoctors.get(0)).getPasswords().getPassword().equals(users.getPasswords().getPassword()) && ((Doctors)correctDoctors.get(0)).getPermissions().equals("Not confirmed")) {
                System.out.println("Not confirmed");
                tmpFalseUser.setName("NC");
                return tmpFalseUser;
            }
        }

        return null;
    }

    @PostMapping(
            path = {"/create"}
    )
    public Users createUser(@RequestBody Users users) {
        List<Users> idList = new ArrayList(this.usersRepository.findByNickname(users.getNickname()));
        idList.removeAll(idList);
        Passwords newUserPassword = new Passwords(users.getPasswords().getPassword());
        new Users(users.getNickname(), users.getName(), users.getLastname(), users.getEmail(), newUserPassword);
        Users newUsers = new Users(users.getEmail(), users.getName(), users.getLastname(), users.getNickname(), "Not confirmed", newUserPassword);
        this.usersRepository.save(newUsers);
        this.notificationService.sendEmail(newUsers);
        return newUsers;
    }
}
