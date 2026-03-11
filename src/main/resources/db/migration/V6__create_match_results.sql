CREATE TABLE match_results (
                               id BIGSERIAL PRIMARY KEY,
                               resume_id BIGINT NOT NULL,
                               jd_id BIGINT NOT NULL,
                               final_score INT NOT NULL,
                               keyword_score INT NOT NULL,
                               semantic_score INT NOT NULL,
                               matched_skills TEXT,
                               missing_skills TEXT,
                               created_at TIMESTAMP NOT NULL DEFAULT NOW(),

                               CONSTRAINT fk_resume FOREIGN KEY (resume_id) REFERENCES resumes(id),
                               CONSTRAINT fk_jd FOREIGN KEY (jd_id) REFERENCES job_descriptions(id)
);
