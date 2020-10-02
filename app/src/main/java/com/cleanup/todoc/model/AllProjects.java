package com.cleanup.todoc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stock all the on use projects for recyclerView from database
 */
public class  AllProjects {

    private static List<Project> allProjects = new ArrayList<>();

    public static void setAllProjects(List<Project> projects){
        allProjects = projects;
    }

    public static List<Project> getAllProjects(){
        return allProjects;
    }
}
