<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/WEB-INF/layouts/header.jsp">
        <c:param name="title" value="终端软件升级管理 " />
    </c:import>
<link rel="stylesheet" href="${ctx}/static/third/bootstrap/css/bootstrap-slider.min.css" />
<link rel="stylesheet" href="${ctx}/static/css/jquery.fileupload.css">
<link rel="stylesheet" href="${ctx}/static/css/jquery.fileupload-ui.css">
</head>
<c:set var="mainTitle" value="终端软件升级管理" />
<c:set var="subTitle" value="${empty appUpgrade ? '添加升级软件' : '编辑升级软件'}" />

<body>
<div class="wrapper">
    <c:import url="/WEB-INF/layouts/nav.jsp" />
    <section>
        <div class="content-wrapper">
            <h3>${mainTitle} &gt;
                <small>${subTitle}</small>
            </h3>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">${subTitle}</div>
                        <div class="panel-body">
                           <form id="form-edit" role="form" method="POST"  enctype="multipart/form-data" class="form-horizontal" action="${ctx}/appUpgrade">
	                            <div class="row">
	                                <div class="col-md-12">
	                                     <input type="hidden" id="id" name="id" value="${appUpgrade.id}">
	                                     
	                                     <div class="form-group">
	                                         <label class="col-md-2 control-label">软件升级版本号:</label>
	                                         <div class="col-md-8">
	                                         	<input type="text" name="versionCode" id="versionCode" placeholder="请输入软件升级版本号"
	                                                class="form-control" value="${appUpgrade.versionCode}">
	                                         </div>
	                                     </div>
	                                     
	                                     <div class="form-group">
	                                         <label class="col-md-2 control-label">软件版本号:</label>
	                                         <div class="col-md-8">
	                                         	<input type="text" name="appVersion" id="appVersion" placeholder="请输入软件版本号"
	                                                class="form-control" value="${appUpgrade.appVersion}">
	                                         </div>
	                                     </div>
	                                     
	                                     
	                                     <div class="form-group">
	                                         <label class="col-md-2 control-label">上传版本升级包:</label>
	                                         <input type="hidden" name="url"  id="url" value = "${appUpgrade.url}" >
											 <div class="col-md-8">
												<div id="urlFileList">
												<c:if test="${not empty appUpgrade.url}">
													<div><a class="filename" target="_blank" href="${appUpgrade.url}">${fileName}</a>
													&nbsp;&nbsp;<a class="btn red del_file" data-filename = "${appUpgrade.url}">删除</a>&nbsp;
													</div><br>
												</c:if>
											   </div>
												 <span class="btn btn-primary fileinput-button">
													 <%--<i class="glyphicon glyphicon-plus"></i>--%>
													 <span>上传文件</span>
													<input id="apk_upload" name="apkFile" type="file" multiple>
												 </span>
												 <div class="progress" style="display: none" >
													 <div class="progress-bar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>
												 </div>
											</div>
	                                     </div>
	                                     
	                                      <div class="form-group">
	                                         <label class="col-md-2 control-label">备注:</label>
	                                         <div class="col-md-8">
	                                         	<input type="text" name="remark" id="remark" placeholder="请输入备注"
	                                                class="form-control" value="${appUpgrade.remark}">
	                                         </div>
	                                     </div>
	                                     
	                                 </div>
	                            </div>
	                            
	                             <a class="btn btn-sm btn-primary" href="javascript:history.go(-1);" role="button">返回</a>
	                             <button type="submit" class="btn btn-submit btn-sm btn-primary"
                                                data-loading-text="${empty appUpgrade ? "添加" : "保存"}中...">
                                            ${empty appUpgrade ? "添 加" : "保存"}
                                 </button>
                                
                            </form> 
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
    </section>
    <c:import url="/WEB-INF/layouts/content_footer.jsp" />
</div>
<c:import url="/WEB-INF/layouts/footer.jsp" />
<script src="${ctx}/static/third/bootstrap/js/bootstrap-slider.min.js"></script>
<script src="${ctx}/static/js/jQueryFileUpload/jquery.ui.widget.js"></script>
<script src="${ctx}/static/js/jQueryFileUpload/jquery.iframe-transport.js"></script>
<script src="${ctx}/static/js/jQueryFileUpload/jquery.fileupload.js"></script>
<script src="${ctx}/static/js/appUpgrade/edit.js"></script>
<script>

$(function(){
	
	 $('#apk_upload').fileupload({
		    autoUpload : true,
	        dataType: 'json',
	        url: window.ctx + '/appUpgrade/uploadFile',
		 	acceptFileTypes: /(\.|\/)(apk)$/i,
		    progressall: function (e, data) {
				$(".progress").show();
				var progress = parseInt(data.loaded / data.total * 100, 10);
				$('.progress .progress-bar').css(
						'width',
						progress + '%'
				);
			 },
	        // 上传完成后的执行逻辑
	        done: function (e, data) {
				if(data.result.status == 'ok') {
					$("#urlFileList").html('<div><a class="filename" href=' + data.result.url + '>' + data.result.fileName + '</a>&nbsp;<a class="btn red del_file">删除</a>&nbsp;&nbsp;</div>');
					delFile(data.result.fileName);
					$('.progress .progress-bar').hide();
				} else {
					Dialog.warning(data.result.errorMsg);
				}
	        }
	    });
	 
	delFile();
	
	function delFile(fileName){
		$(".del_file").off("click").on("click",function(){
			var obj = $(this);
			if(null==fileName || fileName == ""){
				fileName = obj.attr("data-filename");
			}
		
			obj.parent().remove();
			
		})
	}

});
</script>
</body>

</html>