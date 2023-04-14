package org.example;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/db";

    public static void create(Employee employee) {
        String query = "INSERT INTO `employee`( `name`, `surname`, `salary`) VALUES (?,?,?)";

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setDouble(3, employee.getSalary());
            statement.executeUpdate();
            System.out.println("Data stored successfully");
            connection.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error at INSERTION " + e.getMessage());
        }
    }

    public static ArrayList<Employee> selectAllFromDb() {
        String query = "SELECT * FROM employee";
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                double salary = resultSet.getDouble("salary");

                employees.add(new Employee(id, name, surname, salary));
            }

            connection.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Error while selecting ALL " + e.getMessage());
        }
        return employees;
    }

    public static ArrayList<Employee> searchByEmployeeName(String empName) {
        String query = "SELECT * FROM employee WHERE name LIKE ?";
        ArrayList<Employee> employee = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, empName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                double salary = resultSet.getDouble("salary");

                employee.add(new Employee(id, name, surname, salary));
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error while searcing by employee name " + e.getMessage());
        }
        return employee;
    }

    public static void update(Employee emp, int id) {
        String query = "UPDATE `employee` SET `name`=?,`surname`=?,`salary`=? WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, emp.getName());
            statement.setString(2, emp.getSurname());
            statement.setDouble(3, emp.getSalary());
            statement.setInt(4, id);

            statement.executeUpdate();
            statement.close();
            connection.close();
            System.out.println("Data successfully updated");
        } catch (SQLException e) {
            System.out.println("Error while updating employee " + e.getMessage());
        }

    }

    public static void delete(int id) {
        String query = "DELETE FROM `employee` WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("\nRecord with id= " + id + " successfully removed");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error while deleting " + e.getMessage());
        }


    }

    public static int countEmployeesByName(String name) {
        int count = 0;
        String query = "SELECT COUNT(id) as count FROM employee WHERE name LIKE ?";

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt("count");
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error while counting employee by name " + e.getMessage());
        }
        return count;
    }

}
