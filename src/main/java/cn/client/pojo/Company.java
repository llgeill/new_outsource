package cn.client.pojo;


public class Company {

    private String id;
    private String name;
    private String address;
    private CompanyTask companyTask;
    private Admin  admin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CompanyTask getCompanyTask() {
        return companyTask;
    }

    public void setCompanyTask(CompanyTask companyTask) {
        this.companyTask = companyTask;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
