package cn.client.pojo;




public class CompanyTask {

    private String id;
    private String name;
    private String content;

    private int completeState;

    private WorkProject workProject;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCompleteState() {
        return completeState;
    }

    public void setCompleteState(int completeState) {
        this.completeState = completeState;
    }

    public WorkProject getWorkProject() {
        return workProject;
    }

    public void setWorkProject(WorkProject workProject) {
        this.workProject = workProject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
