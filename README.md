# MyJavaSketchbook
![Java](https://img.shields.io/badge/Java-11-blue.svg)
![Maven](https://img.shields.io/badge/build-Maven-blue)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)
[![CodeFactor](https://www.codefactor.io/repository/github/joshd898/myJavaSketchbook/badge)](https://www.codefactor.io/repository/github/joshd898/myJavaSketchbook)
![Code Size](https://img.shields.io/github/languages/code-size/joshD898/myJavaSketchbook)

**MyJavaSketchbook** is a Java desktop application that enables users to create, edit, and manage digital drawings through an guided user interface. The application supports cloud-based storage, allowing users to securely save and access their artwork accross multiple devices from anywhere with an internet connection.

## Technologies

- **Architechture:** Apache Maven
- **User Interface:** Java Swing
- **Database:** PostgreSQL (cloud-hosted via AWS and Supabase)
- **Testing:** JUnit with Docker-based PostgreSQL for integration testing

## Install instructions

The application is packaged into a uber-JAR file using the Apache Maven Shade Plugin, bundling all the required dependencies into a single file.

To run the application:

1. Download the [latest release](https://github.com/JoshD898/myJavaSketchbook/releases/tag/1.0).
2. Run the JAR file with a Java Runtime Environment (JRE 11 or higher).

## Screenshots

<table>
  <tr>
    <td><img src="./screenshots/login.png" width="500"/></td>
    <td><img src="./screenshots/create-account.png" width="500"/></td>
  </tr>
  <tr>
    <td><img src="./screenshots/edit.png" width="500"/></td>
    <td><img src="./screenshots/gallery-view.png" width="500"/></td>
  </tr>
</table>
