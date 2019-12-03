
$(function(){
	require.config({
		/*baseUrl: "../js/lib",*/
		/*paths: {
			"jquery": "../../js/lib/jquery-1.11.3",
			"jstree":"../../dep/jstree/jstree.min",
			"itemcat":"../../js/itemcat/itemcat"
		}*/
		paths: {
			"statisticsDeals":"res/js/statistics/statisticsDeals"
		}
	});

	var model=getAppModel();
	
	if(model=="statisticsDeals"){
		require(["statisticsDeals"], function (statisticsDeals){
			statisticsDeals.init();
		});
	}
});


