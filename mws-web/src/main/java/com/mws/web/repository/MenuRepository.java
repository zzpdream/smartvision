package com.mws.web.repository;

import java.util.List;

import com.mws.model.BaseDao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mws.model.sys.Menu;

/**
 * Dao - 菜单
 */
public interface MenuRepository extends BaseDao<Menu, Integer> {

	public List<Menu> findByStatus(Integer status);

	/**
	 * 查询菜单列表
	 *
	 * @param parentMenu 父菜单
	 * @param status 状态
	 * @param sort 排序
	 * @return 菜单列表
	 */
	List<Menu> findByParentMenuAndStatus(Menu parentMenu, Integer status, Sort sort);

	/**
	 * 查询菜单列表
	 *
	 * @param parentMenu 父菜单
	 * @param sort 排序
	 * @return 菜单列表
	 */
	@Query()
	List<Menu> findByParentMenu(Menu parentMenu, Sort sort);

	@Query(value = "select m.* from sys_menu m,sys_role r,sys_role_menu rm where m.id=rm.menu_id and r.id=rm.role_id and m.pid=:pid and r.id in (:roleIds) and m.status=:status", nativeQuery = true)
	public List<Menu> findByUserRoles(@Param("pid") int pid, @Param("roleIds") String roleIds,
			@Param("status") int status);

	@Query(value = "select m from Menu m left join fetch m.roles r where m.parentMenu.id=:pid and r.id in (:roleIds) and m.status=:status")
	public List<Menu> findMenuListByUserRoles(@Param("pid") int pid, @Param("roleIds") String roleIds,
			@Param("status") int status);

	@Modifying
	@Query(value = "update Menu set sort = sort + 1 where pid = ?1 and sort >= ?2 ")
	public void updateMenuSort(int pid, int sortNum);

	@Modifying
	@Query(value = "delete Menu where id = ?1 ")
	public void deleteById(int id);

}
