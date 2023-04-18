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
    Employee expectedGetFromDbPositiveResult;
    Employee actualGetFromDbPositiveResult;

    @Before
    public void setup() {
        expectedGetFromDbPositiveResult = new Employee(19, "Test", "7test7", 666.666);
        actualGetFromDbPositiveResult = findById(expectedGetFromDbPositiveResult.getId());

        expectedInsertIntoDbPositiveResult = actualInsertIntoDbRecordPositive(expectedGetFromDbPositiveResult);
        actualInsertIntoDbPositiveResult = findById(expectedInsertIntoDbPositiveResult.getId());


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
    public void objCreateRecordPositiveTest() {
        objCreateRecordPositive(expectedGetFromDbPositiveResult, actualGetFromDbPositiveResult);

    }

    @Test
    public void insertIntoDbPositiveTest(){
        objInsertIntoDbPostive(expectedInsertIntoDbPositiveResult,actualInsertIntoDbPositiveResult);
    }

    public void objInsertIntoDbPostive(Employee expectedInsertIntoDbPositiveResult, Employee actualInsertIntoDbPositiveResult){
        Assert.assertEquals(expectedInsertIntoDbPositiveResult.getId(),actualInsertIntoDbPositiveResult.getId());
        Assert.assertEquals(expectedInsertIntoDbPositiveResult.getName(),actualInsertIntoDbPositiveResult.getName());
        Assert.assertEquals(expectedInsertIntoDbPositiveResult.getSurname(),expectedInsertIntoDbPositiveResult.getSurname());
        Assert.assertEquals(expectedInsertIntoDbPositiveResult.getSalary(),actualInsertIntoDbPositiveResult.getSalary(),2);
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
