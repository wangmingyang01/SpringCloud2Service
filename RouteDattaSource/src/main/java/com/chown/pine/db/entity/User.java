package com.chown.pine.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <广告>
 *
 * @author weiwenfeng
 * @version 2017年7月14日
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Getter
@Setter
public class User implements Serializable
{
	/**
	 * id
	 */
	private long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 代码
	 */
	private String code;


	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", code='" + code + '\'' +
				'}';
	}
}
