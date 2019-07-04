package handler;


import com.dangdang.ddframe.rdb.sharding.spring.namespace.parser.ShardingJdbcDataSourceBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import parser.MasterSlaveDataSourceBeanDefinitionParser;

public class ShardingJdbcNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        this.registerBeanDefinitionParser("master-slave-data-source", new MasterSlaveDataSourceBeanDefinitionParser());
        this.registerBeanDefinitionParser("data-source", new ShardingJdbcDataSourceBeanDefinitionParser());
    }
}
