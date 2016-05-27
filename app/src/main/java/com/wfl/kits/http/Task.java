package com.wfl.kits.http;

/**
 * Created by wfl on 16/3/31.
 */
public class Task {
    public String url;
    public TaskType taskType;




    enum TaskType {
        GET, POST, PUT, DELETE
    }
}
