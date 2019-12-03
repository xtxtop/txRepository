define([],function() {	
	var operationParam={
		appPath:getPath("app"),	
		init: function () {	
			operationParam.initData();
		    $(".datepicker").datetimepicker({
	            language: "zh-CN",
	            autoclose: true,
	            todayBtn: true,
	            minuteStep: 5,
	            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	        });
			$("#operationParamEditBtn").click(function(){
				operationParam.edit();
            });		
		},
		initData: function () {
    		$.ajax({
    			url:operationParam.appPath+"/operationParam/getParamList.do",
    			type:"post",
    			data:{paramGroup:2},
    			dataType:"json",
    			success:function(result){ 
    				var paramType = $("#operationParamType").val();
    				var navHtml = "";
    				var list = result.data;
    				var tabIndex = 0;
    				var tabNames = new Array();
    				for(var i = 0; i < list.length; i++){
    					var o = list[i];
    					var flag = false;
    					for(var j = 0; j < tabNames.length; j++){
        					var tabName = tabNames[j];
        					if(tabName == o.paramType){
        						flag = true;
        					}
    					}
    					if(flag){
    						continue;
    					}
    					tabNames[tabIndex] = o.paramType;
    					if((paramType == "" && i == 0) || paramType == o.paramType){
        					navHtml += "<li  class=\"active\"><a href=\"#operationParamTab"+tabIndex+"\" data-toggle=\"tab\">"+o.paramType+"</a></li>";
    					}else{
        					navHtml += "<li><a href=\"#operationParamTab"+tabIndex+"\" data-toggle=\"tab\">"+o.paramType+"</a></li>";
    					}
    					tabIndex++;
    				}
    				$("#operationParamTabs").html(navHtml);
    				var contentHtml = "";
					for(var i = 0; i < tabNames.length; i++){
    					var tabName = tabNames[i];
    					var active = "";
    					if((paramType == "" && i == 0) || paramType == tabName){
    						active = "active";
    					}
        				var itemHtml = "<div class=\"col-md-12 form-horizontal\">";
						itemHtml += "<div class=\"form-group col-md-12\">";
						itemHtml += "</div>";
    					for(var j = 0; j < list.length; j++){
        					var o = list[j];
        					if(tabName == o.paramType){
        						itemHtml += "<div class=\"form-group col-md-12\">";
        						itemHtml += "<label class=\"col-sm-6 control-label key\">"+o.paramName+"<span title="+o.memo+">?</span></br>["+o.paramKey+"]</label>";
        						itemHtml += "<div class=\"col-sm-6\">";
        						itemHtml += "<label class=\"control-label val\">";
        						var value = o.paramValue;
        						if(o.paramValueJson != null && o.paramValueJson.replace(/^\s+|\s+$/gm,'') != ""){
        							var array = eval(o.paramValueJson);
        							if(o.paramValueMode == "checkbox"){
    									value = "";
        								var valueArray = o.paramValue.split(",");
            							for(var n = 0; n < array.length; n++){
	        								for(var m = 0; m < valueArray.length; m++){
	        									if(array[n].value == valueArray[m]){
	            									value += ","+array[n].name;
	            									break;
	        									}
	        								}
            							}
        								if(value.length > 1){
            								value = value.substring(1,value.length);
        								}
        							}else{
            							for(var n = 0; n < array.length; n++){
            								if(array[n].value == value){
            									value = array[n].name;
            								}
            							}
        							}
        						}
        						itemHtml += value;
        						itemHtml += "<span class=\"glyphicon\" onclick=\"editOperationParam('"+operationParam.appPath+"','"+o.paramId+"')\" style=\"cursor: pointer;\">修改</span>";
		       					itemHtml += "</label>";
		       					itemHtml += "</div>";
		       					itemHtml += "</div>";
        					}
    					}
       					itemHtml += "</div>";
    					contentHtml += "<div role=\"tabpanel\" class=\"tab-pane "+active+"\" id=\"operationParamTab"+i+"\">"+itemHtml+"</div>"; 
					}
    				$("#operationParamContent").html(contentHtml);
    			}
    		});
        },
        edit: function () {        	
        	var form = $("form[name=operationParamForm]");
        	var paramValue = form.find("input[name=paramValue]").val();
        	var paramId = form.find("input[name=paramId]").val();
        	if(paramValue == ""){
        		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入参数值！");
		        return false;
        	}
        	if(form.find("input[type=checkbox]").val() != undefined){
        		var flag = false;
        		form.find("input[type=checkbox]").each(function(i){
                    if($(this).is(':checked') == true){
                    	flag = true;
                    }
                });
        		if(!flag){
            		bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择参数值！");
    		        return;
        		}
        	}
        	var paramValueType = form.find("input[name=paramValueType]").val();
        	if(paramValueType == "INTEGER"){
        		if(paramValue % 1 != 0){
                	bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入整数！");
       		        return;
        		}
        	}
        	else if(paramValueType == "POSITIVE_INTEGER"){
        	    var re = /^[0-9]+$/;
        	    if(!re.test(paramValue)){
        	       bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入正整数！");
    		       return;
        	    }
        	}
        	else if(paramValueType == "CRON"){
        	    if(!CronExpressionValidator.validateCronExpression(paramValue)){
        	       bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入正确的CRON表达式！");
    		       return;
        	    }
        	}
			form.ajaxSubmit({
				url : operationParam.appPath+"/operationParam/updateParamValue.do",
				type : "post",
				success : function(data) {
					if (data.code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
							$("#operationParamModalEdit").modal("hide");
							operationParam.initData();
						});							
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + data.msg);
					}
				},
    		});
		}
    };
	return operationParam;
});


