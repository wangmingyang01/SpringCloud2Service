package com.chown.pine.cons;

/**
 * <数据源类型枚举定义>
 *
 * @author zping
 * @version 2019/3/1 0001
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public enum DataSourceType
{
	read ("read", "从数据源"),
	write ("write", "主数据源");


	private String type;
	private String name;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	DataSourceType(String type, String name)
	{
		this.type = type;
		this.name = name;
	}


}
