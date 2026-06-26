CREATE TABLE users (
    id          TEXT PRIMARY KEY ,
    name        TEXT NOT NULL,
    password    TEXT NOT NULL,
    role        TEXT NOT NULL CHECK(role IN ('ADMIN', 'PROFESSOR', 'STUDENT'))
);

CREATE TABLE courses (
    course_id       TEXT PRIMARY KEY ,
    title           TEXT NOT NULL,
    credits         INTEGER NOT NULL,
    capacity        INTEGER NOT NULL,
    day_of_week     TEXT NOT NULL,
    start_time      TEXT NOT NULL,
    end_time        TEXT NOT NULL,
    professor_id    TEXT NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES users(id)
);

CREATE TABLE enrollments (
    student_id      TEXT NOT NULL,
    course_id       TEXT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

CREATE TABLE waitlist (
    student_id      TEXT NOT NULL,
    course_id       TEXT NOT NULL,
    request_time    TEXT NOT NULL DEFAULT (datetime('now')),
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

INSERT INTO users (id, name, password, role)
VALUES ('A-1', 'admin', 'admin', 'ADMIN');