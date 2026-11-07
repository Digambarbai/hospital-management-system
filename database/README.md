# Database

MySQL is used locally by the Spring Boot backend.

Create the empty development database before starting the backend:

```sql
CREATE DATABASE hospital_management
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

The backend defaults to `root` with an empty password only for local development. Set `DB_USERNAME` and `DB_PASSWORD` in your shell when your MySQL account differs. No hospital tables or seed data are included yet.
