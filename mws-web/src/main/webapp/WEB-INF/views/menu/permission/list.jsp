<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/WEB-INF/layouts/header.jsp">
        <c:param name="title" value="权限点列表 - 三级分销" />
    </c:import>
</head>

<c:set var="mainTitle" value="系统管理" />
<c:set var="subTitle" value="${menu.name} - 权限点列表" />

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
                            <form id="pagination-form" class="" method="POST" action="${ctx}/menu/permissions">
                                <input type="hidden" name="search_EQ_menu.id" value="${menuId}">
                                <div class="search-group form-inline">
                                    <input type="text" name="search_LIKE_name" placeholder="权限点名称" class="form-control">
                                    <button type="button" class="btn btn-primary btn-search">搜索</button>
                                </div>
                                <div class="action-group">
                                    <a href="${ctx}/menu/permission" class="btn btn-add btn-success pull-right">添加</a>
                                    <a href="${ctx}/menus?page=${menuPage}&pid=${pid}" class="btn btn-default pull-right">返回</a>
                                    <div class="btn-group">
                                        <button type="button" data-toggle="dropdown" class="btn dropdown-toggle btn-primary">操作
                                            <span class="caret"></span>
                                        </button>
                                        <ul role="menu" class="dropdown-menu animated swing">
                                            <li><a href="javascript:void(0);" class="btn-edit-action">编辑</a>
                                            </li>
                                            <li><a href="javascript:void(0);" class="btn-delete-action">删除</a>
                                            </li>
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

<div id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="false" class="modal fade in">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">

        </div>
    </div>
</div>


<c:import url="/WEB-INF/layouts/footer.jsp" />
<script src="${ctx}/static/js/common/pagination.js"></script>
<script src="${ctx}/static/js/menu/permission/list.js"></script>
<script>
    var menuId = "${menuId}";
</script>
</body>

</html>