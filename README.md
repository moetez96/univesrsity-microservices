# univesrsity-microservices

This repository contains a microservices application called **university-microservices** developed using Spring Cloud and Eureka, composed of the following microservices:

- **address-microservice:** Runs on port 8083
- **api-gateway:** Runs on port 9090
- **eureka-server:** Runs on port 8761
- **student-microservice:** Runs on port 8083

The application is developed in Java using Spring Boot and uses a MySQL database.

## Prerequisites

Before running the microservices, ensure that you have the following installed:

- Java
- MySQL

## Getting Started

Follow the steps below to set up and run the microservices:

1. **Eureka Server:**

   Run the Eureka server first:
   cd eureka-server
   ./mvnw spring-boot:run

2. **API Gateway:**

   Run the API Gateway:
   cd eureka-server
   ./mvnw spring-boot:run

3. **Microservices:**

   Run the address-microservice and student-microservice:
   cd address-microservice
   ./mvnw spring-boot:run

   cd student-microservice
   ./mvnw spring-boot:run

## API Calls
  **Example API calls:**
    Get Student:
GET http://localhost:9090/student-microservice/api/student/getById/2
    Add Student:
POST http://localhost:9090/student-microservice/api/student/create

{
  "firstName": "foulen",
  "lastName": "ben foulen",
  "email": "foulen@gmail.com",
  "addressId": 1
}

## Notes
Ensure that MySQL is running and the database configurations are set appropriately in each microservice.




