<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <th>
                ID
            </th>
            <th>终端IP</th>
            <th>终端座位号</th>
            <th>终端类型</th>
            <th>升级版本号</th>
            <th>版本号</th>
            <th>终端状态</th>
            <th>最新指令状态</th>
            <th>当前指令状态</th>
            <th>心跳时间</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty page.content}">
            <tr>
                <td colspan="9" class="text-center">没有查询到终端机器！</td>
            </tr>
        </c:if>
        <c:forEach items="${page.content}" var="terminal" varStatus="status">
            <tr>
                <td>
                    <div class="checkbox c-checkbox">
                        <label>
                            <input type="checkbox" class="checkbox-item" name="id" value="${terminal.id}">
                            <span class="fa fa-check"></span>
                        </label>
                    </div>
                </td>
                <td>${terminal.id}</td>
                <td><c:out value="${terminal.ip}" default="-" /></td>
                <td><c:out value="${terminal.seatId}" default="-" /></td>
                <td><c:out value="${terminal.terminalType == 1 ? '正式终端' : '列席终端'}" default="-" /></td>
                <td><c:out value="${terminal.versionCode}" default="-" /></td>
                <td><c:out value="${terminal.appVersion}" default="-" /></td>
                <td>
                    <c:choose>
                        <c:when test="${terminal.connectStatus == 1}">
                            <span class="btn btn-green"><c:out value="${terminal.connectStatusName}" default="-"/></span>
                        </c:when>
                        <c:when test="${terminal.connectStatus == 2}">
                            <span class="btn btn-danger"><c:out value="${terminal.connectStatusName}" default="-"/></span>
                        </c:when>
                        <c:otherwise>
                            <span class="btn btn-danger"><c:out value="${terminal.connectStatusName}" default="-"/></span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${terminal.latestCommandStatus}" default="-" /></td>
                <td><c:out value="${terminal.status}" default="-" /></td>
                <td><fmt:formatDate value="${terminal.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:import url="/WEB-INF/layouts/pagination.jsp" />
</div>
