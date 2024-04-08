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
        app.get("/bottoms", ctx -> index(ctx));
        app.post("/bottoms/search", ctx -> searchBottoms(ctx, connectionPool));
    }

    private static void searchBottoms(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String bottomName = ctx.formParam("name");
        try {
            List<Bottom> bottomsList = new BottomMapper().searchBottomsByName(bottomName, connectionPool);
            ctx.attribute("bottomsList", bottomsList);
            ctx.render("/bottoms/index.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Error searching for bottoms: " + e.getMessage());
            ctx.render("/bottoms/index.html");
        }
    }

    private static void index(Context ctx) {
        ctx.render("/bottoms/index.html");
    }
}