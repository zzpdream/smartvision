package com.mws.web.service;

import com.mws.model.BaseDao;
import com.mws.model.sys.Menu;
import com.mws.model.sys.Role;
import com.mws.model.sys.User;
import com.mws.service.CommonService;
import com.mws.web.common.bo.Constant;
import com.mws.web.common.bo.MenuBo;
import com.mws.web.common.exception.WebRequestException;
import com.mws.web.repository.MenuRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Service - 菜单
 */
@Service
public class MenuService extends BaseService<Menu, Integer> {

	private final Logger logger = LoggerFactory.getLogger(MenuService.class);

	@PersistenceContext
	private EntityManager em;

	private MenuRepository menuDao;

	@Autowired
	private AccountService accountService;

	@Resource
	private CommonService commonService;

	@Resource
	@Override
	public void setBaseDao(BaseDao<Menu, Integer> baseDao) {
		this.baseDao = baseDao;
		menuDao = (MenuRepository) baseDao;
	}

	private final Lock lock = new ReentrantLock();// 锁


	/**
	 * 查询菜单信息
	 *
	 * @param id 菜单编号
	 * @return 菜单信息
	 */
	@Transactional(readOnly = true)
	public Menu findMenu(Integer id) {
		if (id == null) {
			return null;
		}
		return menuDao.findOne(id);
	}

	/**
	 * 获取最顶层的根菜单列表
	 *
	 * @param status 菜单状态
	 * @return 根菜单列表
	 */
	@Transactional(readOnly = true)
	public List<Menu> findRootMenuList(Constant.Status status) {
		Sort sort = new Sort(Direction.ASC, "sort");
		return menuDao.findByParentMenuAndStatus(null, status.value, sort);
	}

	/**
	 * 根据用户编号和菜单状态查询菜单列表
	 *
	 * @param uid 用户编号
	 * @param status 菜单状态
	 * @return 根菜单列表
	 */
	@Transactional(readOnly = true)
	public List<Menu> findMenuList(Integer uid, Constant.Status status) {
		List<Menu> menuList = new ArrayList<Menu>();

		// 查询用户信息
		User user  = accountService.getUser(uid);

		if (CollectionUtils.isNotEmpty(user.getRoleList())) {
			for (Role role : user.getRoleList()) {
				if (CollectionUtils.isNotEmpty(role.getMenus())) {
					for (Menu menu : role.getMenus()) {
						if (status.value == menu.getStatus() && !menuList.contains(menu)) {
							menuList.add(menu);
						}
					}
 				}

			}
		}

		return menuList;
	}

	/**
	 * 获取最顶层的根菜单列表
	 *
	 * @return 根菜单列表
	 */
	@Transactional(readOnly = true)
	public List<Menu> findRootMenuList() {
		Sort sort = new Sort(Direction.ASC, "sort");
		return menuDao.findByParentMenu(null, sort);
	}

	/**
	 * 保存菜单
	 *
	 * @param menu 菜单
	 * @return 菜单
	 */
	@Override
	@Transactional
	public Menu save(Menu menu) {
		// 从数据库总查询菜单信息
		Menu entity = findMenu(menu.getId());

		if (menu.getParentMenu().getId() == null) {
			menu.setParentMenu(null);
		}

		// 检查菜单地址是否／开头，若是/开头，删除
		if (StringUtils.isNotBlank(menu.getUrl())) {
			if (menu.getUrl().startsWith("/")) {
				menu.setUrl(menu.getUrl().substring(1));
			}
		}

		if (entity == null) {
			// 新建菜单
			menu.setCreateTime(commonService.getCurrentTime());
			entity = menu;
		} else {
			// 保存菜单
			entity.setParentMenu(menu.getParentMenu());
			entity.setName(menu.getName());
			entity.setUrl(menu.getUrl());
			entity.setIcon(menu.getIcon());
			entity.setSort(menu.getSort());
			entity.setStatus(menu.getStatus());
			entity.setRemark(menu.getRemark());
		}

		return menuDao.save(entity);
	}

	/**
	 * 删除菜单
	 *
	 * @param ids 编号数组
	 */
	@Override
	@Transactional
	public void delete(Integer[] ids) {
		Menu menu;
		for (Integer id : ids) {
			menu = findMenu(id);
			if (menu == null) {
				continue;
			}

			if (CollectionUtils.isNotEmpty(menu.getChildMenu())) {
				throw new WebRequestException("《" + menu.getName() + "》菜单存在子菜单，请先删除子菜单");
			}

			if (CollectionUtils.isNotEmpty(menu.getRoles())) {
				throw new WebRequestException("《" + menu.getName() + "》菜单已经被角色关联，请先取消关联");
			}

			menuDao.delete(menu);
		}
	}






	/**
	 * 根据用户权限获取菜单列表
	 * 
	 * @return
	 */
	@Deprecated
	public List<Menu> getMenuList() {
		List<Menu> menuList = null;
//		User user = accountService.getCurrentUser();
//		boolean isSupervisor = accountService.isSupervisor(user.getUid());
//		int status = Constant.Status.Enable.value;
//		// 如果是超级管理员
//		if (isSupervisor) {
//			Sort sort = new Sort(Direction.ASC, "sort");
//			menuList = menuDao.findByParentMenuAndStatus(null, status, sort);
//		} else {
//			String roleIds = user.getRoleIds();
//			if (StringUtils.isEmpty(roleIds)) {
//				roleIds = "''";
//			}
//			menuList = getMenuList(Constant.ROOT_MENU_ID, roleIds);
//			Set<Menu> userMenus = user.getMenuList();
//			for (Menu menu : menuList) {
//				Iterator<Menu> iterator = menu.getChildMenu().iterator();
//				while (iterator.hasNext()) {
//					Menu subMenu = iterator.next();
//					boolean isContains = false;
//					for (Menu userMenu : userMenus) {
//						if (userMenu.getId() == subMenu.getId()) {
//							isContains = true;
//						}
//
//					}
//					if (!isContains) {
//						iterator.remove();
//					}
//				}
//			}
//		}
		return menuList;
	}

	public List<Menu> getMenuList(int parentMenuId, String roleIds) {
		String jql = "select m from Menu m left join fetch m.roles r where m.pid=:pid and r.id in (:roleIds) and m.status=:status";
		TypedQuery<Menu> query = em.createQuery(jql, Menu.class);
		query.setParameter("pid", parentMenuId);

		query.setParameter("status", Constant.Status.Enable.value);
		for (String id : org.apache.commons.lang3.StringUtils.split(roleIds, ",")) {
			query.setParameter("roleIds", Integer.parseInt(id));
		}
		// List<Menu> menuList = menuDao.findMenuListByUserRoles(parentMenuId,
		// roleIds, Constant.Status.Enable.value);
		return query.getResultList();
	}



	/**
	 * 获取子菜单列表
	 * 
	 * @param pid
	 * @return
	 */
	public List<Menu> findMenuListByPid(int pid) {
		Sort sort = new Sort(Direction.ASC, "parentMenu.id", "sort");
		return menuDao.findByParentMenuAndStatus(new Menu(pid), Constant.Status.Enable.value, sort);
	}

	public String getMenuHtml() {
		List<Menu> menus = getMenuList();
		return MenuBo.outputHtml(menus);
	}



}
