package Manager;

import TaskClasses.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> requestHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (requestHistory.size() < 10) {
            requestHistory.add(task);
        } else {
            requestHistory.removeFirst();
            requestHistory.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return requestHistory;
    }
}
