CREATE TABLE IF NOT EXISTS patients (
    id BIGINT NOT NULL AUTO_INCREMENT,
    patient_code VARCHAR(20) NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    date_of_birth DATE NOT NULL,
    mobile VARCHAR(30) NOT NULL,
    email VARCHAR(150) NULL,
    blood_group VARCHAR(5) NULL,
    address VARCHAR(500) NULL,
    emergency_contact VARCHAR(200) NULL,
    registration_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_patients_patient_code (patient_code),
    UNIQUE KEY uk_patients_mobile (mobile)
);
