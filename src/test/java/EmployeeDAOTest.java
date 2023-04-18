import org.example.Employee;
import org.example.EmployeeDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class EmployeeDAOTest {
    private static final String URL = "jdbc:mysql://localhost:3306/db";


    Employee expectedInsertIntoDbPositiveResult;
    Employee actualInsertIntoDbPositiveResult;

    Employee expectedInsertIntoDbNegativeResult;
    Employee actualInsertIntoDbNegativeResult;


    Employee expectedGetFromDbPositiveResult;
    Employee actualGetFromDbPositiveResult;

    Employee expectedGetFromDbNegativeResult;
    Employee actualGetFromDbNegativeResult;

    Employee expectedDeleteRecordFromDbPositiveResult;
    Employee actualDeleteRecordFromDbPositiveResult;


    @Before
    public void setup() {
        expectedGetFromDbPositiveResult = new Employee(19, "Test", "7test7", 666.666);
        actualGetFromDbPositiveResult = findById(expectedGetFromDbPositiveResult.getId());

        expectedGetFromDbNegativeResult = new Employee(109, "Test", "7test9", 666.6686);
        actualGetFromDbNegativeResult = actualGetFromDbPositiveResult;

        expectedInsertIntoDbPositiveResult = actualInsertIntoDbRecordPositive(expectedGetFromDbPositiveResult);
        actualInsertIntoDbPositiveResult = findById(expectedInsertIntoDbPositiveResult.getId());

        expectedInsertIntoDbNegativeResult = new Employee(555, "TT", "ccc", 566);
        actualInsertIntoDbNegativeResult = actualInsertIntoDbPositiveResult;

        expectedDeleteRecordFromDbPositiveResult = new Employee(19, "Test", "7test7", 666.666);
        deleteById(expectedDeleteRecordFromDbPositiveResult.getId());
        actualDeleteRecordFromDbPositiveResult = findById(expectedDeleteRecordFromDbPositiveResult.getId());


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
        //  System.out.println(EmployeeDAO.selectAllFromDb().size());
    }

    @Test
    //arba count all ir lyginti
    public void selectAllFromDbNegativeTest() {
        Assert.assertFalse(EmployeeDAO.selectAllFromDb().size() == 0);
    }

    @Test
    public void objGetRecordFromDbPositiveTest() {
        objGetRecordFromDbPositive(expectedGetFromDbPositiveResult, actualGetFromDbPositiveResult);

    }

    @Test
    public void objGetRecordFromDbNegativeTest() {
        objGetRecordFromDbNegative(expectedGetFromDbNegativeResult, actualGetFromDbNegativeResult);
    }

    @Test
    public void insertIntoDbPositiveTest() {
        objInsertIntoDbPositive(expectedInsertIntoDbPositiveResult, actualInsertIntoDbPositiveResult);
    }

    @Test
    public void insertIntoDbNegativeTest() {
        objInsertIntoDbNegative(expectedInsertIntoDbNegativeResult, actualInsertIntoDbNegativeResult);
    }

    //delete test
    @Test
    public void deleteDataPositiveTest() {
        Assert.assertNotEquals(expectedDeleteRecordFromDbPositiveResult, actualDeleteRecordFromDbPositiveResult);

    }


    public void deleteById(int id) {
        try {
            String query = "DELETE FROM employee WHERE id = ?";
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

            connection.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error while deleting " + e.getMessage());
        }
    }

    public void objInsertIntoDbPositive(Employee expectedInsertIntoDbPositiveResult, Employee actualInsertIntoDbPositiveResult) {
        Assert.assertEquals(expectedInsertIntoDbPositiveResult.getId(), actualInsertIntoDbPositiveResult.getId());
        Assert.assertEquals(expectedInsertIntoDbPositiveResult.getName(), actualInsertIntoDbPositiveResult.getName());
        Assert.assertEquals(expectedInsertIntoDbPositiveResult.getSurname(), expectedInsertIntoDbPositiveResult.getSurname());
        Assert.assertEquals(expectedInsertIntoDbPositiveResult.getSalary(), actualInsertIntoDbPositiveResult.getSalary(), 2);
    }

    public void objInsertIntoDbNegative(Employee expectedInsertIntoDbNegativeResult, Employee actualInsertIntoDbNegativeResult) {
        Assert.assertNotEquals(expectedInsertIntoDbNegativeResult.getId(), actualInsertIntoDbNegativeResult.getId());
        Assert.assertNotEquals(expectedInsertIntoDbNegativeResult.getName(), actualInsertIntoDbNegativeResult.getName());
        Assert.assertNotEquals(expectedInsertIntoDbNegativeResult.getSurname(), actualInsertIntoDbNegativeResult.getSurname());
        Assert.assertNotEquals(expectedInsertIntoDbNegativeResult.getSalary(), actualInsertIntoDbNegativeResult.getSalary(), 2);
    }

    public void objGetRecordFromDbPositive(Employee expected, Employee actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getSurname(), actual.getSurname());
        Assert.assertEquals(expected.getSalary(), actual.getSalary(), 2);
    }

    public void objGetRecordFromDbNegative(Employee expected, Employee actual) {
        Assert.assertNotEquals(expected.getId(), actual.getId());
        Assert.assertNotEquals(expected.getName(), actual.getName());
        Assert.assertNotEquals(expected.getSurname(), actual.getSurname());
        Assert.assertNotSame(expected.getSalary(), actual.getSalary());

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

    public Employee actualInsertIntoDbRecordPositive(Employee employee) {
        String query = "INSERT INTO employee VALUES (?,?,?,?)";

        try {

            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getName());
            statement.setString(3, employee.getSurname());
            statement.setDouble(4, employee.getSalary());
            statement.executeUpdate();

            connection.close();
            statement.close();

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
            System.out.println("Error   " + e.getMessage());
        }

        return employee;

    }


    @After
    public void tearDown() {
        //aprasomi veiksmai po kiekvieno testo
        //uzdaromi prisijungimai prie DB, txt failu

    }
}
