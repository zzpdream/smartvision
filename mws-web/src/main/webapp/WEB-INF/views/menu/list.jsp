<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/WEB-INF/layouts/header.jsp">
        <c:param name="title" value="菜单管理 - 三级分销" />
    </c:import>
</head>

<c:set var="mainTitle" value="系统管理" />
<c:set var="subTitle" value="菜单列表" />

<body>
<div class="wrapper">
    <c:import url="/WEB-INF/layouts/nav.jsp" />
    <section>
        <div class="content-wrapper">
            <h3>${mainTitle}  &gt;
                <small>${subTitle}</small>
            </h3>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">${subTitle}</div>
                        <div class="panel-body table-fit">
                            <form id="pagination-form" class="" method="POST" action="${ctx}/menus">
                                <input type="hidden" name="pid" value="${pid}">
                                <div class="search-group form-inline">
                                    <input type="text" name="search_LIKE_name" placeholder="菜单名称" class="form-control">
                                    <button type="button" class="btn btn-primary btn-search">搜索</button>
                                </div>
                                <div class="action-group">
                                    <%--<shiro:hasPermission name="menu:update">--%>
                                    <a href="${ctx}/menu?pid=${pid}" class="btn btn-add btn-success pull-right">添加</a>
                                    <%--</shiro:hasPermission>--%>
                                    <c:if test="${not empty pid}">
                                        <a href="${ctx}/menus" class="btn btn-default pull-right">返回</a>
                                    </c:if>
                                    <div class="btn-group">
                                        <button type="button" data-toggle="dropdown" class="btn dropdown-toggle btn-primary">操作
                                            <span class="caret"></span>
                                        </button>
                                        <ul role="menu" class="dropdown-menu animated swing">
                                            <li><a href="javascript:void(0);" class="btn-edit-action">编辑</a>
                                            </li>
                                            <li><a href="javascript:void(0);" class="btn-delete-action">删除</a>
                                            </li>
                                        <c:if test="${not empty pid}">
                                            <li class="divider"></li>
                                            <li><a href="javascript:void(0);" class="btn-permission-action">权限点</a>
                                            </li>
                                        </c:if>
                                        </ul>
                                    </div>
                                </div>
                                <div id="pagination-body">

                                </div>
                                <input type="hidden" name="page" value="${pagination.page}">
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
<script src="${ctx}/static/js/common/pagination.js"></script>
<script src="${ctx}/static/js/menu/list.js"></script>

</body>

</html>