/**
 * 文 件 名:  MybatisConfiguration
 * 版    权:  Quanten Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  zping
 * 修改时间:  2019/3/4 0004
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.chown.pine.config;

import com.chown.pine.cons.DataSourceType;
import com.google.common.collect.Maps;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * <数据库配置>
 *
 * @author zping
 * @version 2019/3/4 0004
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */

@Configuration
@PropertySource("classpath:mybatis.properties")
@ConditionalOnClass({ EnableTransactionManagement.class })
@MapperScan (basePackages = { "com.chown.pine.db" })
public class MybatisConfiguration
{
	@Resource (name = "primaryDataSource")
	private DataSource writeDataSource;

	@Resource (name = "readDataSources")
    private List<DataSource> readDataSources;

	/**
	 * mybatis配置文件路径
	 */
	public static final String MYBATIS_PATH = "classpath:/mybatis-config.xml";

	/**
	 * mybatis映射文件路径
	 */
	public static final String MAPPER_PATH = "classpath*:com/chown/pine/db/mapper/*.xml";

	/**
	 *
	 * mybatis映射包
	 */
	public static final String MAPPER_PACKAGE = "com.chown.pine.db.entity";

	@Bean
	@ConditionalOnMissingBean
	public SqlSessionFactory sqlSessionFactory () throws Exception
	{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean ();
		sqlSessionFactoryBean.setDataSource (roundRobinDataSourceProxy ());
		sqlSessionFactoryBean.setTypeAliasesPackage (MAPPER_PACKAGE);
		//sqlSessionFactoryBean.getObject ().getConfiguration ().setMapUnderscoreToCamelCase (true);

		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		try
		{
			/*扫描mybatis配置文件*/
			//sqlSessionFactoryBean.setConfigLocation (resolver.getResource (MYBATIS_PATH));
			sqlSessionFactoryBean.setMapperLocations (resolver.getResources (MAPPER_PATH));
			return sqlSessionFactoryBean.getObject ();
		}
		catch (Exception e)
		{
			System.out.println ("init database error." + e.getMessage ());
		}

		return sqlSessionFactoryBean.getObject ();
	}

	/**
	 * 有多少个数据源就要配置多少个bean
	 *
	 * @return
	 */
	@Bean
	public AbstractRoutingDataSource roundRobinDataSourceProxy ()
	{
		int size = readDataSources.size();
		MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource (size);
		Map<Object, Object> targetDataSources = Maps.newHashMap ();
		// write存入的key是类型
		targetDataSources.put (DataSourceType.write.getType (), writeDataSource);

		//targetDataSources.put (DataSourceType.read.getType (), readDataSources);
		//多个读数据库时　read存入的key是数字
		for (int i = 0; readDataSources != null && i < readDataSources.size(); i++)
		{
			targetDataSources.put (i, readDataSources.get (i));
		}
		proxy.setDefaultTargetDataSource (writeDataSource);
		proxy.setTargetDataSources (targetDataSources);
		return proxy;
	}
}
