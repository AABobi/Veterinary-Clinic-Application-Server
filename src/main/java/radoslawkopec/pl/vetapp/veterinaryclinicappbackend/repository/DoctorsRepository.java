package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.Doctors;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctors, Integer> {
    List<Doctors> findByNickname(String nickname);

    List<Doctors> findByNameAndLastname(String name, String lastname);

    List<Doctors> findById(int id);

    List<Doctors> findByEmail(String email);
}
