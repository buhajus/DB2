import org.example.Employee;
import org.example.EmployeeDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.ldap.PagedResultsControl;
import java.sql.*;

public class EmployeeDAOTest {
    private static final String URL = "jdbc:mysql://localhost:3306/db";


    boolean expectedPositiveResult;

    boolean expectedNegativeResult;

    Employee expectedCreatePositiveResult;
    Employee actualCreatePositiveResult;

    @Before
    public void setup() {
        expectedPositiveResult = true;
        expectedNegativeResult = false;

        expectedCreatePositiveResult = new Employee(5, "Test", "7test7", 666.666);
     //   actualCreatePositiveResult = new Employee(999, "Test", "7test7", 666.666);

        actualCreatePositiveResult = actualCreateRecordPositive(5);
        System.out.println(actualCreatePositiveResult);

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

    public Employee actualCreateRecordPositive(int id) {
        Employee employee = null;
        String query = "SELECT * FROM employee WHERE name LIKE '"+id+"'";
try {

    Connection connection = DriverManager.getConnection(URL, "root", "");
    PreparedStatement statement = connection.prepareStatement(query);

    ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
        employee = new Employee(resultSet.getInt(id));
    }


}catch (SQLException e){

}



        return employee;
    }


    @After
    public void tearDown() {
        //aprasomi veiksmai po kiekvieno testo
        //uzdaromi prisijungimai prie DB, txt failu

    }
}
