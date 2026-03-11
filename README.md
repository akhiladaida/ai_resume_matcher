#  AI Resume Matcher

> A Spring Boot backend service that intelligently matches candidate resumes to job descriptions using keyword analysis, semantic embeddings, and LLM-generated explanations.

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![OpenAI](https://img.shields.io/badge/OpenAI-API-412991?style=flat-square&logo=openai&logoColor=white)


---

## 📋 Table of Contents

- [Overview](#-overview)
- [Architecture](#-architecture)
- [How Matching Works](#-how-matching-works)
- [Tech Stack](#-tech-stack)
- [API Reference](#-api-reference)
- [Getting Started](#-getting-started)
- [Project Structure](#-project-structure)
- [Future Roadmap](#-future-roadmap)

---

## 🔍 Overview

Recruiters reviewing dozens of resumes for a single role spend hours on manual comparison. **AI Resume Matcher** automates that process with a transparent, explainable scoring pipeline.

The backend:
- Ingests and parses candidate PDF resumes
- Stores and manages job descriptions with required skill sets
- Computes a weighted match score using rule-based keyword analysis and semantic vector similarity
- Generates concise, recruiter-facing explanations via an LLM
- Exposes dashboard-ready APIs for ranking, history, and candidate detail views

> **Design principle:** The LLM explains scores — it does not determine them. All scoring is handled by deterministic backend logic, keeping the system auditable, debuggable, and cost-efficient.

---

## 🏗 Architecture

```
Resume PDF
    │
    ▼
Text Extraction (Apache Tika)
    │
    ▼
Store Resume in DB
    │
    ▼
Load Job Description
    │
    ├──────────────────────────────┐
    ▼                              ▼
Keyword Score                Semantic Score
(Skill matching)             (Embeddings + Cosine Similarity)
    │                              │
    └──────────────┬───────────────┘
                   ▼
          Weighted Final Score
          (70% keyword + 30% semantic)
                   │
                   ▼
        LLM Explanation (OpenAI)
                   │
                   ▼
         Store & Return Result
```

The system is built as a **pure backend service**. A lightweight Streamlit UI is included solely for local testing and demonstration purposes.

---

## 🧠 How Matching Works

### 1. Keyword Score

The backend checks how many of the job's `mustHaveSkills` appear in the resume text.

```
Must-have skills: Java, Spring Boot, SQL, Docker
Resume contains:  Java, Spring Boot, SQL

keywordScore = (3 / 4) × 100 = 75
```

### 2. Semantic Score

Keyword matching misses synonyms and paraphrasing (e.g., *"REST APIs"* vs *"web services"*). To address this, the backend generates vector embeddings for both the resume and job description, then computes **cosine similarity**.

```java
double similarity = SimilarityUtil.cosineSimilarity(resumeVec, jdVec);
int semanticScore = (int) Math.round(similarity * 100);
// e.g. 0.82 → semanticScore = 82
```

### 3. Final Score

Both signals are combined using a weighted formula:

```
finalScore = (0.7 × keywordScore) + (0.3 × semanticScore)
```

**Example:**
| Component      | Value |
|----------------|-------|
| Keyword Score  | 75    |
| Semantic Score | 80    |
| **Final Score**| **77**|

```
(0.7 × 75) + (0.3 × 80) = 52.5 + 24 = 76.5 → 77
```

### 4. LLM Explanation

After scoring, the backend sends structured context to the LLM (job title, JD, resume excerpt, score, matched/missing skills) and receives a plain-language explanation for the recruiter.

**Example output:**
> *"This candidate is a strong match for the Java Backend Engineer role. The resume demonstrates experience in Java, Spring Boot, and SQL, which align with the job's core requirements. The main gap is Docker. Overall, the profile is technically relevant with a minor tooling shortfall."*

**Fallback (no LLM access):**
> *"This candidate matched 3 required skills and is missing 1. Final score: 77."*

---

## 🛠 Tech Stack

| Layer       | Technology                              |
|-------------|-----------------------------------------|
| Language    | Java 17+                                |
| Framework   | Spring Boot 3.x, Spring Data JPA        |
| Database    | PostgreSQL 16                           |
| Migrations  | Flyway                                  |
| PDF Parsing | Apache Tika                             |
| AI / ML     | OpenAI API (embeddings + chat)          |
| Frontend UI | Streamlit (Python) — testing only       |

---

## 📡 API Reference

### Candidates
| Method | Endpoint       | Description              |
|--------|----------------|--------------------------|
| POST   | `/candidates`  | Create a new candidate   |
| GET    | `/candidates`  | List all candidates      |

### Resumes
| Method | Endpoint   | Description                         |
|--------|------------|-------------------------------------|
| POST   | `/resumes` | Upload and parse a PDF resume       |
| GET    | `/resumes` | List all stored resumes             |

### Job Descriptions
| Method | Endpoint | Description                      |
|--------|----------|----------------------------------|
| POST   | `/jds`   | Create a job description         |
| GET    | `/jds`   | List all job descriptions        |

### Matching
| Method | Endpoint                          | Description                          |
|--------|-----------------------------------|--------------------------------------|
| GET    | `/match?resumeId={id}&jdId={id}`  | Run match between a resume and JD    |

### Dashboard
| Method | Endpoint                                   | Description                      |
|--------|---------------------------------------------|----------------------------------|
| GET    | `/dashboard/ranked-candidates?jdId={id}`   | Ranked candidates for a JD       |
| GET    | `/dashboard/candidate/{candidateId}`        | Full candidate detail view       |
| GET    | `/dashboard/match-history?jdId={id}`       | Match history for a JD           |

### Sample Match Response

```json
{
  "score": 77,
  "matchedSkills": ["Java", "Spring Boot", "SQL"],
  "missingSkills": ["Docker"],
  "explanation": "This candidate is a good match because the resume shows strong alignment with Java, Spring Boot, and SQL, which are core job requirements. The main gap is Docker. Overall, the profile demonstrates relevant backend experience with a moderate tooling gap."
}
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- PostgreSQL 16
- OpenAI API key
- Maven 3.8+

### 1. Clone the repository

```bash
git clone https://github.com/your-username/ai-resume-matcher.git
cd ai-resume-matcher
```

### 2. Configure environment

Create an `application.properties` or set environment variables:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/resume_matcher
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

openai.api.key=your_openai_api_key
openai.model=gpt-4o-mini
openai.embedding.model=text-embedding-3-small
```

### 3. Run database migrations

Flyway will automatically apply migrations on startup.

### 4. Build and run

```bash
mvn clean install
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

### 5. (Optional) Run Streamlit frontend

```bash
cd frontend
pip install -r requirements.txt
streamlit run app.py
```

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/resumematcher/
│   │   ├── candidate/        # Candidate management
│   │   ├── resume/           # PDF upload, parsing, storage
│   │   ├── jd/               # Job description management
│   │   ├── matching/         # Scoring pipeline (keyword + semantic)
│   │   ├── explanation/      # LLM explanation generation
│   │   ├── dashboard/        # Aggregated API responses
│   │   └── util/             # SimilarityUtil, etc.
│   └── resources/
│       ├── db/migration/     # Flyway SQL migrations
│       └── application.properties
frontend/
├── app.py                    # Streamlit UI
└── requirements.txt
```

---

## 🗺 Future Roadmap

- [ ] JWT-based recruiter authentication
- [ ] `pgvector` integration for native PostgreSQL vector search
- [ ] Skill normalization and synonym mapping
- [ ] Multi-resume support per candidate
- [ ] Batch matching for large candidate pools
- [ ] Resume improvement recommendations via LLM feedback

---

## 💡 Why This Architecture?

This project deliberately keeps the LLM **out of the scoring loop**. Score computation is fully deterministic — based on skill matching and cosine similarity — making results reproducible, auditable, and cheap to run at scale. The LLM is called once per match, only to translate a structured result into human-readable language.

This separation makes the system production-ready, interview-friendly, and easy to extend.

---


