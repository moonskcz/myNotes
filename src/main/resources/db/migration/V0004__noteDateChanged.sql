ALTER TABLE notes
ADD COLUMN changed_at timestamp with time zone;

ALTER TABLE notes
ADD COLUMN created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL;