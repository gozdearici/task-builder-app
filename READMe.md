
# 🌟 Full Stack Web Application (React + Spring Boot)
[![Java](https://img.shields.io/badge/Java-21+-red?logo=java&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3+-brightgreen?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18+-61DAFB?logo=react&logoColor=white)](https://react.dev/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue?logo=docker&logoColor=white)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-latest-blue?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

# Task Builder App

---

This project is a **Full Stack Web Application** built with 
- **Frontend: React** 
- **Backend: Spring Boot**

It demonstrates a clean architecture, RESTful API integration, and containerized deployment with Docker.

---
## 🛠️ Tech Stack

| Layer | Technology                                            |
|-------|-------------------------------------------------------|
| **Frontend** | React.js, Axios, Vite (or CRA)                        |
| **Backend** | Java 21+, Spring Boot 3+, Spring Web, Spring Data JPA |
| **Database** | PostgreSQL (or H2 for test environment)               |
| **Containerization** | Docker & Docker Compose                               |

---

## 📁 Project Structure
```yaml
project-root/
│
├── backend/
│ ├── src/
│ │ ├── main/java/com/example/app/
│ │ ├── main/resources/
│ │ └── test/
│ ├── pom.xml
│ └── Dockerfile
│
├── frontend/
│ ├── src/
│ ├── public/
│ ├── package.json
│ └── Dockerfile
│
├── docker-compose.yml
└── README.md
```
---

## Running Locally

### Backend
```bash
cd tasks
mvn spring-boot:run
```
Backend runs on -> http://localhost:8081

### Frontend
```bash
cd tasks-frontend
npm install
npm run dev
```

Frontend runs on -> http://localhost:5173

## 🐳 Run with Docker 
1️⃣ Build and start containers

At the backend of the project:
```bash
cd tasks
docker-compose up --build
```

At the frontend of the project:
```bash
cd tasks-frontend
docker-compose up --build
```

3️⃣ Stop each container
docker-compose down


## 🚀 Notes

Make sure Docker, Java 17+, and Node.js 18+ are installed.

- If using PostgreSQL locally, update your credentials in:
    - application.properties (backend)
    - .env (optional for frontend)

- When using Docker, backend and database automatically connect via internal Docker network.
- Frontend adapted from [Devtiro](https://github.com/Devtiro) template. Modified for this project.

## 👩‍💻 Author
**💼 Role:** Full Stack Developer  
**📧 Contact:** gozdenurarici@gmail.com

![Author](https://img.shields.io/badge/By-Gözde_Yıldız-lightpink?style=flat&logo=spring&logoColor=white)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?logo=linkedin&style=flat-square)](https://www.linkedin.com/in/gozdenurarici/)



