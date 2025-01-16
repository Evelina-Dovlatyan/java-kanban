import Manager.*;
import TaskClasses.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {/*
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        Task task1 = new Task("Доделать ТЗ5", "Дописать код", Status.NEW);
        taskManager.putNewTask(task1);
        Task task2 = new Task("Купить продукты", "Сходить в магазин", Status.NEW);
        taskManager.putNewTask(task2);

        Epic epic1 = new Epic("Уборка", "Убраться", Status.NEW);
        taskManager.putNewEpic(epic1);
        Epic epic2 = new Epic("Ужин", "Приготовить", Status.NEW);
        taskManager.putNewEpic(epic2);

        Subtask subtask1 = new Subtask("Пол", "Помыть", Status.NEW, epic1.getId());
        taskManager.putNewSubtask(subtask1);
        Subtask subtask2 = new Subtask("Полки", "Протереть", Status.NEW, epic1.getId());
        taskManager.putNewSubtask(subtask2);
        Subtask subtask3 = new Subtask("Суп", "Сварить", Status.NEW, epic2.getId());
        taskManager.putNewSubtask(subtask3);

        historyManager.add(task1);
        historyManager.add(epic1);
        historyManager.add(epic1);
        historyManager.add(epic1);
        historyManager.add(epic1);
        historyManager.add(epic1);
        historyManager.add(epic1);
        historyManager.add(epic1);
        historyManager.add(epic1);
        historyManager.add(epic1);

        printAllTasks(taskManager);
        getAllHistory(historyManager);

        task1.setStatus(Status.DONE);
        taskManager.updateTask(task1);

        subtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask2);

        printAllTasks(taskManager);
        getAllHistory(historyManager);

        taskManager.removeTask(task1.getId());
        taskManager.removeEpic(epic1.getId());

        printAllTasks(taskManager);
        getAllHistory(historyManager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasksList()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getAllEpicsList()) {
            System.out.println(epic);

            for (Task task : manager.getSubtasksOfEpic(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubtasksList()) {
            System.out.println(subtask);
        }
    }

    private static void getAllHistory(HistoryManager historyManager) {
        System.out.println("История:");
        for (Task task : historyManager.getHistory()) {
            System.out.println(task);
        }*/
    }
}
