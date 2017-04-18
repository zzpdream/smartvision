/**
 * Created by whan on 9/29/15.
 */
$(function() {

    var $loginForm = $("#form-login");
    var $loginBtn  = $("#btn-login");


    var login = {

        init: function() {
            this.validateLoginParam();
        },

        validateLoginParam: function() {
            $loginForm.validate({
                rules: {
                    username: {
                        required: true
                    },
                    password: {
                        required: true
                    }
                },
                messages: {
                    username: {
                        required: "请输入登陆账号"
                    },
                    password: {
                        required: "请输入登陆密码"
                    }
                },
                submitHandler: function() {
                    $loginBtn.button("loading");
                    $.ajax({
                        url: ctx + '/login',
                        method: "POST",
                        data: $loginForm.serialize(),
                        success: function(data) {
                            if (data.code == 0) {
                                location.href = data.redirectUrl;
                            } else {
                                Dialog.danger(data.msg);
                                $loginBtn.button("reset");
                            }
                        }
                    });
                }
            });
        }
    };

    login.init();
});