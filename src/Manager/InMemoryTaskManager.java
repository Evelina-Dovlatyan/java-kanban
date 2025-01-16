package Manager;

import TaskClasses.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private int idCounter = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    @Override
    public ArrayList<Task> getAllTasksList() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpicsList() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasksList() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
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

    @Override
    public Task getTask(int id) {
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    @Override
    public void putNewTask(Task task) {
        task.setId(getNewID());
        tasks.put(task.getId(), task);
    }

    @Override
    public void putNewEpic(Epic epic) {
        epic.setId(getNewID());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void putNewSubtask(Subtask subtask) {
        Epic epic = getEpic(subtask.getEpicId());
        if (epic != null) {
            subtask.setId(getNewID());
            subtasks.put(subtask.getId(), subtask);
            epic.addSubtaskId(subtask.getId());
            updateEpic(epic);
        }
    }

    @Override
    public void updateTask(Task task) {
        tasks.replace(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        updateEpicStatus(epic);
        epics.replace(epic.getId(), epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.replace(subtask.getId(), subtask);
        updateEpic(getEpic(subtask.getEpicId()));
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        Epic epic  = getEpic(id);
        ArrayList<Integer> subtasksID = epic.getSubtasksId();
        for (int subId : subtasksID) {
            subtasks.remove(subId);
        }
        epics.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        Subtask subtask = getSubtask(id);
        Epic epic = getEpic(subtask.getEpicId());
        epic.removeSubtaskId(id);
        updateEpic(epic);
        subtasks.remove(id);
    }


    @Override
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
        }
    }
}
