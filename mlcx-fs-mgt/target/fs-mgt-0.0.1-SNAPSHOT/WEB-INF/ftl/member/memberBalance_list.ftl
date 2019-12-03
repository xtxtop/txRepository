<meta charset="utf-8">
<style>
	.seach-input-item pull-left .form-control {
		width: 50% !important;
	}
	
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30%;
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
			<form class="form-horizontal" name="memberBalanceSearchForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details seach-input-details-change close-content clearfix">
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>会员编号</span><input class="form-control" name="memberNo" placeholder="请输入会员编号">
						</div>
						<div class="seach-input-item seach-input-item-change pull-left">
							<span>冻结状态</span>
							<select class="form-control" name="isFreeze">
								<option value="">全部</option>
								<option value="0">未冻结</option>
								<option value="1">已冻结</option>
							</select>
						</div>
					</div>
					<!-- /.box-body -->
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="sysRoleSearchafter">确定</button>
					</div>
				</div>
			</form>
			<!-- /.box-footer -->
		</div>
		<!-- /.box -->
	</div>
	<!-- /.col -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="memberBalanceTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
			</script>
			<div class="box">
				<div class="box-body">
					<table id="memberBalanceList" class="table table-bettween-far table-hover" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</div>
<!-- /.container-fluid -->
<!--
    <script type="text/javascript" src="${basePath!'' }/res/js/member/main.js"></script>
    -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"memberBalance": "res/js/member/memberBalance_list"
			}
		});
		require(["memberBalance"], function(memberBalance) {
			memberBalance.init();
		});
	});
</script>