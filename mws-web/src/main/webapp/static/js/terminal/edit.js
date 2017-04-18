$(function () {
    var $form = $("#form-edit");
    var $submitBtn = $(".btn-submit");

    jQuery.validator.addMethod("ip", function (value, element) {
        if (this.optional(element)) {
            return;
        }
        var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g;
        if (re.test(value)) {
            if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256) {
                return true;
            }
        }
        return false;
    }, "请填写正确的IP地址。");

    var edit = {
        /** 初始化函数 */
        init: function () {
            this.validateForm();

        },
        /** 验证表单字段 */
        validateForm: function () {
            var validator = $form.validate({
                rules: {
                    seatId: {
                        required: true,
                        digits: true
                    },
                    ip: {
                        required: true,
                        ip: true
                    },
                    terminalType: {
                        required: true
                    }
                },
                messages: {
                    seatId: {
                        required: "请输入终端座位号",
                        digits: "请输入正确数字"
                    },
                    ip: {
                        required: "请输入终端IP",
                        ip: "请输入正确的IP地址"
                    },
                    terminalType: {
                        required: "请选择终端类型"
                    }
                },
                submitHandler: function () {
                    var buttonObj = this.submitButton;
                    $(buttonObj).button("loading");
                    $.ajax({
                        url: $form.attr("action"),
                        type: "POST",
                        data: $form.serialize(),
                        dataType: "JSON",
                        success: function (data) {
                            if (data.code == 0) {
                                Dialog.success(data.msg, function () {
                                    if ($(buttonObj).attr("id") == "saveAgain") {
                                        location.href = ctx + "/terminal/list";
                                    } else {
                                        location.href = ctx + "/terminal/list";
                                    }
                                }, 1500);
                            } else {
                                validator.showErrors(data.errors);
                                $submitBtn.button("reset");
                                Dialog.danger(data.msg);
                            }
                        },
                        error: function () {
                            $submitBtn.button("reset");
                        }
                    });
                }
            })
        }
    };

    edit.init();
});
