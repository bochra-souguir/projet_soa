package com.info.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnexionDB {

	String url = "jdbc:mysql://localhost:3308/tptest?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    String login="root";
    String password="root";
    static Connection cn; // l'objet connection

    private ConnexionDB() {
        super();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection(url, login, password);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnexion () {
        if (cn==null) {
            new ConnexionDB();
        }
        return cn;
    }
}