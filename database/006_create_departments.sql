CREATE TABLE IF NOT EXISTS departments (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(150) NOT NULL,
  code VARCHAR(30) NOT NULL,
  description VARCHAR(1000) NULL,
  head_doctor_id BIGINT NULL,
  status VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_departments_name (name),
  UNIQUE KEY uk_departments_code (code),
  CONSTRAINT fk_departments_head_doctor FOREIGN KEY (head_doctor_id) REFERENCES doctors(id)
);
CREATE TABLE IF NOT EXISTS department_doctors (
  department_id BIGINT NOT NULL,
  doctor_id BIGINT NOT NULL,
  PRIMARY KEY (department_id, doctor_id),
  CONSTRAINT fk_department_doctors_department FOREIGN KEY (department_id) REFERENCES departments(id),
  CONSTRAINT fk_department_doctors_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
CREATE TABLE IF NOT EXISTS department_nurses (
  department_id BIGINT NOT NULL,
  nurse_id BIGINT NOT NULL,
  PRIMARY KEY (department_id, nurse_id),
  CONSTRAINT fk_department_nurses_department FOREIGN KEY (department_id) REFERENCES departments(id),
  CONSTRAINT fk_department_nurses_nurse FOREIGN KEY (nurse_id) REFERENCES nurses(id)
);
