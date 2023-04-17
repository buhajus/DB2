[33mcommit bc981f6b21a9035b60f328af7374309794e4492b[m[33m ([m[1;36mHEAD -> [m[1;32mmaster[m[33m, [m[1;31morigin/master[m[33m)[m
Author: Buhajus <domassteimantas@gmail.com>
Date:   Sun Apr 16 21:17:08 2023 +0300

    v.0.1.1

[1mdiff --git a/.idea/dbnavigator.xml b/.idea/dbnavigator.xml[m
[1mindex 70f212e..f0bf29e 100644[m
[1m--- a/.idea/dbnavigator.xml[m
[1m+++ b/.idea/dbnavigator.xml[m
[36m@@ -2,9 +2,15 @@[m
 <project version="4">[m
   <component name="DBNavigator.Project.DataEditorManager">[m
     <record-view-column-sorting-type value="BY_INDEX" />[m
[31m-    <value-preview-text-wrapping value="true" />[m
[32m+[m[32m    <value-preview-text-wrapping value="false" />[m
     <value-preview-pinned value="false" />[m
   </component>[m
[32m+[m[32m  <component name="DBNavigator.Project.DatabaseBrowserManager">[m
[32m+[m[32m    <autoscroll-to-editor value="false" />[m
[32m+[m[32m    <autoscroll-from-editor value="true" />[m
[32m+[m[32m    <show-object-properties value="true" />[m
[32m+[m[32m    <loaded-nodes />[m
[32m+[m[32m  </component>[m
   <component name="DBNavigator.Project.DatabaseEditorStateManager">[m
     <last-used-providers />[m
   </component>[m
[1mdiff --git a/src/main/java/org/example/Main.java b/src/main/java/org/example/Main.java[m
[1mindex bcd6311..a9f22bf 100644[m
[1m--- a/src/main/java/org/example/Main.java[m
[1m+++ b/src/main/java/org/example/Main.java[m
[36m@@ -5,7 +5,7 @@[m [mimport java.util.ArrayList;[m
 public class Main {[m
     public static void main(String[] args) {[m
 [m
[31m-        Employee employee = new Employee(999, "Test", "7test7", 666.666);[m
[32m+[m[32m        Employee employee = new Employee(99, "Test", "7test7", 666.666);[m
 [m
          EmployeeDAO.create(employee);[m
         EmployeeDAO.update(employee, 2);[m
[1mdiff --git a/src/test/java/EmployeeDAOTest.java b/src/test/java/EmployeeDAOTest.java[m
[1mindex 43ee7e2..a3b8ada 100644[m
[1m--- a/src/test/java/EmployeeDAOTest.java[m
[1m+++ b/src/test/java/EmployeeDAOTest.java[m
[36m@@ -11,28 +11,32 @@[m [mimport java.util.ArrayList;[m
 [m
 public class EmployeeDAOTest {[m
     private static final String URL = "jdbc:mysql://localhost:3306/db";[m
[31m-[m
[32m+[m[32m    private EmployeeDAO dao;[m
 [m
     //boolean expectedPositiveResult;[m
 [m
     //boolean expectedNegativeResult;[m
 [m
[31m-    Employee expectedCreatePositiveResult;[m
[31m-    Employee actualCreatePositiveResult;[m
[32m+[m[32m     Employee expectedCreatePositiveResult;[m
[32m+[m[32m      Employee actualCreatePositiveResult;[m
 [m
     @Before[m
     public void setup() {[m
         //   expectedPositiveResult = true;[m
         // expectedNegativeResult = false;[m
 [m
[31m-        Employee expectedCreatePositiveResult = new Employee(5, "Test", "7test7", 666.666);[m
[32m+[m[32m        Employee expectedCreatePositiveResult = new Employee(999, "Test", "7test7", 666.666);[m
[32m+[m
[32m+[m[32m        //dao.create(expectedCreatePositiveResult);[m
[32m+[m
[32m+[m[32m        Employee actualCreatePositiveResult = findById(expectedCreatePositiveResult.getId());[m
 [m
 [m
[31m-     //   Employee actualCreatePositiveResult = expectedCreatePositiveResult.getId();[m
[32m+[m[32m        //   Employee actualCreatePositiveResult = expectedCreatePositiveResult.getId();[m
 [m
         //   actualCreatePositiveResult = new Employee(999, "Test", "7test7", 666.666);[m
 [m
[31m-        actualCreatePositiveResult = actualCreateRecordPositive(5);[m
[32m+[m[32m    //    actualCreatePositiveResult = actualCreateRecordPositive(5);[m
         System.out.println(actualCreatePositiveResult);[m
 [m
     }[m
[36m@@ -67,9 +71,9 @@[m [mpublic class EmployeeDAOTest {[m
 [m
     public void objCreateRecordPositive(Employee expectedCreatePositiveResult, Employee actualCreatePositiveResult) {[m
         Assert.assertEquals(expectedCreatePositiveResult.getId(), actualCreatePositiveResult.getId());[m
[31m-        // Assert.assertEquals(expectedCreatePositiveResult.getName(), actualCreatePositiveResult.getName());[m
[31m-        // Assert.assertEquals(expectedCreatePositiveResult.getSurname(), actualCreatePositiveResult.getSurname());[m
[31m-        // Assert.assertEquals(expectedCreatePositiveResult.getSalary(), actualCreatePositiveResult.getSalary(), 2);[m
[32m+[m[32m         Assert.assertEquals(expectedCreatePositiveResult.getName(), actualCreatePositiveResult.getName());[m
[32m+[m[32m         Assert.assertEquals(expectedCreatePositiveResult.getSurname(), actualCreatePositiveResult.getSurname());[m
[32m+[m[32m        Assert.assertEquals(expectedCreatePositiveResult.getSalary(), actualCreatePositiveResult.getSalary(), 2);[m
     }[m
 [m
     public boolean connectToDbPositive() {[m
[36m@@ -129,6 +133,29 @@[m [mpublic class EmployeeDAOTest {[m
         return employee;[m
     }[m
 [m
[32m+[m[32m    public Employee findById(int id) {[m
[32m+[m[32m        String query = "SELECT * FROM employee WHERE id = ?";[m
[32m+[m[32m        Employee employee = null;[m
[32m+[m[32m        try {[m
[32m+[m[32m            Connection connection = DriverManager.getConnection(URL, "root", "");[m
[32m+[m[32m            PreparedStatement statement = connection.prepareStatement(query);[m
[32m+[m[32m            statement.setInt(1, id);[m
[32m+[m[32m            ResultSet resultSet = statement.executeQuery();[m
[32m+[m
[32m+[m[32m            if (resultSet.next()) {[m
[32m+[m[32m                employee = new Employee(resultSet.getInt("id"),[m
[32m+[m[32m                        resultSet.getString("name"),[m
[32m+[m[32m                        resultSet.getString("surname"),[m
[32m+[m[32m                        resultSet.getDouble("salary"));[m
[32m+[m[32m            }[m
[32m+[m[32m        } catch (SQLException e) {[m
[32m+[m[32m            System.out.println();[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[32m        return employee;[m
[32m+[m
[32m+[m[32m    }[m
[32m+[m
 [m
     @After[m
     public void tearDown() {[m
