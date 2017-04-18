<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/WEB-INF/layouts/header.jsp">
        <c:param name="title" value="菜单管理 - 三级分销" />
    </c:import>
    <link rel="stylesheet" href="${ctx}/static/third/bootstrap/css/bootstrap-slider.min.css" />
</head>

<c:set var="mainTitle" value="菜单管理" />
<c:set var="subTitle" value="${empty menu.id ? '添加菜单' : '编辑菜单'}" />

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
                            <div class="row">
                                <div class="col-md-6">
                                    <form id="form-edit" role="form" method="POST" action="${ctx}/menu">
                                        <input type="hidden" id="id" name="id" value="${menu.id}">
                                        <div class="form-group">
                                            <label>父菜单</label>
                                            <select name="parentMenu.id" id="parentMenuId" class="form-control">
                                                <option value="">根菜单</option>
                                                <c:forEach items="${rootMenus}" var="root">
                                                    <option value="${root.id}"
                                                        ${menu.parentMenu.id eq root.id ? "selected" : ""}
                                                        ${root.id eq pid ? "selected" : ""}>${root.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <label>菜单名称</label>
                                            <input type="text" name="name" id="name" placeholder="请输入菜单名称"
                                                   class="form-control" value="${menu.name}">
                                        </div>

                                        <div class="form-group">
                                            <label>菜单地址</label>
                                            <input type="text" name="url" id="url" placeholder="请输入菜单地址"
                                                   class="form-control" value="${menu.url}">
                                        </div>

                                        <div class="form-group">
                                            <label>图标</label>
                                            <input type="text" name="icon" id="icon" placeholder="请输入菜单图标"
                                                   class="form-control" value="${menu.icon}">
                                        </div>

                                        <div class="form-group">
                                            <label>排序</label><br>
                                            <input name="sort" data-ui-slider="" type="text" value="20" data-slider-min="1"
                                                   data-slider-max="20" data-slider-step="1" data-slider-value="${menu.sort}"
                                                   data-slider-orientation="horizontal" class="slider slider-horizontal"
                                                   data="value: '20'" style="display: none;">
                                        </div>

                                        <div class="form-group">
                                            <label>状态</label><br>
                                            <label class="radio-inline c-radio">
                                                <input type="radio" name="status" value="1"
                                                       ${menu.status eq 1 ? 'checked' : ''}>
                                                <span class="fa fa-circle"></span>可用
                                            </label>
                                            <label class="radio-inline c-radio">
                                                <input type="radio" name="status" value="0"
                                                       ${menu.status eq 0 ? 'checked' : ''}>
                                                <span class="fa fa-circle"></span>禁用
                                            </label>
                                        </div>

                                        <div class="form-group">
                                            <label>菜单备注说明</label>
                                            <textarea name="remark" id="remark" class="form-control" rows="3"
                                                      placeholder="请输入菜单备注说明">${menu.remark}</textarea>
                                        </div>

                                        <a href="${ctx}/menus?page=${pagination.page}&pid=${empty menu.parentMenu ? pid : menu.parentMenu.id}" class="btn btn-sm btn-back btn-default">返回</a>

                                        <button type="submit" class="btn btn-submit btn-sm btn-primary"
                                                data-loading-text="${empty menu.id ? "添加" : "编辑"}中...">
                                            ${empty menu.id ? "添 加" : "编 辑"}
                                        </button>
                                    </form>
                                </div>
                            </div>
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
<script src="${ctx}/static/js/menu/edit.js"></script>
</body>

</html>