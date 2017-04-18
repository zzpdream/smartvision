<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/layouts/tag.jsp"%>
<c:set var="title" value="${empty user ? '添加' : '编辑'}用户" />
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-label="Close" class="close">
        <span aria-hidden="true">×</span>
    </button>
    <h4 id="editModalLabel" class="modal-title">${title}</h4>
</div>
<div class="modal-body">
    <form id="editForm" action="${ctx}/user" role="form">
        <input type="hidden" name="id" value="${user.id}">
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label>帐号</label>
                    <input type="text" name="account" id="account" value="${user.account}" placeholder="请输入登陆帐号" class="form-control">
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label>密码</label>
                    <input type="password" name="password" id="password"  placeholder="请输入密码" class="form-control">
                    <c:if test="${not empty user.id}">
                        <span class="help-block m-b-none">不填则默认不修改密码</span>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label>姓名</label>
                    <input type="text" name="fullname" id="fullname" value="${user.fullname}" placeholder="请输入姓名" class="form-control">
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label>手机号码</label>
                    <input type="text" name="phone" id="phone" value="${user.phone}" placeholder="请输入手机号码" class="form-control">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label>邮箱</label>
                    <input type="email" name="email" id="email" value="${user.email}" placeholder="请输入邮箱" class="form-control">
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label>昵称</label>
                    <input type="text" name="nickname" id="nickname" value="${user.nickname}" placeholder="请输入昵称" class="form-control">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label>性别</label>
                    <div>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="gender" value="1" ${user.gender eq 1 or empty user ? "checked" : ""}>
                            <span class="fa fa-male"></span>男</label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="gender" value="2" ${user.gender eq 2 ? "checked" : ""}>
                            <span class="fa fa-female"></span>女</label>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label>状态</label>
                    <div>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="status" value="1"  ${user.status eq 1  or empty user ? "checked" : ""}>
                            <span class="fa fa-unlock"></span>正常</label>
                        <label class="radio-inline c-radio">
                            <input type="radio" name="status" value="0"  ${user.status eq 2 ? "checked" : ""}>
                            <span class="fa fa-lock"></span>禁用</label>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12">
                <div class="form-group">
                    <label>角色</label>
                    <div>
                    <c:forEach items="${roles}" var="role">
                        <label class="checkbox-inline c-checkbox">
                            <input class="role" type="checkbox" value="${role.id}" ${user.hasRoleChecked(role.id) ? "checked" : ""}>
                            <span class="fa fa-check"></span> ${role.name}</label>
                    </c:forEach>
                    </div>
                </div>
            </div>
        </div>

    </form>
</div>
<div class="modal-footer">
    <button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
    <button type="button" class="btn btn-save btn-primary" data-loading-text="保存中...">保存</button>
</div>