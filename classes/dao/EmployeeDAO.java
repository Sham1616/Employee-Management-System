// =============================================
// JAVA BACKEND - Complete EmployeeDAO.java
// =============================================
package dao;

import db.DBConnection;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDAO {

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary")
                );
                list.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department = ? ORDER BY id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary")
                );
                list.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getAverageSalary(String department) {
        String sql = "SELECT AVG(salary) as avg_salary FROM employees WHERE department = ?";
        double avgSalary = 0.0;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                avgSalary = rs.getDouble("avg_salary");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avgSalary;
    }

    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        Employee emp = null;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                emp = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emp;
    }

    public boolean addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (name, department, salary) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getDepartment());
            stmt.setDouble(3, emp.getSalary());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    emp.setId(keys.getInt(1));
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET name = ?, department = ?, salary = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getDepartment());
            stmt.setDouble(3, emp.getSalary());
            stmt.setInt(4, emp.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSalary(int id, double salary) {
        String sql = "UPDATE employees SET salary = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, salary);
            stmt.setInt(2, id);
            
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllDepartments() {
        List<String> departments = new ArrayList<>();
        String sql = "SELECT DISTINCT department FROM employees ORDER BY department";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                departments.add(rs.getString("department"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departments;
    }

    public int getEmployeeCount() {
        String sql = "SELECT COUNT(*) as count FROM employees";
        int count = 0;
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}

// =============================================
// JAVA BACKEND - Complete EmployeeServlet.java
// =============================================
package servlet;

import dao.EmployeeDAO;
import model.Employee;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/employees")
public class EmployeeServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;
    private Gson gson;

    @Override
    public void init() {
        employeeDAO = new EmployeeDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        
        try {
            String action = request.getParameter("action");
            String department = request.getParameter("department");
            String id = request.getParameter("id");
            
            JsonObject result = new JsonObject();
            
            if ("departments".equals(action)) {
                List<String> departments = employeeDAO.getAllDepartments();
                result.addProperty("success", true);
                result.add("data", gson.toJsonTree(departments));
                
            } else if ("avgSalary".equals(action) && department != null) {
                double avgSalary = employeeDAO.getAverageSalary(department);
                result.addProperty("success", true);
                result.addProperty("data", avgSalary);
                
            } else if (id != null) {
                Employee emp = employeeDAO.getEmployeeById(Integer.parseInt(id));
                if (emp != null) {
                    result.addProperty("success", true);
                    result.add("data", gson.toJsonTree(emp));
                } else {
                    result.addProperty("success", false);
                    result.addProperty("message", "Employee not found");
                }
                
            } else if (department != null) {
                List<Employee> employees = employeeDAO.getEmployeesByDepartment(department);
                result.addProperty("success", true);
                result.add("data", gson.toJsonTree(employees));
                
            } else {
                List<Employee> employees = employeeDAO.getAllEmployees();
                result.addProperty("success", true);
                result.add("data", gson.toJsonTree(employees));
            }
            
            out.write(gson.toJson(result));
            
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("success", false);
            error.addProperty("message", "Error: " + e.getMessage());
            out.write(gson.toJson(error));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        
        try {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            
            Employee emp = gson.fromJson(sb.toString(), Employee.class);
            
            JsonObject result = new JsonObject();
            if (employeeDAO.addEmployee(emp)) {
                result.addProperty("success", true);
                result.addProperty("message", "Employee added successfully");
                result.add("data", gson.toJsonTree(emp));
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                result.addProperty("success", false);
                result.addProperty("message", "Failed to add employee");
            }
            
            out.write(gson.toJson(result));
            
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("success", false);
            error.addProperty("message", "Error: " + e.getMessage());
            out.write(gson.toJson(error));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        
        try {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;