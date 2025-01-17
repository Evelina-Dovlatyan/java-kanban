package Manager;

import TaskClasses.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    HistoryManager historyManager = Managers.getDefaultHistory();

    private int idCounter = 0;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();


    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

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
            if (!epicsList.contains(epics.get(subtask.getEpicId()))) {
                epicsList.add(epics.get(subtask.getEpicId()));
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
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.add(subtasks.get(id));
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
        Epic epic = epics.get(subtask.getEpicId());
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
        updateEpic(epics.get(subtask.getEpicId()));
    }

    @Override
    public void removeTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpic(int id) {
        Epic epic  = epics.get(id);
        ArrayList<Integer> subtasksID = epic.getSubtasksId();
        for (int subId : subtasksID) {
            subtasks.remove(subId);
        }
        epics.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        Epic epic = epics.get(subtask.getEpicId());
        epic.removeSubtaskId(id);
        updateEpic(epic);
        subtasks.remove(id);
    }


    @Override
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> newSubtasks = new ArrayList<>();
        for (int id : epic.getSubtasksId()) {
            Subtask subtask = subtasks.get(id);
            newSubtasks.add(subtask);
        }
        return newSubtasks;
    }

    private int getNewID() {
        return ++idCounter;
    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<Status> subtaskStatus = new ArrayList<>();
        ArrayList<Integer> subtasksID = epic.getSubtasksId();
        for (int id : subtasksID) {
            Subtask subtask = subtasks.get(id);
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
