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
            <th>名称</th>
            <th>父菜单</th>
            <th>图标</th>
            <th>地址</th>
            <th>排序</th>
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
        <c:forEach items="${page.content}" var="menu" varStatus="status">
            <tr>
                <td>
                    <div class="checkbox c-checkbox">
                        <label>
                            <input type="checkbox" class="checkbox-item" name="id" value="${menu.id}">
                            <span class="fa fa-check"></span>
                        </label>
                    </div>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${empty menu.parentMenu}">
                            <a href="${ctx}/menus?pid=${menu.id}">${menu.name}</a>
                        </c:when>
                        <c:otherwise>
                            ${menu.name}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${menu.parentMenu.name}" default="-" /> </td>
                <td>
                    <c:if test="${not empty menu.icon}"><em class="${menu.icon}"></em></c:if>
                    <c:if test="${empty menu.icon}">-</c:if>
                </td>
                <td><c:out value="${menu.url}" default="无" /></td>
                <th>${menu.sort}</th>
                <td>
                    <c:choose>
                        <c:when test="${menu.status eq 1}"><span class="text-success">可用</span></c:when>
                        <c:otherwise><span class="text-danger">禁用</span></c:otherwise>
                    </c:choose>
                </td>
                <td><fmt:formatDate value="${menu.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:import url="/WEB-INF/layouts/pagination.jsp" />
</div>
