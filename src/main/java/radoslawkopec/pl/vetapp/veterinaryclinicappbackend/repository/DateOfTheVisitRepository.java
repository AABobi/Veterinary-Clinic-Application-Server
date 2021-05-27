package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.DateOfTheVisit;

public interface DateOfTheVisitRepository extends JpaRepository<DateOfTheVisit, Integer> {
    List<DateOfTheVisit> findById(int id);

    List<DateOfTheVisit> findByDateof(String date);

    List<DateOfTheVisit> findByDoctors_id(int id);
}
