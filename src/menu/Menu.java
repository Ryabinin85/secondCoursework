package menu;

import planner.Planner;
import task.TaskPrivacy;
import task.TaskRepeatability;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private final Planner planner = new Planner();

    public void menuRunner() {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            try {
                                planner.addTaskToPlanner(setTitle(), setDescription(), setTaskPrivacy(), setRepeatability(), setDayTime());
                            } catch (InputMismatchException | NullPointerException | DateTimeParseException e) {
                                printIncorrectData();
                            }
                            break;
                        case 2:
                            try {
                                System.out.println("Введите номер задачи, которую необходимо удалить");
                                planner.removeTaskByID(scanner.nextInt());
                            } catch (InputMismatchException e) {
                                scanner.next();
                                printIncorrectData();
                            }
                            break;
                        case 3:
                            try {
                                LocalDate thisDate = setDay();
                                if (thisDate != null) {
                                    System.out.println("Задачи на день: " + thisDate);
                                    planner.findTaskByDate(thisDate);
                                }
                            } catch (DateTimeParseException e) {
                                scanner.next();
                                printIncorrectData();
                            }
                            break;
                        case 4:
                            try {
                                System.out.println("Всего задач в ежедневнике: " + planner.getPlanner().size());
                                planner.getTaskByID(scanner.nextInt());
                            } catch (InputMismatchException e) {
                                scanner.next();
                                printIncorrectData();
                            }
                            break;
                        case 5:
                            try {
                                System.out.println("Для получения следующей даты, введите № задачи.");
                                planner.getNextDateByID(scanner.nextInt());
                            } catch (InputMismatchException e) {
                                scanner.next();
                                printIncorrectData();
                            }
                            break;
                        case 6:
                            planner.printArchive();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println(
                """       
                        1. Добавить задачу
                        2. Удалить задачу
                        3. Получить задачу на указанный день
                        4. Получить задачу по номеру
                        5. Получить следующую дату выполнения
                        6. Посмотреть архивные задачи
                        0. Выход
                        """

        );
    }

    private static String setTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название задачи:");
        return scanner.nextLine();
    }

    private static String setDescription() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите описание задачи:");
        return scanner.nextLine();
    }

    private static LocalDateTime setDayTime() throws NullPointerException, DateTimeParseException {
        return LocalDateTime.of(setDay(), setTime());
    }

    private static LocalDate setDay() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите дату задачи в формате (yyyy.mm.dd):");
        return LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    private static LocalTime setTime() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите время задачи в формате (HH.mm):");
        return LocalTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("HH.mm"));
    }

    private static TaskPrivacy setTaskPrivacy() {
        return switch (inputTaskPrivacy()) {
            case 1 -> TaskPrivacy.PERSONAL;
            case 2 -> TaskPrivacy.WORK;
            default -> TaskPrivacy.NOT_DEFINED;
        };
    }

    private static int inputTaskPrivacy() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int inputNumber;
        System.out.print(
                """
                        Введите приватность задачи:
                        1 - Личная
                        2 - Рабочая
                        """);
        do {
            inputNumber = scanner.nextInt();
        }
        while (inputNumber < 1 || inputNumber > 2);
        return inputNumber;
    }

    private static TaskRepeatability setRepeatability() {
        return switch (inputTaskRepeatability()) {
            case 2 -> TaskRepeatability.DAILY;
            case 3 -> TaskRepeatability.WEEKLY;
            case 4 -> TaskRepeatability.MONTHLY;
            case 5 -> TaskRepeatability.YEARLY;
            default -> TaskRepeatability.SINGLE;
        };
    }

    private static int inputTaskRepeatability() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        int inputNumber;
        System.out.print(
                """
                        Введите повторяемость задачи:
                        1 - Одиночная
                        2 - Ежедневная
                        3 - Еженедельная
                        4 - Ежемесячная
                        5 - Ежегодная
                        """);
        do {
            inputNumber = scanner.nextInt();
        }
        while (inputNumber < 1 || inputNumber > 5);
        return inputNumber;
    }

    private void printIncorrectData() {
        System.out.println("Данные введены некорректно!");
    }
}