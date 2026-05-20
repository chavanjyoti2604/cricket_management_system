🏏 Spring Boot CRUD App: Batsman & Team Management
This is a backend-only Spring Boot application developed using Spring Boot that provides complete CRUD operations for managing Batsman and Team entities. The application is designed using a layered architecture approach consisting of Controller, Service, and Repository layers, making the project clean, scalable, and easy to maintain. It also supports cloud deployment and follows enterprise-level backend development practices.
The application is successfully deployed on Railway and can be accessed using the following link:
Railway Deployment
This project allows users to create, update, retrieve, and delete batsman and team records efficiently. A one-to-many relationship is implemented where one team can contain multiple batsmen. Validation is added using Jakarta Bean Validation to ensure proper data handling and maintain database integrity.
The project includes several important features such as CRUD operations for both Batsman and Team entities, validation checks for fields like batsman name, team name, and batting position, global exception handling for different HTTP errors, and support for both MySQL and H2 databases. The application is also integrated with Postman for API testing and documentation.
The technologies used in this project include Java 17+, Spring Boot 3+, Spring Web, Spring Data JPA, Jakarta Validation, MySQL, H2 Database, Maven, and Postman. These technologies help in building a robust and production-ready REST API application.
The application provides multiple REST API endpoints for managing data. For batsman operations, users can create a new batsman using POST /batsmen, retrieve all batsmen using GET /batsmen, update batsman details using PUT /batsmen/{id}, and delete a batsman using DELETE /batsmen/{id}. Similarly, for team operations, users can create, retrieve, update, and delete team details using /teams endpoints.
A sample request body for creating or updating a batsman is shown below:
{  "batsmanName": "MS Dhoni",  "battingPosition": 7,  "team": {    "teamName": "CSK"  }}
Similarly, a sample request body for creating or updating a team is:

{  "teamName": "MI",  "players": [    {      "batsmanName": "Rohit Sharma",      "battingPosition": 1    },    {      "batsmanName": "Suryakumar Yadav",      "battingPosition": 4    }  ]}
The project also implements proper validations and constraints. The batsmanName and teamName fields cannot be blank and must remain unique in the database. The battingPosition field must contain values only between 1 and 11. If any validation fails, meaningful error responses are returned to the user.
Different types of exception handling are implemented in the application. Validation errors return HTTP 400 status codes, resource-not-found cases return HTTP 404, duplicate entries return HTTP 409, and unexpected server errors return HTTP 500 responses. This improves the reliability and usability of the application.
The project supports both H2 in-memory database and MySQL database configurations. H2 is useful for testing and development purposes, while MySQL is used for persistent cloud-based storage. The application can be easily configured by updating the application.properties file.
To run the project, users can import the project as a Maven project in IntelliJ IDEA or VS Code and run the main Spring Boot application class. The project can also be executed using Maven commands like:
./mvnw clean install./mvnw spring-boot:run
For API testing and documentation, Postman can be used to create collections, add endpoints, save request examples, and publish API documentation online. This makes testing and sharing APIs easier.
👩‍💻 Author
Jyoti Chavan
Backend Developer | Java & Spring Boot Enthusiast
GitHub:https://github.com/chavanjyoti2604
