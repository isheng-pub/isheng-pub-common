package com.isheng.common.base.model.query;

import java.util.Date;

/**
 * 基础查询封装类
 * @author isheng92
 * @date 2019年12月3日 下午9:40:46
 */
public class DataQuery extends BaseQuery{

	private static final long serialVersionUID = -7202043082134167724L;
	
	/**
	 * 页码
	 */
	Integer pageNum;
	
	/**
	 * 每页记录数
	 */
	Integer pageSize;
	
	/**
	 * 创建人
	 */
	private String createUser;
	
	/**
	 * 更新人
	 */
	private String updateUser;
	
	/**
	 * 查询起止时间始
	 */
	private Date startData;
	
	/**
	 * 查询起止时间止
	 */
	private Date endData;
	
	/**
	 * 查询关键字
	 */
	private String keyword;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getStartData() {
		return startData;
	}

	public void setStartData(Date startData) {
		this.startData = startData;
	}

	public Date getEndData() {
		return endData;
	}

	public void setEndData(Date endData) {
		this.endData = endData;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
