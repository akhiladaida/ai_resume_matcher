CREATE TABLE candidates(
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(255) NOT NULL ,
    email VARCHAR(255),
    phone VARCHAR(255),
    location VARCHAR(50)
);


ALTER TABLE resumes
ADD COLUMN candidate_id BIGINT;

ALTER TABLE resumes
ADD CONSTRAINT fk_candidate
FOREIGN KEY(candidate_id)
REFERENCES candidates(id);
