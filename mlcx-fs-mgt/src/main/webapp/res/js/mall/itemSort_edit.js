define([],function() {
var itemSortEdit = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//编辑提交
			$("#editItemSort").click(function(){
				itemSortEdit.editItemSort();
			});
			//编辑页面的关闭
			$("#closeEditItemSort").click(function(){ 
				closeTab("商品分类编辑");
			});
			//初始化树	(在这加一个是修复第一次加载时保留上次选择的)				
			itemSortEdit.viewTree("itemSortTreeSelEdit",null,false,true);
			$("#itemSortTreeSelEdit").jstree().uncheck_all();
            $("#itemSortTreeSelEdit").jstree().deselect_all(true);	
		},

		//分类树
		viewTree: function (domId,condition,multiple,visible) {
			var form = $("form[name=itemSortEditFrom]");
			var parentSortNo = form.find("input[name='parentSortNo']").val();
            $("#"+domId).jstree({
                "core": {
                    "animation": 0,
                    "check_callback": true,
                    "themes": { "stripes": true },
                    "multiple" : multiple ,
                    "data" : {
		        				"url" : itemSortEdit.appPath+"/mall/itemSortTree.do?selectedId="+parentSortNo,
		        				"cache" : false,
		        				"data" : function (node) {
		        					return node;
	        				 }
        			 }                   
                },
                "checkbox" : {
                    "keep_selected_style" : false,
                    "three_state" : false,
                    "cascade" :"undetermined",
                    "visible":visible
                 },
                "plugins": [
                    "contextmenu", "dnd", "search",
                    "state", "types", "wholerow","checkbox"
                ]
            });       
        },
		editItemSort:function() {
			var form = $("form[name=itemSortEditFrom]");
			var parentSortNo;
			if(!$("#itemSortTreeSelEdit").jstree().get_selected().length){
				parentSortNo=0;
			}else{
				parentSortNo=$("#itemSortTreeSelEdit").jstree().get_selected()[$("#itemSortTreeSelEdit").jstree().get_selected().length-1];
			}
			form.find("input[name='parentSortNo']").val(parentSortNo);
			form.ajaxSubmit({
				url : itemSortEdit.appPath + "/mall/updateItemSort.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "商品分类编辑成功", function() {
							closeTab("商品分类编辑");
							$("#itemSortList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "商品分类编辑失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='sortNameEdit']").empty();
					if (form.find("input[name='sortName']").val()=="") {
						$("span[name='sortNameEdit']").append("<font color='red'>请输入商品分类名称！</font>");
						return false;
					}
					if (form.find("input[name='sortNo']").val()==form.find("input[name='parentSortNo']").val()) {
						$("span[name='parentSortNoEdit']").append("<font color='red'>父级分类不能是本分类！</font>");
						return false;
					}
				}
			});
		}
}
return itemSortEdit;
})
