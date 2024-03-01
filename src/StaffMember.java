abstract class StaffMember {
    protected int id;
    protected String name;
    protected String address;
    static int fixedId = 1;
    public StaffMember(String name, String address) {
        this.id =   fixedId++;
        this.name = name;
        this.address = address;

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract double pay();


}