package cn.client.pojo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class AttendanceDate {
    private final StringProperty id=new SimpleStringProperty();
    private final ObjectProperty startTime=new SimpleObjectProperty<Timestamp>();
    private final ObjectProperty endTime=new SimpleObjectProperty<Timestamp>();
    private final StringProperty status= new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public Object getStartTime() {
        return startTime.get();
    }

    public ObjectProperty startTimeProperty() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime.set(startTime);
    }

    public Object getEndTime() {
        return endTime.get();
    }

    public ObjectProperty endTimeProperty() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime.set(endTime);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
