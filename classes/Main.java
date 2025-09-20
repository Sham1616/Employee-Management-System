import dao.EmployeeDAO;
import model.Employee;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Employee Management System ===");
            System.out.println("1. View All Employees");
            System.out.println("2. View Employees by Department");
            System.out.println("3. Get Average Salary by Department");
            System.out.println("4. Add Employee");
            System.out.println("5. Update Salary");
            System.out.println("6. Delete Employee");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    dao.getAllEmployees().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("Enter department: ");
                    String dept = sc.next();
                    dao.getEmployeesByDepartment(dept).forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Enter department: ");
                    dept = sc.next();
                    double avg = dao.getAverageSalary(dept);
                    System.out.println("Average salary in " + dept + " = " + avg);
                    break;
                case 4:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    System.out.print("Enter Name: ");
                    String name = sc.next();
                    System.out.print("Enter Department: ");
                    dept = sc.next();
                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();
                    dao.addEmployee(new Employee(id, name, dept, salary));
                    break;
                case 5:
                    System.out.print("Enter ID: ");
                    id = sc.nextInt();
                    System.out.print("Enter new Salary: ");
                    salary = sc.nextDouble();
                    dao.updateSalary(id, salary);
                    break;
                case 6:
                    System.out.print("Enter ID: ");
                    id = sc.nextInt();
                    dao.deleteEmployee(id);
                    break;
            }
        } while (choice != 0);

        sc.close();
    }
}
