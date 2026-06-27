# 🎓 University Enrollment System
A CLI-based University Course Enrollment System built in Java, focused on Object-Oriented Programming principles with persistent SQLite database storage.

---

## 📌 Features

### 👤 Admin
- Add new courses to the system
- Register new users (Students, Professors, Admins)

### 👨‍🏫 Professor
- View list of courses they teach
- View enrolled students in a specific course

### 👨‍🎓 Student
- View all available courses
- Enroll in a course
- Drop a course
- View enrolled courses with total credits
- Join waitlist when a course is full

---

## 🏗️ Project Structure

```
src/
 ├── Main.java
 ├── model/
 │   ├── Searchable.java
 │   ├── user/
 │   │   ├── User.java
 │   │   ├── Admin.java
 │   │   ├── Professor.java
 │   │   └── Student.java
 │   └── course/
 │       ├── Course.java
 │       └── CourseTime.java
 ├── database/
 │   ├── DBConnection.java
 │   ├── UserDAO.java
 │   ├── CourseDAO.java
 │   ├── EnrollmentDAO.java
 │   └── WaitlistDAO.java
 └── exception/
     ├── CourseFullException.java
     ├── CourseNotFoundException.java
     ├── CreditLimitExceededException.java
     ├── DuplicateCourseException.java
     ├── InvalidInputException.java
     ├── OperationCancelledException.java
     ├── ScheduleConflictException.java
     └── UserNotFoundException.java
schema.sql
```

---

## 🧠 OOP Concepts Used

| Concept | Where |
|--------|-------|
| **Inheritance** | `Student`, `Professor`, `Admin` extend `User` |
| **Abstract Class** | `User` with abstract `showMenu()` and `handleMenu()` |
| **Interface** | `Searchable` implemented by `User` and `Course` |
| **Polymorphism** | `user.showMenu()` called without knowing user type |
| **Encapsulation** | All fields private with getters/setters |
| **DAO Pattern** | Separate DAO classes for each DB table |

---

## 🗄️ Database Structure

| Table | Description |
|-------|-------------|
| `users` | All users (Admin, Professor, Student) with role column |
| `courses` | All courses with professor reference |
| `enrollments` | Student-course many-to-many junction table |
| `waitlist` | Waitlist queue ordered by request_time |

---

## ⚠️ Enrollment Rules

1. **No duplicate courses** — `DuplicateCourseException`
2. **Max 20 credits per term** — `CreditLimitExceededException`
3. **No schedule conflicts** — `ScheduleConflictException`
4. **Capacity limit** — `CourseFullException`

---

## 🕐 Waitlist System

- If a course is full, student joins the waitlist
- Waitlist order is preserved by `request_time` in the database
- When a student drops a course, the first student in the waitlist is automatically enrolled
- Auto-enrollment checks credit limit and schedule conflicts

---

## 🚀 How to Run

### 1. Clone the repository
```bash
git clone https://github.com/SuperSH85/university-system.git
```

### 2. Add the JDBC Driver
Download and add to your project libraries:
- [sqlite-jdbc-3.53.2.0.jar](https://github.com/xerial/sqlite-jdbc/releases)

In IntelliJ: `File → Project Structure → Libraries → + → Java → select the jar`

### 3. Set up the Database
- Create a new SQLite database file named `university.db` in the project root
- Run `schema.sql` to create the tables and seed the default admin

### 4. Configure VM Options
Add this to your run configuration VM options:
```
--enable-native-access=ALL-UNNAMED
```

### 5. Run `Main.java`

Default admin credentials:
```
Name: admin
Password: admin
```

---

## 👤 User IDs

| Type | Format | Example |
|------|--------|---------|
| Admin | `A-{n}` | `A-1` |
| Professor | `PRO-{n}` | `PRO-2` |
| Student | `STU-{n}` | `STU-3` |

---

## 🛠️ Tech Stack

- **Language:** Java
- **Storage:** SQLite via JDBC
- **Interface:** CLI (Command Line)
- **Driver:** xerial/sqlite-jdbc 3.53.2.0

---

## 📚 Course Info Format

```
CRS-1 | Math | 3 credits | MONDAY 10:00-12:00
```

---

## 🔄 Resetting the Database

Run the following in DataGrip or DB Browser:

```sql
DELETE FROM waitlist;
DELETE FROM enrollments;
DELETE FROM courses;
DELETE FROM users;

INSERT INTO users (id, name, password, role)
VALUES ('A-1', 'admin', 'admin', 'ADMIN');
```

---

*Built by Shervin — A.P Java Homework*
