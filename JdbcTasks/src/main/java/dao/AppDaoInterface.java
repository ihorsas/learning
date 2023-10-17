package dao;

import java.util.Arrays;
import java.util.List;

public interface AppDaoInterface {
    String DB_BASE_URL = "jdbc:mysql://127.0.0.1/test";
    String DB_URL = "jdbc:mysql://127.0.0.1/test";
    String DB_NAME = "test";
    List<String> TABLES = Arrays.asList("Employee");

    String CREATE_EMPLOYEE_TABLE = "CREATE TABLE `Employee` (\n"
            + "  `id` int unsigned NOT NULL AUTO_INCREMENT,\n"
            + "  `name` varchar(64) DEFAULT NULL,\n"
            + "  `salary` decimal(10,0) DEFAULT NULL,\n"
            + "  `hire_date` date DEFAULT NULL,\n"
            + "  `department` varchar(64) DEFAULT NULL,\n"
            + "  PRIMARY KEY (`id`)\n"
            + ") AUTO_INCREMENT=5;";
    String USER = "root";
    String PASS = "";

    boolean createDatabase();
    boolean createTables();

}
