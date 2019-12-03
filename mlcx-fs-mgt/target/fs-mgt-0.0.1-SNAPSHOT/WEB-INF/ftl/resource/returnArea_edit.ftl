<meta charset="utf-8">
<div class="container-fluid backgroundColor">
	<form name="returnAreaEditForm">
		<input name="isPloygon" type="hidden" value='${isPloygon}'/>
						<input name="returnAreaId" type="hidden" value='${returnArea.returnAreaId}'/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">还车区域编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;名称：</label>
						</td>
						<td>
							<input class="form-control val" name="areaName" value="${returnArea.areaName}" maxlength="20"/>
							<span id="returnAreaNameEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
								 <#list cities as citie>
									 <option <#if returnArea.cityId==citie.dataDictItemId>
									 selected
									 </#if> value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>
								</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>&nbsp;&nbsp;坐标：</label>
						</td>
						<td>
							<input type="hidden" name="ploygonPoints" value="${returnArea.ploygonPoints}"/>
								<input   style="width:50px;height:34px;"  name="longitude" value="${returnArea.longitude}"/>N
								<input   style="width:50px;height:34px;"  name="latitude"  value="${returnArea.latitude}"/>E
								<button type="button" id="searchGCEditArea" class="btn btn-default pull-right " >地图选点</button>
                                <span id="longitudeEdit"></span><span id="latitudeEdit"></span>
						</td>
						<td>
						</td>
						<td>
						</td>
					</tr>
					
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="savereturnArea" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closereturnArea" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<!-- 弹出框地图选址操作-->
    <div class="modal fade" id="returnAreaEditSearchGCModal" style="height:100%;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">获取坐标</h4>
                </div>
                <div class="modal-body" style="max-height:height:100%;">
                       <form class="form-horizontal" name="returnAreaEditSearchGCForm">
                     <div id="searchGCMapEdit">

                        </div>
                        <div class="form-group" style="margin-top:15px;">
                            <label class="col-sm-3 control-label">&nbsp;&nbsp;要查询的地址：</label>
							<div class="col-sm-6">
							 <input class="form-control" type="text" name="gcAddress" >
							</div>
                        </div>
                        <div class="form-group" style="margin-top:15px;">
                            <label class="col-sm-3 control-label">&nbsp;&nbsp;坐标：</label>
							<div class="col-sm-6">
							 <input class="form-control" name="gcPoint" readonly>
							</div>
							<button type="button" class="btn btn-default " id="searchPointEdit">查询</button>
                        </div>
                        <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal">取消</button>
                       <input type="button" class="btn btn-default pull-right" id="returnAreaEditSearchGCBtn" value="保存">
                   </div>
                   </form>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
 <!-- 弹出框 画多边形 操作-->
    <div class="modal fade" id="returnAreaEditPloygonPointsModal" style="height:600px;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">获取坐标</h4>
                </div>
                <div class="modal-body" style="max-height:800px;">
                     	<div id="ploygonPointsMapEditArea">
                        </div>
                        <form class="form-horizontal"  name="ploygonPointsSearchEditForm">
		                    <div class="form-group" style="margin-top:15px;">
			                    <label class="col-sm-3 control-label" style="margin-left:-20px;">要查询的地址：</label>
								<div class="col-sm-5">
								 <input class="form-control"  style="margin-left:-15px;" name="gcAddress" >
								</div>
								<div style="float:left;margin-left:-10px;" class="col-sm-3"><label class="control-label" style="margin-left:-10px;">如：深圳市南山区</label></div>
								<button type="button" class="btn btn-default " id="ploygonPointsSearchEditressEdit">查询</button>
			                </div>
		                </form>
                        <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal">取消</button>
                       <input type="button" class="btn btn-default pull-right" id="returnAreaEditPloygonPointsBtn" value="保存">
                   	<p style="float:left">结束画图时，双击鼠标左键</p>
	                <input type="button"  class="btn btn-default pull-right" value="删除多边形"  id="deleteEditPloygonPointsBtn"/>
                  </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog-->
    </div><!-- /.modal -->
 <!-- 弹出框-->
<script type="text/javascript">
    $(function () {
      require.config({paths: {"returnAreaEdit":"res/js/resource/returnArea_edit"}});
		require(["returnAreaEdit"], function (returnAreaEdit){
			returnAreaEdit.init();
		});

        $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            clearBtn: true,//清除按钮
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
          $('.business_hours_ed').daterangepicker({
				timePicker:true,
				format:'HH:mm',

			});
    });
</script>
<!--加载鼠标绘制工具-->
<script type="text/javascript" src="res/dep/baidu-DrawingManager/DrawingManager_min.js"></script>
<link rel="stylesheet" href="res/dep/baidu-DrawingManager/DrawingManager_min.css" />