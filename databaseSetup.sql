-- These are the statements used to set up the tables in the PostgreSQL database

CREATE TABLE Users (
    userID SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Drawings (
    drawingID SERIAL PRIMARY KEY,
    userID INTEGER REFERENCES Users(userID),
    title VARCHAR(255),
    drawing_bytes BYTEA NOT NULL
)