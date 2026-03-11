CREATE TABLE resumes (
                         id BIGSERIAL PRIMARY KEY,
                         file_name VARCHAR(255) NOT NULL,
                         content TEXT NOT NULL,
                         uploaded_at TIMESTAMP DEFAULT NOW()
);
