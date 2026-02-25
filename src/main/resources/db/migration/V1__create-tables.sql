CREATE TABLE IF NOT EXISTS users (
    user_id INT NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    account_creation_date DATE NOT NULL,
    goal VARCHAR(500) NULL,
    token_email_verification TEXT NULL,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT users_pk PRIMARY KEY (user_id)
    )
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS subjects (
    subject_id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    subject_name VARCHAR(100) NOT NULL,
    CONSTRAINT subjects_pk PRIMARY KEY (subject_id),
    CONSTRAINT subjects_users_FK
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    )
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS study_cycle (
    study_cycle_id INT NOT NULL AUTO_INCREMENT,
    subject_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NULL,
    CONSTRAINT study_cycle_pk PRIMARY KEY (study_cycle_id),
    CONSTRAINT study_cycle_subjects_FK
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
    )
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS flashcards (
    flashcard_id INT NOT NULL AUTO_INCREMENT,
    subject_id INT NULL,
    question VARCHAR(500) NOT NULL,
    answer VARCHAR(255) NOT NULL,
    num_correct_answers INT NULL,
    num_wrong_answers INT NULL,
    CONSTRAINT flashcards_pk PRIMARY KEY (flashcard_id),
    CONSTRAINT flashcards_subjects_FK
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
    )
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS study_record (
    study_record_id INT NOT NULL AUTO_INCREMENT,
    study_cycle_id INT NOT NULL,
    duration TIME NOT NULL,
    date DATE NOT NULL,
    completed BOOLEAN NULL,
    notify_record BOOLEAN NULL,
    CONSTRAINT study_record_pk PRIMARY KEY (study_record_id),
    CONSTRAINT study_record_study_cycle_FK
    FOREIGN KEY (study_cycle_id) REFERENCES study_cycle(study_cycle_id)
    )
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci;