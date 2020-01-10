/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 *
 * @description：
 *
 * @fileName：CurdService1.java
 * @author：isheng92 
 * @dateTime：2019年7月18日 下午9:13:00
 */
package com.isheng.common.base.service;

import java.io.Serializable;
import java.util.List;
import com.github.pagehelper.PageInfo;
import com.isheng.common.base.model.dto.DataDTO;

/**
 * 数据操作service接口
 * @author isheng92
 * @param <Mapper>
 * @param <DTO>
 * @date 2019年11月28日 下午11:54:29
 */
public interface DataService<DTO extends DataDTO> extends BaseService{

	/**
	 * 新增保存，返回保存成功后的主键
	 * @param entity
	 * @return
	 */
	Integer save(DTO entity);
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	Integer deleteById(Serializable id);
	
	/**
	 * 根据主键批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteByIds(List<Serializable> ids);
	
	/**
	 * 更新
	 * @param entity
	 * @return
	 */
	Integer update(DTO dto);
	
	/**
	 * 查询满足条件的记录数
	 * @param dto
	 * @return
	 */
	Long getCount(DTO dto);
	
	/**
	 * 根据主键获取
	 * @param id
	 * @return
	 */
	DTO getById(Serializable id);
	
	/**
	 * 根据条件查询
	 * @param query
	 * @return
	 */
	List<DTO> getList(DTO query);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param query
	 * @return
	 */
	PageInfo<DTO> getPaging(Integer pageNum, Integer pageSize, DTO dto);
	
}
