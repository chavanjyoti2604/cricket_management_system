🏏 Spring Boot CRUD App: Batsman & Team Management

This is a backend-only Spring Boot application that provides CRUD operations for managing Batsman and Team entities.

✅ Deployed on Railway:
Railway Deployment

🔗 Use the above URL to interact with the API endpoints via tools like Postman or cURL.

A Spring Boot application to manage Batsmen and their Teams with full CRUD operations, validation, error handling, and ready integration with both MySQL or H2. This backend is designed for scalability, cloud deployment, and enterprise-grade coding practices.

📦 Features
Full CRUD operations for Batsman and Team entities
One-to-many relationship: One Team → Many Batsmen
Validation using Jakarta Bean Validation:
Unique names for both entities
battingPosition must be between 1 and 11
Required fields check (like names and batting position)
Clear and concise error handling (400, 404, 409, 500)
Layered architecture (Controller → Service → Repository)
MySQL and H2 support
Postman API documentation support
⚙️ Tech Stack
Java 17+
Spring Boot 3+
Spring Web
Spring Data JPA
Jakarta Validation
MySQL / H2 Database
Maven
Postman (API Testing)
📬 API Endpoints
🟦 Batsman APIs
POST /batsmen → Create new batsman
GET /batsmen → Get all batsmen
PUT /batsmen/{id} → Update batsman by ID
DELETE /batsmen/{id} → Delete batsman by ID
Sample Batsman Request (POST / PUT)
{
  "batsmanName": "MS Dhoni",
  "battingPosition": 7,
  "team": {
    "teamName": "CSK"
  }
}
🟩 Team APIs
POST /teams → Create a new team
GET /teams → Get all teams
PUT /teams/{id} → Update team by ID
DELETE /teams/{id} → Delete team by ID
Sample Team Request (POST / PUT)
{
  "teamName": "MI",
  "players": [
    {
      "batsmanName": "Rohit Sharma",
      "battingPosition": 1
    },
    {
      "batsmanName": "Suryakumar Yadav",
      "battingPosition": 4
    }
  ]
}
❗ Validations & Constraints
batsmanName → Not blank, must be unique
battingPosition → Must be between 1 and 11 inclusive
teamName → Not blank, must be unique
🚫 Error Handling
400 → Validation Failed
{
  "battingPosition": "Must be <= 11"
}
404 → Resource Not Found
{
  "error": "Team 'MI' not found"
}
409 → Duplicate Entry
{
  "error": "Batsman already exists"
}
500 → Internal Server Error
{
  "error": "Internal Server Error"
}
🧪 Database Setup
H2 Database (In-Memory)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

➡ Visit: http://localhost:8080/h2-console

MySQL Database
spring.datasource.url=jdbc:mysql://interchange.proxy.rlwy.net:53065/railway
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
🔧 Build & Run
Using Maven
./mvnw clean install
./mvnw spring-boot:run
Using IntelliJ IDEA / VS Code
Import project as Maven Project
Run DemoApplication.java
📒 Postman API Documentation
Open Postman
Click Import
Create collection → Batsman-Team App
Add all API endpoints with request body examples
Save the collection
Click ... on Collection → View in Web
Click Publish Docs
Optional Postman Test Script
pm.test("Status code is 200", function () {
  pm.response.to.have.status(200);
});
👩‍💻 Author

Jyoti Chavan
Backend Developer | Java & Spring Boot Enthusiast

GitHub:https://github.com/chavanjyoti2604
