package app.controllers;

import app.entities.Top;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.TopMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class TopController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/tops", ctx -> index(ctx));
        app.post("/tops/search", ctx -> searchTops(ctx, connectionPool));
    }

    private static void searchTops(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String topName = ctx.formParam("name");
        try {
            List<Top> topsList = new TopMapper().searchTopsByName(topName, connectionPool);
            ctx.attribute("topsList", topsList);
            ctx.render("/tops/index.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Error searching for tops: " + e.getMessage());
            ctx.render("/tops/index.html");
        }
    }

    private static void index(Context ctx) {
        ctx.render("/tops/index.html");
    }
}