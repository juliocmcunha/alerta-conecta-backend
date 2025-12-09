package net.devs404.alerta_conecta_backend.database;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DataBase
{
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String pass;

    private static String URL;
    private static String USER;
    private static String PASS;

    @PostConstruct
    public void init() {
        URL = url;
        USER = user;
        PASS = pass;
    }

    public static Connection connect()
    {
        Connection connect;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(URL, USER, PASS);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        return connect;
    }
}
