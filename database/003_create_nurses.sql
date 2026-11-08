CREATE TABLE IF NOT EXISTS nurses (
    id BIGINT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(150) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    date_of_birth DATE NULL,
    mobile VARCHAR(30) NOT NULL,
    email VARCHAR(150) NULL,
    qualification VARCHAR(150) NOT NULL,
    department VARCHAR(100) NOT NULL,
    years_of_experience INT NOT NULL,
    shift VARCHAR(20) NOT NULL,
    joining_date DATE NOT NULL,
    address VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL,
    login_email VARCHAR(150) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_nurses_login_email (login_email)
);
