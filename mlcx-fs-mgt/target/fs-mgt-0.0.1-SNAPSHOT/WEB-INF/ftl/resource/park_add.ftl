<meta charset="utf-8">
<style>
	.daterangepicker .calendar-date {
		border: 1px solid #ddd;
		padding: 4px;
		border-radius: 4px;
		background: #fff;
		display: none;
	}
</style>
<div class="container-fluid backgroundColor">
	<form name="parkAddForm">
		<input name="isPloygon" type="hidden" value='${isPloygon}' />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">新增场站</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>名称：</label>
						</td>
						<td>
							<input class="form-control val" name="parkName" maxlength="20" placeholder="请输入名称"/>
							<span id="parkNameAdd"></span>

						</td>
						<td>
							<label class="  control-label key"><span></span>加盟商：</label>
						</td>
						<td class="btn-btnA-con">
							<input type="hidden" id="franchiseeId" name="franchiseeId" value="" />
							<input type="text" class="form-control val"  placeholder="请输入加盟商" name="franchiseeName" readonly="readonly" />
							<input type="button" class="btn btn-info btn-btnA" id="relateFranchiseeAdd" value="选择">
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key" style="white-space:nowrap;"><span>*</span>所在地区：</label>
						</td>
						<td>
					<div style="width: 100%;display: inline-flex;">
                        <select class="col-sm-4" name="addrRegion1Id" value="${park.addrRegion1Id}" onchange="getResultCityAddPark(this.value)" style="width:90px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;">
                        <#list plist as province>
                        <option <#if park.addrRegion1Id==province.regionId>selected</#if> value="${province.regionId}" >
                        ${province.regionName}
                        </option>
                        </#list>
                        </select>


                        <span id="itemcityAdd">
                        <#if park.addrRegion2Id!=0&&park.addrRegion2Id!=null>
                         <select class="col-sm-4" name="addrRegion2Id" style="width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;" onchange="getResultCountryAddPark(this.value)">
                        <#list plists2 as pr>
                        <option <#if park.addrRegion2Id==pr.regionId>selected</#if> value="${pr.regionId}" >
                        ${pr.regionName}
                        </option>
                        </#list>
                        </select>
                        </#if>
                        </span>

                       <span id="countrycityAdd">
                        <#if park.addrRegion3Id!=0&&park.addrRegion3Id!=null>
                         <select class="col-sm-4" name="addrRegion3Id" style="width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;">
                        <#list plists3 as pr>
                        <option <#if park.addrRegion3Id==pr.regionId>selected</#if> value="${pr.regionId}" >
                        ${pr.regionName}
                        </option>
                        </#list>
                        </select>
                        </#if>
                        </span>
                        
                        <div class=""><span id="addrRegion1IdAdd"></span></div>
                        </div>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
								<option value="">
									请选择
								</option>
								<#list cities as citie>
									<option value="${citie.dataDictItemId}">
										${citie.itemValue}
									</option>
								</#list>
							</select>
							<span id="cityIdAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">片区：</label>
						</td>
						<td>
							<select name="regionId" class="form-control val">
								<option value="">请选择</option>
							</select>
							
						</td>
						<td>
							<label class=" control-label key"><span>*</span>详细街道</label>
						</td>
						<td>
							<input class="form-control val" name="addrStreet"  placeholder="请输入详细街道"/>
							<span id="addrStreetAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>坐标：</label>
						</td>
						<td>
							<input type="hidden" name="ploygonPoints" />
							<input style="width:50px;height:34px;" name="longitude" />N
							<input style="width:50px;height:34px;" name="latitude" />E
							<button type="button" id="searchGC" class="btn btn-default pull-right ">地图选点</button>
							<span id="longitudeAdd"></span><span id="latitudeAdd"></span>
						</td>
						<#if isPloygon??>
							<#if isPloygon=="0">
								<td>
									<label class=" control-label key"><span>*</span>电子围栏半径：</label>
								</td>
								<td>
									<input class="form-control val" name="electronicFenceRadius" maxlength="4"  placeholder="请输入电子围栏半径"/>
									<label>米</label>
									<span id="electronicFenceRadiusAdd"></span>
								</td>
							</#if>
						</#if>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>类别：</label>
						</td>
						<td>
							<select name="parkType" class="form-control val">
								<option value="2">使用类</option>
								<option value="1">管理类</option>
							</select>
							<span id="parkTypeAdd"></span>
						</td>

						<td>
							<label class=" control-label key"><span>*</span>是否开放：</label>
						</td>
						<td>
							<select name="isPublic" class="form-control val">
								<option value="1">开放</option>
								<option value="0">不开放</option>
							</select>
							<span id="isPublicAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">样式：</label>
						</td>
						<td>
							<select name="styleId" class="form-control val">
								<option value="">请选择</option>
								<#list styles as stylep>
									<option value="${stylep.dataDictItemId}">
										${stylep.itemValue}
									</option>
								</#list>
							</select>
							<span id="styleIdAdd"></span>
						</td>
						<td>
							<label class=" control-label key">车位数：</label>
						</td>
						<td>
							<input class="form-control val" name="parkingSpaceNumber" maxlength="4"  placeholder="请输入车位数">
							<span id="parkingSpaceNumberAdd"></span>
						</td>
					</tr>

					<tr>
						<td>
							<label class=" control-label key">服务：</label>
						</td>
						<td>
							<label class="">
                                <input type="checkbox"     class="butcheck" value="快充"> 快充
                                </label>
							<label class="">
                                    <input type="checkbox" class="butcheck" value="慢充"> 慢充
                                </label>
							<label class="">
                                    <input type="checkbox" class="butcheck" value="清洗"> 清洗
                                </label>
							<label class="">
                                    <input type="checkbox" class="butcheck" value="维修"> 维修
                                </label>
							<input type="hidden" name="supportedServices" id="supportedServices">
							<div><span name="supportedServices"></span></div>
						</td>
						<td>
							<label class=" control-label key">所属：</label>
						</td>
						<td>
							<select name="ownerType" class="form-control val">
								<option value="">请选择</option>
								<option value="1">自有</option>
								<option value="2">租用</option>
							</select>
							<span id="ownerTypeAdd"></span>
						</td>
					</tr>
					<tr class="add-car-last-tr">
						<td>
							<label class=" control-label key">所属集团：</label>
						</td>
						<td>
							<div id="parkCompanyRelIds">
								<#list companyList as company>
									<label class="">
                                            <input type="checkbox"  class="butcheck" value="${company.companyId}"> ${company.companyName}
                                        </label>
								</#list>
								<input type="hidden" name="companyIds" id="parkCompanyIds" value="">
							</div>

						</td>
						<td>
							<label class=" control-label key"><span>*</span>图片：</label>
							<input name="parkPicUrl1" type="hidden" />
						</td>
						<td class="odd-td">
						<div class="img-td-upload" id="parkPicUrlImg">
						    <div id="addParkPhotoButton" class="add-img-position">
						    	<h3 class="icon">+</h3>
						    	<h3 class="text">添加图片</h3>
						    </div>
						</div>
						<span id="parkPicUrl1Add"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">场站电费：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="electricPrice" maxlength="4"  placeholder="请输入场站电费">
							<span class="btn-btnA">元/度</span>
							<span id="electricPriceAdd"></span>
						</td>
						<td>
							<label class=" control-label key">场站租金：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="parkRent"  placeholder="请输入场站租金">
							<span class="btn-btnA">元/月</span>
							<span id="parkRentAdd"></span>
						</td>
					</tr>
					<tr>
							<td>
								<label class=" control-label key"><span>*</span>节假日营业：</label>
							</td>
							<td>
								<label><input name="isBusinessFestival" type="radio" value="1" checked/>是 </label>
						<label><input name="isBusinessFestival" type="radio" value="0" />否 </label>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>取车附加费用：</label>
							</td>
							<td class="btn-btnA-con">
								<input class="form-control val" name="serviceFeeGet" maxlength="13" placeholder="0"  placeholder="请输入取车附加费用"/>
					<span class="btn-btnA">元</span>
					<span id="serviceFeeGetAdd"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>还车附加费用：</label>
							</td>
							<td>
								<input class="form-control val"  placeholder="请输入换车附加费用" name="serviceFeeBack" maxlength="13" placeholder="0" />
					<span>元</span>
					<span id="serviceFeeBackAdd"></span>
							</td>
							<td>
								<label class=" control-label key"><span>*</span>营业时间：</label>
							</td>
							<td class="btn-btnA-con">
								<input class="business_hours  form-control val" name="businessHours" readonly="readonly" value="00:00 - 23:59" />
								<button type="button" class="business_hours  form-control val btn-btnA" name="businessHours" style="width:50px;height:35px;">修改</button>
								<span id="businessHoursAdd"></span>
							</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="addPark" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="addclosePark" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<!-- 弹出框 获取坐标地址 操作-->
