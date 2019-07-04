package test;

import com.dangdang.ddframe.rdb.sharding.jdbc.MasterSlaveDataSource;
import com.dangdang.ddframe.rdb.sharding.spring.datasource.SpringShardingDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/sharding-jdbc.xml")
public class ShareJdbcTest extends AbstractJUnit4SpringContextTests {

    /**
     * 多个jar情况下，包含相应路径和文件名的xsd文件
     * @throws SQLException
     */
    @Test
    public void doEqualSharding() throws SQLException {
        SpringShardingDataSource wholeDataSource = (SpringShardingDataSource)applicationContext.getBean("wholeDataSource");
    }
}
