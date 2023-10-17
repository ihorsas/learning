package utils;

import model.Employee;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Random;
import java.util.UUID;

public class Utils {
    public static Employee getRandomEmployee() {
        return Employee.builder()
                .salary(new BigDecimal(new Random().nextInt() / 100000.0))
                .hireDate(new Date(System.currentTimeMillis()))
                .department("IT")
                .name(String.valueOf(UUID.randomUUID()))
                .build();
    }
}
