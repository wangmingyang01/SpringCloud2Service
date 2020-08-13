/**
 * 文 件 名:  DataBaseConfiguration
 * 版    权:  Quanten Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zping
 * 修改时间:  2019/3/1 0001
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.chown.pine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * <数据源配置>
 *
 * @author zping
 * @version 2019/3/1 0001
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Configuration
public class DataSourceConfiguration
{

	/**
	 * 主数据源 : 负责数据写操作
	 */
	@Resource(name = "primaryDataSource")
	private DataSource writeDataSource;

	/**
	 * 备数据库 ：负责数据读操作
	 */
	@Resource (name = "read01DataSource")
	private DataSource read01DataSource;
	@Resource (name = "read02DataSource")
	private DataSource read02DataSource;

	@Bean(name = "readDataSources")
	public List<DataSource> readDataSources(){
		List<DataSource> dataSources=new ArrayList<>();
		dataSources.add(read01DataSource);
		dataSources.add(read02DataSource);
		return dataSources;
	}
}
