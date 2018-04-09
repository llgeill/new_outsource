package cn.client.pojo;



public class User {

    private String id;
    private String name;
    private String password;

    private String numberPhone;

    private String confidentialityAgreementImage;

    private String contractImage;

    private String faceImage;
    private String role;
    private int audit;

    private String companyId;

//    private List<Attendance> attendanceList;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getConfidentialityAgreementImage() {
        return confidentialityAgreementImage;
    }

    public void setConfidentialityAgreementImage(String confidentialityAgreementImage) {
        this.confidentialityAgreementImage = confidentialityAgreementImage;
    }

    public String getContractImage() {
        return contractImage;
    }

    public void setContractImage(String contractImage) {
        this.contractImage = contractImage;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    //    public List<Attendance> getAttendanceList() {
//        return attendanceList;
//    }
//
//    public void setAttendanceList(List<Attendance> attendanceList) {
//        this.attendanceList = attendanceList;
//    }
}
