package com.xuexi.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author
 * @version TODO
 * @class 客户端连接工具
 * @CalssName HiveJDBCClient
 * @create 2020-07-28 10:39
 * @Des TODO
 */
public class JDBCClientUtils {
    /**
     * 获取HiveJDBC的连接客户端
     *
     * @return Connection
     * @throws Exception
     */
/*    public static Connection getHiveJDBCClient() throws Exception {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection hiveJDBCConnection = DriverManager.getConnection("jdbc:hive2://192.168.1.241:10000/tashanopc", "hive", null);
        if (hiveJDBCConnection == null) {
            throw new Exception("连接失败");
        } else {
            return hiveJDBCConnection;
        }
    }*/

    /**
     * 获取 hivePresto的链接客户端--未安装
     *
     * @return Connection
     * @throws Exception
     */
/*    public static Connection getHivePrestoClient() throws Exception {
        Class.forName("com.facebook.presto.jdbc.PrestoDriver");
        Connection hivePrestoConnection = DriverManager.getConnection("jdbc:presto://192.168.10.10:8085/hive/test", "hive", null);
        if (hivePrestoConnection == null) {
            throw new Exception("连接失败");
        } else {
            return hivePrestoConnection;
        }
    }*/

    /**
     * 获取MySQL客户端
     *
     * @return Connection
     * @throws Exception
     */
    public static Statement getMysqlClient(String ip, String port, String userName, String password, String databaseName) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String mysql_dbURL = "jdbc:mysql://" + ip + ":" + port + "/" + databaseName + "?rewriteBatchedStatements=true";
        Connection mysql_dbConn = DriverManager.getConnection(mysql_dbURL, userName, password);
        Statement mysql_statement = mysql_dbConn.createStatement();
        if (mysql_statement == null) {
            throw new Exception("mysql数据库连接失败");
        } else {
            return mysql_statement;
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String mysql_dbURL = "jdbc:mysql://10.238.251.3:3306/bigdata_connect?rewriteBatchedStatements=true";
        Connection mysql_dbConn = DriverManager.getConnection(mysql_dbURL, "bigdata_connect", "bigdata_connect");
        Statement mysql_statement = mysql_dbConn.createStatement();
    }
}
