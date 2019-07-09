package com.demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.dangdang.ddframe.rdb.sharding.jdbc.MasterSlaveDataSource;
import com.dangdang.ddframe.rdb.sharding.jdbc.ShardingConnection;
import com.dangdang.ddframe.rdb.sharding.spring.datasource.SpringShardingDataSource;
import com.google.common.collect.Range;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 创建时间:2017年2月23日
 * @author jiangjianbin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/spring-test.xml")
public class DbAlgorithm extends AbstractJUnit4SpringContextTests{


    /**
     * 注释掉rbb_0 bean异常栈可以看出调用了DruidDataSource对象getConnection
     * @throws SQLException
     */
    @Test
    public void doEqualSharding() throws SQLException {
        SpringShardingDataSource wholeDataSource = (SpringShardingDataSource)applicationContext.getBean("wholeDataSource");
        ShardingConnection connection = wholeDataSource.getConnection();
        Statement statement = connection.createStatement();
//        ResultSet executeQuery = statement.executeQuery("SELECT * FROM  USER ");
        statement.execute("SELECT * FROM  user u LEFT JOIN user_test ut on ut.id=u.id  WHERE name LIKE '%g%'  limit 0,2");
        ResultSet rs = statement.getResultSet();
//        rs.last();
//        rs.insertRow();
//        rs.cancelRowUpdates();
        // 展开结果集数据库
        while(rs.next()){
            // 通过字段检索
            int id  = rs.getInt("id");
            String name = rs.getString("name");
            String url = rs.getString("age");

            // 输出数据
            System.out.print("ID: " + id);
            System.out.print(", anme: " + name);
            System.out.print(", age: " + url);
            System.out.print("\n");
        }

    }

    @Test
    public void testMasterSlaveDataSource() throws SQLException {
        MasterSlaveDataSource rbb_0 = (MasterSlaveDataSource) applicationContext.getBean("rbb_0");
        Connection connection = rbb_0.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM  user ");
        ResultSet rs = statement.getResultSet();
        // 展开结果集数据库
        while(rs.next()){
            // 通过字段检索
            int id  = rs.getInt("id");
            String name = rs.getString("name");
            String url = rs.getString("age");

            // 输出数据
            System.out.print("ID: " + id);
            System.out.print(", anme: " + name);
            System.out.print(", age: " + url);
            System.out.print("\n");
        }
    }

    /**
     * 没有逻辑分表，表名为user
     * @throws SQLException
     */
    @Test
    public void testDruidDataSource() throws SQLException {
        DruidDataSource master0 = (DruidDataSource) applicationContext.getBean("master0");
        Connection connection = master0.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM  user ");
        ResultSet rs = statement.getResultSet();
        // 展开结果集数据库
        while(rs.next()){
            // 通过字段检索
            int id  = rs.getInt("id");
            String name = rs.getString("name");
            String url = rs.getString("age");

            // 输出数据
            System.out.print("ID: " + id);
            System.out.print(", anme: " + name);
            System.out.print(", age: " + url);
            System.out.print("\n");
        }
    }
}
