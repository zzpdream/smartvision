$(function() {

    var $editModal = $("#editModal");

    var list = {

        /** 初始化 */
        init: function() {
            this.initPagination();
            this.initEditModal();
            this.bindAddAction();
        },

        /** 初始化分页 */
        initPagination: function() {
            Pagination.init({
                pageSize: 12,   // 每页显示12条记录
                editBtn: {
                    action: function(id) {
                        list.bindEditAction(id);
                    }
                },
                deleteBtn: {
                    url: ctx + "/menu/permission/delete",
                    callback: function(data) {
                        // 回调函数

                    }
                }
            });
        },

        /** 绑定添加事件 */
        bindAddAction: function() {
            $(".btn-add").on("click", function(event) {
                event.preventDefault();

                $editModal.find(".modal-content").load(ctx + "/menu/permission");

                $editModal.modal("show");
            });
        },

        /**
         * 绑定编辑事件
         */
        bindEditAction: function(id) {
            $editModal.find(".modal-content").load(ctx + "/menu/permission/" + id);

            $editModal.modal("show");
        },

        /** 初始化编辑框 */
        initEditModal: function() {
            var self = this;
            $editModal.on("hide.bs.modal", function() {
                $(this).removeData("bs.modal");
            }).on("shown.bs.modal", function() {
                self.bindFormAction();
            });
        },

        /** 绑定表单事件 */
        bindFormAction: function () {
            var $editForm = $("#editForm");
            var $button = $(".btn-save");

            // 表单验证
            var validate = $editForm.validate({
                rules: {
                    name: {
                        required: true,
                        maxlength: 30
                    },
                    permission: {
                        required: true,
                        maxlength: 30
                    }
                },
                messages: {
                    name: {
                        required: "请输入权限点名称",
                        maxlength: "名称长度不能超过{0}个字符"
                    },
                    permission: {
                        required: "请输入权限点",
                        maxlength: "权限点长度不能超过{0}个字符"
                    }
                },
                submitHandler: function(form) {
                    $.ajax({
                        url: $editForm.attr("action"),
                        method: "POST",
                        data: $editForm.serialize(),
                        beforeSend: function() {
                            $(this).button("loading");
                        },
                        success: function(data) {
                            if (data.code == 0) {
                                $editModal.modal("hide");
                                Pagination.reload();
                            } else {
                                validate.showErrors(data.errors);
                                $button.button("reset");
                            }
                        },
                        error: function() {
                            $button.button("reset");
                        }
                    });
                }
            });

            // 提交编辑表单
            $button.on("click", function() {
                $editForm.submit();
            });

            // 给menuId赋值
            $("#menuId").val(menuId);
        }

    };

    list.init();
});
