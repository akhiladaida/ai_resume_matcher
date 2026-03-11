CREATE TABLE job_descriptions (
                                  id BIGSERIAL PRIMARY KEY,
                                  title VARCHAR(255) NOT NULL,
                                  description TEXT NOT NULL,
                                  must_have_skills TEXT NOT NULL,
                                  nice_to_have_skills TEXT
);
