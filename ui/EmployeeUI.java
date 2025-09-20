package ui;

import dao.EmployeeDAO;
import model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeUI extends JFrame {
    private EmployeeDAO employeeDAO;
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public EmployeeUI() {
        employeeDAO = new EmployeeDAO();
        setTitle("Employee Management System");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Department", "Salary"}, 0);
        employeeTable = new JTable(tableModel);
        refreshTable();

        // Buttons
        JButton addBtn = new JButton("Add Employee");
        JButton updateBtn = new JButton("Update Salary");
        JButton deleteBtn = new JButton("Delete Employee");
        JButton refreshBtn = new JButton("Refresh");
        JButton searchBtn = new JButton("Search by Department");
        JButton avgBtn = new JButton("Average Salary");

        // Button actions
        addBtn.addActionListener(e -> addEmployeeDialog());
        updateBtn.addActionListener(e -> updateSalaryDialog());
        deleteBtn.addActionListener(e -> deleteEmployee());
        refreshBtn.addActionListener(e -> refreshTable());
        searchBtn.addActionListener(e -> searchByDepartment());
        avgBtn.addActionListener(e -> showAverageSalary());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(avgBtn);

        add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // clear table
        List<Employee> employees = employeeDAO.getAllEmployees();
        for (Employee emp : employees) {
            tableModel.addRow(new Object[]{emp.getId(), emp.getName(), emp.getDepartment(), emp.getSalary()});
        }
    }

    private void addEmployeeDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField deptField = new JTextField();
        JTextField salaryField = new JTextField();

        Object[] message = {
                "ID:", idField,
                "Name:", nameField,
                "Department:", deptField,
                "Salary:", salaryField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Employee emp = new Employee(
                    Integer.parseInt(idField.getText()),
                    nameField.getText(),
                    deptField.getText(),
                    Double.parseDouble(salaryField.getText())
            );
            employeeDAO.addEmployee(emp);
            refreshTable();
        }
    }

    private void updateSalaryDialog() {
        int row = employeeTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee first!");
            return;
        }
        int empId = (int) employeeTable.getValueAt(row, 0);
        String newSalary = JOptionPane.showInputDialog(this, "Enter new salary:");
        if (newSalary != null) {
            employeeDAO.updateSalary(empId, Double.parseDouble(newSalary));
            refreshTable();
        }
    }

    private void deleteEmployee() {
        int row = employeeTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee first!");
            return;
        }
        int empId = (int) employeeTable.getValueAt(row, 0);
        employeeDAO.deleteEmployee(empId);
        refreshTable();
    }

    private void searchByDepartment() {
        String dept = JOptionPane.showInputDialog(this, "Enter Department to Search:");
        if (dept != null && !dept.trim().isEmpty()) {
            tableModel.setRowCount(0); // clear table
            List<Employee> employees = employeeDAO.getEmployeesByDepartment(dept);
            for (Employee emp : employees) {
                tableModel.addRow(new Object[]{emp.getId(), emp.getName(), emp.getDepartment(), emp.getSalary()});
            }
        }
    }

    private void showAverageSalary() {
        double avg = EmployeeDAO.getAverageSalary();
        JOptionPane.showMessageDialog(this, "Average Salary: " + avg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmployeeUI().setVisible(true);
        });
    }
}
