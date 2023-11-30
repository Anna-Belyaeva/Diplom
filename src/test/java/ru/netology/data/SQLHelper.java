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

    private static final String user = System.getProperty("app");
    private static final String password = System.getProperty("pass");

    private static Connection getConnMySQL() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", user, password);
    }

    private static Connection getConnPostgresQL() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", user, password);
    }

    @SneakyThrows
    public static DataHelper.PaymentStatus getStatusMySQL() {
        var codeSQL = "SELECT status FROM payment_entity";
        var conn = getConnMySQL();
        var code = runner.query(conn, codeSQL, new ScalarHandler<String>());

        return new DataHelper.PaymentStatus(code);
    }

    @SneakyThrows
    public static DataHelper.PaymentStatus getStatusPostgresQL() {
        var codeSQL = "SELECT status FROM payment_entity";
        var conn = getConnPostgresQL();
        var code = runner.query(conn, codeSQL, new ScalarHandler<String>());

        return new DataHelper.PaymentStatus(code);
    }

    @SneakyThrows
    public static void cleanMySQLBase() {
        var connection = getConnMySQL();
        runner.execute(connection, "DELETE FROM credit_request_entity;");
        runner.execute(connection, "DELETE FROM order_entity;");
        runner.execute(connection, "DELETE FROM payment_entity;");
    }

    @SneakyThrows
    public static void cleanPostgresQLBase() {
        var connection = getConnPostgresQL();
        runner.execute(connection, "DELETE FROM credit_request_entity;");
        runner.execute(connection, "DELETE FROM order_entity;");
        runner.execute(connection, "DELETE FROM payment_entity;");
    }
}
