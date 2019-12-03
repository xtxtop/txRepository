/**
 * 注入bootstrap 样式 2016/4/20
 */

$.extend($.validator.defaults, {
	errorElement: "em",
	errorPlacement: function(error, element){
		error.addClass("help-block" );
		element.parent().addClass( "has-feedback");
		if(element.prop("type") === "checkbox"){
			error.insertAfter(element.parent("label"));
		}else{
			error.insertAfter(element);
		}
		if(!element.next("span")[0]){
			$("<span class='glyphicon glyphicon-remove form-control-feedback'></span>").insertAfter(element);
		}
	},
	success: function(label, element){
		if(!$(element).next("span")[0]){
			$("<span class='glyphicon glyphicon-ok form-control-feedback'></span>").insertAfter($(element));
		}
	},
	highlight: function(element, errorClass, validClass){
		$(element).parent().addClass("has-error").removeClass("has-success");
		$(element).next("span").addClass("glyphicon-remove").removeClass("glyphicon-ok");
	},
	unhighlight: function(element, errorClass, validClass) {
		$(element).parent().addClass("has-success").removeClass("has-error");
		$(element).next("span").addClass("glyphicon-ok").removeClass("glyphicon-remove");
	}
});

/*$.extend($.validator.classRuleSettings,{
		required:function(){
            $(this).val($.trim($(this).val()));
            return true;
        }
})*/

$.validator.prototype.resetForm = function(form){
		if($.fn.resetForm) {
		   //$( this.currentForm ).resetForm();
		}
		this.invalid = {};
		this.submitted = {};
		this.prepareForm();
		this.hideErrors();
		var elements = this.elements()
			.removeData( "previousValue" )
			.removeAttr( "aria-invalid" );
		
		this.resetElements( elements );
		$(form).find(".has-success").removeClass("has-success");
		$(form).find(".glyphicon-ok").removeClass("glyphicon-ok");
		$(form).find(".has-error").removeClass("has-error");
}

$.validator.prototype.resetElements = function(elements) {
	var i;
	if (this.settings.unhighlight) {
		for(i = 0; elements[i]; i++){
			this.settings.unhighlight.call(this, elements[i],this.settings.errorClass, "");
			this.findByName(elements[i].name).removeClass(this.settings.validClass);
			this.findByName(elements[i].name).parent().removeClass("has-success");
			this.findByName(elements[i].name).next("span").removeClass("glyphicon-ok");
		}
	} else {
		elements.removeClass(this.settings.errorClass).removeClass(this.settings.validClass);
		
	}
}

$.fn.resetForm = function() {
    return this.each(function() {
        // guard against an input with the name of 'reset'
        // note that IE reports the reset function as an 'object'
        if (typeof this.reset == 'function' || (typeof this.reset == 'object' && !this.reset.nodeType)) {            
            var validator = $.data(this, "validator" );
            if(validator){
                validator.resetForm(this);
            }
            this.reset();
        }
    });
};

/**
 * 
 *  使用说明  tel: {//字段名称 电话
 *             required: true,
 *             telphone: true
 *          },
 * 
 */

//校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-” 
$.validator.addMethod("telphone",function(s){ 
	//var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/; 
	var patrn=/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
	if(!patrn.exec(s)) return false; 
	return true; 
},"请输入有效的电话号码")
//校验手机号码：必须以数字开头，除数字外，可含有“-” 
$.validator.addMethod("mobile",function(s){ 
	//var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/; 
	var patrn=/^0?(13[0-9]|15[012356789]|18[0236789]|14[57]|177)[0-9]{8}$/;
	if(!patrn.exec(s)) return false; 
	return true; 
},"请输入有效的手机号码")
//校验IP
$.validator.addMethod("ip",function(s){
	var patrn=/^[0-9.]{1,20}$/; 
	if(!patrn.exec(s)) return false;
	return true; 
},"请输入有效的IP地址")
//校验身份证
$.validator.addMethod("idCard",function(s){
	var patrn = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	if(!patrn.exec(s)) return false; 
	return true; 
},"请输入有效的身份证号")
//校验英文字符和数字
$.validator.addMethod("varchar",function(s){ 
	var patrn=/^[0-9a-zA-Z]*$/; 
	if(!patrn.exec(s)) return false; 
	return true; 
},"请输入有效的字符！")

//是否正整数
$.validator.addMethod("positiveInteger",function(s){ 
	var patrn=/^[0-9]*[1-9][0-9]*$/; 
	if(!patrn.exec(s)) return false; 
	return true; 
},"请输入有效的正整数！")
