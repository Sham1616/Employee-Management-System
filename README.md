# 🏢 Employee Management System (Full-Stack Java Application)

A comprehensive Employee Management System built with Java, MySQL, and modern web technologies. This project demonstrates full-stack development with JDBC, Servlets, JSP, and a responsive frontend. Perfect for learning enterprise Java development patterns including DAO, MVC architecture, and RESTful web services.

## 🚀 Enhanced Features

### Core Functionality
- **Complete CRUD Operations** - Create, Read, Update, Delete employees
- **Department-wise Filtering** - View employees by specific departments
- **Salary Analytics** - Calculate and display department-wise average salaries
- **Real-time Statistics** - Dashboard showing total employees, departments, and salary metrics
- **Search & Filter** - Advanced filtering capabilities by department

### Technical Enhancements
- **RESTful Web Services** - JSON-based API endpoints using Servlets
- **Modern Web Interface** - Responsive HTML5/CSS3/JavaScript frontend
- **DAO Design Pattern** - Clean separation of data access logic
- **MVC Architecture** - Proper separation of concerns
- **CORS Support** - Cross-origin resource sharing for API calls
- **Error Handling** - Comprehensive exception handling and user feedback
- **Database Connection Pooling** - Efficient database resource management

## 🛠️ Tech Stack

### Backend
- **Language:** Java (JDK 8+)
- **Database:** MySQL 8.x
- **Web Framework:** Jakarta Servlets & JSP
- **JSON Processing:** Google Gson
- **JDBC Driver:** MySQL Connector/J

### Frontend
- **HTML5/CSS3** - Modern responsive design
- **Vanilla JavaScript** - Interactive user interface
- **CSS Grid & Flexbox** - Advanced layout techniques
- **Responsive Design** - Mobile-friendly interface

## 📂 Enhanced Project Structure

```
src/
 ├── db/
 │   └── DBConnection.java          # Database connection management
 ├── dao/
 │   └── EmployeeDAO.java           # Data Access Object with CRUD operations
 ├── model/
 │   └── Employee.java              # Employee entity (POJO)
 ├── servlet/
 │   └── EmployeeServlet.java       # RESTful API endpoints
 └── Main.java                      # Console application (legacy)

webapp/
 ├── index.html                     # Modern responsive frontend
 ├── employee-list.jsp              # JSP view (alternative interface)
 ├── css/
 │   └── style.css                  # Stylesheets
 └── js/
     └── script.js                  # Frontend JavaScript

resources/
 └── lib/
     └── mysql-connector-j-*.jar    # JDBC driver
```

## 📑 Database Setup (MySQL)

```sql
-- Create Database
CREATE DATABASE employee_management;
USE employee_management;


-- Employees Table
CREATE TABLE  employee_management(
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    join_date DATE DEFAULT (CURRENT_DATE)
);

-- Insert sample data
INSERT INTO employee_management (name, department, salary) VALUES
('Alice Johnson', 'IT', 75000),
('Bob Smith', 'Finance', 65000),
('Charlie Brown', 'HR', 55000),
('David Miller', 'IT', 80000),
('Emma Davis', 'Finance', 70000),
('Frank Wilson', 'Marketing', 60000),
('Grace Taylor', 'Finance', 68000),
('Henry Moore', 'IT', 85000),
('Ivy Clark', 'HR', 52000),
('Jack Lewis', 'Marketing', 58000);
```


## ⚙️ Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/Sham1616/EmployeeManagementSystem.git
cd EmployeeManagementSystem
```

### 2. Database Configuration
- Update `classes/db/DBConnection.java` with your MySQL credentials:
```java
private static final String URL = "jdbc:mysql://localhost:3306/employee_management";
private static final String USER = "root";  
private static final String PASSWORD = "your_password";
```

### 3. Add Dependencies
- Download [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
- Download [Google Gson](https://github.com/google/gson) for JSON processing
- Place JAR files in the `lib/` directory

### 4. Compilation Options

#### Console Application
```bash
# Windows
javac -cp "lib/*;." classes/Main.java
java -cp "lib/*;classes" Main

# Linux/Mac
javac -cp "lib/*:." classes/Main.java
java -cp "lib/*:classes" Main
```

#### Web Application
Deploy to a servlet container like Apache Tomcat:
1. Create a WAR file with your compiled classes
2. Deploy to Tomcat's webapps directory
3. Access via `http://localhost:8080/EmployeeManagement/`

