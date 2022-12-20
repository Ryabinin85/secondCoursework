package task;

public enum TaskRepeatability {
    SINGLE("Однократная"),
    DAILY("Ежедневная"),
    WEEKLY("Еженедельная"),
    MONTHLY("Ежемесячная"),
    YEARLY("Ежегодная");

    private final String repeatability;

    TaskRepeatability(String repeatability) {
        this.repeatability = repeatability;
    }

    public String getRepeatability() {
        return repeatability;
    }

    @Override
    public String toString() {
        return repeatability;
    }
}