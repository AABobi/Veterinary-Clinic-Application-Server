package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.Admins;

@Repository
public interface AdminsRepository extends JpaRepository<Admins, Integer> {
    List<Admins> findByNickname(String nickname);

    List<Admins> findByNameAndLastname(String name, String lastname);

    List<Admins> findById(int id);

    List<Admins> findByEmail(String email);
}
