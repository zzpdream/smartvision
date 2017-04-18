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
            <th>用户数</th>
            <th>创建用户</th>
            <th>添加时间</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty page.content}">
            <tr>
                <td colspan="5" class="text-center">没有查询到角色信息！</td>
            </tr>
        </c:if>
        <c:forEach items="${page.content}" var="role" varStatus="status">
            <tr>
                <td>
                    <div class="checkbox c-checkbox">
                        <label>
                            <input type="checkbox" class="checkbox-item" name="id" value="${role.id}">
                            <span class="fa fa-check"></span>
                        </label>
                    </div>
                </td>
                <td><c:out value="${role.name}" default="-" /></td>
                <td><c:out value="${fn:length(role.users)}" default="" /></td>
                <td><c:out value="${role.user.fullname}" default="-" /></td>
                <td><fmt:formatDate value="${role.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:import url="/WEB-INF/layouts/pagination.jsp" />
</div>
