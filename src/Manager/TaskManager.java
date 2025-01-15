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
        ArrayList<Subtask> subtasksList = getAllSubtasksList();
        ArrayList<Epic> epicsList = new ArrayList<>();

        for (Subtask subtask : subtasksList) {
            if (!epicsList.contains(getEpic(subtask.getEpicId()))) {
                epicsList.add(getEpic(subtask.getEpicId()));
            }
        }
        for (Epic epic : epicsList) {
            epic.removeAllSubtaskId();
            updateEpic(epic);
        }
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
        task.setId(getNewID());
        tasks.put(task.getId(), task);
    }

    public void putNewEpic(Epic epic) {
        epic.setId(getNewID());
        epics.put(epic.getId(), epic);
    }

    public void putNewSubtask(Subtask subtask) {
        Epic epic = getEpic(subtask.getEpicId());
        if (epic == null) {
            return;
        } else {
            subtask.setId(getNewID());
            subtasks.put(subtask.getId(), subtask);
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


    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        Epic epic = getEpic(epicId);
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (int id : epic.getSubtasksId()) {
            Subtask subtask = getSubtask(id);
            subtasks.add(subtask);
        }
        return subtasks;
    }

    private int getNewID() {
        return ++idCounter;
    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<Status> subtaskStatus = new ArrayList<>();
        ArrayList<Integer> subtasksID = epic.getSubtasksId();
        for (int id : subtasksID) {
            Subtask subtask = getSubtask(id);
            subtaskStatus.add(subtask.getStatus());
        }
        if (!subtasksID.isEmpty()) {
            if (subtaskStatus.contains(Status.IN_PROGRESS)) {
                epic.setStatus(Status.IN_PROGRESS);
            } else if (subtaskStatus.contains(Status.NEW) &&
                    subtaskStatus.contains(Status.DONE)) {
                epic.setStatus(Status.IN_PROGRESS);
            } else {
                epic.setStatus(subtaskStatus.getFirst());
            }
        } else {
            return;
        }
    }
}
