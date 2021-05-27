package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.Users;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository.UsersRepository;

@Service
public class MailService {
    private JavaMailSender javaMailSender;
    @Autowired
    public UsersRepository usersRepository;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(Users users) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(users.getEmail());
        mail.setSubject("Przychodnia weterynaryjna");
        String mainUrl = "http://localhost:4200/UserLogInComponent/" + String.valueOf(users.getId());
        mail.setText(mainUrl);
        this.javaMailSender.send(mail);
    }
}