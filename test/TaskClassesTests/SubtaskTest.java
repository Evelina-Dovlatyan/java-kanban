package TaskClassesTests;

import TaskClasses.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void isEqualOnlyById() {
        Subtask subtask1 = new Subtask("name1", "description1", 1, Status.NEW, 1);
        Subtask subtask2 = new Subtask("name2", "description2", 1, Status.DONE, 2);
        assertEquals(subtask1, subtask2, "Задачи не равны.");
    }

    @Test
    void putSubtaskAsEpic() {
        Subtask subtask = new Subtask("name", "description", 1, Status.NEW, 2);
        subtask.setEpicId(1);
        assertEquals(2, subtask.getEpicId(), "Подзадача добавила себя как эпик.");
    }

}