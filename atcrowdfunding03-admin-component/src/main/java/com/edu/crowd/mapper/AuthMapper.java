package com.edu.crowd.mapper;

import com.edu.crowd.entity.Auth;
import com.edu.crowd.entity.AuthExample;
import com.edu.crowd.entity.Role;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

	List<Integer> selectAssignedAuthById(Integer roleId);

	void deleteOldRelationShip(Integer roleId);

	void insertNewRelationShip(@Param("roleId") Integer roleId, @Param("authList") List<Integer> authList);

	List<String> selectRoleByAdminId(Integer adminId);
}