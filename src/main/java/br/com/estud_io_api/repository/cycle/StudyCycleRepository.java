package br.com.estud_io_api.repository.cycle;

import br.com.estud_io_api.entity.cycle.StudyCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyCycleRepository extends JpaRepository<StudyCycle, Integer> {
}
