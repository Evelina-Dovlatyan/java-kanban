package ManagerTests;

import Manager.HistoryManager;
import Manager.Managers;
import TaskClasses.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private static HistoryManager historyManager;

    @BeforeEach
    void beforeEach() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void add() {
        Task task = new Task("name", "description", Status.NEW);
        historyManager.add(task);

        final List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История пустая.");
        assertEquals(1, history.size(), "История пустая.");
    }

    @Test
    void taskAfterAddByHistoryManager() {
        Task task = new Task("name", "description", Status.NEW);
        historyManager.add(task);

        final List<Task> savedHistory = historyManager.getHistory();
        final Task savedTask = savedHistory.getFirst();

        assertNotNull(savedHistory, "История пустая.");
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(savedTask.getName(), task.getName(), "Поля name не совпадают");
        assertEquals(savedTask.getId(), task.getId(), "Поля id не совпадают");
        assertEquals(savedTask.getDescription(), task.getDescription(), "Поля description не совпадают");
        assertEquals(savedTask.getStatus(), task.getStatus(), "Поля status не совпадают");
    }

    @Test
    void listSizePreservation() {
        Task task1 = new Task("name1", "description1",1, Status.NEW);
        historyManager.add(task1);

        Task task2 = new Task("name2", "description2", 2, Status.IN_PROGRESS);
        for (int i = 0; i < 9; i++) {
            historyManager.add(task2);
        }

        final List<Task> savedHistory1 = historyManager.getHistory();
        final Task savedTask1 = savedHistory1.getFirst();

        historyManager.add(task2);

        final List<Task> savedHistory2 = historyManager.getHistory();
        final Task savedTask2 = savedHistory2.getFirst();

        assertNotNull(savedHistory1, "История пустая.");
        assertNotNull(savedHistory2, "История пустая.");
        assertNotNull(savedTask1, "Задача не найдена.");
        assertNotNull(savedTask2, "Задача не найдена.");
        assertEquals(10, savedHistory2.size(), "Размер списка не сохранил свое предельное значение");
        assertNotEquals(savedTask1, savedTask2, "Не удален самый старый элемент списка");
    }

}