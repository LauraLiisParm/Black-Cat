# Black-Cat backend
A sub-functionality of the food delivery application, which
calculates the delivery fee for food couriers.

## Project Requirements
- Java 21
- H2 Database
H2 is included as a dependency in this project, so you do not need to install it separately. It runs in-memory by default, but it can also be configured to persist data on disk.

## Running the app
**./gradlew bootrun**

runs the app

**./gradlew test**

runs the tests

## Key Dependencies
- Java 21: Offers improved performance, enhanced security features, and new language enhancements that make development faster and more efficient.
- Spring Framework: Provides powerful features for dependency injection, data access, transaction management, and more.
- Hibernate: Used as the Object-Relational Mapping (ORM) tool to interact with the underlying database.
- H2 Database: Utilized for testing purposes, ensuring isolated and fast tests without impacting the production database.

 **Links**:
 
[Weather data source](https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php)

[Additional data source](https://www.ilmateenistus.ee/teenused/ilmainfo/eesti-vaatlusandmed-xml/)

 
