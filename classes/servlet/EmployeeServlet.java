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
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            
            Employee emp = gson.fromJson(sb.toString(), Employee.class);
            
            JsonObject result = new JsonObject();
            if (employeeDAO.updateEmployee(emp)) {
                result.addProperty("success", true);
                result.addProperty("message", "Employee updated successfully");
                result.add("data", gson.toJsonTree(emp));
            } else {
                result.addProperty("success", false);
                result.addProperty("message", "Failed to update employee");
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        
        try {
            String idStr = request.getParameter("id");
            JsonObject result = new JsonObject();
            
            if (idStr != null) {
                int id = Integer.parseInt(idStr);
                if (employeeDAO.deleteEmployee(id)) {
                    result.addProperty("success", true);
                    result.addProperty("message", "Employee deleted successfully");
                } else {
                    result.addProperty("success", false);
                    result.addProperty("message", "Failed to delete employee");
                }
            } else {
                result.addProperty("success", false);
                result.addProperty("message", "Employee ID is required");
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
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}