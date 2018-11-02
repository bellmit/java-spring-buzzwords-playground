CREATE TABLE authentication_token (
  authentication_token VARCHAR(255) PRIMARY KEY,
  authentication_subject_id UUID,
  authentication_expires_at TIMESTAMP WITH TIME ZONE
);