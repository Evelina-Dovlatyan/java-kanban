package TaskClasses;

import java.util.ArrayList;
import java.util.Collections;

public class Epic extends Task {
    protected ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(String name, String description, int id, Status status) {
        super(name, description, id, status);
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public void addSubtaskId(int subtaskId) {
        if (subtaskId != getId()) {
            subtasksId.add(subtaskId);
        }
    }

    public void removeSubtaskId(int id) {
        subtasksId.removeAll(Collections.singleton(id));
    }

    public void removeAllSubtaskId() {
        subtasksId.clear();
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasksId=" + subtasksId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
