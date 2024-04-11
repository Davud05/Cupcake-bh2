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
        /*
        app.post("search", ctx -> search(ctx, connectionPool));
        app.get("search", ctx -> ctx.render("cupcake.html"));

        app.post("cart", ctx -> createOrder(ctx, connectionPool));
        app.get("cart", ctx -> ctx.render("cupcake.html"));

        app.post("createorders", ctx -> createOrder(ctx, connectionPool));
        app.get("createorder", ctx -> ctx.render("createorder.html"));

        app.post("vieworders", ctx -> viewOrder(ctx, connectionPool));
        app.get("vieworders", ctx -> ctx.render("vieworders.html"));
*/

        app.post("create-customer-success", ctx -> createCustomer(ctx, connectionPool));
        app.get("create-customer-success", ctx -> ctx.render("index.html"));
        app.get("createcustomer", ctx -> ctx.render("createcustomer.html"));
        app.post("createcustomer", ctx -> createCustomer(ctx, connectionPool));

        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("logout", ctx -> logout(ctx));
        app.get("cupcake", ctx -> ctx.render("cupcake.html"));
    }


    private static void index(Context ctx, ConnectionPool connectionPool) {
        ctx.render("/index.html");
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
