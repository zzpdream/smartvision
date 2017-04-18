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
                        maxlength: 45
                    },
                    url: {
                        maxlength: 100,
                        checkUrl: true
                    },
                    remark: {
                        maxlength: 500
                    },
                    icon: {
                        checkIcon: true
                    }
                },
                messages: {
                    name: {
                        required: "请输入菜单名称",
                        maxlength: "菜单名称长度不能超过{0}个字符"
                    },
                    url: {
                        maxlength: "链接地址长度不能超过{0}个字符"
                    },
                    remark: {
                        maxlength: "菜单备注说明不能超过{0}个字符"
                    }
                },
                submitHandler: function() {
                    $submitBtn.button("loading");
                    $.ajax({
                        url: $form.attr("action"),
                        type: "POST",
                        data: $form.serialize(),
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
        }
    };

    edit.init();
});