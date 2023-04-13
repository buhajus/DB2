package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Employee employee = new Employee("Jonas", "Rodmans", 10053.25);

        //  EmployeeDAO.create(employee);
        EmployeeDAO.update(employee, 2);


        for (Employee emp : EmployeeDAO.selectAllFromDb()) {
            System.out.println(emp);
        }
        String search = "Jonas";
        System.out.println("\nsearch by name " + search);
        for (Employee emp : EmployeeDAO.searchByEmployeeName(search)) {
            System.out.println(emp);
        }

        //   EmployeeDAO.delete(5);

        System.out.println("Search result with employee " + search + ": " + EmployeeDAO.countEmployeesByName(search));

    }
}