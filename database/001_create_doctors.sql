-- Idempotent Doctor Management schema migration.
-- Creates no data and does not alter or drop existing tables.
CREATE TABLE IF NOT EXISTS doctors (
    id BIGINT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(150) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    date_of_birth DATE NULL,
    mobile VARCHAR(30) NOT NULL,
    email VARCHAR(150) NULL,
    qualification VARCHAR(150) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    years_of_experience INT NOT NULL,
    consultation_fee DECIMAL(10,2) NULL,
    status VARCHAR(20) NOT NULL,
    login_email VARCHAR(150) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_doctors_login_email UNIQUE (login_email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
