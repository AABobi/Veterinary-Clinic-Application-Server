package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.History;

public interface HistoryRepository extends JpaRepository<History, Integer> {
}
