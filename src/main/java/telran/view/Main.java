package telran.view;

import java.time.LocalDate;

record Employee(long id, String name, LocalDate birth_date, String department, int salary){}

public class Main
{
    static InputOutput io = new StandardInputOutput();
    /*********************** */
    // For HW #35 constants
    final static int MIN_SALARY = 5000;
    final static int MAX_SALARY = 30000;
    final static String[] DEPARTMENTS = { "QA", "Audit", "Development", "Management" };
    // name should be at least 3 English letters; first - capital, others - lower
    // case
    final static long MIN_ID = 100000;
    final static long MAX_ID = 999999;
    final static int MIN_AGE = 18;
    final static int MAX_AGE = 70;

    /*********************************** */
    public static void main(String[] args)
    {
        //readEmployeeAsObject();
        readEmployeeBySeparateFields();
    }

    static void readEmployeeAsObject()
    {
        Employee employee = io.readObject("Enter the Employee data in the format: \n" +
                        "[#id]#[Name]#[birthDate<yyyy-MM-dd>]#[Department]#[Salary]",
                "Wrong format for Employee data", str -> {
                    String[] tokens = str.split("#");
                    return new Employee(Long.parseLong(tokens[0]), tokens[1], LocalDate.parse(tokens[2]),
                            tokens[3], Integer.parseInt(tokens[4]));

                });
        io.writeLine("You are entered the following Employee data");
        io.writeLine(employee);
    }
    static  void readEmployeeBySeparateFields()
    {
        String[] prompts = {"Enter Employee ID (int positive)", "Enter name", "Enter birthdate (yyyy-mm-dd)", "Enter department", "Enter salary (int positive)"};
        int number_details = prompts.length;
        String[] tokens = new String[number_details];
        io.writeString("Entered the Employee data:");
        for (int i=0; i<number_details; i++) {
            tokens[i] = io.readString(prompts[i] + ": ");
        }
        Employee employee = new Employee(Long.parseLong(tokens[0]), tokens[1], LocalDate.parse(tokens[2]),
                tokens[3], Integer.parseInt(tokens[4]));

        io.writeLine("You are entered the following Employee data");
        io.writeLine(employee);
    }
}
