jQuery.validator.addMethod("isPhone", function(value, element) {
	var tel = /^(13[0-9]|15\d{1}|17\d{1}|18\d{1})\d{8}$/;
	return this.optional(element) || (tel.test(value));
}, "请输入正确的手机号码");


jQuery.validator.addMethod("isCard", function(value, element) {
	var tel = /^51\d{9}$/;
	return this.optional(element) || (tel.test(value));
}, "请输入正确的11位会员卡号");