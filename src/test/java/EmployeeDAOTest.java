import org.example.Employee;
import org.example.EmployeeDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.ldap.PagedResultsControl;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAOTest {
    private static final String URL = "jdbc:mysql://localhost:3306/db";
    private EmployeeDAO dao;

    //boolean expectedPositiveResult;

    //boolean expectedNegativeResult;

     Employee expectedCreatePositiveResult;
      Employee actualCreatePositiveResult;

    @Before
    public void setup() {
        //   expectedPositiveResult = true;
        // expectedNegativeResult = false;

         expectedCreatePositiveResult = new Employee(19, "Test", "7test7", 666.666);
       // System.out.println( expectedCreatePositiveResult.getId());

        //dao.create(expectedCreatePositiveResult);

         actualCreatePositiveResult = findById(expectedCreatePositiveResult.getId());
      System.out.println(actualCreatePositiveResult.getId());
        System.out.println(actualCreatePositiveResult.getName());
        System.out.println(actualCreatePositiveResult.getSurname());
        System.out.println(actualCreatePositiveResult.getSalary());

        //   Employee actualCreatePositiveResult = expectedCreatePositiveResult.getId();

        //   actualCreatePositiveResult = new Employee(999, "Test", "7test7", 666.666);

    //    actualCreatePositiveResult = actualCreateRecordPositive(5);
       // System.out.println(actualCreatePositiveResult);

    }

    @Test
    public void testConnectionToDbPositiveTest() {
        Assert.assertTrue(connectToDbPositive());
    }

    @Test
    public void testConnectionToDbNegativeTest() {
        Assert.assertFalse(connectToDbNegative());
    }

    @Test
    public void selectAllFromDbPositiveTest() {
        Assert.assertTrue(EmployeeDAO.selectAllFromDb().size() > 0);
        System.out.println(EmployeeDAO.selectAllFromDb().size());
    }

    @Test
    //arba count all ir lyginti
    public void selectAllFromDbNegativeTest() {
        Assert.assertFalse(EmployeeDAO.selectAllFromDb().size() == 0);
    }

    @Test
    public void objCreateRecordPositiveTest() {
        objCreateRecordPositive(expectedCreatePositiveResult, actualCreatePositiveResult);

    }

    public void objCreateRecordPositive(Employee expectedCreatePositiveResult, Employee actualCreatePositiveResult) {
        Assert.assertEquals(expectedCreatePositiveResult.getId(), actualCreatePositiveResult.getId());
         Assert.assertEquals(expectedCreatePositiveResult.getName(), actualCreatePositiveResult.getName());
         Assert.assertEquals(expectedCreatePositiveResult.getSurname(), actualCreatePositiveResult.getSurname());
        Assert.assertEquals(expectedCreatePositiveResult.getSalary(), actualCreatePositiveResult.getSalary(), 2);
    }

    public boolean connectToDbPositive() {
        try {

            Connection connection = DriverManager.getConnection(URL, "root", "");
            if (connection.isValid(1)) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error with connection to DB" + e.getMessage());
        }
        return false;
    }

    public boolean connectToDbNegative() {
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            if (connection.isValid(1))
                return false;
        } catch (SQLException e) {
            System.out.println("Error with connection to DB" + e.getMessage());
        }


        return true;
    }

    public Employee actualCreateRecordPositive(int idP) {
        String query = "SELECT * FROM employee WHERE id = ?";
        Employee employee = null;
        try {

            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idP);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                double salary = resultSet.getDouble("salary");

                employee = new Employee(id, name, surname, salary);

            }


        } catch (SQLException e) {

        }


        return employee;
    }

    public Employee findById(int id) {
        String query = "SELECT * FROM employee WHERE id = ?";
        Employee employee = new Employee();
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                 employee = new Employee(resultSet.getInt("id"),
                                        resultSet.getString("name"),
                                        resultSet.getString("surname"),
                                        resultSet.getDouble("salary"));
            }
        } catch (SQLException e) {
            System.out.println("Error   "+ e.getMessage());
        }

        return employee;

    }


    @After
    public void tearDown() {
        //aprasomi veiksmai po kiekvieno testo
        //uzdaromi prisijungimai prie DB, txt failu

    }
}
