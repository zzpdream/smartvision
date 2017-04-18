$(function() {

    var $editModal = $("#editModal");

    var list = {

        /** 初始化 */
        init: function() {
            this.initPagination();
            this.initEditModal();
            this.bindAddEvent();
            this.bindDisableAction();
            this.bindEnableAction();
        },

        /** 初始化分页 */
        initPagination: function() {
            var self = this;
            Pagination.init({
                pageSize: 10,
                editBtn: {
                    action: function(id) {
                        self.bindEditEvent(id);
                    }
                },
                deleteBtn: {
                    url: ctx + "/user/delete",
                    callback: function(data) {
                        // 回调函数
                    }
                }
            });
        },

        /** 绑定添加用户事件 */
        bindAddEvent: function() {
            $(".btn-add").on("click", function(e) {
                e.preventDefault();

                // 模态框数据
                $editModal.find(".modal-content").load(ctx + "/user");

                // 显示模态框
                $editModal.modal("show");
            });
        },

        /** 绑定编辑用户事件 */
        bindEditEvent: function (id) {
            // 模态框数据
            $editModal.find(".modal-content").load(ctx + "/user/" + id);

            // 显示模态框
            $editModal.modal("show");
        },

        /** 初始化系统用户编辑框 */
        initEditModal: function() {
            var self = this;
            $editModal.on("hide.bs.modal", function() {
                $(this).removeData("bs.modal");
            }).on("shown.bs.modal", function() {
                self.bindFormAction();
            });
        },

        /** 绑定禁用操作 */
        bindDisableAction: function() {
            $(".btn-disable").on("click", function() {
                var ids = Pagination.getCheckedIds();

                if (ids.length <= 0) {
                    Dialog.warning("请先选择您要禁用的用户");
                    return;
                }

                Dialog.confirm("禁用用户", "确认要禁用这些用户？",  function(result) {
                    if (result == true) {
                        $.ajax({
                            url: ctx + "/user/disable",
                            type: "POST",
                            data: {
                                id: ids
                            },
                            dataType:"json",
                            success: function(data) {
                                if (data.code == 0) {
                                    Dialog.success(data.msg);
                                    Pagination.reload();
                                }
                            }
                        })
                    }
                });
            });
        },

        /** 绑定解禁操作 */
        bindEnableAction: function() {
            $(".btn-enable").on("click", function() {
                var ids = Pagination.getCheckedIds();

                if (ids.length <= 0) {
                    Dialog.warning("请先选择您要解禁的用户");
                    return;
                }

                Dialog.confirm("解禁用户", "确认要解禁这些用户？",  function(result) {
                    if (result == true) {
                        $.ajax({
                            url: ctx + "/user/enable",
                            type: "POST",
                            data: {
                                id: ids
                            },
                            dataType:"json",
                            success: function(data) {
                                if (data.code == 0) {
                                    Dialog.success(data.msg);
                                    Pagination.reload();
                                }
                            }
                        })
                    }
                });
            });
        },

        /** 获取选中的角色编号 */
        getCheckedRoleIds: function() {
            var params = [];
            $(".role:checked").each(function(i) {
                params.push({name: "roleList["+i+"].id", value: this.value});
            });
            return params;
        },

        /** 绑定表单事件 */
        bindFormAction: function() {
            var $editForm = $("#editForm");
            var $button = $(".btn-save");

            // 表单验证
            var validate = $editForm.validate({
                rules: {
                    account: {
                        required: true,
                        maxlength: 45
                    },
                    nickname: {
                        required: true,
                        maxlength: 50
                    },
                    fullname: {
                        maxlength: 45
                    },
                    email: {
                        required: true,
                        email: true,
                        maxlength: 45
                    },
                    phone: {
                        required: true,
                        mobile: true
                    }
                },
                messages: {
                    account: {
                        required: "请输入帐号",
                        maxlength: "帐号长度不能超过{0}个字符"
                    },
                    nickname: {
                        required: "请输入昵称",
                        maxlength: "昵称长度不能超过{0}个字符"
                    },
                    fullname: {
                        maxlength: "姓名长度不能超过{0}个字符"
                    },
                    email: {
                        required: "请输入邮箱地址",
                        email: "您输入的邮箱地址不正确",
                        maxlength: "邮箱地址长度不能超过{0}个字符"
                    },
                    phone: {
                        required: "请输入手机号码",
                        mobile: "您输入的手机号码格式不正确"
                    }
                },
                submitHandler: function(form) {

                    var checkedRoles = list.getCheckedRoleIds();
                    if (checkedRoles.length <= 0) {
                        Dialog.warning("请至少给用户选择一个角色");
                        return;
                    }


                    $.ajax({
                        url: $editForm.attr("action"),
                        method: "POST",
                        data: $editForm.serialize() + "&" + $.param(checkedRoles),
                        dataType:"json",
                        beforeSend: function() {
                            $(this).button("loading");
                        },
                        success: function(data) {
                            if (data.code == 0) {
                            	Dialog.success(data.msg, function() {
                            		$editModal.modal("hide");
                                    Pagination.reload();
                                }, 1500);
                                
                            } else {
                                validate.showErrors(data.errors);
                                $button.button("reset");
                                Dialog.danger(data.msg);
                            }
                        }
                    });
                }
            });

            // 提交编辑表单
            $button.on("click", function() {
                $editForm.submit();
            });
        }

    };

    list.init();
});
