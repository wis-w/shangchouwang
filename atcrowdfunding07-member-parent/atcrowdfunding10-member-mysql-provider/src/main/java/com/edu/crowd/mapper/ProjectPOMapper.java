package com.edu.crowd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edu.crowd.entity.po.ProjectPO;
import com.edu.crowd.entity.po.ProjectPOExample;
import com.edu.crowd.entity.vo.DetailProjectVO;
import com.edu.crowd.entity.vo.ProtalTypeVO;

public interface ProjectPOMapper {
    int countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

	void insertRelationship(@Param("typeIdList") List<Integer> typeIdList, @Param("projectId") Integer projectId);

	void insertTagRelationship(@Param("tagIdList")List<Integer> tagIdList, @Param("projectId")Integer projectId);
	
	List<ProtalTypeVO> selectProtalTypeList();
	
	DetailProjectVO selectDetailProjectVO(Integer id);
}