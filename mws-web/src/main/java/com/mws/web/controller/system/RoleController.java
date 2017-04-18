package com.mws.web.controller.system;

import com.mws.model.sys.Role;
import com.mws.web.common.controller.WebController;
import com.mws.web.common.vo.Pagination;
import com.mws.web.context.ParameterCache;
import com.mws.web.service.MenuService;
import com.mws.web.service.RoleService;
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
 * Controller - 角色
 */
@Controller
public class RoleController extends WebController {

	@Resource
	private RoleService roleService;

    @Resource
    private MenuService menuService;

    /**
     * 角色列表页
     */
//    @RequiresPermissions("role:view")
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	public String list(Pagination pagination, Model model) {
        model.addAttribute("pagination", pagination);
        model.addAttribute("projectName", ParameterCache.getSystemProp("project.name"));
		model.addAttribute("projectFoot", ParameterCache.getSystemProp("project.foot"));
		
		return "role/list";
	}

    /**
     * 角色列表页
     */
//    @RequiresPermissions("role:view")
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public String list(Pagination pagination, HttpServletRequest request, Model model) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        model.addAttribute("page", roleService.list(pagination, searchParams));
        model.addAttribute("projectName", ParameterCache.getSystemProp("project.name"));
		model.addAttribute("projectFoot", ParameterCache.getSystemProp("project.foot"));
		
        return "role/nested";
    }

    /**
     * 添加角色
     */
//    @RequiresPermissions("role:update")
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public String add(Pagination pagination, Model model) {
        model.addAttribute("pagination", pagination);
        model.addAttribute("rootMenus", menuService.findRootMenuList());
        model.addAttribute("projectName", ParameterCache.getSystemProp("project.name"));
		model.addAttribute("projectFoot", ParameterCache.getSystemProp("project.foot"));
		
        return "role/edit";
	}

    /**
     * 编辑角色
     */
//    @RequiresPermissions("role:update")
	@RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Integer id, Pagination pagination, Model model) {
        model.addAttribute("pagination", pagination);
        model.addAttribute("rootMenus", menuService.findRootMenuList());
        model.addAttribute("role", roleService.findRole(id));
		
        model.addAttribute("projectName", ParameterCache.getSystemProp("project.name"));
		model.addAttribute("projectFoot", ParameterCache.getSystemProp("project.foot"));
		return "role/edit";
	}

    /**
     * 保存角色
     */
    @ResponseBody
//    @RequiresPermissions("role:update")
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public JsonMap save(@Valid Role role, BindingResult result) {
        JsonMap ret;
        if (result.hasErrors()) {
            ret = parseErrorResult(result, "保存失败");
        } else {
            roleService.save(role);
            ret = new JsonMap(0, "保存成功");
        }
        return ret;
    }

    /**
     * 删除角色
     */
    @ResponseBody
//    @RequiresPermissions("role:delete")
	@RequestMapping(value = "/role/delete", method = RequestMethod.POST)
	public JsonMap del(@RequestParam("id[]") Integer[] ids) {
        roleService.delete(ids);
		return new JsonMap(0, "删除成功");
	}

}
