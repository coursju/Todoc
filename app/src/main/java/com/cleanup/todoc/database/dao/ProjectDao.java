package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

public interface ProjectDao {
    @Query("SELECT * FROM project")
    LiveData<List<Project>> getProject();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);
}
