<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/WEB-INF/layouts/header.jsp">
        <c:param name="title" value="系统用户管理" />
    </c:import>
</head>

<c:set var="mainTitle" value="系统管理" /> 
<c:set var="subTitle" value="用户列表" />

<body>
<div class="wrapper">
    <c:import url="/WEB-INF/layouts/nav.jsp" />
    <section>
        <div class="content-wrapper">
           <h3 >${mainTitle} &gt; <small>${subTitle}</small></h3>       
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body table-fit">
                            <form id="pagination-form" class="" method="POST" action="${ctx}/users">
                                <input type="hidden" name="pid" value="${pid}">
                                <div class="search-group form-inline">
                                    <input type="text" name="search_LIKE_account" placeholder="帐号" class="form-control">
                                    <button type="button" class="btn btn-primary btn-search">搜索</button>
                                </div>
                                <div class="action-group">
                                    <a href="javascript:void(0);" class="btn btn-add btn-success pull-right">添加</a>
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
                                            <li class="divider"></li>
                                            <li><a href="javascript:void(0);" class="btn-disable">禁用</a>
                                            </li>
                                            <li><a href="javascript:void(0);" class="btn-enable">解禁</a>
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
    <div class="modal-dialog">
        <div class="modal-content">

        </div>
    </div>
</div>


<c:import url="/WEB-INF/layouts/footer.jsp" />
<script src="${ctx}/static/js/common/pagination.js"></script>
<script src="${ctx}/static/js/user/list.js"></script>
</body>

</html>