function editOperationParam(path, id){
	$.ajax({
		url:path+"/operationParam/getParam.do",
		type:"post",
		data:{paramId:id},
		dataType:"json",
		success:function(result){ 
			var o = result.data;
			$("#operationParamType").val(o.paramType);
			$("#operationParamId").val(o.paramId);
			$("#operationParamValueType").val(o.paramValueType);
			$("#operationParamName").html(o.paramName);
			var valueHtml = "";
			if(o.paramValueMode == "text"){
			    valueHtml = "<input type=\"text\" maxlength=\"500\" class=\"form-control val\" name=\"paramValue\" value=\""+o.paramValue+"\">";				
			}else if(o.paramValueMode == "radio"){
				if(o.paramValueJson != null && o.paramValueJson.replace(/^\s+|\s+$/gm,'') != ""){
					var array = eval(o.paramValueJson);
					for(var n = 0; n < array.length; n++){
						var checked = "";
						if(array[n].value == o.paramValue){
							checked = "checked";
						}
						valueHtml += "<input type=\"radio\" name=\"paramValue\" value=\""+array[n].value+"\" "+checked+">"+array[n].name+"</input>&nbsp;&nbsp;";
					}
				}
			}else if(o.paramValueMode == "checkbox"){
				if(o.paramValueJson != null && o.paramValueJson.replace(/^\s+|\s+$/gm,'') != ""){
					var array = eval(o.paramValueJson);
					for(var n = 0; n < array.length; n++){
						var checked = "";
						var valueArray = o.paramValue.split(",");
						for(var m = 0; m < valueArray.length; m++){
							if(array[n].value == valueArray[m]){
								checked = "checked";
								break;
							}
						}
						valueHtml += "<input type=\"checkbox\" name=\"paramValue\" value=\""+array[n].value+"\" "+checked+"/>"+array[n].name+"&nbsp;&nbsp;";
					}
				}
			}else if(o.paramValueMode == "select"){
				if(o.paramValueJson != null && o.paramValueJson.replace(/^\s+|\s+$/gm,'') != ""){
					var array = eval(o.paramValueJson);
					valueHtml += "<select name=\"paramValue\" class=\"form-control val\">";
					for(var n = 0; n < array.length; n++){
						var selected = "";
						if(array[n].value == o.paramValue){
							selected = "selected";
						}
						valueHtml += "<option value=\""+array[n].value+"\" "+selected+">";
						valueHtml += array[n].name;
                        valueHtml += "</option>";
					}
                    valueHtml += "</select>";
				}
			}else if(o.paramValueMode == "date"){				
				valueHtml = "<input class=\"datepicker form-control val\" name=\"paramValue\" id=\"operationParamValue\" readonly=\"readonly\" value=\""+o.paramValue+"\"/>";
			}
			$("#operationParamValueDiv").html(valueHtml);
			$("#operationParamModalEdit").modal("show");
			if(o.paramValueMode == "date"){
				$(".datepicker").datetimepicker({
		            language: "zh-CN",
		            minView: "month",
		            autoclose: true,
		            todayBtn: true,
		            minuteStep: 5,
		            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		        });
			}
		}
	});
}

