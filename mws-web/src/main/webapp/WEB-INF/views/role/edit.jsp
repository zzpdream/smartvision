<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/WEB-INF/layouts/header.jsp">
        <c:param name="title" value="角色管理 - 三级分销" />
    </c:import>
    <link rel="stylesheet" href="${ctx}/static/third/bootstrap/css/bootstrap-slider.min.css" />
    <style>
        .tree {
            min-height:20px;
            margin-bottom:20px;
        }
        .tree ul:first-child {
            padding-left: 0;
        }
        .tree li {
            list-style-type:none;
            margin:0;
            padding:10px 5px 0 5px;
            position:relative
        }
        .tree li::before, .tree li::after {
            content:'';
            left:-20px;
            position:absolute;
            right:auto
        }
        .tree li::before {
            border-left:1px solid #999;
            bottom:50px;
            height:100%;
            top:0;
            width:1px
        }
        .tree li::after {
            border-top:1px solid #999;
            height:20px;
            top:25px;
            width:25px
        }
        .tree li span.item {
            -moz-border-radius:5px;
            -webkit-border-radius:5px;
            border:1px solid #999;
            border-radius:5px;
            display:inline-block;
            padding:6px 12px;
            text-decoration:none
        }
        .tree li.parent_li>span {
            cursor:pointer
        }
        .tree>ul>li::before, .tree>ul>li::after {
            border:0
        }
        .tree li:last-child::before {
            height:26px
        }
        .tree li.parent_li>span:hover, .tree li.parent_li>span:hover+ul li span {
            background:#eee;
            border:1px solid #94a0b4;
            color:#000
        }
        .tree .checkbox {
            min-height: inherit;
            padding-top: 0;
        }
        .tree span hr {
            margin: 7px 0 0 0;
        }
        .tree label {
            font-weight: normal;
        }
    </style>
</head>

<c:set var="mainTitle" value="角色管理" />
<c:set var="subTitle" value="${empty role.id ? '添加角色' : '编辑角色'}" />

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

                        <div class="panel-body">
                                <form id="form-edit" role="form" method="POST" action="${ctx}/role" class="form-horizontal">
                                    <input type="hidden" id="id" name="id" value="${role.id}">

                                    <fieldset>
                                        <div class="form-group">
                                            <label class="col-md-2 control-label">名称</label>
                                            <div class="col-md-6">
                                                <input type="text" name="name" id="name" placeholder="请输入角色名称"
                                                       class="form-control" value="${role.name}">
                                            </div>
                                        </div>
                                    </fieldset>


                                    <div class="form-group">
                                        <label class="col-md-2 control-label">角色菜单</label>
                                        <div class="col-md-10">
                                            <div class="tree">
                                                <ul>
                                                    <li>
                                                        <span class="item"><i class="fa fa-bars"></i> 根目录</span>
                                                        <ul>
                                                        <c:forEach items="${rootMenus}" var="rootMenu">
                                                            <li>
                                                                <span class="item"><i class="fa fa-minus"></i> ${rootMenu.name}</span>
                                                            <c:if test="${not empty rootMenu.childMenu}">
                                                                <ul>
                                                                <c:forEach items="${rootMenu.childMenu}" var="menu">
                                                                    <li>
                                                                        <span class="item">
                                                                            <div class="checkbox c-checkbox checkbox-menu">
                                                                                <label>
                                                                                    <input type="checkbox" class="menu" value="${menu.id}" ${role.hasMenuChecked(menu.id) ? 'checked' : ''}>
                                                                                    <span class="fa fa-check"></span> ${menu.name}</label>
                                                                            </div>
                                                                            <c:if test="${not empty menu.permissions}">
                                                                                <hr>
                                                                                <c:forEach items="${menu.permissions}" var="permission">
                                                                                    <div class="checkbox-inline c-checkbox checkbox-permission">
                                                                                        <label>
                                                                                            <input type="checkbox" class="permission" value="${permission.id}" ${role.hasPermissionChecked(permission.id) ? 'checked' : ''}>
                                                                                            <span class="fa fa-check"></span> ${permission.name}</label>
                                                                                    </div>
                                                                                </c:forEach>
                                                                            </c:if>
                                                                        </span>
                                                                    </li>
                                                                </c:forEach>
                                                                </ul>
                                                            </c:if>
                                                            </li>
                                                        </c:forEach>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>

                                    <fieldset></fieldset>
                                    <div class="form-group">
                                        <div class="col-md-10 col-md-offset-2">
                                            <button type="submit" class="btn btn-submit btn-sm btn-primary"
                                                    data-loading-text="${empty role.id ? "添加" : "编辑"}中...">
                                                ${empty role.id ? "添 加" : "编 辑"}
                                            </button>
                                            <a href="${ctx}/roles?page=${pagination.page}" class="btn btn-sm btn-back btn-default">返回</a>
                                        </div>
                                    </div>
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
<script src="${ctx}/static/js/role/edit.js"></script>
</body>

</html>