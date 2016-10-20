package com.tmac.onsite.bean;

/**
 * Created by user on 16/10/20.
 */

public class TaskListBean {

    private String status;
    private TaskBean tasks;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TaskBean getTasks() {
        return tasks;
    }

    public void setTasks(TaskBean tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "TaskListBean{" +
                "status='" + status + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
