CREATE TABLE person (
  person_id UUID PRIMARY KEY,
  person_name VARCHAR(128),
  person_created_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE person_authentication (
  person_id UUID PRIMARY KEY REFERENCES person (person_id) ON DELETE CASCADE,
  person_email VARCHAR(256) UNIQUE ,
  person_password_hash VARCHAR(256)
);