### 5. Standalone Frontend (Development Mode)
For frontend development without backend:
- Open `index.html` directly in a browser
- Uses localStorage for data persistence
- Includes sample data for testing

## 🎯 Application Interfaces

### 1. Modern Web Interface (`index.html`)
- **Dashboard Statistics** - Visual overview of employee data
- **Interactive Forms** - Add/Edit employees with validation
- **Responsive Tables** - Sortable and filterable employee lists
- **Department Analytics** - Real-time salary calculations
- **Toast Notifications** - User feedback for all operations

### 2. RESTful API Endpoints (`EmployeeServlet`)
```
GET    /api/employees              # Get all employees
GET    /api/employees?department=X # Get employees by department
GET    /api/employees?id=X         # Get employee by ID
GET    /api/employees?action=departments # Get all departments
GET    /api/employees?action=avgSalary&department=X # Get avg salary
POST   /api/employees              # Add new employee
PUT    /api/employees              # Update employee
DELETE /api/employees?id=X         # Delete employee
```

### 3. JSP Interface (`employee-list.jsp`)
- Server-side rendering with JSP
- Form-based employee management
- Traditional MVC pattern implementation

### 4. Console Interface (`Main.java`)
- Menu-driven command-line interface
- Direct database operations
- Educational/debugging purposes

## 📊 Sample Screenshots

### Interface
<img width="1917" height="972" alt="image" src="https://github.com/user-attachments/assets/150ecb03-1355-4373-9814-1fb5f219866a" />
<img width="1918" height="971" alt="image" src="https://github.com/user-attachments/assets/d6e7a929-7cdc-47c7-bf4a-8039c62d5011" />

## 🏗️ Architecture Patterns Implemented

### 1. **Model-View-Controller (MVC)**
- **Model:** `Employee.java` - Data representation
- **View:** HTML/JSP pages - User interface
- **Controller:** `EmployeeServlet.java` - Request handling

### 2. **Data Access Object (DAO)**
- `EmployeeDAO.java` - Encapsulates database operations
- Clean separation between business logic and data access
- Reusable and testable data operations

### 3. **RESTful Web Services**
- HTTP methods mapping (GET, POST, PUT, DELETE)
- JSON request/response format
- Stateless communication
- CORS support for cross-origin requests

### 4. **Repository Pattern**
- Centralized data access logic
- Abstract database operations
- Easy to mock for testing

## 📚 Learning Outcomes

### Backend Development
- **JDBC Programming** - Database connectivity and operations
- **Servlet Development** - HTTP request/response handling
- **JSON Processing** - Data serialization/deserialization
- **Design Patterns** - DAO, MVC, Repository patterns
- **Exception Handling** - Robust error management
- **Database Design** - Relational database modeling

### Frontend Development
- **Responsive Web Design** - Mobile-first approach
- **Modern CSS** - Grid, Flexbox, animations
- **DOM Manipulation** - Dynamic content updates
- **AJAX Communications** - Asynchronous web requests
- **User Experience** - Intuitive interface design

### Full-Stack Integration
- **API Design** - RESTful service architecture
- **Client-Server Communication** - HTTP protocols
- **Data Flow** - Request-response lifecycle
- **Cross-Origin Requests** - CORS implementation

## 🔧 Prerequisites

- **Java Development Kit (JDK 8+)**
- **MySQL Server 8.x**
- **Apache Tomcat 9+ (for web interface)**
- **MySQL Connector/J driver**
- **Google Gson library**
- **Modern web browser** (Chrome, Firefox, Safari, Edge)
- **Basic knowledge of Java, SQL, HTML, CSS, JavaScript**

## 🚦 Getting Started

1. **Set up the database** using the SQL scripts provided
2. **Configure database connection** in `DBConnection.java`
3. **Add required JAR dependencies** to your classpath
4. **Choose your interface:**
   - Console: Run `Main.java`
   - Web: Deploy to Tomcat and access `index.html`
   - API: Use REST endpoints with tools like Postman


## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

**Sham1616** - [GitHub Profile](https://github.com/Sham1616)

---

⭐ **If this project helped you learn full-stack Java development, please give it a star!**

---

*This project demonstrates enterprise-level Java development practices and is perfect for students, beginners, and professionals looking to understand full-stack web development with Java technologies.*
