class SalariesEmployee extends StaffMember {
    private double salary;
    private double bonus;

    SalariesEmployee(String name, String address, double salary, double bonus) {
        super(name, address);
        this.salary = salary;
        this.bonus = bonus;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public double pay() {
        return salary + bonus;
    }
}