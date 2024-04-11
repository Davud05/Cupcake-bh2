package app.controllers;

import app.entities.Admin;
import app.entities.Customer;
import app.exceptions.DatabaseException;
import app.persistence.AdminMapper;
import app.persistence.ConnectionPool;
import app.persistence.CustomerMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AdminController
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

        app.post("create-admin-success", ctx -> createAdmin(ctx, connectionPool));
        app.get("create-admin-success", ctx -> ctx.render("index.html"));
        app.get("createadmin", ctx -> ctx.render("createadmin.html"));
        app.post("createadmin", ctx -> createAdmin(ctx, connectionPool));

        app.post("adminlogin", ctx -> login(ctx, connectionPool));
        app.get("adminlogout", ctx -> logout(ctx));
        app.get("/admin/", ctx -> ctx.render("index.html"));
    }


    private static void index(Context ctx, ConnectionPool connectionPool) {
        ctx.render("/index.html");
    }

    private static void createAdmin(Context ctx, ConnectionPool connectionPool)
    {
        // Hent form parametre
        String adminemail = ctx.formParam("adminemail");
        String adminpassword = ctx.formParam("adminpassword");
        String password2 = ctx.formParam("password2");
        String adminname = ctx.formParam("adminname");

        if (adminpassword.equals(password2))
        {
            try
            {
                CustomerMapper.createCustomer(adminemail, adminpassword,adminname, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet som ADMIN med email: " + adminemail +
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
        String adminEmail = ctx.formParam("adminemail");
        String adminPassword = ctx.formParam("adminpassword");

        // Check om bruger findes i DB med de angivne username + password
        try
        {
            Admin admin = AdminMapper.login(adminEmail, adminPassword, connectionPool);
            ctx.sessionAttribute("currentAdmin", admin);
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