<div class="modal fade" id="parkAddSearchGCModal" style="height:800px;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">获取坐标</h4>
			</div>
			<div class="modal-body" style="max-height:800px;">
				<form class="form-horizontal" name="parkAddSearchGCForm">
					<div id="searchGCMap">

					</div>
					<div class="form-group" style="margin-top:15px;">
						<label class=" control-label key">要查询的地址：</label>
						<div class="col-sm-6">
							<input class="form-control val" type="text" name="gcAddress">
						</div>
					</div>
					<div class="form-group" style="margin-top:15px;">
						<label class=" control-label key">坐标：</label>
						<div class="col-sm-6">
							<input class="form-control val" name="gcPoint" readonly>
						</div>
						<button type="button" class="btn btn-default " id="searchPoint">查询</button>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default pull-right" data-dismiss="modal">取消</button>
						<input type="button" class="btn btn-default pull-right" id="parkAddSearchGCBtn" value="保存">
					</div>
				</form>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 加盟商 -->
<div class="modal" id="relateFranchiseeModel">
	<div class="modal-dialog" style="width:750px;">
		<div class="modal-content">
			<form name="relateFranchiseeSerachForm">
				<div class="with-border">
					 <div class="title-new-details">选择加盟商</div>
					<!-- /.box-tools -->
					<div class="pull-down-menu-car">
							<div class="parkNo-frombg">
								<span>加盟商编号</span>
								<input class="form-control-input" name="franchiseeNo" id="franchiseeNo" value="">
							</div>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body  box-body-change-padding">
				</div>
				<!-- /.box-body -->
				<!--修改-->
				<div class="box-bullet">
					<div class="box-footer">
						<!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->
						<button type="button" class="btn-new-type" id="relateFranchiseeSearch">确定</button>
						<button type="reset" class="btn-new-type">清空</button>
					</div>
					<!-- /.box-footer -->
				</div>
			</form>
			<div class="border-bullet">
				<!--定义操作列按钮模板-->
				<script id="relateFranchiseeBtnTplAdd" type="text/x-handlebars-template">
					{{#each func}}
					<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
				</script>
				<div class="box">

					<div class="box-header">
						<!-- <h3 class="box-title">数据列</h3> -->
					</div>
					<!-- /.box-header -->
					<div class="box-body box-body-change-padding">
						<table id="relateFranchiseeListAdd" class="table table-bordered table-striped table-hover" width="100%">
						</table>
					</div>
					<!-- /.box-body -->
					<div class="carRedPacketAddParkTool-bullet" id="franchiseeToolssssAdd">
					</div>
				</div>
				<!-- /.box -->
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</div>

<!-- 弹出框-->
<!-- 弹出框 画多边形 操作-->
<div class="modal fade" id="parkAddPloygonPointsModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">获取坐标</h4>
			</div>

			<div class="modal-body" style="max-height:750px;">
				<div id="ploygonPointsMap">

				</div>
				<form class="form-horizontal" name="ploygonPointsSearchForm">
					<div class="form-group" style="margin-top:15px;margin-left:-23px;">
						<label class="col-sm-3 control-label key">要查询的地址：</label>
						<div class="col-sm-5">
							<input class="form-control val" style="margin-left:-35px;" name="gcAddress">
						</div>
						<div style="float:left;margin-left:-55px;" class="col-sm-3"><label class="control-label key">如：深圳市南山区</label></div>
						<button type="button" class="btn btn-default " id="ploygonPointsSearchAddress">查询</button>
					</div>
				</form>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-right" data-dismiss="modal">取消</button>
					<input type="button" class="btn btn-default pull-right" id="parkAddPloygonPointsBtn" value="保存">
					<p style="float:left">结束画图时，双击鼠标左键</p>
					<input type="button" class="btn btn-default pull-right" value="删除多边形" id="deletePloygonPointsBtn" />
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<!-- 弹出框-->
<div class="modal fade" id="parkPhotoAddModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">场站上传图片</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="parkphotoForm">
					<div class="form-group">
						<label class="col-md-3 control-label val">图片URL：</label>
						<div class="col-md-8">
							<input type="text" class="form-control" placeholder="" name="parkPicUrl1" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label val">场站图片：</label>
						<div class="col-md-8">
							<div id="parkFineUploader"><span name="parkErrorInfo"></span></div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					<input type="button" class="btn btn-default pull-right sure" id="addParkPhotoBtn" value="确定">
				</div>
			</div>

		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"parkAdd": "res/js/resource/park_add"
			}
		});
		require(["parkAdd"], function(parkAdd) {
			parkAdd.init();
		});
		var config = new Object();
		config.uploadId = "parkFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "park_photo";
		config.itemLimit = 1;
		config.allowedExtensions = ["jpeg", "jpg", "gif", "png"];
		config.sizeLimit = 500 * 1024;
		config.minSizeLimit = 10 * 1024;
		config.formName = "parkphotoForm";
		//文件回显inputName
		config.inputName = "parkPicUrl1";
		//错误提示span标签name
		config.spanName = "parkErrorInfo";
		require.config({
			paths: {
				"upload": "res/js/resource/uploadFile"
			}
		});
		require(["upload"], function(upload) {
			upload.init(config);
		});
		$(".datetimepicker").datetimepicker({
			language: "zh-CN",
			autoclose: true,
			clearBtn: true, //清除按钮
			todayBtn: true,
			minuteStep: 5,
			format: "yyyy-mm-dd hh:ii:ss" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
		$('.business_hours').daterangepicker({
			timePicker: true,
			format: 'HH:mm',

		});
	});
</script>
<!--加载鼠标绘制工具-->
<script type="text/javascript" src="res/dep/baidu-DrawingManager/DrawingManager_min.js"></script>
<link rel="stylesheet" href="res/dep/baidu-DrawingManager/DrawingManager_min.css" />