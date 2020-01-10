package com.isheng.common.base.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isheng.common.base.convertor.DataConvertor;
import com.isheng.common.base.mapper.DataMapper;
import com.isheng.common.base.model.dto.DataDTO;
import com.isheng.common.base.model.entity.DataEntity;
import com.isheng.common.constant.Constant;

/**
 * 基础操作service抽象类
 * @author isheng92
 * @param <DTO>
 * @param <Entity>
 * @date 2019年12月29日 下午4:51:20
 */
public abstract class AbstractDataService<DTO extends DataDTO, Entity extends DataEntity> implements DataService<DTO> {
	
	protected abstract DataMapper<DTO, Entity> getMapper();
	
	protected abstract DataConvertor<DTO, Entity> getConvertor();
	
	@Override
	public Integer save(DTO dto) {
		dto.preInsert();
		Entity entity = getConvertor().dtoToEntity(dto);
		Integer count = getMapper().insert(entity);
		return null != count ? count : 0;
	}

	@Override
	public Integer deleteById(Serializable id) {
		Integer count = getMapper().deleteById(id);
		return null != count ? count : 0;
	}

	@Override
	public Integer deleteByIds(List<Serializable> ids) {
		Integer count = 0;
		if (null != ids && ids.size() > 0) {
			List<Serializable> idList = ids.stream().filter(id -> null != id).distinct().collect(Collectors.toList());
			if (null != idList && idList.size() > 0) {
				count = getMapper().deleteByIds(ids);
			}
		}
		return (null != count) ? count : 0;
	}

	@Override
	public Integer update(DTO dto) {
		dto.preUpdate();
		Entity entity = getConvertor().dtoToEntity(dto);
		Integer count = getMapper().update(entity);
		return null != count ? count : 0;
	}

	@Override
	public Long getCount(DTO dto) {
		Long count =  getMapper().queryCount(dto);
		return null != count ? count : 0;
	}

	@Override
	public DTO getById(Serializable id) {
		Entity entity = getMapper().queryById(id);
		return getConvertor().entityToDTO(entity);
	}

	@Override
	public List<DTO> getList(DTO dto) {
		List<Entity> entitys = getMapper().queryList(dto);
		List<DTO> dtos = getConvertor().entitysToDTOS(entitys); 
		return (null != dtos) ? dtos : Collections.emptyList();
	}

	@Override
	public PageInfo<DTO> getPaging(Integer pageNum, Integer pageSize, DTO dto) {
		if (null == pageNum || pageNum <= 0) {
			pageNum = Constant.Default.PAGE_NUM;
		}
		if (null == pageSize || pageSize <= 0) {
			pageSize = Constant.Default.PAGE_SIZE;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<DTO> dtos = getList(dto);
		return new PageInfo<DTO>(dtos);
	}
	
}
