package br.com.estud_io_api.repository.cycle;

import br.com.estud_io_api.entity.cycle.StudyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRecordRepository extends JpaRepository<StudyRecord, Integer> {
}
