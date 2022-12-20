package planner;

import task.Task;
import task.TaskPrivacy;
import task.TaskRepeatability;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Planner {
    private final Set<Task> planner = new HashSet<>(10);
    private final Set<Task> archive = new HashSet<>(10);

    public Set<Task> getPlanner() {
        return planner;
    }

    public void addTaskToPlanner(String title, String description, TaskPrivacy taskPrivacy,
                                 TaskRepeatability taskRepeatability, LocalDateTime taskStartDay) {
        planner.add(new Task(title, description, taskPrivacy, taskRepeatability, taskStartDay));
        System.out.println("Задача добавлена\n" + "Присвоен номер: " + (Task.getNextId()-1));
    }

    public void removeTaskByID(int id) {
        addToArchive(id);
        if (planner.removeIf(task -> task.getId() == id)) {
            System.out.printf("Задача %d удалена\n", id);
        } else {
            System.out.println("Задачи с таким номером нет.");
        }
    }

    private void addToArchive(int id) {
        for (Task task : planner) {
            if (task.getId() == id) {
                archive.add(task);
            }
        }
    }

    public void findTaskByDate(LocalDate date) {
        boolean isFound = false;

        for (Task task : planner) {
            if (task.isRepeat(date)) {
                System.out.println(task);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Задач на текущий день нет");
        }
    }

    public void getTaskByID(int id) {
        boolean isFound = false;
        for (Task task : planner) {
            if (task.getId() == id) {
                System.out.println(task);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            System.out.println("Задачи с таким номером нет");
        }
    }

    public void getNextDateByID(int id) {
        boolean isFound = false;
        for (Task task : planner) {
            if (task.getId() == id && task.getNextDate() != null) {
                System.out.printf("Задача № %d. Следующая дата выполнения: %s\n", id, task.getNextDate().toString());
                isFound = true;
                break;
            }
            if (task.getId() == id && task.getTaskRepeatability().equals(TaskRepeatability.SINGLE)) {
                System.out.printf("Задача № %d однократная\n", id);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            System.out.println("Задача отсутствует");
        }
    }

    public void printArchive() {
        if (!archive.isEmpty()) {
            archive.forEach(System.out::println);
        } else {
            System.out.println("Архив задач пуст!");
        }
    }
}