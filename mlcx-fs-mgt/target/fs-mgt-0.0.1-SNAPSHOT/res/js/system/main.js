
$(function(){
	require.config({
		/*baseUrl: "../js/lib",*/
		/*paths: {
			"jquery": "../../js/lib/jquery-1.11.3",
			"jstree":"../../dep/jstree/jstree.min",
			"itemcat":"../../js/itemcat/itemcat"
		}*/
		paths: {
			"sysUser":"res/js/system/sysUser",
			"sysRole":"res/js/system/sysRole",
			"sysRegion":"res/js/system/sysRegion",
			"sysPermission":"res/js/system/sysPermission",
			"sysParam":"res/js/system/sysParam",
			"sysOpLog":"res/js/system/sysOpLog",
			"dirtyWord":"res/js/system/dirtyWord",
			"sysOpLogUser":"res/js/system/sysOpLogUser",
			"dataDict":"res/js/system/dataDict",
			"businessParam":"res/js/system/businessParam",
			"operationParam":"res/js/system/operationParam"
		}
	});

	var model=getAppModel();
	
	
	if(model=="sysUser"){
		require(["sysUser","sysRole"], function (sysUser){
			sysUser.init();
		});
	}
	
	if(model=="sysRole"){
		require(['sysRole'], function (sysRole){
			sysRole.init();
		});
	}
	
	if(model=="sysRegion"){
		require(["sysRegion"], function (sysRegion){
			sysRegion.init();
		});
	}
	
	
	if(model=="sysPermission"){
		require(["sysPermission"], function (sysPermission){
			sysPermission.init();
		});
	}
	
	if(model=="sysParam"){
		require(["sysParam"], function (sysParam){
			sysParam.init();
		});
	}
	
	if(model=="sysOpLog"){
		require(["sysOpLog"], function (sysOpLog){
			sysOpLog.init();
		});
	}
	if(model=="dirtyWord"){
		require(["dirtyWord"], function (dirtyWord){
			dirtyWord.init();
		});
	}
	if(model=="sysOpLogUser"){
		require(["sysOpLogUser"], function (sysOpLogUser){
			sysOpLogUser.init();
		});
	}
	if(model=="dataDict"){
		require(["dataDict"], function (dataDict){
			dataDict.init();
		});
	}
	if(model=="businessParam"){
		require(["businessParam"], function (businessParam){
			businessParam.init();
		});
	}
	if(model=="operationParam"){
		require(["operationParam"], function (operationParam){
			operationParam.init();
		});
	}
});


