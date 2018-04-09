package cn.client.pojo;



import java.util.ArrayList;
import java.util.List;

public class CompanyForTaskVo {
    private Company company=null;
    private List<UserTask>  userTaskList=new ArrayList<>();

    public CompanyForTaskVo() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<UserTask> getUserTaskList() {
        return userTaskList;
    }

    public void setUserTaskList(List<UserTask> userTaskList) {
        this.userTaskList = userTaskList;
    }
}
