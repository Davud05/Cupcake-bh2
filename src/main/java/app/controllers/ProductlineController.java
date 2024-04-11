package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ProductlineMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class ProductlineController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("orderview", ctx -> index(ctx, connectionPool));
        app.post("search", ctx -> search(ctx, connectionPool));
        app.get("search", ctx -> ctx.render("cupcake.html"));
        app.post("addorder", ctx -> addToOrder(ctx, connectionPool));
        app.get("addorder", ctx -> ctx.render("addorders.html"));
    }

    private static void search(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String top = ctx.formParam("top");
        String bottom = ctx.formParam("bottom");
        int productlineAmount = Integer.parseInt(ctx.formParam("quantity"));
        if (!(("Vælg topping").equals(top)) || !(("Vælg bund").equals(bottom))) {

            try {
                List<Productline> productlineList = ProductlineMapper.search(top, bottom, productlineAmount, connectionPool);
                if (productlineList.isEmpty()) {
                    ctx.attribute("message", "There are no cupcakes");
                    ctx.render("index.html");
                } else {
                    ctx.sessionAttribute("productlineList", productlineList);
                    ctx.render("index.html");
                }
            } catch (DatabaseException e) {
                ctx.attribute("message", "Please choose categories!!!");
                ctx.render("index.html");
            }
        }
    }

    private static void addToOrder(Context ctx, ConnectionPool connectionPool) {

        Customer customer = ctx.sessionAttribute("currentCustomer");
        int customerId = customer.getCustomerId();

        try {
            Order newOrder = ProductlineMapper.addToOrder(customerId, connectionPool);
            ctx.attribute("message", "You have added to your order");
            ctx.sessionAttribute("customerOrder", newOrder);
            ctx.render("/cupcake.html");

        } catch (DatabaseException e) {
            ctx.attribute("message", "Something went wrong, try again");
            ctx.render("/cupcake.html");
        }


    }

    private static void index(Context ctx, ConnectionPool connectionPool) {
        ctx.render("/index.html");
    }
}