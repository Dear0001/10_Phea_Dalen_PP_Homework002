import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.nocrala.tools.texttablefmt.CellStyle.HorizontalAlign;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String GREEN = "\u001B[32m";
    public static final String PURPLE = "\u001B[35m";


    public static void main(String[] args) {
        List<StaffMember> staffMembers = new ArrayList<>();
        initializeStaffMembers(staffMembers);
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            displayMenu();
            option = readOption(scanner);
            if (option == -1) {
                System.out.println(RED + "Invalid option, input must be a digit." + RESET);
                continue;
            }

            switch (option) {
                case 1 -> insertEmployee(staffMembers);
                case 2 -> updateEmployee(staffMembers);
                case 3 -> displayPagination(staffMembers);
                case 4 -> deleteEmployee(staffMembers);
                case 5 -> System.exit(0);
                default -> System.out.println(RED + "Invalid option, please try again!...." + RESET);
            }
        } while (option != 5);
    }
    /**
     * displayMenu method to show options to user and get user input
     * @return void
     * @see Table
     * @see BorderStyle
     * @see ShownBorders
     * @see CellStyle
     * @see HorizontalAlign
     * */
    private static void displayMenu() {
        String padding = " " .repeat(5);
        // Display menu options
        Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        table.setColumnWidth(0, 50, 80);
        table.addCell(PURPLE + "STAFF MANAGEMENT SYSTEM", new CellStyle(HorizontalAlign.center));
        table.addCell(padding+"1. Insert Employee", new CellStyle(HorizontalAlign.left));
        table.addCell(padding+"2. Update Employee", new CellStyle(HorizontalAlign.left));
        table.addCell(padding+"3. Display Employee", new CellStyle(HorizontalAlign.left));
        table.addCell(padding+"4. Remove Employee", new CellStyle(HorizontalAlign.left));
        table.addCell(padding+"5. Exit", new CellStyle(HorizontalAlign.left));
        System.out.println(table.render());
        System.out.println("-----------------------------------------");
        System.out.print("Choose option (1-5): ");
    }

