# MOMDB

MOMDB (My Own Movie Database) is a web application designed to be a smaller, personal version of IMDb (Internet Movie Database). It allows users to browse movies, manage watchlists, write reviews, and get recommendations. The project includes a full database schema, data, and a web application with multiple user roles.

## Project Structure

The repository is organized into the following main directories:

-   `database/`: Contains all database-related files, including schema, data, and diagrams.
-   `web/`: The Java Spring web application source code.
-   `!boceto/`: Design sketches and wireframes for the application.
-   `!entrega/`: Project deliverable files.

---

## Database

The `database` directory holds the core of the movie data.

-   **Entity-Relationship Diagram (`er/`)**: Includes the ER diagrams in various formats (`.png`, Visual Paradigm `.vpp`, MySQL Workbench `.mwb`) that define the database structure.
-   **Schema (`scripts/creation/`)**: The `MOMDB.sql` file contains the SQL script to create all the necessary tables, relationships, and constraints.
-   **Data (`scripts/data/`)**:
    -   `real_data.sql`: SQL script to populate the database with real movie data.
    -   `synthetic_data.sql`: SQL script to populate the database with generated synthetic data for testing and development.
-   **Data Management (`scripts/removal/`)**: Scripts to clear database contents or drop all tables.
-   **Data Processing (`datasets/`)**: Includes an R Markdown file (`db_distill.Rmd`) used for data extraction and cleaning.

---

## Web Application

The web application is built using Java and the Spring Framework.

### Technology Stack

-   **Backend**: Java, Spring Boot, Spring MVC, Spring Data JPA, Hibernate.
-   **Frontend**: JSP (JavaServer Pages), HTML, CSS, JavaScript.
-   **Build Tool**: Apache Maven.

### Features & User Roles

The application supports different types of users, each with specific permissions and features:

-   **User**: The standard user role. Can browse movies, add them to a watchlist or favorites, write reviews, and view their profile.
-   **Recommender**: A "pro" user who can also provide movie recommendations to other users.
-   **Editor**: Can edit movie details, add new people (actors, directors), and manage cast and crew information.
-   **Analyst**: Has access to data analytics, can view aggregated statistics about movies and genres, and compare movies.
-   **Admin**: Has full control over the application, including user management and managing a list of "generic entities" like genres, keywords, production companies, etc.

### How to Run

1.  **Set up the Database**:
    -   Create a database (e.g., MySQL).
    -   Run the `database/scripts/creation/MOMDB.sql` script to create the tables.
    -   (Optional) Populate the database using `real_data.sql` or `synthetic_data.sql`.
2.  **Configure the Application**:
    -   Modify `web/momdb/src/main/resources/application.properties` to point to your database (URL, username, password).
3.  **Build and Run**:
    -   Navigate to the `web/momdb/` directory.
    -   Run the application using Maven:
        ```bash
        mvn spring-boot:run
        ```
    -   The application should be accessible at `http://localhost:8080`.
