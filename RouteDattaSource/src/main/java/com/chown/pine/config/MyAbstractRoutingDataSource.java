/**
 * 文 件 名:  MyAbstractRoutingDataSource
 * 版    权:  Quanten Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zping
 * 修改时间:  2019/3/1 0001
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.chown.pine.config;

import com.chown.pine.cons.DataSourceType;
import com.chown.pine.context.DataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <多数据源切换>
 *
 * @author zping
 * @version 2019/3/1 0001
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource
{

	/**
	 * 数据源
	 */
	private final int dataSourceNumber;

	public MyAbstractRoutingDataSource(int dataSourceNumber)
	{
		this.dataSourceNumber = dataSourceNumber;
	}

    private AtomicInteger count = new AtomicInteger(0);

    /**
     *通过determineCurrentLookupKey()方法获取一个key，
     *通过key从targetDataSources中(map集合)获取数据源DataSource对象
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHolder.getJdbcType();
        if(StringUtils.isEmpty(typeKey) || typeKey.equals(DataSourceType.write.getType())) {
            //targetDataSources map存的是类型
            return DataSourceType.write.getType();
        }
        // 读 简单负载均衡　targetDataSources map存的是数字
        int number = count.getAndAdd(1);
        int lookupKey = number % dataSourceNumber;
        return new Integer(lookupKey);
    }
}
