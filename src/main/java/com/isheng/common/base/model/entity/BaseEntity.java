
package com.isheng.common.base.model.entity;

import java.io.Serializable;

/**
 * 基础Entity
 * @author isheng92
 * @date 2019年11月28日 下午11:38:38
 */
public abstract class BaseEntity implements Serializable {

	/**  */
	private static final long serialVersionUID = 8340637349545544695L;

	/**
	 * 主键ID
	 */
	private Serializable id;

	public Serializable getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}
	
}
