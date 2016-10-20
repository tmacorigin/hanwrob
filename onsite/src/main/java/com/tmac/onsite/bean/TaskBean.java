package com.tmac.onsite.bean;

/**
 * Created by user on 16/10/20.
 */
public class TaskBean {

    private String taskId;
    private String taskState;
    private String preformAddress;
    private String finishedTime;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getPreformAddress() {
        return preformAddress;
    }

    public void setPreformAddress(String preformAddress) {
        this.preformAddress = preformAddress;
    }

    public String getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(String finishedTime) {
        this.finishedTime = finishedTime;
    }

    @Override
    public String toString() {
        return "TaskBean{" +
                "taskId='" + taskId + '\'' +
                ", taskState='" + taskState + '\'' +
                ", preformAddress='" + preformAddress + '\'' +
                ", finishedTime='" + finishedTime + '\'' +
                '}';
    }
}