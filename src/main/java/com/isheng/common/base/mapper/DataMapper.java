package com.isheng.common.base.mapper;

import java.io.Serializable;
import java.util.List;

public interface DataMapper <DTO, Entity> extends BaseMapper{
	
	/**
	 * 插入
	 * @param entity
	 * @return
	 */
	Integer insert(Entity entity);
	
	/**
	 * 根据ID删除
	 * @param id
	 * @return
	 */
	Integer deleteById(Serializable id);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteByIds(List<Serializable> ids);
	
	/**
	 * 更新
	 * @param entity
	 * @return
	 */
	Integer update(Entity entity);
	
	/**
	 * 查询数量
	 * @param dto
	 * @return
	 */
	Long queryCount(DTO dto);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	Entity queryById(Serializable id);
	
	/**
	 * 查询所有满足条件的数据
	 * @param dto
	 * @return
	 */
	List<Entity> queryList(DTO dto);
}
