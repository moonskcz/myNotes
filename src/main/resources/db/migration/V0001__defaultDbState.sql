CREATE TABLE users (
    id serial PRIMARY KEY,
    userName VARCHAR ( 50 ) UNIQUE NOT NULL,
    email VARCHAR ( 50 ) UNIQUE NOT NULL,
    auth VARCHAR (255)
);

CREATE TABLE notes (
    id serial PRIMARY KEY,
    userId INT NOT NULL,
    content VARCHAR (2048),
    FOREIGN KEY (userId)
          REFERENCES users (id)
)