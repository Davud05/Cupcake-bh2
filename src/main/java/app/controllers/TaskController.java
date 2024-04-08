package app.controllers;

import app.entities.Task;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.TaskMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class TaskController {
    public static void addRoutes (Javalin app, ConnectionPool connectionPool) {
        app.post("addtask", ctx -> addTask(ctx, connectionPool));
        app.get("addtask", ctx -> ctx.render("/task.html"));

    }

    private static void addTask(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String taskName = ctx.formParam("taskname");
        if (taskName.length() > 3)
        {
            User user = ctx.sessionAttribute("currentUser");
            try
            {
                Task newTask = TaskMapper.addTask(user, taskName, connectionPool);
                List<Task> taskList = TaskMapper.getAllTasksPerUser(user.getUserId(), connectionPool);
                ctx.attribute("taskList", taskList);
                // get TASKS belonging to this user in database
                ctx.render("task.html");

            } catch (DatabaseException e)
            {
                ctx.attribute("message", "noget gik galt. Prøv evt. igen");
                ctx.render("task.html");
            }
        }
    }
}
