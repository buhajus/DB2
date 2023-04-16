package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Employee employee = new Employee(99, "Test", "7test7", 666.666);

         EmployeeDAO.create(employee);
        EmployeeDAO.update(employee, 2);

        if (EmployeeDAO.selectAllFromDb().isEmpty())
            System.out.println("There is no data");
        else {
            System.out.println("List of all employees:");
            for (Employee emp : EmployeeDAO.selectAllFromDb()) {

                System.out.println(emp);
            }

        }


        String search = "Jonas";
        System.out.println("\nsearch by name " + search);
        for (Employee emp : EmployeeDAO.searchByEmployeeName(search)) {
            System.out.println(emp);
        }

        //   EmployeeDAO.delete(5);

        System.out.println("Search results with employee " + search + ": " + EmployeeDAO.countEmployeesByName(search));

    }
}