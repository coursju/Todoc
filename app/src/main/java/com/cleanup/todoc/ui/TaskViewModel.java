package com.cleanup.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    // REPOSITORIES
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<List<Project>> currentProject;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init() {
        if (this.currentProject == null) {
            currentProject = projectDataSource.getProjects();
        }
    }

    // -------------
    // FOR PROJECTS
    // -------------

    public LiveData<List<Project>> getProjects() { return this.currentProject;  }

    // -------------
    // FOR TASKS
    // -------------

    public LiveData<List<Task>> getTasks() {
        return taskDataSource.getTasks();
    }

    public void createTask(Task task) {
//        executor.execute(() -> {
//            taskDataSource.createTask(task);
//        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDataSource.createTask(task);

            }
        });
    }

    public void deleteTask(Task task) {
        executor.execute(() -> {
            taskDataSource.deleteTask(task);
        });
    }

}
