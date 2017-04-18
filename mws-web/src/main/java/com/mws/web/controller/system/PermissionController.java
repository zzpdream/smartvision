package com.mws.web.controller.system;

import com.mws.model.sys.Permission;
import com.mws.web.common.controller.WebController;
import com.mws.web.common.vo.Pagination;
import com.mws.web.context.ParameterCache;
import com.mws.web.service.MenuService;
import com.mws.web.service.PermissionService;
import com.mws.web.web.JsonMap;
import com.mws.web.web.Servlets;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * Controller - 权限点
 *
 * @author xingkong1221
 * @date 2015-11-06
 */
@Controller
public class PermissionController extends WebController {

    @Resource
    private PermissionService permissionService;

    @Resource
    private MenuService menuService;

    /**
     * 权限点列表页
     */
    @RequestMapping(value = "/menu/permissions", method = RequestMethod.GET)
    public String list(@RequestParam(value = "menu_id", required = false) Integer menuId,
                       @RequestParam(value = "menu_page", required = false) Integer menuPage,
                       @RequestParam(value = "pid", required = false) Integer pid,
                       Pagination pagination, Model model) {
        model.addAttribute("menuId", menuId);
        model.addAttribute("pagination", pagination);
        model.addAttribute("pid", pid);
        model.addAttribute("menuPage", menuPage);
        model.addAttribute("menu", menuService.get(menuId));
        model.addAttribute("projectName", ParameterCache.getSystemProp("project.name"));
		model.addAttribute("projectFoot", ParameterCache.getSystemProp("project.foot"));
		
        return "menu/permission/list";
    }

    /**
     * 权限点列表页
     */
    @RequestMapping(value = "/menu/permissions", method = RequestMethod.POST)
    public String list(Pagination pagination, HttpServletRequest request, Model model) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        model.addAttribute("page", permissionService.list(pagination, searchParams));
        model.addAttribute("projectName", ParameterCache.getSystemProp("project.name"));
		model.addAttribute("projectFoot", ParameterCache.getSystemProp("project.foot"));
		
        return "menu/permission/nested";
    }

    /**
     * 创建权限点
     */
    @RequestMapping(value = "/menu/permission", method = RequestMethod.GET)
    public String add(Model model) {
    	model.addAttribute("projectName", ParameterCache.getSystemProp("project.name"));
		model.addAttribute("projectFoot", ParameterCache.getSystemProp("project.foot"));
		
        return "menu/permission/edit";
    }

    /**
     * 编辑权限点
     */
    @RequestMapping(value = "/menu/permission/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("permission", permissionService.get(id));
        model.addAttribute("projectName", ParameterCache.getSystemProp("project.name"));
		model.addAttribute("projectFoot", ParameterCache.getSystemProp("project.foot"));
		
        return "menu/permission/edit";
    }

    /**
     * 保存权限点
     */
    @ResponseBody
    @RequestMapping(value = "/menu/permission", method = RequestMethod.POST)
    public JsonMap save(@Valid Permission permission, BindingResult result) {
        JsonMap ret;
        if (result.hasErrors()) {
            ret = parseErrorResult(result, "保存失败");
        } else {
            permissionService.save(permission);
            ret = new JsonMap(0, "保存成功");
        }
        return ret;
    }

    /**
     * 删除权限点
     */
    @ResponseBody
    @RequestMapping(value = "/menu/permission/delete", method = RequestMethod.POST)
    public JsonMap delete(@RequestParam("id[]") Integer ids) {
        permissionService.delete(ids);
        return new JsonMap(0, "删除成功");
    }

}
