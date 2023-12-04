package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {

    }

    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static String getStatusPayment() {
        var codeSQL = "SELECT status FROM payment_entity";
        var conn = getConn();
        var code = runner.query(conn, codeSQL, new ScalarHandler<String>());

        return code;
    }

    @SneakyThrows
    public static String getStatusCredit() {
        var codeSQL = "SELECT status FROM credit_request_entity";
        var conn = getConn();
        var code = runner.query(conn, codeSQL, new ScalarHandler<String>());

        return code;
    }

    @SneakyThrows
    public static void cleanDataBase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM order_entity;");
        runner.execute(connection, "DELETE FROM payment_entity;");
        runner.execute(connection, "DELETE FROM credit_request_entity;");
    }

}


