import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;
import java.util.Random;

public class Main {
    static final String DB_URL = "jdbc:mysql://127.0.0.1/test";
    static final String USER = "root";
    static final String PASS = "";
    static final String SQL = "SELECT * FROM Employee;";
    static final String QUERY = "DESCRIBE Employee";

    public static void main(String[] args) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_UPDATABLE);
//        ResultSet rs = stmt.executeQuery(QUERY);
        ) {
//            stmt.setInt(1, 1);
            try (ResultSet rs = stmt.executeQuery()) {
                printResults(rs);

                rs.moveToInsertRow();
                rs.updateInt("id", 2);
                rs.updateString("name", "Sas");
                rs.updateBigDecimal("salary", new BigDecimal(new Random().nextInt() / 100000.0));
                rs.updateDate("hire_date", new Date(System.currentTimeMillis()));
                rs.updateString("department", "IT");
                rs.insertRow();

                printResults(rs);

                rs.moveToInsertRow();
                rs.previous();
                rs.deleteRow();
            }
            //for calling INSERT, UPDATE, or DELETE
//      stmt.executeUpdate()
            // Extract data from result set

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printResults(ResultSet rs) throws SQLException {
        rs.beforeFirst();
        while (rs.next()) {
            // Retrieve by column name
            System.out.println("\nRow before update");
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Salary: " + rs.getBigDecimal("salary"));
            System.out.println("hire_date: " + rs.getDate("hire_date"));
            System.out.println("department: " + rs.getString("department"));

            rs.updateBigDecimal("salary", new BigDecimal(new Random().nextInt() / 100000.0));
            rs.updateRow();
            System.out.println("\nRow after update");

            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Salary: " + rs.getBigDecimal("salary"));
            System.out.println("hire_date: " + rs.getDate("hire_date"));
            System.out.println("department: " + rs.getString("department"));
        }
    }
}