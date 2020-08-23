package com.edu.crowd.mapper;

import com.edu.crowd.entity.Admin;
import com.edu.crowd.entity.AdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * 
 * @author a1553
 *
 */
public interface AdminMapper {
    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
    
    /**
     * 根据关键字查询
     * @param keyword
     * @return
     */
    List<Admin> selectAdminByKeyWord(String keyword);

    /**
     * 根据id查询用户密码
     * @param id
     * @return
     */
	String selectAdminById(Integer id);

	/**
	 * 增加角色分配关系
	 * @param adminId
	 * @param roleIdList
	 */
	void saveRelationShip(@Param("adminId") Integer adminId, @Param("roleIdList") List<Integer> roleIdList);

	/**
	 * 删除原有的角色分配
	 * @param adminId
	 */
	void deleteRoleById(Integer adminId);
}