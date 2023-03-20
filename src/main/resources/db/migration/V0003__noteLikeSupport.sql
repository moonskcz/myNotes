CREATE TABLE likes (
    user_id INT,
    note_id INT,
    FOREIGN KEY (user_id)
              REFERENCES users (id),
    FOREIGN KEY (note_id)
              REFERENCES notes (id),
    PRIMARY KEY (user_id, note_id)
);