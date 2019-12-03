<meta charset="utf-8">
<div class="container-fluid">
		<!-- /.box-header -->
		<form class="form-horizontal" name="parkMonitorSerachForm">
				<div class="clearfix">
					<div class="seach-input-item-change pull-left">
						<span>场站名称：</span>
						<input class="input-select-style" name="parkName" placeholder="输入场站名称">
					</div>
					<div class="seach-input-item-change pull-left">
						<span>状态：</span>
						<select class="input-select-style" name="isAvailable">
							<option value="">全部</option>
							<option value="1">启用</option>
							<option value="0">停用</option>
						</select>
					</div>
					<div class="seach-input-item-change pull-left">
						<span>是否可见：</span>
						<select class="input-select-style" name="isView">
							<option value="">全部</option>
							<option value="1">可见</option>
							<option value="0">隐藏</option>
						</select>
					</div>
				</div>
			<!-- /.box-body -->
				<div class="box-footer" style="float: right;margin-top: -40px;">

					<button type="button" class="btn-new-type" id="parkMonitorSearch">
                                                         确定
                    </button>
					<button type="reset" class="btn-new-type">
                                                         清空
                    </button>
				</div>
		</form>
		<!-- /.box-footer -->
	<div id="park-map"></div>
</div>
<!-- /.container-fluid -->
<style type="text/css">
	#park-map {
		width: 100%;
		overflow: hidden;
		border:1px solid #dedede;
	}
	
	.carInfro {
		width: 97%;
		margin: 0 auto;
		list-style: none;
		overflow: hidden;
		padding-left: 5px;
	}
	
	.carInfro li {
		margin-right: 10px;
		height: 25px;
		line-height: 25px;
		font-size: 14px;
	}
	
	.carInfroFull {
		width: 100%;
		margin: 0 auto;
		list-style: none;
		overflow: hidden;
		padding-left: 10px;
	}
	
	.carInfroFull h3 {
		margin-right: 10px;
		height: 25px;
		line-height: 35px;
		font-size: 30px;
	}
	
	.carInfroFull li {
		margin-right: 10px;
		height: 25px;
		line-height: 30px;
		font-size: 25px;
		margin-bottom: 40px;
	}
	
	.buttonFull {
		font-size: 18px;
		height: "50px";
		width: "100px";
	}
	/*全屏样式*/
	
	.fillScree {
		position: absolute!important;
		top: 0;
		left: 0;
		z-index: 10000!important;
	}
	.input-select-style{
	border: 1px solid #eee;
    width: 150px;
    padding: 7px;
    margin-right: 70px;
    border-radius:3px;
    }
    .input-select-style:first-child{
    margin-right: 0;
    }
    .container-fluid {
    padding-right: 0px;
    }
</style>
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"parkMonitor": "res/js/monitor/park_monitor"
			}
		});
		require(["parkMonitor"], function(parkMonitor) {
			parkMonitor.init();
		});
	});
</script>