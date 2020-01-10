package com.isheng.common.base.convertor;

import java.util.List;

/**
 * Entity与DTO数据相互转换接口
 * @author isheng92
 * @param <DTO>
 * @param <Entity>
 * @date 2019年12月29日 下午4:09:40
 */
public interface DataConvertor<DTO, Entity> {
	
	/**
	 * Entity转DTO
	 * @param entity
	 * @return
	 */
	public abstract DTO entityToDTO(Entity entity);
	
	/**
	 * Entity集合转DTO集合
	 * @param entitys
	 * @return
	 */
	public abstract List<DTO> entitysToDTOS(List<Entity> entitys);
	
	/**
	 * DTO转Entity
	 * @param dto
	 * @return
	 */
	public abstract Entity dtoToEntity(DTO dto);
	
	/**
	 * DTO集合转Entity集合
	 * @param dtos
	 * @return
	 */
	public abstract List<Entity> dtosToEntitys(List<DTO> dtos);

}
