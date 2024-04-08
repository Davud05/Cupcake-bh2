package app.controllers;

import app.entities.Food;
import app.entities.Productline;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.FoodMapper;
import app.persistence.ProductlineMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class ProductlineController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/order", ctx -> index(ctx, connectionPool));
        app.post("/search", ctx -> search(ctx, connectionPool));
        app.get("/search", ctx -> ctx.render("/index.html"));

    }

    private static void search(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String top = ctx.formParam("top");
        String bottom = ctx.formParam("bottom");
        if (!(("Choose top").equals(top)) || !(("Choose bottom").equals(bottom))) {

            try {
                List<Productline> productlineList = ProductlineMapper.search(top, bottom, connectionPool);
                if (productlineList.isEmpty()) {
                    ctx.attribute("message", "There is no cupcakes");
                    ctx.render("index.html");
                } else {
                    ctx.attribute("productlineList", productlineList);
                    ctx.render("index.html");
                }
            } catch (DatabaseException e) {
                ctx.attribute("message", "Please choose categories!!!");
                ctx.render("index.html");
            }
        }
    }

    private static void index(Context ctx, ConnectionPool connectionPool) {
        ctx.render("/index.html");
    }
}