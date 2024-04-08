package app.controllers;

import app.entities.Bottom;
import app.exceptions.DatabaseException;
import app.persistence.BottomMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class BottomController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/bottoms", ctx -> listBottoms(ctx, connectionPool));
    }

    private static void listBottoms(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        try {
            List<Bottom> bottoms = new BottomMapper().findAll(connectionPool); //
            ctx.attribute("bottomsList", bottoms);
            ctx.render("Cupcake-bh2/cupcake.html");  // Tilføj HTML(Men ved ikke hvordan) c4/index.html"
        } catch (DatabaseException e) {
            ctx.attribute("message", "Error retrieving bottoms: " + e.getMessage());
            ctx.render("Cupcake-bh2/cupcake.html"); // Tilføj HTML(Men ved ikke hvordan)
        }
    }
}