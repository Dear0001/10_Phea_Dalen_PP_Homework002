class HourlySalaryEmployee extends StaffMember {
    private int hourWorked;
    private double rate;

    public HourlySalaryEmployee(String name, String address, int hourWorked, double rate) {
        super(name, address);
        this.hourWorked = hourWorked;
        this.rate = rate;
    }

    public int getHourWorked() {
        return hourWorked;
    }

    public void setHourWorked(int hourWorked) {
        this.hourWorked = hourWorked;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    @Override
    public double pay() {
        return hourWorked * rate;
    }
}