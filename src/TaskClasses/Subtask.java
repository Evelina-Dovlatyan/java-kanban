package TaskClasses;

public class Subtask extends Task {
    protected int epicId;

    public Subtask(String name, String description, int id, Status status, int epicId) {
        super(name, description, id, status);
        if (epicId != id) {
            this.epicId = epicId;
        }
    }

    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        if (epicId != id) {
            this.epicId = epicId;
        }
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
