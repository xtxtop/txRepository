$(function(){
	require.config({
		paths: {
			"workerOrder":"res/js/workerOrder/workerOrder"
		}
	});

	var model=getAppModel();
	
	
	if(model=="workerOrder"){
		require(["workerOrder"], function (workerOrder){
			workerOrder.init();
		});
	}
	
});


