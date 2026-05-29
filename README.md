# 🎓 University Enrollment System

A CLI-based University Course Enrollment System built in Java, focused on Object-Oriented Programming principles.

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
 │   └── UniversitySystem.java
 └── exception/
     ├── CourseFullException.java
     ├── CourseNotFoundException.java
     ├── CreditLimitExceededException.java
     ├── DuplicateCourseException.java
     ├── InvalidInputException.java
     ├── ScheduleConflictException.java
     └── UserNotFoundException.java
```

---

## 🧠 OOP Concepts Used

| Concept | Where |
|--------|-------|
| **Inheritance** | `Student`, `Professor`, `Admin` extend `User` |
| **Abstract Class** | `User` with abstract `showMenu()` |
| **Interface** | `Searchable` implemented by `User` and `Course` |
| **Polymorphism** | `user.showMenu()` called without knowing user type |
| **Encapsulation** | All fields private with getters/setters |
| **Static Utility Class** | `UniversitySystem` as in-memory database |

---

## ⚠️ Enrollment Rules

1. **No duplicate courses** — `DuplicateCourseException`
2. **Max 20 credits per term** — `CreditLimitExceededException`
3. **No schedule conflicts** — `ScheduleConflictException`
4. **Capacity limit** — `CourseFullException`

---

## 🕐 Waitlist System

- If a course is full, student can join the waitlist (`Queue<Student>`)
- When a student drops a course, the first student in the waitlist is automatically enrolled
- Auto-enrollment checks credit limit and schedule conflicts

---

## 🚀 How to Run

1. Clone the repository:
```bash
git clone https://github.com/SuperSH85/university-system.git
```

2. Open in IntelliJ IDEA or any Java IDE

3. Run `Main.java`

4. Default admin credentials:
```
Name: admin
Password: admin
```

---

## 👤 User IDs

| Type | Format | Example |
|------|--------|---------|
| Admin | `A-1` | `A-1` |
| Professor | `PRO-1` | `PRO-1` |
| Student | `STU-1` | `STU-1` |

---

## 🛠️ Tech Stack

- **Language:** Java
- **Storage:** In-memory (ArrayList, Queue)
- **Interface:** CLI (Command Line)

---

## 📚 Course Info Format

```
CRS-1 | Math | 3 credits | MONDAY 10:00-12:00
```

---

*Built by Shervin — A.P Java Homework*
