package dao.impl;

import dao.AppDao;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl extends AppDao<Employee> {
    @Override
    public boolean create(Employee employee) {
        String SQL = "INSERT INTO Employee(name, salary, hire_date, department) values(?, ?, ?, ?);";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, employee.getName());
            pstmt.setBigDecimal(2, employee.getSalary());
            pstmt.setDate(3, employee.getHireDate());
            pstmt.setString(4, employee.getDepartment());
            pstmt.executeUpdate();
            System.out.printf("Employee %s is created successfully...%n", employee);
            return true;
        } catch (SQLException e) {
            System.out.printf("Employee insertion failed: %s%n", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Employee employee) {
        String SQL = "UPDATE Employee\n" +
                "   SET name = ?, salary = ?, hire_date = ?, department = ?\n" +
                " WHERE id = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, employee.getName());
            pstmt.setBigDecimal(2, employee.getSalary());
            pstmt.setDate(3, employee.getHireDate());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setInt(5, employee.getId());
            pstmt.executeUpdate();
            System.out.printf("Employee %s is updated successfully...%n", employee);
            return true;
        } catch (SQLException e) {
            System.out.printf("Employee update failed: %s%n", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Employee employee) {
        String SQL = "DELETE FROM Employee " +
                    "WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, employee.getId());
            pstmt.executeUpdate();
            System.out.printf("Employee %s is deleted successfully...%n", employee);
            return true;
        } catch (SQLException e) {
            System.out.printf("Employee deletion failed: %s%n", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Employee> getAll() {
        String QUERY = "SELECT * FROM Employee;";
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY);) {
            while (rs.next()) {
                employees.add(Employee.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .salary(rs.getBigDecimal("salary"))
                        .hireDate(rs.getDate("hire_date"))
                        .department(rs.getString("department"))
                        .build());
            }
            System.out.println("All Employees are selected successfully");

        } catch (SQLException e) {
            System.out.printf("Employees selection failed: %s%n", e.getMessage());
            e.printStackTrace();
        }

        return employees;
    }

    @Override
    public Employee getById(Integer id) {
        String SQL = "SELECT * FROM Employee WHERE id = ?;";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL);
        ) {
            ps.setString(1, "");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Employee.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .salary(rs.getBigDecimal("salary"))
                            .hireDate(rs.getDate("hire_date"))
                            .department(rs.getString("department"))
                            .build();
                }
            }
            System.out.println("All Employees are selected successfully");
        } catch (SQLException e) {
            System.out.printf("Employees selection failed: %s%n", e.getMessage());
            e.printStackTrace();
        }

        throw new RuntimeException(String.format("Employee with an id %s was not found in db", id));
    }
}
