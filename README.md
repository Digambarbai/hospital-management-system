# 🏥 Hospital Management System

### A Modern Full-Stack Hospital Management Web Application

A responsive, full-stack **Hospital Management System** developed as a college project using **React.js, Bootstrap, Java Spring Boot, Spring Data JPA, and MySQL**.

The application is designed to simplify hospital administration by managing doctors, nurses, patients, appointments, departments, hospital beds and medical records through a centralized platform.

---

## 🚀 Technology Stack

| Layer | Technologies |
|---|---|
| Frontend | React.js, Bootstrap, Vite |
| Backend | Java 21, Spring Boot |
| Database | MySQL |
| ORM / Database Access | Spring Data JPA, Hibernate |
| Authentication | Spring Security, BCrypt |
| Build Tools | Maven, npm |
| Version Control | Git, GitHub |
| Frontend Hosting | Netlify |

## ✨ Key Features

- Modern, responsive dashboard with an interactive collapsible sidebar.
- Admin and Doctor login with role-based authentication.
- Dynamic CRUD operations using REST APIs and MySQL.
- Search, filtering, pagination and form validation.
- Responsive layouts for desktop, tablet and mobile.
- Secure password hashing and session-based authentication.
- User-friendly notifications, confirmation dialogs and loading states.

## 🏥 Application Modules

| Module | Description |
|---|---|
| Dashboard | Hospital statistics and administration overview |
| Patient Management | Manage patient registration and information |
| Doctor Management | Manage doctors, professional details and login credentials |
| Nurse Management | Manage nursing staff and department assignments |
| Appointment Management | Schedule and manage patient appointments |
| Bed Management | Manage wards, rooms, beds and patient allocations |
| Medical Records | Manage patient medical history and treatment information |
| Departments | Manage hospital departments and assigned staff |

## 🔐 Authentication & Security

The application supports role-based login through Spring Security.

**Admin:** Accesses the administrative dashboard and authorized hospital management modules.

**Doctor:** Logs in using registered credentials and accesses the Doctor Dashboard and permitted features.

Security includes BCrypt password hashing, server-side session management, protected APIs and role-based authorization.

## 📁 Project Structure

```text
hospital_assignment/
├── frontend/              # React + Bootstrap + Vite
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   ├── App.jsx
│   │   └── main.jsx
│   └── package.json
│
├── backend/               # Java + Spring Boot
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       └── resources/
│   └── pom.xml
│
├── database/              # MySQL migration scripts
├── netlify.toml           # Netlify configuration
└── README.md
```

## ⚙️ Prerequisites

Install the following tools:

- Java JDK 21
- Apache Maven
- Node.js and npm
- MySQL
- Git
- Visual Studio Code (recommended)

## 🛠️ Local Installation

**1. Clone the repository**

```bash
git clone https://github.com/Digambarbai/hospital-management-system.git
cd hospital-management-system
```

**2. Configure the database**

Create a MySQL database named `hospital_management`.

```sql
CREATE DATABASE IF NOT EXISTS hospital_management;
```

Configure the Spring Boot database connection using local environment variables. Apply the required SQL migrations from the `database/` directory.

Never commit database credentials or passwords.

**3. Start the backend**

```bash
cd backend
mvn spring-boot:run
```

Backend URL: `http://localhost:8080`

**4. Start the frontend**

Open a separate terminal:

```bash
cd frontend
npm install
npm run dev
```

Frontend URL: `http://localhost:5173`

## 🌐 Deployment

The React frontend is configured for deployment on **Netlify**.

| Setting | Value |
|---|---|
| Branch | main |
| Build Command | `npm --prefix frontend run build` |
| Publish Directory | `frontend/dist` |
| API Configuration | `VITE_API_BASE_URL` |

The Spring Boot backend and MySQL database require separate hosting for the full application to work online.

## 🧪 Testing

The project can be tested locally by verifying:

- Admin and Doctor authentication.
- Doctor, Nurse and Patient CRUD operations.
- Appointment scheduling and management.
- Department management.
- Ward, room and bed allocation.
- Responsive navigation and page refresh behavior.
- Validation, authorization and database persistence.

Backend tests can be run using `mvn test`, and the frontend production build can be verified with `npm run build`.

## 🔮 Future Enhancements

- Nurse login and role-specific dashboard.
- Advanced appointment scheduling.
- Hospital analytics and reporting.
- Email and SMS notifications.
- Cloud deployment of the complete application.

## 🎓 Academic Project

**Project Name:** Hospital Management System

**Project Type:** College Full-Stack Web Development Project

**Technology:** React.js + Java Spring Boot + MySQL

**Purpose:** Academic learning, full-stack development and demonstration.

---

Developed as a college project to demonstrate modern frontend development, REST API integration, relational database design and secure user authentication.