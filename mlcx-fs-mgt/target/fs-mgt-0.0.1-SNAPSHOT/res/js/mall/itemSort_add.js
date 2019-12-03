define([],function() {
var itemSortAdd = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//新增提交
			$("#addItemSort").click(function(){
				itemSortAdd.saveItemSort();
			});
			//新增页面的关闭
			$("#closeAddItemSort").click(function(){
				closeTab("新增商品分类");
			});
			//初始化树	(在这加一个是修复第一次加载时保留上次选择的)				
			itemSortAdd.viewTree("itemSortTreeSelAdd",null,false,true);
			$("#itemSortTreeSelAdd").jstree().uncheck_all();
            $("#itemSortTreeSelAdd").jstree().deselect_all(true);	
		},

		//分类树
		viewTree: function (domId,condition,multiple,visible) {
            $("#"+domId).jstree({
                "core": {
                    "animation": 0,
                    "check_callback": true,
                    "themes": { "stripes": true },
                    "multiple" : multiple ,
                    "data" : {
		        				"url" : itemSortAdd.appPath+"/mall/itemSortTree.do",
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
		saveItemSort:function() {
			var form = $("form[name=itemSortAddFrom]");
			var parentSortNo;
			if(!$("#itemSortTreeSelAdd").jstree().get_selected().length){
				parentSortNo=0;
			}else{
				parentSortNo=$("#itemSortTreeSelAdd").jstree().get_selected()[$("#itemSortTreeSelAdd").jstree().get_selected().length-1];
			}
			form.find("input[name='parentSortNo']").val(parentSortNo);
			form.ajaxSubmit({
			url : itemSortAdd.appPath + "/mall/addItemSort.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					$("#itemSortTreeSelAdd").jstree().refresh();
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "商品分类添加成功", function() {						
						closeTab("新增商品分类");
						$("#itemSortList").DataTable().ajax.reload(function(){
						});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "商品分类添加失败！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("span[name='sortNameAdd']").empty();
				if (form.find("input[name='sortName']").val()=="") {
					$("span[name='sortNameAdd']").append("<font color='red'>请输入商品分类名称！</font>");
					return false;
				}
			}
		});
		}
}
return itemSortAdd;
})
