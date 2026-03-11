CREATE TABLE embeddings (
                            id BIGSERIAL PRIMARY KEY,
                            source_type VARCHAR(20) NOT NULL,  -- RESUME or JD
                            source_id BIGINT NOT NULL,         -- resumeId or jdId
                            model VARCHAR(100) NOT NULL,
                            vector_json TEXT NOT NULL,         -- store embedding array as JSON string
                            created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_embeddings_source ON embeddings(source_type, source_id);
