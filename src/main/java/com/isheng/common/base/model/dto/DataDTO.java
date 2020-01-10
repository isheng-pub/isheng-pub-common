package com.isheng.common.base.model.dto;

import java.io.Serializable;
import java.util.Date;

import com.isheng.common.idgen.IdGen;

/**
 * 数据传输对象
 * @author isheng92
 * @date 2019年12月3日 下午9:35:44
 */
public abstract class DataDTO extends BaseDTO {

	private static final long serialVersionUID = -453831797073248213L;
	
	/**
	 * 页码
	 */
	private Integer pageNum;
	
	/**
	 * 每页记录数
	 */
	private Integer pageSize;
	
	/**
	 * 主键ID
	 */
	private Serializable id;
	
	/**
	 * 创建人
	 */
	private String createUser;
	
	/**
	 * 更新人
	 */
	private String updateUser;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 关键字
	 */
	private String keyword;
	
	protected boolean newRecord = false;
	
	public void preInsert() {
		if (isNewRecord()) {
			setId(IdGen.nextId());
		}
		this.createTime = new Date();
		this.updateTime = this.createTime;
	};
	
	public void preUpdate() {
		this.updateTime = new Date();
	};

	public Serializable getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public boolean isNewRecord() {
		return newRecord || getId() == null;
	}

	public void setNewRecord(boolean newRecord) {
		this.newRecord = newRecord;
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

	@Override
	public String toString() {
		return "DataDTO [pageNum=" + pageNum + ", pageSize=" + pageSize + ", id=" + id + ", createUser=" + createUser
				+ ", updateUser=" + updateUser + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", keyword=" + keyword + ", newRecord=" + newRecord + "]";
	}
	
}
