package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.Passwords;

public interface PasswordsRepository extends JpaRepository<Passwords, Integer> {
    List<Passwords> findByPassword(String password);

    List<Passwords> findById(int id);
}
