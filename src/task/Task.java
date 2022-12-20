package task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Task {
    private static int nextId = 1;
    private static LocalDateTime dateOfCreation;
    private final int id;
    private final String title;
    private final String description;
    private final LocalDateTime taskStartDateTime;
    private final TaskPrivacy taskPrivacy;
    private final TaskRepeatability taskRepeatability;

    public Task(String title, String description, TaskPrivacy taskPrivacy, TaskRepeatability taskRepeatability, LocalDateTime taskStartDate) {
        this.title = validateTitle(title);
        this.description = description;
        this.taskPrivacy = taskPrivacy;
        this.taskRepeatability = taskRepeatability;
        this.taskStartDateTime = taskStartDate;

        id = nextId;
        nextId++;
        dateOfCreation = LocalDateTime.now();
    }

    public TaskRepeatability getTaskRepeatability() {
        return taskRepeatability;
    }

    public int getId() {
        return id;
    }

    public static int getNextId() {
        return nextId;
    }

    public LocalDate getTaskStartDate() {
        return taskStartDateTime.toLocalDate();
    }

    public LocalDate getNextDate() {
        return switch (this.taskRepeatability) {
            case SINGLE -> null;
            case DAILY -> getTaskStartDate().plusDays(1L);
            case WEEKLY -> getTaskStartDate().plusWeeks(1L);
            case MONTHLY -> getTaskStartDate().plusMonths(1L);
            case YEARLY -> getTaskStartDate().plusYears(1L);
        };
    }

    public static String validateTitle(String titleValidation) {
        return titleValidation == null
                || titleValidation.isEmpty()
                || titleValidation.isBlank() ? String.format("Задача №%d", nextId) : titleValidation;
    }

    public boolean isRepeat(LocalDate date) {
        long difference = timeDifference(date);
        boolean isRepeat = false;
        LocalDate tempDate = getTaskStartDate();

        for (int i = 0; i <= difference; i++) {
            if (tempDate.equals(date)) {
                isRepeat = true;
                break;
            } else tempDate = repeatedDate(tempDate);
        }
        return isRepeat;
    }

    private long timeDifference(LocalDate date) {
        return switch (taskRepeatability) {
            case SINGLE -> 0L;
            case DAILY -> ChronoUnit.DAYS.between(getTaskStartDate(), date);
            case WEEKLY -> ChronoUnit.WEEKS.between(getTaskStartDate(), date);
            case MONTHLY -> ChronoUnit.MONTHS.between(getTaskStartDate(), date);
            case YEARLY -> ChronoUnit.YEARS.between(getTaskStartDate(), date);
        };
    }

    private LocalDate repeatedDate(LocalDate date) {
        LocalDate tempDate = null;
        switch (taskRepeatability) {
            case DAILY -> tempDate = date.plusDays(1L);
            case WEEKLY -> tempDate = date.plusWeeks(1L);
            case MONTHLY -> tempDate = date.plusMonths(1L);
            case YEARLY -> tempDate = date.plusYears(1L);
        }
        return tempDate;
    }

    @Override
    public String toString() {
        return String.format(
                "\n__________________________________________________" +
                "\nЗадача №%d. %10s\nОписание: %s\n", id, title, description +
                "\nДата выполнения задачи: " + taskStartDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")) +
                "\nТип: " + taskPrivacy +
                "\nПовторяемость: " + taskRepeatability +
                "\nСоздана: " + dateOfCreation.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")) +
                "\n__________________________________________________"
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(taskStartDateTime, task.taskStartDateTime) &&
                taskPrivacy == task.taskPrivacy && taskRepeatability == task.taskRepeatability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, taskStartDateTime, taskPrivacy, taskRepeatability);
    }
}