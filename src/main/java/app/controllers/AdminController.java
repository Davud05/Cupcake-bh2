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
        app.get("create-admin-success", ctx -> ctx.render("__admin-login.html"));
        app.get("createadmin", ctx -> ctx.render("createadmin.html"));
        app.post("createadmin", ctx -> createAdmin(ctx, connectionPool));


        app.post("admin-login-success", ctx -> adminlogin(ctx, connectionPool));
        app.get("admin-login-success", ctx -> ctx.render("__admin-overview.html"));
        app.get("adminlogin", ctx -> ctx.render("__admin-login.html"));
        app.post("adminlogin", ctx -> adminlogin(ctx, connectionPool));
        app.get("adminlogout", ctx -> adminlogout(ctx));

        // TESTING CONTROLLERS

        app.post("__admin-ordrer-oversigt", ctx -> createAdmin(ctx, connectionPool));
        app.get("__admin-ordrer-oversigt", ctx -> ctx.render("__admin-ordrer-oversigt.html"));
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
                AdminMapper.createAdmin(adminemail, adminpassword,adminname, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet som ADMIN med email: " + adminemail +
                        ". Nu skal du logge på.");
                ctx.render("__admin-login.html");
            }
            catch (DatabaseException e)
            {
                ctx.attribute("message", "Din Email findes allerede. Prøv igen, eller log ind");
                ctx.render("__admin-login.html");
            }
        }
        else
        {
            ctx.attribute("message", "Dine to passwords matcher ikke! Prøv igen");
            ctx.render("__admin-login.html");
        }

    }

    private static void adminlogout(Context ctx)
    {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }


    public static void adminlogin(Context ctx, ConnectionPool connectionPool)
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
            ctx.render("/__admin-oversigt.html");
        }
        catch (DatabaseException e)
        {
            // Hvis nej, send tilbage til login side med fejl besked
            ctx.attribute("message", e.getMessage() );
            ctx.render("/__admin-login.html");
        }

    }
}
