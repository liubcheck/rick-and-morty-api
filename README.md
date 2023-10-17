# Rick and Morty API
This is a simple Spring Boot application which works with an external **Rick and Morty API**

## Features

- Retrieve information about a random **Rick & Morty** character.
- Fetch all characters whose names contain a given string.
- Regularly update the character database from an external service.
- Interact with the API using a local PostgreSQL database.

## Project Architecture

The **Rick and Morty application** follows a three-tier architectural structure:

- **DAO (Data Access Object)**: Responsible for handling character information updates and specific queries.
- **Services**: Contains the business logic of the application.
- **Controller**: Handles incoming requests, invokes services, and sends responses.

## Technologies & Tools

The project uses the following technologies and tools:

- [Rick and Morty API Documentation](https://rickandmortyapi.com/documentation/)
- Java 17
- Git
- Apache Maven
- Spring Boot
- PostgreSQL
- Swagger
- Checkstyle plugin
- Docker

## How to Run

Follow these steps to run the Rick and Morty API:

1. **Prerequisites:**
   - Ensure that you have Docker and Docker Compose installed on your system.

2. **Clone the Repository:**
   - Clone the Rick and Morty API repository to your local machine using Git:

     ```shell
     git clone <repository-url>
     cd rick-and-morty-api
     ```

3. **Set Environment Variables:**
   - Create a **.env** file (if it doesn't already exist) in the project's root directory and set the necessary environment variables in the similar way:

     ```shell
     POSTGRES_USER=postgres
     POSTGRES_PASSWORD=12345678
     POSTGRES_DB=rickandmorty
     POSTGRES_LOCAL_PORT=5433
     POSTGRES_DOCKER_PORT=5432

     SPRING_LOCAL_PORT=6868
     SPRING_DOCKER_PORT=8080
     DEBUG_PORT=5005
     ```

4. **Build and Start the Containers:**
   - Open a terminal in the project's root directory and run the following command to build and start the Docker containers defined in the docker-compose.yml file:

     ```shell
     docker-compose up --build
     ```

5. **Access the API:**
   - Once the containers are running, you can access the Rick and Morty API at the following endpoints:
      - API Swagger Documentation: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
      - API Endpoints: [http://localhost:8080/api/v1/characters](http://localhost:8080/api/v1/characters)
      - Health Check Endpoint: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

6. **Stop the Containers:**
   - To stop the running containers, open a terminal in the project's root directory and press `Ctrl + C` to stop the containers gracefully.
