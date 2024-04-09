package app.controllers;

import app.entities.Top;
import app.exceptions.DatabaseException;
import app.persistence.TopMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class TopController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/tops", ctx -> listTops(ctx, connectionPool));
    }

    private static void listTops(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        try {
            List<Top> tops = new TopMapper().findAll(connectionPool);
            ctx.attribute("topsList", tops);
            ctx.render("/cupcake.html"); // Tilføj HTML(Men ved ikke hvordan)
        } catch (DatabaseException e) {
            ctx.attribute("message", "Error retrieving tops: " + e.getMessage());
            ctx.render("/cupcake.html"); // Tilføj HTML(Men ved ikke hvordan)
        }
    }
}