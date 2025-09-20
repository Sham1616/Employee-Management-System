package dao;

import db.DBConnection;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    //  Get all employees
   public List<Employee> getAllEmployees() {
    List<Employee> employees = new ArrayList<>();
    String sql = "SELECT emp_id, name, department, salary FROM employees";

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            int id = rs.getInt("emp_id");          // or rs.getInt(1)
            String name = rs.getString("name"); 
            String department = rs.getString("department");
            double salary = rs.getDouble("salary");

            Employee emp = new Employee(id, name, department, salary); // ✅ correct constructor
            employees.add(emp);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return employees;
}

    //  Get employees by department
    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    //  Get average salary by department
    public double getAverageSalary(String department) {
        String sql = "SELECT AVG(salary) as avg_salary FROM employees WHERE department = ?";
        double avgSalary = 0;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                avgSalary = rs.getDouble("avg_salary");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avgSalary;
    }

    //  Insert a new employee
    public void addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (emp_id, name, department, salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, emp.getId());
            stmt.setString(2, emp.getName());
            stmt.setString(3, emp.getDepartment());
            stmt.setDouble(4, emp.getSalary());
            stmt.executeUpdate();
            System.out.println("Employee added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  Update employee salary
    public void updateSalary(int id, double newSalary) {
        String sql = "UPDATE employees SET salary=? WHERE emp_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newSalary);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee salary updated!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  Delete employee
    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE emp_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee deleted!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
