define([],function() {	
	var advert={
			init: function () {	
				var typeView=$("#typeView").val();
				var advertTypeView=$("#advertTypeView").val();
				var advertPositionView=$("#advertPositionView").val();
				$("#type_view").html(initSelectView(typeView,type_advert));
				if(typeView==1){
					$("#advert_view_tr").hide();
					$("#advertPosition_view").html(initSelectView(advertPositionView,advertPosition_o))
				}else if(typeView==2||typeView==3){
					$("#advertType_view").html(initSelectView(advertTypeView,advertPosition_t));
					if(advertTypeView==1){
						$("#advertPosition_view").html(initSelectView(advertPositionView,advertPosition_t_o))
					}else if(advertTypeView==2){
						$("#advertPosition_view").html(initSelectView(advertPositionView,advertPosition_t_t))
					}else if(advertTypeView==3){
						$("#advertPosition_view").html(initSelectView(advertPositionView,advertPosition_t_s))
					}else if(advertTypeView==4){
						$("#advertPosition_view").html(initSelectView(advertPositionView,advertPosition_t_f))
					}else if(advertTypeView==5){
						$("#advertPicUrlViewMengLong").hide();
						$("#advertPosition_view").html(initSelectView(advertPositionView,advertPosition_t_fv))
					}else if(advertTypeView==6){
						$("#advertPosition_view").html(initSelectView(advertPositionView,advertPosition_t_sx))
					}
				}else if(typeView==16){
					$("#advert_view_tr").hide();
					$("#advertPosition_view").html(initSelectView(advertPositionView,advertPosition_six));
				}else if(typeView==17){
					$("#advert_view_tr").hide();
					$("#advertPosition_view").html(initSelectView(advertPositionView,advertPosition_se));
				}else{
					$("#advert_view_tr").hide();
					$("#advertPosition_view").html(initSelectView(advertPositionView,advertPosition_f));
				}
				
			},
	    };
	return advert;
});

//初始选择
function initSelectView(s,x){
	 for(var i=0;i<x.length;i++){
			if(s==(i+1)){
			return x[i];
			}
		}
}
