package parser;

import com.dangdang.ddframe.rdb.sharding.jdbc.MasterSlaveDataSource;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.util.Iterator;
import java.util.List;

public class MasterSlaveDataSourceBeanDefinitionParser extends AbstractBeanDefinitionParser {
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(MasterSlaveDataSource.class);
        factory.addConstructorArgValue(this.parseId(element));
        factory.addConstructorArgReference(this.parseMasterDataSourceRef(element));
        factory.addConstructorArgValue(this.parseSlaveDataSources(element, parserContext));
        return factory.getBeanDefinition();
    }

    private String parseId(Element element) {
        return element.getAttribute("id");
    }

    private String parseMasterDataSourceRef(Element element) {
        return element.getAttribute("master-data-source-ref");
    }

    private List<BeanDefinition> parseSlaveDataSources(Element element, ParserContext parserContext) {
        List<String> slaveDataSources = Splitter.on(",").trimResults().splitToList(element.getAttribute("slave-data-sources-ref"));
        List<BeanDefinition> result = new ManagedList(slaveDataSources.size());
        Iterator var5 = slaveDataSources.iterator();

        while(var5.hasNext()) {
            String each = (String)var5.next();
            result.add(parserContext.getRegistry().getBeanDefinition(each));
        }

        return result;
    }
}
