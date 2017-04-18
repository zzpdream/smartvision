<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/WEB-INF/layouts/header.jsp">
        <c:param name="title" value="系统用户管理" />
    </c:import>
</head>

<c:set var="mainTitle" value="终端管理" />
<c:set var="subTitle" value="终端机器查询" />

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
                        <div class="panel-body table-fit">
                            <form id="pagination-form" class="" method="POST" action="${ctx}/terminal/list">
                                <input type="hidden" name="pid" value="${pid}">
                                <div class="search-group form-inline">
                                    <input type="text" id="search_EQ_seatId" name="search_EQ_seatId" placeholder="座位号" class="form-control">
                                    <input type="text" id="search_EQ_ip" name="search_EQ_ip" placeholder="终端IP" class="form-control">
                                    <select id="search_EQ_terminalType" name="search_EQ_terminalType" class="form-control">
                                        <option value="">终端类型</option>
                                        <option value="1">正式终端</option>
                                        <option value="2">列席终端</option>
                                    </select>
                                    <select id="search_EQ_connectStatus" name="search_EQ_connectStatus" class="form-control">
                                        <option value="">连接状态</option>
                                        <%--<option value="0">未连接</option>--%>
                                        <option value="1">已连接</option>
                                        <option value="2">已断开</option>
                                    </select>
                                    <button type="button" class="btn btn-primary btn-search">搜索</button>
                                    <div class="form-control ">
                                        总数: <strong>${totalTerminals}</strong>
                                        已连接数: <strong>${connectedTerminals}</strong>
                                        已断开数: <strong>${disconnectedTerminals}</strong>
                                    </div>
                                </div>
                                <div class="action-group">
                                    <button type="button" class="btn btn-add btn-success pull-right">添加</button>
                                    <div class="btn-group">
                                        <button type="button" data-toggle="dropdown" class="btn dropdown-toggle btn-primary">操作
                                            <span class="caret"></span>
                                        </button>
                                        <ul role="scenicPoi" class="dropdown-menu animated swing">
                                            <li><a href="#" class="btn-edit-action">编辑</a>
                                            </li>
                                            <li><a href="#" class="btn-delete-action">删除</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div id="pagination-body">

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
<script src="${ctx}/static/js/common/pagination.js"></script>
<script src="${ctx}/static/js/terminal/list.js"></script>
</body>
</html>