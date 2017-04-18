<%@ page import="com.mws.web.common.bo.Constant" %>
<%--
  Created by IntelliJ IDEA.
  User: whan
  Date: 10/9/15
  Time: 9:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp" %>
<div class="table-responsive">
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th>
                <div class="checkbox c-checkbox">
                    <label>
                        <input type="checkbox" class="checkbox-global">
                        <span class="fa fa-check"></span>
                    </label>
                </div>
            </th>
            <th>帐号</th>
            <th>昵称</th>
            <th>姓名</th>
            <th>性别</th>
            <th>手机号码</th>
            <th>邮箱</th>
            <th>状态</th>
            <th>添加时间</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty page.content}">
            <tr>
                <td colspan="8" class="text-center">没有查询到菜单！</td>
            </tr>
        </c:if>
        <c:forEach items="${page.content}" var="user" varStatus="status">
            <tr>
                <td>
                    <div class="checkbox c-checkbox">
                        <label>
                            <input type="checkbox" class="checkbox-item" name="id" value="${user.id}">
                            <span class="fa fa-check"></span>
                        </label>
                    </div>
                </td>
                <td><c:out value="${user.account}" default="-" /></td>
                <td><c:out value="${user.nickname}" default="-" /></td>
                <td><c:out value="${user.fullname}" default="-" /></td>
                <td>
                    <c:choose>
                        <c:when test="${user.gender eq 1}">男</c:when>
                        <c:when test="${user.gender eq 2}">女</c:when>
                        <c:when test="${user.gender eq 0}">未知</c:when>
                    </c:choose>
                </td>
                <td><c:out value="${user.phone}" default="-" /></td>
                <td><c:out value="${user.email}" default="-" /></td>
                <td>
                    <c:choose>
                        <c:when test="${user.status eq 1}"><span class="text-success">正常</span></c:when>
                        <c:otherwise><span class="text-danger">禁用</span></c:otherwise>
                    </c:choose>
                </td>
                <td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:import url="/WEB-INF/layouts/pagination.jsp" />
</div>
