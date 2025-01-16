package TaskClassesTests;

import TaskClasses.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void isEqualOnlyById() {
        Task task1 = new Task("name1", "description1", 1, Status.NEW);
        Task task2 = new Task("name2", "description2", 1, Status.DONE);
        assertEquals(task1, task2, "Задачи не равны.");
    }
}