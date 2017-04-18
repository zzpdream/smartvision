<%--
  Created by IntelliJ IDEA.
  User: whan
  Date: 9/29/15
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="tag.jsp"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="description" content="${not empty param.description ? param.description : "管理后台"}">
<meta name="keywords" content="${not empty param.keywords ? param.keywords : "管理后台"}">
<title>${not empty param.title ? param.title : "管理后台"}</title>

<link rel="shortcut icon" href="${ctx}/static/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" href="${ctx}/static/third/fontawesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/static/third/simple-line-icons/css/simple-line-icons.css">
<link rel="stylesheet" href="${ctx}/static/css/animate.min.css">
<link rel="stylesheet" href="${ctx}/static/css/whirl.css">
<link rel="stylesheet" href="${ctx}/static/third/bootstrap/css/bootstrap_3.0.2.css" id="bscss">
<link href="${ctx}/static/libs/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/third/simditor/styles/simditor.css">
<link rel="stylesheet" href="${ctx}/static/third/simditor/styles/simditor-html.css">
<link rel="stylesheet" href="${ctx}/static/third/simditor/styles/simditor-markdown.css">
<link rel="stylesheet" href="${ctx}/static/css/app.css" id="maincss">
<link rel="stylesheet" href="${ctx}/static/css/jquery.fileupload-ui.css" id="maincss">
<link rel="stylesheet" href="${ctx}/static/css/jquery.fileupload.css" id="maincss">
<!-- DATETIMEPICKER-->
<link rel="stylesheet" href="${ctx}/static/bootstrap-plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">

<link rel="stylesheet" href="${ctx}/static/third/form-editable/css/bootstrap-editable.css">
   <!-- =============== BOOTSTRAP STYLES ===============-->
