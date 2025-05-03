# MySketchApp


TAGS TO ADD: Java-11 Maven, PostgrSQL, Codecov, Codefactor

MySketchApp is a Java desktop application that allows users to create and edit drawings. A cloud based server is implemented to allow for 



This project follows the Model - View - Control design pattern to allow for ...




## Technologies
- Architechture: Apache Maven
- GUI: Java Swing
- Database: PostgreSQL
- Testing: JUnit, Docker

## Database

The cloud based PostgreSQL database for this project is hosted on Amazon Web Service servers through an account with Supabase.

databaseSetup.sql contains the statements used to initialize the tables in the database.

## Testing
Thourough testing of the Model and Data Access Object classes was performed using JUnit.

To test the Data Access Object classes, Docker is used to create and destroy a local PostgreSQL server whenever tests are run.

## Install instructions

The Apache Maven shade plugin was used to package the entire project and all dependencies into a single JAR file.

The desktop application is distributed as a runnable JAR file. Download the latest release here. A Java Runtime Environment (JRE) is required to run the JAR file.



## Screenshots