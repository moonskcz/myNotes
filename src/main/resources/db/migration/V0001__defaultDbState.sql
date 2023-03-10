CREATE TABLE users (
    id serial PRIMARY KEY,
    email VARCHAR ( 50 ) UNIQUE NOT NULL,
    auth VARCHAR (255)
);

CREATE TABLE notes (
    id serial PRIMARY KEY,
    user_id INT NOT NULL,
    content VARCHAR (2048),
    FOREIGN KEY (user_id)
          REFERENCES users (id)
)