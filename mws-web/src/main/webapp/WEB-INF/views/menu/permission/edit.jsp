<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<c:set var="title" value="${empty permission ? '添加' : '编辑'}权限点" />
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-label="Close" class="close">
        <span aria-hidden="true">×</span>
    </button>
    <h4 id="editModalLabel" class="modal-title">${title}</h4>
</div>
<div class="modal-body">
    <form id="editForm" action="${ctx}/menu/permission" role="form">
        <input type="hidden" name="id" value="${permission.id}">
        <input type="hidden" id="menuId" name="menu.id">
        <div class="form-group">
            <label>名称</label>
            <input type="text" name="name" id="name" value="${permission.name}"
                   placeholder="请输入权限点名称" class="form-control">
        </div>
        <div class="form-group">
            <label>权限点</label>
            <input type="text" name="permission" id="permission" value="${permission.permission}"
                   placeholder="请输入权限点" class="form-control">
        </div>
    </form>
</div>
<div class="modal-footer">
    <button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
    <button type="button" class="btn btn-save btn-primary" data-loading-text="保存中...">保存</button>
</div>