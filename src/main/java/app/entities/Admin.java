package app.entities;

public class Admin {
    int adminId;
    String adminName;
    String adminPassword;
    String adminEmail;

    public Admin(int adminId,String adminEmail, String adminPassword ) {
        this.adminId = adminId;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    public Admin(int adminId, String adminName, String adminPassword, String adminEmail) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.adminEmail = adminEmail;
    }

    public int getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                '}';
    }
}
