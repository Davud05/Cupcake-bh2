package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.*;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class CustomerController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.post("search", ctx -> search(ctx, connectionPool));
        app.get("search", ctx -> ctx.render("cupcake.html"));

        app.post("cart", ctx -> createOrder(ctx, connectionPool));
        app.get("cart", ctx -> ctx.render("cupcake.html"));

        app.post("createorders", ctx -> createOrder(ctx, connectionPool));
        app.get("createorder", ctx -> ctx.render("createorder.html"));

        app.post("vieworders", ctx -> viewOrder(ctx, connectionPool));
        app.get("vieworders", ctx -> ctx.render("vieworders.html"));

        app.post("create-customer-success", ctx -> createCustomer(ctx, connectionPool));
        app.get("create-customer-success", ctx -> ctx.render("index.html"));
        app.get("createcustomer", ctx -> ctx.render("createcustomer.html"));
        app.post("createcustomer", ctx -> createCustomer(ctx, connectionPool));

        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("logout", ctx -> logout(ctx));
        app.get("cupcake", ctx -> ctx.render("cupcake.html"));
    }
    private static void search(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String top = ctx.formParam("top");
        String bottom = ctx.formParam("bottom");
        int productlineAmount = Integer.parseInt(ctx.formParam("quantity"));
        if (!(("Choose top").equals(top)) || !(("Choose bottom").equals(bottom))) {

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

    private static void addToOrder (Context ctx, ConnectionPool connectionPool) {

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

    private static void viewOrder(Context ctx, ConnectionPool connectionPool) {
        ctx.render("/allorders");
    }

    private static void createOrder(Context ctx, ConnectionPool connectionPool) {

    }

    private static void createCustomer(Context ctx, ConnectionPool connectionPool)
    {
        // Hent form parametre
        String customeremail = ctx.formParam("customeremail");
        String customerpassword = ctx.formParam("customerpassword");
        String password2 = ctx.formParam("password2");
        String customername = ctx.formParam("customername");

        if (customerpassword.equals(password2))
        {
            try
            {
                CustomerMapper.createCustomer(customeremail, customerpassword,customername, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet med email: " + customeremail +
                        ". Nu skal du logge på.");
                ctx.render("index.html");
            }
            catch (DatabaseException e)
            {
                ctx.attribute("message", "Din Email findes allerede. Prøv igen, eller log ind");
                ctx.render("index.html");
            }
        }
        else
        {
            ctx.attribute("message", "Dine to passwords matcher ikke! Prøv igen");
            ctx.render("index.html");
        }

    }

    private static void logout(Context ctx)
    {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }


    public static void login(Context ctx, ConnectionPool connectionPool)
    {
        // Hent form parametre
        String customerEmail = ctx.formParam("customeremail");
        String customerPassword = ctx.formParam("customerpassword");

        // Check om bruger findes i DB med de angivne username + password
        try
        {
            Customer customer = CustomerMapper.login(customerEmail, customerPassword, connectionPool);
            ctx.sessionAttribute("currentCustomer", customer);
            // Hvis ja, send videre til forsiden med login besked
            ctx.attribute("message", "Du er nu logget ind");
            ctx.render("/cupcake.html");
        }
        catch (DatabaseException e)
        {
            // Hvis nej, send tilbage til login side med fejl besked
            ctx.attribute("message", e.getMessage() );
            ctx.render("/index.html");
        }

    }
}
