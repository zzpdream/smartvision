package com.mws.web.interceptor;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mws.model.sys.Menu;
import com.mws.web.common.bo.Constant;
import com.mws.web.service.AccountService;
import com.mws.web.service.MenuService;
import com.mws.web.web.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Interceptor - 菜单
 *
 * @author xingkong1221
 * @date 2015-10-08
 */
public class MenuInterceptor implements HandlerInterceptor {

    private Logger logger = Logger.getLogger(MenuInterceptor.class);

    @Resource
    private MenuService menuService;

    @Resource
    private AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        // 剔除异步请求
        if ("GET".equalsIgnoreCase(request.getMethod()) && !CommonUtils.isAjaxRequest(request) && modelAndView != null) {

            // 查询所有菜单 和 当前访问地址
            String  currentAccount = accountService.getCurrentAccount();
            List<Menu> menuList;
            List<Menu> rootMenuList;
            List<Menu> childMenuList;
            //如果是超级管理员时
            if (accountService.isSupervisor(currentAccount)) {
                menuList = getAdminMenus();
            } else {
                menuList = menuService.findMenuList(accountService.getCurrentUser().getId(), Constant.Status.Enable);
            }
            rootMenuList = getRootMenu(menuList);
            childMenuList = getChildMenu(menuList);

            String currentURI = request.getRequestURI();

            // 一级菜单编号 和 子菜单编号
            Integer activeRootMenuId = 0;
            Integer activeMenuId = 0;

            for (Menu root : rootMenuList) {
                for (Menu menu : root.getChildMenu()) {
                    // 判断菜单地址是否包含当前访问地址
                    if (StringUtils.isNotBlank(menu.getUrl())
                            && currentURI.contains(menu.getUrl())) {
                        activeMenuId = menu.getId();
                        activeRootMenuId = root.getId();
                    }
                }
            }
            modelAndView.addObject("menus", rootMenuList);
            modelAndView.addObject("childMenus", childMenuList);
            modelAndView.addObject("activeRootMenuId", activeRootMenuId);
            modelAndView.addObject("activeMenuId", activeMenuId);

            if (logger.isDebugEnabled()) {
                logger.debug("activeRootMenuId = " + activeRootMenuId);
                logger.debug("activeMenuId = " + activeMenuId);
            }
        }
    }

    private List<Menu> getAdminMenus() {
        List<Menu> rootMenuList = Lists.newArrayList();
        Menu menu = new Menu(1, "系统管理", "", "fa fa-cogs", null);
        Set<Menu> childMenuList = Sets.newHashSet();
        childMenuList.add(new Menu(2, "用户管理", "users", "fa fa-cogs", menu));
        childMenuList.add(new Menu(3, "菜单管理", "menus", "", menu));
        childMenuList.add(new Menu(4, "角色管理", "roles", "fs", menu));
        menu.setChildMenu(childMenuList);
        rootMenuList.add(menu);
        rootMenuList.addAll(childMenuList);
        return rootMenuList;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {

    }

    /**
     * 遍历获取所有父菜单
     *
     * @param menuList 菜单
     * @return 所有父菜单
     */
    private List<Menu> getRootMenu(List<Menu> menuList) {
        List<Menu> rootMenuList = new ArrayList<Menu>();

        if (CollectionUtils.isNotEmpty(menuList)) {
            for (Menu menu : menuList) {
                // 判断有无父菜单
                if (menu.getParentMenu() == null) {
                    rootMenuList.add(menu);
                }
            }
        }

        return rootMenuList;
    }

    /**
     * 遍历获取所有子菜单
     *
     * @param menuList 菜单
     * @return 所有父菜单
     */
    private List<Menu> getChildMenu(List<Menu> menuList) {
        List<Menu> childMenuList = new ArrayList<Menu>();

        if (CollectionUtils.isNotEmpty(menuList)) {
            for (Menu menu : menuList) {
                // 判断有无父菜单
                if (menu.getParentMenu() != null) {
                    childMenuList.add(menu);
                }
            }
        }

        return childMenuList;
    }


}
