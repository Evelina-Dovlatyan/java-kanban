package TaskClassesTests;

import TaskClasses.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void isEqualOnlyById() {
        Epic epic1 = new Epic("name1", "description1", 1, Status.NEW);
        Epic epic2 = new Epic("name2", "description2", 1, Status.DONE);
        assertEquals(epic1, epic2, "Задачи не равны.");
    }

    @Test
    void putEpicAsSubtask() {
        Epic epic = new Epic("name1", "description1", 1, Status.NEW);
        epic.addSubtaskId(epic.getId());
        assertEquals(new ArrayList<>(), epic.getSubtasksId(), "Эпик добавил себя как подзадачу.");
    }

}