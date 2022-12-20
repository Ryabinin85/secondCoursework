package task;

public enum TaskPrivacy {
    PERSONAL("Личная"),
    WORK("Рабочая"),
    NOT_DEFINED("Не определена");

    private final String taskPrivacy;

    TaskPrivacy(String taskPrivacy) {
        this.taskPrivacy = taskPrivacy;
    }

    public String getTaskPrivacy() {
        return taskPrivacy;
    }

    @Override
    public String toString() {
        return taskPrivacy;
    }
}
