$(function() {

    var $form         = $("#form-edit");
    var $submitBtn    = $(".btn-submit");
    var $parentMenuId = $("#parentMenuId");

    var edit = {

        /** 初始化函数 */
        init: function() {
            this.bindSortSlider();
            this.validateForm();
            this.addValidateMethods();
            this.bindTreeAction();
        },

        /** 绑定排序值滑动效果 */
        bindSortSlider: function() {
            $("[data-ui-slider]").slider();
        },

        /** 添加验证规则 */
        addValidateMethods: function() {
            // 一级菜单图标不能为空
            jQuery.validator.addMethod('checkIcon', function(value, element) {
                var parentMenuId = $parentMenuId.val();
                return parentMenuId != "" || value != "";
            }, "请输入菜单图标");

            // 二级菜单的地址不能为空
            jQuery.validator.addMethod('checkUrl', function(value, element) {
                var parentMenuId = $parentMenuId.val();
                return parentMenuId == "" || value != "";
            }, "请输入菜单地址");
        },

        /** 验证表单字段 */
        validateForm: function() {
            var validator = $form.validate({
                rules: {
                    name: {
                        required: true,
                        maxlength: 30
                    }
                },
                messages: {
                    name: {
                        required: "请输入菜单名称",
                        maxlength: "菜单名称长度不能超过{0}个字符"
                    }
                },
                submitHandler: function() {
                    $submitBtn.button("loading");
                    $.ajax({
                        url: $form.attr("action"),
                        type: "POST",
                        data: $form.serialize() + "&" + $.param(edit.getMenuIds()) + "&" + $.param(edit.getPermissionIds()),
                        dataType: "JSON",
                        success: function(data) {
                            if (data.code == 0) {
                                Dialog.success(data.msg, function() {
                                    location.href = $(".btn-back").attr("href");
                                }, 1500);
                            } else {
                                validator.showErrors(data.errors);
                                $submitBtn.button("reset");
                                Dialog.danger(data.msg);
                            }
                        }
                    });

                }
            })
        },

        /** 获取菜单编号 */
        getMenuIds: function() {
            var params = [];

            $(".menu:checked").each(function(i) {
                params.push({name: "menus["+i+"].id", value: this.value});
            });

            return params;
        },

        /** 获取权限点编号 */
        getPermissionIds: function() {
            var params = [];

            $(".permission:checked").each(function(i) {
                params.push({name: "permissions["+i+"].id", value: this.value});
            });

            return params;
        },


        /** 绑定树 */
        bindTreeAction: function() {
            $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
            $('.tree li.parent_li > span').on('click', function (e) {
                var children = $(this).parent('li.parent_li').find(' > ul > li');
                if (children.is(":visible")) {
                    children.hide('fast');
                    $(this).attr('title', 'Expand this branch').find(' > i').addClass('fa-plus').removeClass('fa-minus');
                } else {
                    children.show('fast');
                    $(this).attr('title', 'Collapse this branch').find(' > i').addClass('fa-minus').removeClass('fa-plus');
                }
                e.stopPropagation();
            });

            // 点击菜单自动勾选权限点
            $(".menu").on("click", function() {
                $(this).parent().parent().siblings(".checkbox-inline").find(".permission").prop("checked", this.checked);
            });

            $(".permission").on("click", function() {
                // 获取菜单
                var $parentMenu = $(this).parent().parent().siblings(".checkbox-menu").find(".menu");

                if (this.checked) {
                    // 选中操作
                    $parentMenu.prop("checked", true);
                } else {
                    // 不选中
                    var unchecked = true;
                    $(this).parent().parent().siblings(".checkbox-permission").find(".permission").each(function() {
                        if (this.checked) {
                            unchecked = false;
                        }
                    });

                    if (unchecked) {
                        $parentMenu.prop("checked", false);
                    }
                }
            });
        }
    };

    edit.init();
});