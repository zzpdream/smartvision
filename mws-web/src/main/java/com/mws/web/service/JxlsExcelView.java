package com.mws.web.service;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * 
 * @ClassName: JxlsExcelView
 * @Description: 导出Excel业务是实现类
 * @author: ranfi
 * @date: Jan 8, 2014 4:21:22 PM
 * 
 */
@Service
public class JxlsExcelView extends AbstractView {

	private static String content_type = "application/vnd.ms-excel; charset=UTF-8";
	private static String extension = ".xlsx";

	private String fileName;
	private String location;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public JxlsExcelView() {
		setContentType(content_type);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Workbook workbook;

		/* 获得XLS模板 */
		LocalizedResourceHelper helper = new LocalizedResourceHelper(getApplicationContext());
		Locale userLocale = RequestContextUtils.getLocale(request);
		Resource inputFile = helper.findLocalizedResource(location, extension, userLocale);

		/* 向POI的workbook中填充数据 */

		XLSTransformer transformer = new XLSTransformer();
		workbook = transformer.transformXLS(inputFile.getInputStream(), map);

		/* 设置HTML头实现重新定向输出流到客户端 */
		response.reset();
		response.setContentType(getContentType());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		/* 将数据流写入 */
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
		out.close();
	}

}
