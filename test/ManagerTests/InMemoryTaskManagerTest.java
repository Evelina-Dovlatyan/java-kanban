package ManagerTests;

import Manager.*;
import TaskClasses.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    void putNewTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.putNewTask(task);

        final Task savedTask = taskManager.getTask(task.getId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getAllTasksList();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void putNewEpic() {
        Epic epic = new Epic("Test putNewEpic", "Test putNewEpic description", Status.NEW);
        taskManager.putNewEpic(epic);

        final Epic savedEpic = taskManager.getEpic(epic.getId());

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают");

        final List<Epic> epics = taskManager.getAllEpicsList();

        assertNotNull(epics, "Эпики не возвращаются");
        assertEquals(1, epics.size(), "Неверное количество эпиков");
        assertEquals(epic, epics.get(0), "Эпики не совпадают");
    }

    @Test
    void putNewSubtask() {
        Epic epic = new Epic("Test putNewEpic", "Test putNewEpic description", 1, Status.NEW);
        taskManager.putNewEpic(epic);
        Subtask subtask = new Subtask("Test putNewSubtask", "Test putNewSubtask description", Status.NEW, epic.getId());
        taskManager.putNewSubtask(subtask);

        final Subtask savedSubtask = taskManager.getSubtask(subtask.getId());

        assertNotNull(savedSubtask, "Подзадача не найдена.");
        assertEquals(subtask, savedSubtask, "Подзадачи не совпадают");

        final List<Subtask> subtasks = taskManager.getAllSubtasksList();

        assertNotNull(subtasks, "Подзадачи не возвращаются");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач");
        assertEquals(savedSubtask, subtasks.get(0), "Подзадачи не совпадают");
    }

    @Test
    void taskWithIdVsTaskWithGivenId() {
        Task task1 = new Task("name", "description", Status.NEW);
        taskManager.putNewTask(task1);

        final Task savedTask1 = taskManager.getTask(task1.getId());

        Task task2 = new Task("name", "description", task1.getId(), Status.NEW);
        taskManager.putNewTask(task2);

        final Task savedTask2 = taskManager.getTask(task2.getId());

        assertNotNull(savedTask1, "Задача не найдена.");
        assertNotNull(savedTask2, "Задача не найдена.");
        assertNotEquals(savedTask1,savedTask2, "Задачи сохранены с одинаковым id");
    }

    @Test
    void epicWithIdVsEpicWithGivenId() {
        Epic epic1 = new Epic("name", "description", Status.NEW);
        taskManager.putNewEpic(epic1);

        final Epic savedEpic1 = taskManager.getEpic(epic1.getId());

        Epic epic2 = new Epic("name", "description", epic1.getId(),  Status.NEW);
        taskManager.putNewEpic(epic2);

        final Epic savedTask2 = taskManager.getEpic(epic2.getId());

        assertNotNull(savedEpic1, "Эпик не найден.");
        assertNotNull(savedTask2, "Эпик не найден.");
        assertNotEquals(savedEpic1,savedTask2, "Эпики сохранены с одинаковым id");
    }

    @Test
    void subtaskWithIdVsSubtaskWithGivenId() {
        Epic epic = new Epic("name", "description", Status.NEW);
        taskManager.putNewEpic(epic);

        final Epic savedEpic = taskManager.getEpic(epic.getId());
        assertNotNull(savedEpic, "Эпик не найден.");

        Subtask subtask1 = new Subtask("name", "description", Status.NEW, savedEpic.getId());
        taskManager.putNewSubtask(subtask1);

        final Subtask savedSubtask1 = taskManager.getSubtask(subtask1.getId());
        assertNotNull(savedSubtask1, "Подзадача не найдена.");

        Subtask subtask2 = new Subtask("name", "description", savedSubtask1.getId(), Status.NEW,savedEpic.getId());
        taskManager.putNewSubtask(subtask2);

        final Subtask savedSubtask2 = taskManager.getSubtask(subtask2.getId());
        assertNotNull(savedSubtask2, "Подзадача не найдена.");
        assertNotEquals(savedSubtask1,savedSubtask2, "Подзадачи сохранены с одинаковым id");
    }

    @Test
    void taskStabilityWhenPutByTaskManager() {
        Task task = new Task("name", "description", Status.NEW);
        taskManager.putNewTask(task);

        final Task savedTask = taskManager.getTask(task.getId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(savedTask.getName(), task.getName(), "Поля name не совпадают");
        assertEquals(savedTask.getId(), task.getId(), "Поля id не совпадают");
        assertEquals(savedTask.getDescription(), task.getDescription(), "Поля description не совпадают");
        assertEquals(savedTask.getStatus(), task.getStatus(), "Поля status не совпадают");
    }

    @Test
    void epicStabilityWhenPutByTaskManager() {
        Epic epic = new Epic("name", "description", Status.NEW);
        taskManager.putNewEpic(epic);

        final Epic savedEpic = taskManager.getEpic(epic.getId());

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(savedEpic.getName(), epic.getName(), "Поля name не совпадают");
        assertEquals(savedEpic.getId(), epic.getId(), "Поля id не совпадают");
        assertEquals(savedEpic.getDescription(), epic.getDescription(), "Поля description не совпадают");
        assertEquals(savedEpic.getStatus(), epic.getStatus(), "Поля status не совпадают");
        assertEquals(savedEpic.getSubtasksId(), epic.getSubtasksId(), "Поля subtasksId не совпадают");
    }

    @Test
    void subtaskStabilityWhenPutByTaskManager() {
        Epic epic = new Epic("name", "description", Status.NEW);
        taskManager.putNewEpic(epic);

        final Epic savedEpic = taskManager.getEpic(epic.getId());
        assertNotNull(savedEpic, "Эпик не найден.");

        Subtask subtask = new Subtask("name", "description", Status.NEW, savedEpic.getId());
        taskManager.putNewSubtask(subtask);

        final Subtask savedSubtask = taskManager.getSubtask(subtask.getId());
        assertNotNull(savedSubtask, "Задача не найдена.");
        assertEquals(savedSubtask.getName(), subtask.getName(), "Поля name не совпадают");
        assertEquals(savedSubtask.getId(), subtask.getId(), "Поля id не совпадают");
        assertEquals(savedSubtask.getDescription(), subtask.getDescription(), "Поля description не совпадают");
        assertEquals(savedSubtask.getStatus(), subtask.getStatus(), "Поля status не совпадают");
        assertEquals(savedSubtask.getEpicId(), subtask.getEpicId(), "Поля epicId не совпадают");
    }

    @Test
    void removeAllTasks() {
        Task task1 = new Task("Test addNewTask1", "Test addNewTask description1", Status.NEW);
        taskManager.putNewTask(task1);

        final Task savedTask1 = taskManager.getTask(task1.getId());
        assertNotNull(savedTask1, "Задача не найдена.");

        Task task2 = new Task("Test addNewTask2", "Test addNewTask description2", Status.NEW);
        taskManager.putNewTask(task2);

        final Task savedTask2 = taskManager.getTask(task2.getId());
        assertNotNull(savedTask2, "Задача не найдена.");

        final List<Task> tasks1 = taskManager.getAllTasksList();
        final int tasks1Size = tasks1.size();
        assertNotNull(tasks1, "Задачи не возвращаются.");
        assertEquals(2, tasks1Size, "Неверное количество задач.");

        taskManager.removeAllTasks();

        final List<Task> tasks2 = taskManager.getAllTasksList();
        final int tasks2Size = tasks2.size();
        assertNotNull(tasks2, "Задачи не возвращаются.");
        assertEquals(0, tasks2Size, "Удаление задач не произошло.");
    }

    @Test
    void removeAllEpics() {
        Epic epic1 = new Epic("name1", "description1", Status.NEW);
        taskManager.putNewEpic(epic1);

        final Epic savedEpic1 = taskManager.getEpic(epic1.getId());
        assertNotNull(savedEpic1, "Задача не найдена.");

        Epic epic2 = new Epic("name2", "description2", Status.NEW);
        taskManager.putNewEpic(epic2);

        final Epic savedEpic2 = taskManager.getEpic(epic2.getId());
        assertNotNull(savedEpic2, "Задача не найдена.");

        final List<Epic> epics1 = taskManager.getAllEpicsList();
        final int epics1Size = epics1.size();
        assertNotNull(epics1, "Задачи не возвращаются.");
        assertEquals(2, epics1Size, "Неверное количество задач.");

        taskManager.removeAllEpics();

        final List<Epic> epics2 = taskManager.getAllEpicsList();
        final int epics2Size = epics2.size();
        assertNotNull(epics2, "Задачи не возвращаются.");
        assertEquals(0, epics2Size, "Удаление задач не произошло.");
    }

    @Test
    void removeAllSubtasks() {
        Epic epic = new Epic("name", "description", Status.NEW);
        taskManager.putNewEpic(epic);

        final Epic savedEpic = taskManager.getEpic(epic.getId());
        assertNotNull(savedEpic, "Задача не найдена.");

        Subtask subtask1 = new Subtask("name1", "description1", Status.NEW, epic.getId());
        taskManager.putNewSubtask(subtask1);

        final Subtask savedSubtask1 = taskManager.getSubtask(subtask1.getId());
        assertNotNull(savedSubtask1, "Задача не найдена.");

        Subtask subtask2 = new Subtask("name2", "description2", Status.NEW, epic.getId());
        taskManager.putNewSubtask(subtask2);

        final Subtask savedSubtask2 = taskManager.getSubtask(subtask2.getId());
        assertNotNull(savedSubtask2, "Задача не найдена.");

        final List<Subtask> subtasks1 = taskManager.getAllSubtasksList();
        final int subtasks1Size = subtasks1.size();
        final ArrayList<Subtask> subtasks1OfEpic = taskManager.getSubtasksOfEpic(epic.getId());

        assertNotNull(subtasks1, "Задачи не возвращаются.");
        assertEquals(2, subtasks1Size, "Неверное количество задач.");
        assertEquals(2, subtasks1OfEpic.size(), "Удаление подзадач в эпике не произошло.");

        taskManager.removeAllSubtasks();

        final List<Subtask> subtasks2 = taskManager.getAllSubtasksList();
        final int subtasks2Size = subtasks2.size();
        final ArrayList<Subtask> subtasks2OfEpic = taskManager.getSubtasksOfEpic(epic.getId());

        assertNotNull(subtasks2, "Задачи не возвращаются.");
        assertEquals(0, subtasks2Size, "Удаление подзадач не произошло.");
        assertEquals(0, subtasks2OfEpic.size(), "Удаление подзадач в эпике не произошло.");
    }

    @Test
    void removeTask() {
        Task task1 = new Task("Test addNewTask1", "Test addNewTask description1", Status.NEW);
        taskManager.putNewTask(task1);

        final Task savedTask1 = taskManager.getTask(task1.getId());
        assertNotNull(savedTask1, "Задача не найдена.");

        Task task2 = new Task("Test addNewTask2", "Test addNewTask description2", Status.NEW);
        taskManager.putNewTask(task2);

        final Task savedTask2 = taskManager.getTask(task2.getId());
        assertNotNull(savedTask2, "Задача не найдена.");

        final List<Task> tasks1 = taskManager.getAllTasksList();
        assertNotNull(tasks1, "Задачи не возвращаются.");

        taskManager.removeTask(savedTask1.getId());

        final List<Task> tasks2 = taskManager.getAllTasksList();
        assertNotNull(tasks2, "Задачи не возвращаются.");
        assertFalse(tasks2.contains(savedTask1), "Задача не удалена.");
    }

    @Test
    void removeEpic() {
        Epic epic1 = new Epic("name1", "description1", Status.NEW);
        taskManager.putNewEpic(epic1);

        final Epic savedEpic1 = taskManager.getEpic(epic1.getId());
        assertNotNull(savedEpic1, "Задача не найдена.");

        Subtask subtask1 = new Subtask("name1", "description1", Status.NEW, epic1.getId());
        taskManager.putNewSubtask(subtask1);

        Epic epic2 = new Epic("name2", "description2", Status.NEW);
        taskManager.putNewEpic(epic2);

        final Epic savedEpic2 = taskManager.getEpic(epic2.getId());
        assertNotNull(savedEpic2, "Задача не найдена.");

        final List<Epic> epics1 = taskManager.getAllEpicsList();
        assertNotNull(epics1, "Задачи не возвращаются.");

        taskManager.removeEpic(savedEpic1.getId());

        final List<Epic> epics2 = taskManager.getAllEpicsList();
        final List<Subtask> subtasks = taskManager.getAllSubtasksList();
        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(0, subtasks.size(), "Удаление подзадач не произошло.");
        assertNotNull(epics2, "Задачи не возвращаются.");
        assertFalse(epics2.contains(savedEpic1), "Задача не удалена.");
    }

    @Test
    void removeSubtask() {
        Epic epic = new Epic("name", "description", Status.NEW);
        taskManager.putNewEpic(epic);

        final Epic savedEpic = taskManager.getEpic(epic.getId());
        assertNotNull(savedEpic, "Эпик не найден.");

        Subtask subtask1 = new Subtask("name1", "description1", Status.NEW, epic.getId());
        taskManager.putNewSubtask(subtask1);

        final Subtask savedSubtask1 = taskManager.getSubtask(subtask1.getId());
        assertNotNull(savedSubtask1, "Подзадача не найдена.");

        Subtask subtask2 = new Subtask("name2", "description2", Status.NEW, epic.getId());
        taskManager.putNewSubtask(subtask2);

        final Subtask savedSubtask2 = taskManager.getSubtask(subtask2.getId());
        assertNotNull(savedSubtask2, "Подзадача не найдена.");

        final List<Subtask> subtasks1 = taskManager.getAllSubtasksList();
        assertNotNull(subtasks1, "Подзадачи не возвращаются.");

        taskManager.removeSubtask(savedSubtask1.getId());

        final List<Subtask> subtasks2 = taskManager.getAllSubtasksList();
        final ArrayList<Subtask> subtasks2OfEpic = taskManager.getSubtasksOfEpic(epic.getId());
        assertNotNull(subtasks2, "Подзадачи не возвращаются.");
        assertFalse(subtasks2.contains(savedSubtask1), "Подзадача не удалена.");
        assertFalse(subtasks2OfEpic.contains(savedSubtask1), "Удаление подзадачи в эпике не произошло.");
    }
}