////////////////////////////////////////////////////////////////

    /**
     * readOption method to read user input option or validation
     * @see Pattern
     * */
    private static int readOption(Scanner scanner) {
        // Read user input option
        String input = scanner.nextLine();
        return Pattern.matches("^[1-9]$", input) ? Integer.parseInt(input) : -1;
    }
    /**
     * validatePositiveDoubleInput method to validate positive double input
     * use in insertEmployee method
     * */
    private static double validatePositiveDoubleInput(Scanner scanner, String prompt, String errorMessage) {
        double value;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextDouble()) {
                System.out.println(RED + "Invalid input. Please enter a number." + RESET);
                System.out.print("Enter again: ");
                scanner.next();
            }
            value = scanner.nextDouble();
            if (value <= 0) {
                System.out.println(RED + errorMessage + RESET);
            }
        } while (value <= 0);
        return value;
    }
    /**
     * validateNonNegativeIntInput the same validatePositiveDoubleInput
     * */
    private static int validateNonNegativeIntInput(Scanner scanner, String prompt, String errorMessage) {
        int value;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println(RED + "Invalid input. Please enter a number." + RESET);
                System.out.print("Enter again: ");
                scanner.next();
            }
            value = scanner.nextInt();
            if (value < 0) {
                System.out.println(RED + errorMessage + RESET);
            }
        } while (value < 0);
        return value;
    }
    static String validateName(Scanner scanner, String prompt) {
        String name;
        do {
            System.out.print(prompt);
            name = scanner.nextLine();
            if (!isValidName(name)) {
                System.out.println(RED + "Name should contain only alphabetic characters and spaces. Please try again." + RESET);
            }
        } while (!isValidName(name));
        return name;
    }

    static boolean isValidName(String name) {
        String regex = "^[a-zA-Z\\s]+$"; // Updated regex to include spaces
        return name.matches(regex);
    }

    //////////////////////////  end Validate     ////////////////////////////////////

    /**
     * initializeStaffMembers method to initialize staff members
     * @param staffMembers
     * @return void
     * @see List
     * */
    private static void initializeStaffMembers(List<StaffMember> staffMembers) {
        staffMembers.add(new Volunteer("Dalen", "PP", 1000.00));
        staffMembers.add(new SalariesEmployee("Dear", "TBK", 1500.00, 200.00));
        staffMembers.add(new HourlySalaryEmployee("Soda chan", "TK", 10, 10));

        staffMembers.add(new Volunteer("Json", "JJ", 1250));
        staffMembers.add(new SalariesEmployee("Uta", "NN", 800.00, 100.00));
        staffMembers.add(new HourlySalaryEmployee("John Cina", "TK", 10, 10));

        staffMembers.add(new Volunteer("Dina", "TTN", 3000.00));
        staffMembers.add(new SalariesEmployee("Re Ja", "HH", 2500.00, 250.00));
        staffMembers.add(new HourlySalaryEmployee("NaNa", "TK", 12, 12));
    }
    static void insertEmployee(List<StaffMember> staffMembers) {
        Scanner scanner = new Scanner(System.in);
        int type;
        do {
            System.out.println("============== * Insert Employee * ==============");
            System.out.println("Choose Type:");
            Table t = new Table(4, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
            t.setColumnWidth(0, 20, 50);
            t.setColumnWidth(1, 20, 50);
            t.setColumnWidth(2, 20, 50);
            t.setColumnWidth(3, 20, 50);
            t.addCell("1. Volunteer", new CellStyle(HorizontalAlign.center));
            t.addCell("2. Salaries Employee", new CellStyle(HorizontalAlign.center));
            t.addCell("3. Hour", new CellStyle(HorizontalAlign.center));
            t.addCell("4. Back", new CellStyle(HorizontalAlign.center));
            System.out.println(t.render());
            System.out.print("Enter Type Number: ");
            while (!scanner.hasNextInt()) {
                System.out.println(RED + "Invalid input. Value must be digit only" + RESET);
                System.out.print("Please enter a number: ");
                scanner.next();
            }
            type = scanner.nextInt();
            scanner.nextLine();

            if (type < 1 || type > 4) {
                System.out.println(RED + "number of option should be started from 1" + RESET);
                continue;
            }

            switch (type) {
                case 1: {
                    System.out.println("============== Insert Volunteer Employee ==============");
                    String name = validateName(scanner, "Enter name: ");
                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();
                    double salary = validatePositiveDoubleInput(scanner, "Enter salary: ", RED+"Salary must be a positive number."+RESET);
                    staffMembers.add(new Volunteer(name, address, salary));
                    System.out.println("Insert Volunteer successfully!");
                    break;
                }
                case 2: {
                    System.out.println("============== Insert Salaries Employee ==============");
                    String name = validateName(scanner, "Enter name: ");
                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();
                    double salary = validatePositiveDoubleInput(scanner, "Enter salary: ", RED+"Salary must be a positive number."+RESET);
                    double bonus = validatePositiveDoubleInput(scanner, "Enter bonus: ", RED+"Bonus must be a positive number."+RESET);
                    staffMembers.add(new SalariesEmployee(name, address, salary, bonus));
                    System.out.println("Insert Salaries Employee successfully!");
                    break;
                }
                case 3: {
                    System.out.println("============== Insert Hourly Employee ==============");
                    String name = validateName(scanner, "Enter name: ");
                    System.out.print("Enter address: ");
                    String address = scanner.nextLine();
                    int hourWorked = validateNonNegativeIntInput(scanner, "Enter hour worked: ", RED+"Hour worked cannot be negative."+RESET);
                    double rate = validatePositiveDoubleInput(scanner, "Enter rate: ", RED+"Rate must be a positive number."+RESET);
                    staffMembers.add(new HourlySalaryEmployee(name, address, hourWorked, rate));
                    System.out.println("Insert Hourly successfully!");
                    break;
                }
                case 4: {
                    break;
                }
                default: {
                    System.out.println(RED + "Invalid option, please try again!...." + RESET);
                    break;
                }
            }
        } while (type != 4);
    }

    static void deleteEmployee(List<StaffMember> staffMembers) {
        Scanner scanner = new Scanner(System.in);
        int deleteId;
        do {
            System.out.println("============== * Remove Employee * ==============");
            System.out.print("Enter ID Staff to remove (0 to exit): ");
            deleteId = scanner.nextInt();
            if (deleteId == 0) {
                break; // Exit the loop if the user enters 0
            }
            if (deleteId < 1 || deleteId > staffMembers.size()) {
                System.out.println(RED + "Invalid ID, please try again!...." + RESET);
                continue;
            }
            // Adjust IDs after deletion
            for (int i = deleteId - 1; i < staffMembers.size(); i++) {
                staffMembers.get(i).id--;
            }
            staffMembers.remove(deleteId - 1);
            System.out.println(BLUE + "Remove employee successfully!" + RESET);
        } while (true);
    }

    static void updateEmployee(List<StaffMember> staffMembers) {
        Scanner scanner = new Scanner(System.in);
        int updateId;
        do {
            int index = 0;
            System.out.println("============== * Update Employee * ==============");
            System.out.print("Enter ID Staff to update: ");
            updateId = scanner.nextInt();
            if (updateId < 1 || updateId > staffMembers.size()) {
                System.out.println(RED + "Invalid ID, please try again!...." + RESET);
                continue;
            }
            System.out.println("Select one column to update: ");
            if (staffMembers.get(updateId - 1) instanceof Volunteer) {
                Table t = new Table(6, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
                Tabled(t);
                Payment(staffMembers, updateId, index, t);
                t.addCell("$" + ((Volunteer) staffMembers.get(updateId - 1)).getSalary(), new CellStyle(HorizontalAlign.center));
                t.addCell("$" + staffMembers.get(updateId - 1).pay(), new CellStyle(HorizontalAlign.center));

                System.out.println(t.render());
                System.out.println(BLUE+"1. Update Name \t 2. Update Address \t 3. Update Salary \t 0. Back" +RESET);
            } else if (staffMembers.get(updateId - 1) instanceof HourlySalaryEmployee hourlyEmployee) {
                Table t = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
                t.setColumnWidth(0, 20, 50);
                t.addCell(GREEN + "Type", new CellStyle(HorizontalAlign.center));
                t.addCell(GREEN + "ID", new CellStyle(HorizontalAlign.center));
                t.addCell(GREEN + "Name", new CellStyle(HorizontalAlign.center));
                t.addCell(GREEN + "Address", new CellStyle(HorizontalAlign.center));
                t.addCell(GREEN + "Hour", new CellStyle(HorizontalAlign.center));
                t.addCell(GREEN + "Rate", new CellStyle(HorizontalAlign.center));
                t.addCell(GREEN + "Pay", new CellStyle(HorizontalAlign.center));

                t.addCell(staffMembers.get(updateId - 1).getClass().getSimpleName());
                t.addCell(staffMembers.get(updateId - 1).getName(), new CellStyle(HorizontalAlign.center));
                t.addCell(String.valueOf(++index), new CellStyle(HorizontalAlign.center));
                t.addCell(staffMembers.get(updateId - 1).getAddress(), new CellStyle(HorizontalAlign.center));
                t.addCell(String.valueOf(hourlyEmployee.getHourWorked()), new CellStyle(HorizontalAlign.center));
                t.addCell(String.valueOf(hourlyEmployee.getRate()), new CellStyle(HorizontalAlign.center));
                t.addCell("$" + hourlyEmployee.pay(), new CellStyle(HorizontalAlign.center));

                System.out.println(t.render());
                System.out.println(BLUE+"1. Update Name \t 2. Update Address \t 3. Update Hour \t 4. Rate \t 0. Back"+RESET);
            } else if (staffMembers.get(updateId - 1) instanceof SalariesEmployee) {
                Table t = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
                Tabled(t);
                t.addCell(GREEN + "Bonus", new CellStyle(HorizontalAlign.center));
                Payment(staffMembers, updateId, index, t);
                t.addCell("$" + ((SalariesEmployee) staffMembers.get(updateId - 1)).getSalary(), new CellStyle(HorizontalAlign.center));
                t.addCell("$" + ((SalariesEmployee) staffMembers.get(updateId - 1)).getBonus(), new CellStyle(HorizontalAlign.center));
                t.addCell("$" + staffMembers.get(updateId - 1).pay(), new CellStyle(HorizontalAlign.center));

                System.out.println(t.render());
                System.out.println(BLUE+"1. Update Name \t 2. Update Address \t 3. update salary \t 4. Update Bonus \t 0. Back"+RESET);
            }
            System.out.print("=> Select Column Number: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1: {
                    String name = validateName(scanner, "Enter new name: ");
                    staffMembers.get(updateId - 1).setName(name);
                    Table t = new Table(6, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
                    Tabled(t);
                    Payment(staffMembers, updateId, index, t);
                    t.addCell("$" + ((Volunteer) staffMembers.get(updateId - 1)).getSalary(), new CellStyle(HorizontalAlign.center));
                    t.addCell("$" + staffMembers.get(updateId - 1).pay(), new CellStyle(HorizontalAlign.center));

                    System.out.println(t.render());
                    System.out.println("=> Update name successfully!");
                    return;
                }
                case 2: {
                    System.out.print("=> Change address to: ");
                    String address = scanner.nextLine();
                    staffMembers.get(updateId - 1).setAddress(address);
                    Table t = new Table(6, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
                    Tabled(t);
                    Payment(staffMembers, updateId, index, t);
                    t.addCell("$" + ((Volunteer) staffMembers.get(updateId - 1)).getSalary(), new CellStyle(HorizontalAlign.center));
                    t.addCell("$" + staffMembers.get(updateId - 1).pay(), new CellStyle(HorizontalAlign.center));

                    System.out.println(t.render());
                    System.out.println(BLUE+"Update address successfully!"+RESET);
                    return;
                }
                case 3: {
                    if (staffMembers.get(updateId - 1) instanceof SalariesEmployee salariesEmployee) {
                        double salary = validatePositiveDoubleInput(scanner, "Enter salary: ", RED+"Salary must be a positive number."+RESET);

                        salariesEmployee.setSalary(salary);
                        Table t = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
                        Tabled(t);
                        t.addCell(GREEN + "Bonus", new CellStyle(HorizontalAlign.center));
                        Payment(staffMembers, updateId, index, t);
                        t.addCell("$" + ((SalariesEmployee) staffMembers.get(updateId - 1)).getSalary(), new CellStyle(HorizontalAlign.center));
                        t.addCell("$" + ((SalariesEmployee) staffMembers.get(updateId - 1)).getBonus(), new CellStyle(HorizontalAlign.center));
                        t.addCell("$" + staffMembers.get(updateId - 1).pay(), new CellStyle(HorizontalAlign.center));

                        System.out.println(t.render());
                        System.out.println(BLUE+ "Update salary successfully!"+RESET);
                        return;
                    } else if (staffMembers.get(updateId - 1) instanceof HourlySalaryEmployee hourlyEmployee) {
                        int hourWorked = validateNonNegativeIntInput(scanner, "Enter hour worked: ", RED+"Hour worked cannot be negative."+RESET);
                        hourlyEmployee.setHourWorked(hourWorked);
                        updateRateTable(staffMembers, updateId, index, hourlyEmployee);
                        System.out.println(BLUE+"Update hour worked successfully!"+RESET);
                        break;
                    } else {
                        double salary = validatePositiveDoubleInput(scanner, "Enter rate: ", RED+"Rate must be a positive number."+RESET);
                        ((SalariesEmployee) staffMembers.get(updateId - 1)).setSalary(salary);
                        Table t = new Table(6, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
                        Tabled(t);
                        Payment(staffMembers, updateId, index, t);
                        t.addCell("$" + ((Volunteer) staffMembers.get(updateId - 1)).getSalary(), new CellStyle(HorizontalAlign.center));
                        t.addCell("$" + staffMembers.get(updateId - 1).pay(), new CellStyle(HorizontalAlign.center));

                        System.out.println(t.render());
                        System.out.println(BLUE+"Update new rate successfully!"+RESET);
                    }
                    return;
                }
                case 4: {
                    if (staffMembers.get(updateId - 1) instanceof SalariesEmployee salariesEmployee) {
                        double bonus = validatePositiveDoubleInput(scanner, "Enter bonus: ", RED+"Bonus must be a positive number."+RESET);
                        Table t = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
                        t.setColumnWidth(0, 20, 50);
                        t.addCell(GREEN + "Type", new CellStyle(HorizontalAlign.center));
                        t.addCell(GREEN + "ID", new CellStyle(HorizontalAlign.center));
                        t.addCell(GREEN + "Name", new CellStyle(HorizontalAlign.center));
                        t.addCell(GREEN + "Address", new CellStyle(HorizontalAlign.center));
                        t.addCell(GREEN + "Salary", new CellStyle(HorizontalAlign.center));
                        t.addCell(GREEN + "Bonus", new CellStyle(HorizontalAlign.center));
                        t.addCell(GREEN + "Pay", new CellStyle(HorizontalAlign.center));

                        t.addCell(staffMembers.get(updateId - 1).getClass().getSimpleName());
                        t.addCell(staffMembers.get(updateId - 1).getName(), new CellStyle(HorizontalAlign.center));
                        t.addCell(String.valueOf(++index), new CellStyle(HorizontalAlign.center));
                        t.addCell(staffMembers.get(updateId - 1).getAddress(), new CellStyle(HorizontalAlign.center));
                        t.addCell(String.valueOf(salariesEmployee.getSalary()), new CellStyle(HorizontalAlign.center));
                        t.addCell(String.valueOf(salariesEmployee.getBonus()), new CellStyle(HorizontalAlign.center));
                        t.addCell("$" + salariesEmployee.pay(), new CellStyle(HorizontalAlign.center));
                        System.out.println(t.render());
                        salariesEmployee.setBonus(bonus);
                        System.out.println("Update bonus successfully!");
                        return;
                    }  else if (staffMembers.get(updateId - 1) instanceof HourlySalaryEmployee hourlyEmployee) {
                        double rate = validatePositiveDoubleInput(scanner, "Enter rate: ", RED+"Rate must be a positive number."+RESET);
                        hourlyEmployee.setRate(rate);
                        updateRateTable(staffMembers, updateId, index, hourlyEmployee);
                        System.out.println("Updated Rate successfully!");
                    }
                    break;
                }
                case 0: {
                    return;
                }
                default: {
                    System.out.println(RED + "Invalid option, please try again!...." + RESET);
                    break;
                }
            }
        } while (updateId != 3);
    }

    private static void updateRateTable(List<StaffMember> staffMembers, int updateId, int index, HourlySalaryEmployee hourlyEmployee) {
        Table t = new Table(7, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
        t.setColumnWidth(0, 20, 50);
        t.addCell(GREEN + "Type", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "ID", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "Name", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "Address", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "Hour", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "Rate", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "Pay", new CellStyle(HorizontalAlign.center));

        t.addCell(staffMembers.get(updateId - 1).getClass().getSimpleName());
        t.addCell(staffMembers.get(updateId - 1).getName(), new CellStyle(HorizontalAlign.center));
        t.addCell(String.valueOf(++index), new CellStyle(HorizontalAlign.center));
        t.addCell(staffMembers.get(updateId - 1).getAddress(), new CellStyle(HorizontalAlign.center));
        t.addCell(String.valueOf(hourlyEmployee.getHourWorked()), new CellStyle(HorizontalAlign.center));
        t.addCell(String.valueOf(hourlyEmployee.getRate()), new CellStyle(HorizontalAlign.center));
        t.addCell("$" + hourlyEmployee.pay(), new CellStyle(HorizontalAlign.center));
        System.out.println(t.render());
    }

    private static void Payment(List<StaffMember> staffMembers, int updateId, int index, Table t) {
        t.addCell(GREEN + "Pay", new CellStyle(HorizontalAlign.center));

        t.addCell(staffMembers.get(updateId - 1).getClass().getSimpleName());
        t.addCell(String.valueOf(++index), new CellStyle(HorizontalAlign.center));
        t.addCell(staffMembers.get(updateId - 1).getName(), new CellStyle(HorizontalAlign.center));
        t.addCell(staffMembers.get(updateId - 1).getAddress(), new CellStyle(HorizontalAlign.center));
    }

    /**
     * limited column width
     * @param t
     * @return void
     * */
    public static void Tabled(Table t) {
        t.setColumnWidth(0, 20, 50);
        Type(t);
    }
    /**
     * display table method to display staff members by using texttablefmt library
     * @param staffMembers
     * @return void
     * @see Table
     * @see CellStyle
     * @see HorizontalAlign
     * @see BorderStyle
     * @see ShownBorders
     * @see List
     * @see StaffMember
     * @see Volunteer
     * @see SalariesEmployee
     * @see HourlySalaryEmployee
     * */
    static void displayTable(List<StaffMember> staffMembers) {
        Table t = new Table(9, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
        t.setColumnWidth(0, 25, 50);
        t.setColumnWidth(1, 15, 50);
        t.setColumnWidth(2, 15, 50);
        t.setColumnWidth(3, 15, 50);
        t.setColumnWidth(4, 15, 50);
        t.setColumnWidth(5, 15, 50);
        t.setColumnWidth(6, 15, 50);
        t.setColumnWidth(7, 15, 50);
        t.setColumnWidth(8, 15, 50);

        Type(t);
        t.addCell(GREEN + "Bonus", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "Hour", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "Rate", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "Pay", new CellStyle(HorizontalAlign.center));

        for (int i = 0; i < staffMembers.size(); i++) {
            StaffMember member = staffMembers.get(i);
            t.addCell(member.getClass().getSimpleName(), new CellStyle(HorizontalAlign.left));
            t.addCell(String.valueOf(member.getId()), new CellStyle(HorizontalAlign.center));
            t.addCell(member.getName(), new CellStyle(HorizontalAlign.center));
            t.addCell(member.getAddress(), new CellStyle(HorizontalAlign.center));

            if (member instanceof SalariesEmployee salariesEmployee) {
                t.addCell("$" + salariesEmployee.getSalary(), new CellStyle(HorizontalAlign.center));
                t.addCell("$" + salariesEmployee.getBonus(), new CellStyle(HorizontalAlign.center));
                t.addCell("___", new CellStyle(HorizontalAlign.center));
                t.addCell("___", new CellStyle(HorizontalAlign.center));
            } else if (member instanceof HourlySalaryEmployee hourlyEmployee) {
                t.addCell("___", new CellStyle(HorizontalAlign.center));
                t.addCell("___", new CellStyle(HorizontalAlign.center));
                t.addCell(String.valueOf(hourlyEmployee.getHourWorked()), new CellStyle(HorizontalAlign.center));
                t.addCell(String.valueOf(hourlyEmployee.getRate()), new CellStyle(HorizontalAlign.center));
            } else {
                Volunteer volunteer = (Volunteer) member;
                t.addCell("$" + volunteer.getSalary(), new CellStyle(HorizontalAlign.center));
                t.addCell("___", new CellStyle(HorizontalAlign.center));
                t.addCell("___", new CellStyle(HorizontalAlign.center));
                t.addCell("___", new CellStyle(HorizontalAlign.center));
            }
            t.addCell("$" + member.pay(), new CellStyle(HorizontalAlign.center));
        }

        System.out.println(t.render());
    }
    /**
     * displayInfo method is used to display staff members with pagination
     * @param staffMembers
     * @return void
     * @see Scanner
     * @see List
     * @see StaffMember
     * */
    static void displayPagination(List<StaffMember> staffMembers) {
        Scanner scanner = new Scanner(System.in);
        // Default page size
        int pageSize = 3;
        int totalPages = (int) Math.ceil((double) staffMembers.size() / pageSize);
        int currentPage = 1; // page 1
        int option;
        do {

            // Filter staff members based on pagination
            List<StaffMember> currentPageMembers = IntStream.range((currentPage - 1) * pageSize, Math.min(currentPage * pageSize, staffMembers.size()))
                    .mapToObj(staffMembers::get)
                    .collect(Collectors.toList());

            // Display current page content
            displayTable(currentPageMembers);

            // Introduce animation delay
            try {
                TimeUnit.SECONDS.sleep(3); // Wait for 3 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Display pagination options
            System.out.println("--------------------------------------");
            System.out.println("1. First  \t\t2. Next  \t\t3. Previous  \t\t4. Last  \t\t5. Back");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> currentPage = 1;
                case 2 -> currentPage = Math.min(currentPage + 1, totalPages);
                case 3 -> currentPage = Math.max(currentPage - 1, 1);
                case 4 -> currentPage = totalPages;
                case 5 -> {
                    return;
                }
                default -> System.out.println(RED + "Invalid option. Please try again." + RESET);
            }
        } while (true);
    }

    private static void Type(Table t) {
        t.addCell(GREEN + "Type", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "ID", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "Name", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "Address", new CellStyle(HorizontalAlign.center));
        t.addCell(GREEN + "Salary", new CellStyle(HorizontalAlign.center));
    }
}