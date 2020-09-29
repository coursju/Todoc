package com.cleanup.todoc.database;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;

    private long idProject = 1;
    private Project theProject = new Project(idProject,"Projet Tartampion", 0xFFEADAD1);

    private Task task1 = new Task(idProject, "afaire1", 123);
    private Task task2 = new Task(idProject, "afaire2", 345);
    private Task task3 = new Task(idProject, "afaire3", 678);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void getProjects() throws InterruptedException {
        this.database.projectDao().createProject(theProject);
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getProject());
        assertTrue(projects.get(0).getName().equals(theProject.getName()) && projects.get(0).getId() == idProject);
    }


    @Test
    public void taskShouldBeEmpty() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }


    @Test
    public void addTask() throws InterruptedException {
        this.database.projectDao().createProject(theProject);
        this.database.taskDao().insertTask(task1);
        this.database.taskDao().insertTask(task2);
        this.database.taskDao().insertTask(task3);
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.size() == 3);
    }


    @Test
    public void removeTask() throws InterruptedException {
        this.database.projectDao().createProject(theProject);
        this.database.taskDao().insertTask(task1);
        Task addTask = LiveDataTestUtil.getValue(this.database.taskDao().getTasks()).get(0);
        this.database.taskDao().deleteTask(addTask);
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }
}
