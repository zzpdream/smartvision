package com.mws.web.controller;


import com.google.common.collect.Maps;
import com.mws.model.AppUpgrade;
import com.mws.web.common.bo.SystemGlobal;
import com.mws.web.common.controller.WebController;
import com.mws.web.common.vo.Pagination;
import com.mws.web.service.AppUpgradeService;
import com.mws.web.web.JsonMap;
import com.mws.web.web.Servlets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Controller - APK
 */
@Controller
public class AppUpgradeController extends WebController {

    @Autowired
    private AppUpgradeService appUpgradeService;

    /**
     * APK列表
     */
    @RequestMapping(value = "/appUpgrades", method = RequestMethod.GET)
    public String list(Pagination pagination, Model model) {
        model.addAttribute("pagination", pagination);
        return "appUpgrade/list";
    }

    /**
     * APK列表
     */
    @RequestMapping(value = "/appUpgrades", method = RequestMethod.POST)
    public String list(Pagination pagination, HttpServletRequest request, Model model) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        model.addAttribute("page", appUpgradeService.list(pagination, searchParams, new Sort(Sort.Direction.DESC, "id")));
        return "appUpgrade/nested";
    }

    /**
     * 创建APK
     */
    @RequestMapping(value = "/appUpgrade", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request, Pagination pagination) {
        model.addAttribute("pagination", pagination);
        return "appUpgrade/edit";
    }

    /**
     * 编辑APK
     */
    @RequestMapping(value = "/appUpgrade/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request, Pagination pagination) {
        AppUpgrade appUpgrade = appUpgradeService.findAppUpgrade(id);
        model.addAttribute("appUpgrade", appUpgrade);
        model.addAttribute("pagination", pagination);
        String path = appUpgrade.getUrl();
        if (StringUtils.isNotEmpty(path)) {
            int index = path.lastIndexOf("/");
            String fileName = path.substring(index + 1, path.length());
            model.addAttribute("fileName", fileName);
        }
        return "appUpgrade/edit";
    }

    /**
     * 保存APK
     */
    @ResponseBody
    @RequestMapping(value = "/appUpgrade", method = RequestMethod.POST)
    public JsonMap save(@Valid AppUpgrade appUpgrade, BindingResult result, HttpServletRequest request) {
        JsonMap ret = new JsonMap();
        try {
            if (result.hasErrors()) {
                ret = parseErrorResult(result, "保存失败");
                return ret;
            }
            //获取文件后缀
            String newSuffixPath = "/app/" + appUpgrade.getVersionCode() + "/app.apk";
            String newFilePath = SystemGlobal.getResourceUploadFolder() + newSuffixPath;
            String parentFolder = new File(newFilePath).getParent();
            if (!new File(parentFolder).exists()) {
                new File(parentFolder).mkdirs();
            }
            //原文件目录
            String oldFilePath = SystemGlobal.getResourceUploadFolder() + "/app/tmp/app.apk";
            //复制文件
            if (new File(oldFilePath).exists()) {
                FileUtils.copyFile(new File(oldFilePath), new File(newFilePath));
            }
            //保存新路径
            appUpgrade.setUrl(SystemGlobal.getResourceDownloadUrl() + newSuffixPath);
            appUpgradeService.save(appUpgrade);
        } catch (Exception e) {
            ret = parseErrorResult(result, "保存失败");
        }
        return ret;
    }

    /**
     * 删除APK
     */
    @ResponseBody
    @RequestMapping(value = "/appUpgrade/delete", method = RequestMethod.POST)
    public JsonMap delete(@RequestParam("id[]") Integer[] ids) {
        appUpgradeService.delete(ids);
        return new JsonMap(0, "删除成功");
    }


    /**
     * 上传APK
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/appUpgrade/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uplaodFiles(@RequestParam("apkFile") MultipartFile file) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("status", "ok");
        try {
            if (file != null) {
                String filename = file.getOriginalFilename();
                String suffixName = StringUtils.substring(filename, StringUtils.lastIndexOf(filename, "."));
                if (!suffixName.equalsIgnoreCase(".apk")) {
                    result.put("status", "error");
                    result.put("errorMsg", "上传文件格式不正确,请上传.apk的文件");
                    return result;
                }
                String suffixPath = "/app/tmp/app.apk";
                String saveFilePath = SystemGlobal.getResourceUploadFolder() + suffixPath;
                InputStream in = file.getInputStream();
                String parentFolder = new File(saveFilePath).getParent();
                if (!new File(parentFolder).exists()) {
                    new File(parentFolder).mkdirs();
                }
                IOUtils.copy(in, new FileOutputStream(saveFilePath));
                result.put("fileName", file.getOriginalFilename());
                result.put("url", SystemGlobal.getResourceDownloadUrl() + suffixPath);
            }
        } catch (Exception e) {
            result.put("status", "error");
        }
        return result;
    }
}
