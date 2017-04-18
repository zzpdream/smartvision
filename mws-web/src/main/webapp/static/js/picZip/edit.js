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
                	
                	title: {
                        required: true,
                        maxlength: 100
                    },
                    remark:{
                    	 required: true,
                         maxlength: 100
                    }
                },
                messages: {
                	
                	title: {
                        required: "请输入标题",
                        maxlength: "标题长度不能超过{0}个字符"
                    },
                    remark:{
                    	required: "请输入标题",
                        maxlength: "标题长度不能超过{0}个字符"
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
                                		location.href = ctx + "/picZip";
                                	}
                                	else{
                                		location.href = ctx + "/picZips";
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
    		var index=$(".filename").attr("href").indexOf("/picZip");
    		url =  $(".filename").attr("href").substring(index,$(".filename").attr("href").length) ;	
    	}
		$("#url").attr("value",url);
		return true;
	});  
});
