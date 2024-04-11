package app.controllers;

import app.entities.Customer;
import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class OrderController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/viewShoppingCart", ctx -> viewShoppingCart(ctx, connectionPool));
        app.get("/viewShoppingCart", ctx -> ctx.render("/vieworders.html"));
        app.post("/pay", ctx -> payForOrder(ctx, connectionPool));
        app.get("/pay", ctx -> ctx.render("/index.html"));
    }

    private static void viewShoppingCart(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        Customer customer = ctx.sessionAttribute("currentCustomer");
        int customerId = customer.getCustomerId();

            try {
                List<Order> orderList = OrderMapper.viewShoppingCart(customerId, connectionPool);
                if (orderList.isEmpty()) {
                    ctx.attribute("message", "You have not added any cupcakes to your order");
                    ctx.render("/cupcake.html");
                } else {
                    ctx.sessionAttribute("orderlist", orderList);
                    ctx.render("/vieworders.html");
                }
            } catch (DatabaseException e) {
                ctx.attribute("message", "Something went wrong while looking in shopping cart");
                ctx.render("/cupcake.html");
            }


    }

    private static void payForOrder (Context ctx, ConnectionPool connectionPool) {

        Customer customer = ctx.sessionAttribute("currentCustomer");
        Order customerOrder = ctx.sessionAttribute("customerOrder");
        int customerId = customer.getCustomerId();

        try {
            OrderMapper.payForOrder(customerOrder, customerId, connectionPool);

            if (customer.getCustomerBalance() >= customerOrder.getOrderPrice()) {
                ctx.attribute("message", "You have paid for your order. Thank you for your purchase");
                ctx.req().getSession().removeAttribute("customerOrder");
                ctx.render("/cupcake.html");
            }
            else {
                ctx.attribute("message", "You have insufficient funds");
                ctx.render("/cupcake.html");
                }

        } catch (DatabaseException e) {
            ctx.attribute("message", "Something went wrong while administering payment.");
            ctx.render("/cupcake.html");
        }


    }

    private static void index(Context ctx, ConnectionPool connectionPool) {
        ctx.render("/index.html");
    }
}