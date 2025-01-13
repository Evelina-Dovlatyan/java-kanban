package Manager;

import TaskClasses.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private int idCounter = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public ArrayList<Task> getAllTasksList() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpicsList() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasksList() {
        return new ArrayList<>(subtasks.values());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public void putNewTask(Task task) {
        final int id = ++idCounter;
        task.setId(id);
        tasks.put(id, task);
    }

    public void putNewEpic(Epic epic) {
        final int id = ++idCounter;
        epic.setId(id);
        epics.put(id, epic);
    }

    public void putNewSubtask(Subtask subtask) {
        Epic epic = getEpic(subtask.getEpicId());
        if (epic == null) {
            System.out.println("Нет такого эпика: " + subtask.getEpicId());
        } else {
            final int id = ++idCounter;
            subtask.setId(id);
            subtasks.put(id, subtask);
            epic.addSubtaskId(subtask.getId());
            updateEpic(epic);
        }
    }

    public void updateTask(Task task) {
        tasks.replace(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        updateEpicStatus(epic);
        epics.replace(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.replace(subtask.getId(), subtask);
        updateEpic(getEpic(subtask.getEpicId()));
    }

    public void removeTask(int id) {
        tasks.remove(id);
    }

    public void removeEpic(int id) {
        Epic epic  = getEpic(id);
        ArrayList<Integer> subtasksID = epic.getSubtasksId();
        for (int subId : subtasksID) {
            subtasks.remove(subId);
        }
        epics.remove(id);
    }

    public void removeSubtask(int id) {
        Subtask subtask = getSubtask(id);
        Epic epic = getEpic(subtask.getEpicId());
        epic.removeSubtaskId(id);
        updateEpic(epic);
        subtasks.remove(id);
    }

    public void updateEpicStatus(Epic epic) {
        ArrayList<Status> subtaskStatus = new ArrayList<>();
        ArrayList<Integer> subtasksID = epic.getSubtasksId();
        for (int id : subtasksID) {
            Subtask subtask = getSubtask(id);
            subtaskStatus.add(subtask.getStatus());
        }
        if (subtaskStatus.contains(Status.IN_PROGRESS)) {
            epic.setStatus(Status.IN_PROGRESS);
        } else if (subtaskStatus.contains(Status.NEW)  &&
                subtaskStatus.contains(Status.DONE)) {
            epic.setStatus(Status.IN_PROGRESS);
        } else {
            epic.setStatus(subtaskStatus.getFirst());
        }
    }

    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        Epic epic = getEpic(epicId);
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (int id : epic.getSubtasksId()) {
            Subtask subtask = getSubtask(id);
            subtasks.add(subtask);
        }
        return subtasks;
    }
}
