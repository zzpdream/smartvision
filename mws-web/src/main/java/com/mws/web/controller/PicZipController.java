package com.mws.web.controller;

import com.google.common.collect.Maps;
import com.mws.core.utils.StringHelper;
import com.mws.model.PicZip;
import com.mws.web.common.bo.SystemGlobal;
import com.mws.web.common.controller.WebController;
import com.mws.web.common.vo.Pagination;
import com.mws.web.service.PicZipService;
import com.mws.web.web.JsonMap;
import com.mws.web.web.Servlets;
import org.apache.commons.codec.digest.DigestUtils;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Controller - picZip
 */
@Controller
public class PicZipController extends WebController {

    @Autowired
    private PicZipService picZipService;

    /**
     * picZip列表
     */
    @RequestMapping(value = "/picZips", method = RequestMethod.GET)
    public String list(Pagination pagination, Model model) {
        model.addAttribute("pagination", pagination);
        return "picZip/list";
    }

    /**
     * picZip列表
     */
    @RequestMapping(value = "/picZips", method = RequestMethod.POST)
    public String list(Pagination pagination, HttpServletRequest request, Model model) {
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        model.addAttribute("page", picZipService.list(pagination, searchParams, new Sort(Sort.Direction.DESC, "id")));
        return "picZip/nested";
    }

    /**
     * 创建picZip
     */
    @RequestMapping(value = "/picZip", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request, Pagination pagination) {
        model.addAttribute("pagination", pagination);
        return "picZip/edit";
    }

    /**
     * 编辑picZip
     */
    @RequestMapping(value = "/picZip/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request, Pagination pagination) {
        PicZip picZip = picZipService.findPicZip(id);
        model.addAttribute("picZip", picZip);
        model.addAttribute("pagination", pagination);
        String path = picZip.getUrl();
        if (StringUtils.isNotEmpty(path)) {
            int index = path.lastIndexOf("/");
            String fileName = path.substring(index + 1, path.length());
            model.addAttribute("fileName", fileName);
        }
        return "picZip/edit";
    }

    /**
     * 保存picZip
     */
    @ResponseBody
    @RequestMapping(value = "/picZip", method = RequestMethod.POST)
    public JsonMap save(@Valid PicZip picZip, BindingResult result, HttpServletRequest request) {
        JsonMap ret = new JsonMap();
        try {
            if (result.hasErrors()) {
                ret = parseErrorResult(result, "保存失败");
                return ret;
            }
            //获取文件后缀
            String tmpUrl = request.getParameter("tmpUrl");
            String suffixName;
            if (StringUtils.isNotBlank(tmpUrl)) {
                suffixName = StringUtils.substring(tmpUrl, StringUtils.lastIndexOf(tmpUrl, "."));
            } else {
                suffixName = ".zip";
            }
            String newFileSuffixPath = "/pic/photo" + suffixName;
            String newFilePath = SystemGlobal.getResourceUploadFolder() + newFileSuffixPath;
            String parentFolder = new File(newFilePath).getParent();
            if (!new File(parentFolder).exists()) {
                new File(parentFolder).mkdirs();
            }
            //原文件目录
            String oldFilePath = SystemGlobal.getResourceUploadFolder() + "/pic/tmp/photo" + suffixName;
            if (new File(oldFilePath).exists()) {
                //复制文件
                FileUtils.copyFile(new File(oldFilePath), new File(newFilePath));
                FileInputStream fis = new FileInputStream(newFilePath);
                String md5 = DigestUtils.md5Hex(fis);
                picZip.setMd5(md5);

                fis.close();
            }
            //保存新路径
            picZip.setUrl(SystemGlobal.getResourceDownloadUrl() + newFileSuffixPath);
            picZipService.save(picZip);
        } catch (Exception e) {
            ret = parseErrorResult(result, "保存失败");
        }
        return ret;
    }

    /**
     * 删除picZip
     */
    @ResponseBody
    @RequestMapping(value = "/picZip/delete", method = RequestMethod.POST)
    public JsonMap delete(@RequestParam("id[]") Integer[] ids) {
        picZipService.delete(ids);
        return new JsonMap(0, "删除成功");
    }


    /**
     * 上传picZip
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/picZip/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uplaodCertFiles(@RequestParam("zipFile") MultipartFile file) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("status", "ok");
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                if (!suffixName.equalsIgnoreCase(".zip")) {
                    result.put("status", "error");
                    result.put("errorMsg", "上传文件格式不正确,请上传.zip文件");
                    return result;
                }
                String suffixPath = "/pic/tmp/photo" + suffixName;
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
            result.put("errorMsg", "上传文件异常");
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/ranfi/Downloads/photo1.zip");
        FileInputStream fis = new FileInputStream(file);
        String md5 = DigestUtils.md5Hex(fis);
        fis.close();

        String md5Str1 = StringHelper.getMd5(file);

        System.out.println("md5值:" + md5 + "," + md5Str1);


    }
}
