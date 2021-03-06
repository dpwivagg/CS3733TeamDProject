package edu.wpi.cs3733.programname.database.ModificationMethods;

import edu.wpi.cs3733.programname.ManageController;
import edu.wpi.cs3733.programname.commondata.Employee;
import edu.wpi.cs3733.programname.commondata.Interpreter;
import edu.wpi.cs3733.programname.database.CsvWriter;
import edu.wpi.cs3733.programname.database.DBConnection;

import java.sql.SQLException;

public class EmployeesMethod {

    private DBConnection dbConnection;
    private CsvWriter wrt;
    public EmployeesMethod(DBConnection dbConnection){
        this.dbConnection = dbConnection;
        this.wrt = new CsvWriter();}

    public void addEmployee(Employee employee){
        String username = employee.getUsername();
        String password = employee.getPassword();
        String firstName = employee.getFirstName();
        String middleName = employee.getMiddleName();
        String lastName = employee.getLastName();
        boolean sysAdmin = employee.getSysAdmin();
        int sysAdminInt = sysAdmin? 1: 0;
        String serviceType = employee.getServiceType();
        String email = employee.getEmail();
        String str;
        try {
            str = "insert into Employees values('" + username + "', '" + password + "', '" + firstName + "', '" + middleName + "', '" + lastName + "'," + sysAdminInt + ", '" + serviceType + "','" + email + "')";
            //System.out.println(str);
            dbConnection.executeUpdate(str);
            this.wrt.writeEmployees(dbConnection.getConnection());
        } catch (SQLException e) {
            System.out.println("Insert Employee Failed!");
            e.printStackTrace();
        }
    }

    public void deleteEmployee(Employee employee){
        String username = employee.getUsername();
        String str;
        try {
            str = "DELETE FROM Employees WHERE username = '" + username + "'";
            System.out.println(str);
            dbConnection.executeUpdate(str);
            this.wrt.writeNodes(dbConnection.getConnection());
        } catch (SQLException e) {
            System.out.println("Delete Employee Failed!");
            e.printStackTrace();
        }
    }

    public void editEmployee(Employee employee) {
        String username = employee.getUsername();
        String password = employee.getPassword();
        String firstName = employee.getFirstName();
        String middleName = employee.getMiddleName();
        String lastName = employee.getLastName();
        boolean sysAdmin = employee.getSysAdmin();
        int sysAdminInt = sysAdmin? 1: 0;
        String serviceType = employee.getServiceType();
        String email = employee.getEmail();
        String str;

        try {
            str = "UPDATE Employees SET password = '" + password + "', firstName = '" + firstName + "', middleName = '" + middleName +
                    "', lastName = '" + lastName + "', sysAdmin = " + sysAdminInt + ", serviceType = '" + serviceType + "', email = '"
                    + email +  "' WHERE username = '" + username + "'";
            dbConnection.executeUpdate(str);
            this.wrt.writeEmployees(dbConnection.getConnection());
        } catch (SQLException e) {
            System.out.println("Edit Employee Failed!");
            e.printStackTrace();
        }
    }
}
