package servlet;

import dao.EmployeeDAO;
import model.Employee;

import com.google.gson.Gson; // Add gson-2.x.jar to WEB-INF/lib
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;

    @Override
    public void init() {
        employeeDAO = new EmployeeDAO();
    }

    // GET → return JSON list
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        List<Employee> employees = employeeDAO.getAllEmployees();
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(employees));
    }

    // POST → add employee
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        Employee emp = new Gson().fromJson(sb.toString(), Employee.class);
        employeeDAO.addEmployee(emp);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    // DELETE → delete employee
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            employeeDAO.deleteEmployee(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
