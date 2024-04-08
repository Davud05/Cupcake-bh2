package app.controllers;

import app.entities.Customer;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CustomerMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class CustomerController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {

        app.get("/createorder", ctx -> ctx.render("createorder.html"));
        app.post("/createorders", ctx -> createOrder(ctx, connectionPool));

        app.get("/vieworders", ctx -> ctx.render("vieworders.html"));
        app.post("/vieworders", ctx -> viewOrder(ctx, connectionPool));

        app.get("/createcustomer", ctx -> ctx.render("createcustomer.html"));
        app.post("/createcustomer", ctx -> createCustomer(ctx, connectionPool));
        app.post("/login", ctx -> login(ctx, connectionPool));
        app.get("/logout", ctx -> logout(ctx));
    }

    private static void viewOrder(Context ctx, ConnectionPool connectionPool) {

    }

    private static void createOrder(Context ctx, ConnectionPool connectionPool) {

    }

    private static void createCustomer(Context ctx, ConnectionPool connectionPool)
    {
        // Hent form parametre
        String customerEmail = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        if (password1.equals(password2))
        {
            try
            {
                CustomerMapper.createCustomer(customerEmail, password1, password2, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet med brugernavn: " + customerEmail +
                        ". Nu skal du logge på.");
                ctx.render("cupcake.html");
            }

            catch (DatabaseException e)
            {
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen, eller log ind");
                ctx.render("createcustomer.html");
            }
        } else
        {
            ctx.attribute("message", "Dine to passwords matcher ikke! Prøv igen");
            ctx.render("createcustomer.html");
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
