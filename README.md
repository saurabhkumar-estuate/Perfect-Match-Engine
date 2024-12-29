# Perfect-Match-Engine
The Perfect Match Engine is a Spring Boot-based application designed to find and recommend top user matches based on predefined rules such as gender, age difference, and shared interests.

#Features
REST API to fetch top user matches for a given user.
Matchmaking logic based on:
Opposite gender prioritization.
Age difference penalty.
Shared interests bonus.
Hardcoded user data for simplicity.

#Technologies Used
Java (version 8 or higher)
Spring Boot
Lombok (for data classes)

#Project Structure
plaintext
Copy code
src/main/java/com/est/perfect/engine
├── PerfectMatchEngineApplication.java  // Main application class
├── controller
│   └── UserController.java            // REST controller for user-related APIs
├── service
│   └── RecommendationService.java     // Business logic for matchmaking
├── user
│   └── User.java                      // Data model for users

#Getting Started
Prerequisites
Java Development Kit (JDK 8 or higher)
Maven
An IDE (e.g., IntelliJ IDEA, Eclipse, or Spring Tool Suite)


#Setup Instructions
Clone this repository:

bash
Copy code
git clone https://github.com/your-repo/perfect-match-engine.git
Navigate to the project directory:

bash
Copy code
cd perfect-match-engine
Build the project using Maven:

bash
Copy code
mvn clean install
Run the application:

bash
Copy code
mvn spring-boot:run
The application will start on http://localhost:8080.

#API Endpoints
Get Matches for a User
URL: GET /api/users/{userId}/matches

#Parameters:

userId (Path Variable): ID of the user to find matches for.
topN (Query Parameter): Number of top matches to return.
Response:
A list of users sorted by their matchmaking score.

#Example Request:

http
Copy code
GET /api/users/1/matches?topN=3
Example Response:

json
Copy code
[
  {
    "id": 2,
    "name": "User 2",
    "gender": "Male",
    "age": 27,
    "interests": ["Cricket", "Football", "Movies"]
  },
  {
    "id": 3,
    "name": "User 3",
    "gender": "Male",
    "age": 26,
    "interests": ["Movies", "Tennis", "Football", "Cricket"]
  },
  {
    "id": 5,
    "name": "User 5",
    "gender": "Female",
    "age": 32,
    "interests": ["Cricket", "Football", "Movies", "Badminton"]
  }
]

#Business Logic
Gender Rule: Opposite gender matches are given a bonus score of 10.
Age Rule: A penalty is applied based on the absolute age difference between users.
Interest Rule: A bonus is awarded for each shared interest.
Score Calculation:

The score is calculated as:
score = 10 (if opposite gender) - ageDifference + matchingInterests

Note: Scores are negated for sorting purposes so that higher scores rank higher.

#Future Improvements
Integrate a database to store user data.
Add user registration and update functionality.
