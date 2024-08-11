CREATE TABLE `estud-io`.users (
	user_id INT auto_increment NOT NULL,
	name varchar(50) NOT NULL,
	email varchar(250) NOT NULL,
	password varchar(250) NOT NULL,
	account_creation_date DATE NOT NULL,
	goal varchar(500) NULL,
	CONSTRAINT users_pk PRIMARY KEY (user_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `estud-io`.subjects (
    subject_id INT auto_increment NOT NULL,
    user_id INT NOT NULL,
    name varchar(100) NOT NULL,
    CONSTRAINT subjects_pk PRIMARY KEY (subject_id),
    CONSTRAINT subjects_users_FK FOREIGN KEY (user_id) REFERENCES `estud-io`.users(user_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `estud-io`.study_cycle (
    study_cycle_id INT auto_increment NOT NULL,
    subject_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NULL,
    CONSTRAINT study_cycle_pk PRIMARY KEY (study_cycle_id),
    CONSTRAINT study_cycle_subjects_FK FOREIGN KEY (subject_id) REFERENCES `estud-io`.subjects(subject_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `estud-io`.flashcards (
    flashcard_id INT auto_increment NOT NULL,
    subject_id INT NULL,
    question varchar(500) NOT NULL,
    answer varchar(100) NOT NULL,
    num_correct_answers INT NULL,
    num_wrong_answers INT NULL,
    CONSTRAINT flashcards_pk PRIMARY KEY (flashcard_id),
    CONSTRAINT flashcards_subjects_FK FOREIGN KEY (subject_id) REFERENCES `estud-io`.subjects(subject_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `estud-io`.study_record (
    study_record_id INT NOT NULL,
    study_cycle_id INT NOT NULL,
    duration TIME NOT NULL,
    `date` DATE NOT NULL,
    completed BIT NULL,
    notify_record BIT NULL,
    CONSTRAINT study_record_pk PRIMARY KEY (study_record_id),
    CONSTRAINT study_record_study_cycle_FK FOREIGN KEY (study_cycle_id) REFERENCES `estud-io`.study_cycle(study_cycle_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;






