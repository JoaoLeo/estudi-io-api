package br.com.estud_io_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "study_record", catalog = "estud-io")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyRecord {

    @Id
    @Column(name = "study_record_id")
    private Integer studyRecordId;

    @ManyToOne
    @JoinColumn(name = "study_cycle_id", nullable = false)
    private StudyCycle studyCycle;

    @Column(name = "duration", nullable = false)
    private LocalTime duration;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "notify_record")
    private Boolean notifyRecord;
}
