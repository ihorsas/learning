import dao.AppDao;
import dao.AppDaoInterface;
import dao.impl.EmployeeDaoImpl;
import model.Employee;

import java.util.List;

import static utils.Utils.getRandomEmployee;

public class App {
    public static void main(String[] args) {
        AppDao<Employee> appDao = new EmployeeDaoImpl();

        // prepare a db
        appDao.createDatabase();
        appDao.createTables();

        // create an employee
        Employee employee = getRandomEmployee();
        appDao.create(employee);

        // get all employees and update the last one
        List<Employee> employees = appDao.getAll();
        Employee employeeToUpdate = employees.get(employees.size() - 1);
        employeeToUpdate.setDepartment("Non-IT");
        appDao.update(employeeToUpdate);
        System.out.println(appDao.getAll());

        // delete just updated employee
        appDao.delete(employeeToUpdate);

        // get employee by id
        employees = appDao.getAll();
        Employee employeeToGet = employees.get(employees.size() - 1);
        System.out.println(appDao.getById(employeeToGet.getId()));
    }
}
