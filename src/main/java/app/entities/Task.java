package app.entities;

public class Task {
    private int taskId;
    private String taskName;
    private Boolean taskDone;
    private int userId;

    public Task(int taskId, String taskName, Boolean taskDone, int userId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDone = taskDone;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", userName='" + taskName + '\'' +
                ", taskDone=" + taskDone +
                ", userId=" + userId +
                '}';
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public Boolean getTaskDone() {
        return taskDone;
    }

    public int getUserId() {
        return userId;
    }
}
