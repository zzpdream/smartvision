var mapObj;
var marker = [];
var windowsArr = [];
$(function() {

    var $form = $("#form-edit");
    var $submitBtn = $(".btn-submit");

    var edit = {

        /** 初始化函数 */
        init: function() {
            this.bindSortSlider();
            this.validateForm();
           
        },

        /** 绑定排序值滑动效果 */
        bindSortSlider: function() {
            $("[data-ui-slider]").slider();
        },

        /** 验证表单字段 */
        validateForm: function() {
            var validator = $form.validate({
                rules: {
                	
                	appVersion: {
                        required: true,
                        maxlength: 100
                    },
                    versionCode:{
                    	required: true,
                    	digits: true,
                        maxlength: 11
                    }
                },
                messages: {
                	
                	appVersion: {
                        required: "请输入版本号",
                        maxlength: "版本号长度不能超过{0}个字符"
                    },
                    versionCode:{
                    	required: "请输入最新升级软件的版本号",
                    	digits:"请输入正确数字",
                        maxlength: "长度不能超过{0}个字符"
                    }
                },
                submitHandler: function() {
                	var buttonObj = this.submitButton;
                	$(buttonObj).button("loading");
                    $.ajax({
                        url: $form.attr("action"),
                        type: "POST",
                        data: $form.serialize(),
                        dataType: "JSON",
                        success: function(data) {
                            if (data.code == 0) {
                                Dialog.success(data.msg, function() {
                                	if($(buttonObj).attr("id") == "saveAgain"){
                                		location.href = ctx + "/appUpgrade";
                                	}
                                	else{
                                		location.href = ctx + "/appUpgrades";
                                	}
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

    $('.btn-submit').click(function(){
    	var url = "";
    	var list=$(".filename");
    	if(list.length > 0){
    		var index=$(".filename").attr("href").indexOf("/download");
    		url =  $(".filename").attr("href").substring(index,$(".filename").attr("href").length) ;	
    	}
		$("#url").attr("value",url);
		return true;
	});  
});
