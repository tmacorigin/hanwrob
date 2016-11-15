package com.tmac.onsite.bean;

import com.toolset.dataManager.dataManagerdataBase;

/**
 * Created by user on 16/10/20.
 */
public class TaskBean extends dataManagerdataBase {

    public String taskId;
    public String taskState;
    public String preformAddress;
    public String finishedTime;
    public String readState;
    public String robState;
    public String endTime;

    public TaskBean(){};

    public TaskBean(String taskId, String taskState, String preformAddress, String finishedTime) {
        this.taskId = taskId;
        this.taskState = taskState;
        this.preformAddress = preformAddress;
        this.finishedTime = finishedTime;
    }

    public TaskBean(String taskId, String taskState, String preformAddress, String finishedTime, String robState) {
        this.taskId = taskId;
        this.taskState = taskState;
        this.preformAddress = preformAddress;
        this.finishedTime = finishedTime;
        this.robState = robState;
    }

    public TaskBean(String taskId, String taskState, String preformAddress, String finishedTime, String robState, String readState) {
        this.taskId = taskId;
        this.taskState = taskState;
        this.preformAddress = preformAddress;
        this.finishedTime = finishedTime;
        this.robState = robState;
        this.readState = readState;
    }

    public TaskBean(String taskId, String taskState, String preformAddress, String finishedTime, String readState, String robState, String endTime) {
        this.taskId = taskId;
        this.taskState = taskState;
        this.preformAddress = preformAddress;
        this.finishedTime = finishedTime;
        this.readState = readState;
        this.robState = robState;
        this.endTime = endTime;
    }

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

    public String getReadState() {
        return readState;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }

    public String getRobState() {
        return robState;
    }

    public void setRobState(String robState) {
        this.robState = robState;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TaskBean{" +
                "taskId='" + taskId + '\'' +
                ", taskState='" + taskState + '\'' +
                ", preformAddress='" + preformAddress + '\'' +
                ", finishedTime='" + finishedTime + '\'' +
                ", readState='" + readState + '\'' +
                ", robState='" + robState + '\'' +
                '}';
    }
}
