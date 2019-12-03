<meta charset="utf-8">
<style>
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30% !important;
		}
		.seach-input-item pull-left .form-control {
			width: 45% !important;
			margin-right: 20%;
		}
		.pull-down-menu span {
			width: 20%;
		}
	}
	
	@media only screen and (max-width:768px) {
		.row .sorting_disabled {
			font-size: 1.1rem !important;
		}
	}
	
	@media only screen and (min-width:768px) and (max-width:1024px) {
		.row .sorting_disabled {
			font-size: 1.2rem !important;
		}
	}
	
	@media only screen and (min-width:1024px) and (max-width:1366px) {
		.row .sorting_disabled {
			font-size: 1.3rem !important;
		}
	}
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
			<!--<span class="pull-right moreButton" id="moreCarList">更多</span>-->
		</div>
		<div class="search-input-content">

			<!-- /.box-header -->
			<form class="form-horizontal" name="chargerGunSearchForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details seach-input-details-change close-content clearfix">
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>充电枪编码</span>
							<input type="text" class="form-control" id="chargingGunCode" name="chargingGunCode" placeholder="请输入充电枪编码">
						</div>
						  <div class="seach-input-item pull-left">
                            <span>充电站</span>
                            <select class="form-control" name="chargingStationNo">
                                <option value="">--全部--</option>
                                <#list station as s>
                                <#if s.isAvailable==1>
                                <option value="${s.stationNo }">${s.stationName }</option>
                                </#if>
                                </#list>
                            </select>
                        </div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>工作状态</span>
							<select class="form-control" name="workStatus">
								<option value="">--全部--</option>
								<option value="1">告警</option>
								<option value="2">待机</option>
								<option value="3">工作</option>
								<option value="4">离线</option>
								<option value="5">完成</option>
								<option value="6">正在操作充电桩</option>
								<option value="7">预约中</option>
							</select>
						</div>
						<input type="hidden" value="${parkNo}" name="parkNo" />
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="chargerGunSearchBt">确定</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="chargerTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="chargingGunList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</div>
</div>
<!-- /.modal -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"chargerGun": "res/js/ml/chargerGun/chargerGun_list"
			}
		});
		require(["chargerGun"], function(chargerGun) {
			chargerGun.init();
		});
	});
</script>