package ManagerTests;

import Manager.Managers;
import TaskClasses.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void managersAlwaysReturnCompletedTaskManager() {
        assertEquals(new ArrayList<Task>(), Managers.getDefault().getAllTasksList(), "Класс не проинициализирован и не готов к работе.");
    }

    @Test
    void managersAlwaysReturnCompletedHistoryManager() {
        assertEquals(new ArrayList<Task>(), Managers.getDefaultHistory().getHistory(), "Класс не проинициализирован и не готов к работе.");
    }

}