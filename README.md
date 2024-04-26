Setup Instructions
To set up and run the application locally, follow these steps:

Clone the Repository
Navigate to project directory 
Build the project 
" mvn clean install"
Run application 
mvn spring-boot:run

Personally I used Intellij IDEA Ultimate which allows me to run the application within a click 
You should have some prerequisites installed basically a JAVA HOME and that's all !!
You clone the project in you local environment all the dependencies mentioned in the maven file will get downloaded once the project is cloned and opened by you 
You can connect with any Databases also within a click maybe it be H2 in memory database or PostgreSQL remote database 


Endpoints
The following endpoints are available in the application:
Authors: /authors
GET /authors: Get all authors
POST /authors: Create a new author
Books: /books
GET /books: Get all books
POST /books: Create a new book
PATCH /books/{id}: Update a book
Rentals: /rentals
GET /rentals: Get all rentals
POST /rentals: Create a new rental

Much more API's will be available since the prject is still under production 

Creating a new Author and testing the API through Postman can be as simple as just providing the JSON object and then using the GET endpoint to check whether the uthor was saved or not 

For example 

POST /authors
Content-Type: application/json

{
  "name": "John Doe",
  "biography": "A prolific writer in various genres."
}


Design Decisions:
Used Spring Boot for rapid development and easy integration with Spring Data JPA for database operations.
Employed a layered architecture with controllers, services, repositories, and DTOs to ensure separation of concerns and maintainability.
Implemented RESTful API endpoints for CRUD operations to interact with the application.
Implemented centralized error handling using Spring's @RestControllerAdvice to handle exceptions globally.
Customized error responses to provide meaningful error messages and appropriate HTTP status codes for better client understanding and debugging.
Conducted comprehensive unit tests using JUnit and Mockito to verify core business logic, RESTful endpoints, and error handling.


If you are curious and wanna review the code yourself feel free
I am always open to learn from others and grow more 
Thank you :)
