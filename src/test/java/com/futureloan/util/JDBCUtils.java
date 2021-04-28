package com.futureloan.util;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JDBCUtils {
    public static Connection createConnection(){
        String url="jdbc:mysql://api.lemonban.com/futureloan?useUnicode=true&characterEncoding=utf-8";
        String user="future";
        String password="123456";
        Connection conn=null;
        try {
            conn= DriverManager.getConnection(url,user,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }
    public static void main(String[] args) throws SQLException {
        Connection conn=createConnection();
        QueryRunner queryRunner=new QueryRunner();
//        //增加
//        String sql_insert="insert into member value (20301,'qq','25D55AD283AA400AF464C76D713C07AD','17311221134',1,'4000.00','2021-01-28 15:24:20')";
//        queryRunner.update(conn,sql_insert);
//        String sql_query="select * from member where id=20300";
//        Map<String, Object> query = queryRunner.query(conn, sql_query, new MapHandler());
//        System.out.println(query);
        //更改
        String sql_update="update member set reg_name='lemon' where id= 20300;";
        queryRunner.update(conn,sql_update);
        String sql_query="select * from member where id=20300";
       Map<String, Object> query = queryRunner.query(conn, sql_query, new MapHandler());
       System.out.println(query);
//        //
//        //删除
//        String sql_delete="delete from member where id=20300";
//        //查询
//        queryRunner.update(conn,sql_delete);
//        //查询
//        String sql_query="select * from member where id=20300";
//        Map<String, Object> query = queryRunner.query(conn, sql_query, new MapHandler());
//        System.out.println(query);
    }
    public static void updateDB(String sql){
        Connection conn=createConnection();
        QueryRunner queryRunner=new QueryRunner();
        try {
            queryRunner.update(conn,sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            //关闭数据库链接
            closeConnection(conn);
        }

    }
    public static List<Map<String, Object>> queryAllData(String sql){
        Connection conn=createConnection();
        QueryRunner queryRunner=new QueryRunner();
        List<Map<String, Object>> result=null;
        try {
            result = queryRunner.query(conn, sql, new MapListHandler());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            //关闭数据库链接
            closeConnection(conn);
        }
        return result;
    }

    public static Map<String, Object> querySingleData(String sql){
        Connection conn=createConnection();
        QueryRunner queryRunner=new QueryRunner();
        Map<String, Object> result=null;
        try {
            result = queryRunner.query(conn, sql, new MapHandler());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            //关闭数据库链接
            closeConnection(conn);
        }
        return result;
    }
    public static long queryCountData(String sql){
        Connection conn=createConnection();
        QueryRunner queryRunner=new QueryRunner();
        long result=0;
        try {
            result = queryRunner.query(conn, sql, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            //关闭数据库链接
            closeConnection(conn);
        }
        return result;
    }

    public static void closeConnection(Connection connection){
        //判空
        if(connection!=null) {
            //关闭数据库连接
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
