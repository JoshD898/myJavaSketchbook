package org.joshd898.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractDatabaseTest {
    protected Connection connection;
    private String containerName;

    @BeforeAll
    // Use Docker to launch a temporary SQL server, then form a connection
    public void startPostgreSQLServer() throws Exception {
        containerName = getClass().getSimpleName().toLowerCase();

        runCommand("docker", "run", "--name", containerName, "-e", "POSTGRES_USER=testuser",
                "-e", "POSTGRES_PASSWORD=testpass", "-e", "POSTGRES_DB=testdb",
                "-p", "5432:5432", "-d", "postgres");

        Thread.sleep(2000); // Wait 2 seconds for the database to initialize

        String psqlString = "postgres://testuser:testpass@localhost:5432/testdb";
        URI dbUri = new URI(psqlString);
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        connection = DriverManager.getConnection(dbUrl, username, password);

        Statement statement = connection.createStatement();

        statement.executeUpdate(
            "CREATE TABLE Users ("
                + "userID SERIAL PRIMARY KEY,"
                + "username VARCHAR(255) NOT NULL UNIQUE," 
                + "password VARCHAR(255) NOT NULL"
            + ");"
        );

        statement.executeUpdate(
            "CREATE TABLE Drawings ("
                + "drawingID SERIAL PRIMARY KEY,"
                + "userID INTEGER REFERENCES Users(userID),"
                + "title VARCHAR(255),"
                + "drawing_bytes BYTEA NOT NULL"
            + ");"
        );

        statement.close();
    }

    @AfterAll
    // Close the connection to the server then delete the server
    public void stopPostgreSQLServer() throws IOException, SQLException, InterruptedException {
        connection.close();
        runCommand("docker", "rm", "-f", containerName);
    }

    // Helper function to run a command from the terminal
    private void runCommand(String... command) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
