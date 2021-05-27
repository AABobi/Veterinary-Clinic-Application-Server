package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    List<Users> findByNameOrLastname(String name, String lastname);

    List<Users> findByNameAndLastname(String name, String lastname);

    List<Users> findById(int id);

    List<Users> findByNickname(String nickname);

    List<Users> findByEmail(String email);
}
