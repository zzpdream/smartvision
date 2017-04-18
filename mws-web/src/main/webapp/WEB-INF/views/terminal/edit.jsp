<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/WEB-INF/layouts/header.jsp">
        <c:param name="title" value="终端管理" />
    </c:import>
</head>
<c:set var="mainTitle" value="终端管理" />
<c:set var="subTitle" value="${empty terminal ? '添加终端' : '编辑终端'}" />
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
                            <form id="form-edit" role="form" method="POST" class="form-horizontal" action="${ctx}/terminal/save">
                                <input type="hidden" name="id" value="${terminal.id}">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">终端座位号</label>
                                            <div class="col-md-8"><input type="text" name="seatId" id="seatId" value="${terminal.seatId}" placeholder="请输入终端座位号" class="form-control"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">终端IP</label>
                                            <div class="col-md-8"> <input type="ip" name="ip" id="ip" value="${terminal.ip}"  placeholder="请输入终端IP" class="form-control"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">终端类型</label>
                                            <div class="col-md-8">
                                                <select id="terminalType" name="terminalType" class="form-control">
                                                    <option value="">请选择</option>
                                                    <option value="1" <c:if test="${terminal.terminalType ==1}">selected</c:if>>正式终端</option>
                                                    <option value="2" <c:if test="${terminal.terminalType ==2}">selected</c:if> >列席终端</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <a class="btn btn-sm btn-primary" href="javascript:history.go(-1);" role="button">返回</a>
                                <button type="submit" class="btn btn-submit btn-sm btn-primary"
                                        data-loading-text="${empty terminal ? "添加" : "保存"}中...">
                                    ${empty terminal ? "添 加" : "保存"}
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
<script src="${ctx}/static/js/terminal/edit.js"></script>