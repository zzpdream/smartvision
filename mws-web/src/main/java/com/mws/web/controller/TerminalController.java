package com.mws.web.controller;

import com.mws.model.Terminal;
import com.mws.web.common.vo.Pagination;
import com.mws.web.service.TerminalService;
import com.mws.web.web.JsonMap;
import com.mws.web.web.Servlets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * 代表终端控制器
 * <p/>
 * <p/>
 * Created by ranfi on 3/6/16.
 */
@Controller
@RequestMapping("/terminal")
public class TerminalController {

    @Resource
    private TerminalService terminalService;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Pagination pagination, Model model) {
        model.addAllAttributes(terminalService.findTerminalConnets());
        return "terminal/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(Pagination pagination, HttpServletRequest request, Model model) {
        pagination.setPageSize(30);
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

        Page<Terminal> page = terminalService.list(pagination, searchParams, new Sort(Sort.Direction.ASC, "seatId"));
        model.addAttribute("page", page);
        return "terminal/nested";
    }

    /**
     * 创建终端机器
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request, Pagination pagination) {
        model.addAttribute("pagination", pagination);
        return "terminal/edit";
    }

    /**
     * 编辑APK
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request, Pagination pagination) {
        Terminal terminal = terminalService.findOne(id);
        model.addAttribute("terminal", terminal);
        model.addAttribute("pagination", pagination);
        return "terminal/edit";
    }


    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JsonMap save(@Valid Terminal terminal, BindingResult result, HttpServletRequest request) {
        terminalService.saveTerminal(terminal);
        return new JsonMap(0, "添加成功");
    }

    /**
     * 删除APK
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonMap delete(@RequestParam("id[]") Integer[] ids) {
        terminalService.deleteTerminal(ids);
        return new JsonMap(0, "删除成功");
    }

}
