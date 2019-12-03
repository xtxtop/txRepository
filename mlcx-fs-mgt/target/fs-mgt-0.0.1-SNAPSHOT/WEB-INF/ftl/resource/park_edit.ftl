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
	<form name="parkEditForm">
		<input name="isPloygon" type="hidden" value='${isPloygon}' />
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">场站编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>编号：</label>
						</td>
						<td>
							<input class="form-control val" name="parkNo" value="${park.parkNo}" readonly style="background-color:#ffffff;" />
						</td>
						<td>
							<label class=" control-label key"><span>*</span>名称：</label>
						</td>
						<td>
							<input class="form-control val" name="parkName" value="${park.parkName}" maxlength="20" />
							<span id="parkNameEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>城市：</label>
						</td>
						<td>
							<select name="cityId" class="form-control val">
								<#list cities as citie>
									<option <#if park.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" > ${citie.itemValue}
									</option>
								</#list>
							</select>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>片区：</label>
						</td>
						<td>
							<select name="regionId" class="form-control val">
								<#list dots as d>
									<option <#if park.regionId==d.dataDictItemId>selected</#if> value="${d.dataDictItemId}" > ${d.itemValue}
									</option>
								</#list>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>所在地区：</label>
						</td>
						<td>
						<div style="width: 100%;display: inline-flex;">
							<select class="col-sm-4" name="addrRegion1Id" value="${park.addrRegion1Id}" onchange="getResultCity(this.value)" style="width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;">
								<#list plist as province>
									<option <#if park.addrRegion1Id==province.regionId>selected</#if> value="${province.regionId}" > ${province.regionName}
									</option>
								</#list>
							</select>

							<span id="itemcity">
                              <#if park.addrRegion2Id!=0&&park.addrRegion2Id!=null>
                               <select class="col-sm-4" name="addrRegion2Id" style="width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;" onchange="getResultCountry(this.value)">
                              <#list plists2 as pr>
                              <option <#if park.addrRegion2Id==pr.regionId>selected</#if> value="${pr.regionId}" >
                              ${pr.regionName}
                              </option>
                              </#list>
                              </select>
                              </#if>
                              </span>

							<span id="itemarea">
                              <#if park.addrRegion3Id!=0&&park.addrRegion3Id!=null>
                              <select class="col-sm-4" name="addrRegion3Id" style="width:70px;height:34px;border: 1px solid #ccc;margin-right:5px;padding-right:0px;padding-left:0px;">
                              <#list plists3 as p3>
                              <option
                              <#if park.addrRegion3Id==p3.regionId>selected</#if> value="${p3.regionId}" >
                              ${p3.regionName}
                              </option>
                             </#list>
                              </select>
                              </#if>
                              </span>
                             </div>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>详细街道：</label>
						</td>
						<td>
							<input class="form-control val" name="addrStreet" value="${park.addrStreet}" />
							<span id="addrStreetEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">加盟商：</label>
						</td>
						<td>
							<input type="hidden" id="franchiseeNo" name="franchiseeNo" value="" />
							<input class="form-control val" name="franchiseeName" value="${franchisee.franchiseeName}" readonly="readonly" />
						</td>
						<td>
							<label class=" control-label key"><span>*</span>坐标：</label>
						</td>
						<td>
							<input type="hidden" name="ploygonPoints" value="${park.ploygonPoints}" />
							<input style="width:50px;height:34px;" name="longitude" value="${park.longitude}" />N
							<input style="width:50px;height:34px;" name="latitude" value="${park.latitude}" />E
							<button type="button" id="searchGCEdit" class="btn btn-default pull-right ">地图选点</button>
							<span id="longitudeEdit"></span>
							<span id="latitudeEdit"></span>
						</td>
					</tr>
					<tr>
						<#if isPloygon??>
							<#if isPloygon=="0">
								<td>
									<label class=" control-label key"><span>*</span>电子围栏半径：</label>
								</td>
								<td>
									<input class="form-control val" name="electronicFenceRadius" value="${park.electronicFenceRadius}" maxlength="4" /><span>米</span>
									<span id="electronicFenceRadiusEdit"></span>
								</td>
							</#if>
						</#if>

						<!--<div class="form-group col-md-6">
							<label class=" control-label key"><span>*</span> 类别：</label>
							<div class="">
                                <#if isPloygon??>
                                    <#if isPloygon=="0">
                                    </#if>
                                </#if>
						    </div>
                        </div>-->
						<td>
							<label class=" control-label key"><span>*</span> 类别：</label>
						</td>
						<td>
							<select name="parkType" class="form-control val">
								<option value="2" <#if park.parkType=2>selected="selected"</#if> >使用类</option>
								<option value="1" <#if park.parkType=1>selected="selected"</#if> >管理类</option>
							</select>
							<span id="parkTypeEdit"></span>
						</td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>是否开放：</label>
						</td>
						<td>
							<label class="radio-inline">
                                    <input type="radio" class="isAvailable"  name="isPublic" <#if park.isPublic=1>checked</#if>   value="1"> 是
                                </label>
							<label class="radio-inline">
                                    <input type="radio" class="isAvailable" name="isPublic"   <#if park.isPublic=0>checked</#if>  value="0"> 否
                                </label>
							<span id="isPublicEdit"></span>
						</td>
						<td>
							<label class=" control-label key">样式：</label>
						</td>
						<td>
							<select name="styleId" class="form-control val">
								<option value="">请选择</option>
								<#list styles as stylep>
									<option <#if park.styleId==stylep.dataDictItemId>selected</#if> value="${stylep.dataDictItemId}" > ${stylep.itemValue}
									</option>
								</#list>
							</select>
							<span id="styleIdEdit"></span>
						</td>
					</tr>

					<tr>
						<td>
							<label class=" control-label key">车位数：</label>
						</td>
						<td class="btn-btnA-con">
							<label class="control-label val">
							 <input class="form-control val" name="parkingSpaceNumber"  value="${park.parkingSpaceNumber}">
							</lable>
							<span id="ownerTypeEdit"></span>
							 <input type="button" class="btn btn-default btn-btnA" data-id="${park.parkNo}" id="carParkingSpaceOnFormBtn" value="车位维护">
						</td>
						<td>
							<label class=" control-label key"><span>*</span>电桩数：</label>
						</td>
						<td class="btn-btnA-con">
							<label class="control-label val">
							<#if park.chargerNumber=="">
							0
							<#else>
							 ${park.chargerNumber}
							</#if>
							</lable>
							<div><input type="button" class="btn btn-default btn-btnA" data-id="${park.parkNo}" id="carChargerOnFormBtn" value="电桩维护" ></div>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>车辆数：</label>
						</td>
						<td>
							<label class="control-label val">${park.carNumber}</lable>
						</td>
						<td>
							<label class=" control-label key">服务：</label>
						</td>
						<td>
							<div class=" val" id="supportedServicesParkEdit">
								<label class="">
		                    <input type="checkbox" <#if park.supportedServices=="快充"||park.supportedServices=="快充,慢充"||park.supportedServices=="快充,慢充,清洗"||park.supportedServices=="快充,慢充,维修"||park.supportedServices=="快充,清洗"||park.supportedServices=="快充,维修"||park.supportedServices=="快充,清洗,维修"||park.supportedServices=="快充,慢充,清洗,维修">checked</#if> class="butcheck" value="快充"> 快充
							</label>
								<label class="">
							<input type="checkbox" <#if park.supportedServices=="慢充"||park.supportedServices=="快充,慢充"||park.supportedServices=="慢充,清洗"||park.supportedServices=="慢充,维修"||park.supportedServices=="快充,慢充,清洗"||park.supportedServices=="慢充,清洗,维修"||park.supportedServices=="快充,慢充,清洗,维修">checked</#if>  class="butcheck" value="慢充"> 慢充
							</label>
								<label class="">
							<input type="checkbox" <#if park.supportedServices=="清洗"||park.supportedServices=="快充,慢充,清洗"||park.supportedServices=="慢充,清洗,维修"||park.supportedServices=="快充,清洗"||park.supportedServices=="慢充,清洗"||park.supportedServices=="清洗,维修"||park.supportedServices=="快充,慢充,清洗,维修">checked</#if> class="butcheck" value="清洗"> 清洗
							</label>
								<label class="">
							<input type="checkbox" <#if park.supportedServices=="维修"||park.supportedServices=="快充,维修"||park.supportedServices=="慢充,维修"||park.supportedServices=="快充,慢充,维修"||park.supportedServices=="清洗,维修"||park.supportedServices=="快充,慢充,清洗,维修">checked</#if> class="butcheck" value="维修"> 维修
							</label>
							</div>
							<input type="hidden" name="supportedServices" id="supportedServicesEdit" value="${park.supportedServices}">
							<span name="supportedServices"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">所属：</label>
						</td>
						<td>
							<select name="ownerType" class="form-control val">
								<option value="">请选择</option>
								<option value="1" <#if park.ownerType=1>selected="selected"</#if> >自有</option>
								<option value="2" <#if park.ownerType=2>selected="selected"</#if> >租用</option>
							</select>
							<span id="ownerTypeEdit"></span>
						</td>
						<td>
							<label class=" control-label key">所属集团：</label>
						</td>
						<td>
							<div class=" val" id="parkCompanyRelIdsEdit">
								<#list companyList as company>
									<label class="">
		                    			<input type="checkbox"  class="butcheck" value="${company.companyId}"
		                    			<#list parkCompanyRelList as companyRel><#if companyRel.companyId==company.companyId>checked="checked"</#if></#list>
		                    			> ${company.companyName}
									</label>
								</#list>
							</div>
							<input type="hidden" name="companyIds" id="parkCompanyIdsEdit" value="">
						</td>
					</tr>
					<tr>
						<#if park.isAvailable==1>
							<td>
								<label class=" control-label key"><span>*</span>状态：</label>
							</td>
							<td>
								<label class="control-label val">
							 启用
							</lable>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>启用时间：</label>
							</td>
							<td>
								<label class="control-label val">
							${park.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</lable>
						</td>
						<#else>
						<td>
							<label class=" control-label key"><span>*</span>状态：</label>
							</td>
							<td>
								<label class="control-label val">
							 停用
							</lable>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>停用时间：</label>
							</td>
							<td>
								<label class="control-label val">
							${park.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</lable>
						</td>
						</#if>
					</tr>
					<tr class="add-car-last-tr">
						<td>
							<label class=" control-label key"><span>*</span>图片：</label>
							</td>
							<td class="odd-td">
	                          <input name="parkPicUrl1" type="hidden" />
	                          <div class="img-td-upload" style="background-image: url(${imagePath!''}/${park.parkPicUrl1});" id="parkPicUrl1">
		                         <div id="editParkPhotoBtn" class="add-img-position">
			                        <h3 class="icon">+</h3>
			                        <h3 class="text">添加图片</h3>
		                         </div>
	                          </div>
	                          <span id="parkPicUrl1Edit"></span>
                            </td>
							<td>
								<label class=" control-label key">场站电费：</label>
							</td>
							<td class="btn-btnA-son">
								<input class="form-control val" name="electricPrice" value="${park.electricPrice}" maxlength="4">
								<span class="btn-btnA">元/度</span> 
								<span id="electricPriceEdit"></span>
							</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key">场站租金：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="parkRent" value="${park.parkRent}"> 
							<span class="btn-btnA">元/月</span>
							<span id="parkRentEdit"></span>
						</td>
						<!-- <div class="form-group col-md-6">
							<label class=" control-label key"><span>*</span>交租日期：</label>
							<div class="">
							 <input class="form-control val" name="payRentDayOfMonth"  value="${park.payRentDayOfMonth}" >
							</div>
							<div ><span id="payRentDayOfMonthEdit"></span></div>
						</div> -->
						<td>
							<label class=" control-label key"><span>*</span>创建日期：</label>
						</td>
						<td>
							<label class="control-label val">
							${park.createTime?string('yyyy-MM-dd')}
							</lable>
							</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>更新日期：</label>
						</td>
						<td>
							<label class="control-label val">
							${park.updateTime?string('yyyy-MM-dd')}
							</lable>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>营业时间：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="business_hours_ed  form-control val" name="businessHours" value="${park.businessHours}" readonly="readonly" />
							<div class="btn-btnA">
								<button type="button" class="business_hours_ed  form-control val" name="businessHours" style="width:50px;height:35px;">修改</button>
							</div>

							<span id="businessHoursAdde"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>节假日营业：</label>
						</td>
						<td>
							<#if park.isBusinessFestival==1>
							<label><input name="isBusinessFestival" type="radio" value="1"  checked />是 </label>
								<label><input name="isBusinessFestival" type="radio" value="0"/>否 </label>
							</#if>
							<#if park.isBusinessFestival==0>
							<label><input name="isBusinessFestival" type="radio" value="1" />是 </label>
								<label><input name="isBusinessFestival" type="radio" value="0" checked/>否 </label>
							</#if>

						</td>
						<td>
						<label class=" control-label key"><span>*</span>取车附加费用：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="serviceFeeGet" value="${park.serviceFeeGet}"   maxlength="13"/>
							<span class="btn-btnA">元</span>
							<span id="serviceFeeGetAdde"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>还车附加费用：</label>
						</td>
						<td class="btn-btnA-con">
							<input class="form-control val" name="serviceFeeBack" value="${park.serviceFeeBack}" maxlength="13"/>
							<span class="btn-btnA">元</span>
							<span id="serviceFeeBackAdde"></span>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="savePark" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closePark" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="editParkPhotoModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">场站上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="parkPhotoEditForm">
					<div class="form-group" style = "display: none">
                            <label class=" control-label val">图片URL：</label>
                            <div class="">
                               <input type="text" class="form-control" placeholder="" name="parkPicUrl1" value="${park.parkPicUrl1}" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class=" control-label val">场站图片：</label>
                           <div class="">
                                <div id="parkFineEditUploader"><span name="parkEditErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right"
							id="editParkPhotoButton" value="确定">
					</div>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
 <!-- 弹出框地图选址操作-->
    <div class="modal fade" id="parkEditSearchGCModal" style="height:800px;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">获取坐标</h4>
                </div>
                <div class="modal-body" style="max-height:800px;">
                       <form class="form-horizontal" name="parkEditSearchGCForm">
                     <div id="searchGCMapEdit">

                        </div>
                        <div class="form-group" style="margin-top:15px;">
                            <label class=" control-label val">要查询的地址：</label>
							<div class="">
							 <input class="form-control val" type="text" name="gcAddress" >
							</div>
                        </div>
                        <div class="form-group" style="margin-top:15px;">
                            <label class=" control-label val">坐标：</label>
							<div class="">
							 <input class="form-control val" name="gcPoint" readonly>
							</div>
							<button type="button" class="btn btn-default " id="searchPointEdit">查询</button>
                        </div>
                        <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal">取消</button>
                       <input type="button" class="btn btn-default pull-right" id="parkEditSearchGCBtn" value="保存">
                   </div>
                   </form>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
 <!-- 弹出框 画多边形 操作-->
    <div class="modal fade" id="parkEditPloygonPointsModal" style="">
        <div class="modal-dialog">
            <div class="modal-content" style="margin-top:100px !important;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">获取坐标</h4>
                </div>
                <div class="modal-body" style="max-height:750px;">
                     	<div id="ploygonPointsMapEdit">
                        </div>
                        <form class="form-horizontal"  name="ploygonPointsSearchEditForm">
                        	<div class="form-group" style="margin-top:15px;margin-left:-23px;">
								<label class="col-sm-3 control-label key">要查询的地址：</label>
								<div class="col-sm-5">
									<input class="form-control val" style="margin-left:-35px;" name="gcAddress">
								</div>
								<div style="float:left;margin-left:-55px;" class="col-sm-3"><label class="control-label key">如：深圳市南山区</label></div>
								<button type="button" class="btn btn-default " id="ploygonPointsSearchAddressEdit">查询</button>
							</div>
		                </form>
                        <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal">取消</button>
                       <input type="button" class="btn btn-default pull-right" id="parkEditPloygonPointsBtn" value="保存">
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
      require.config({paths: {"parkEdit":"res/js/resource/park_edit"}});
		require(["parkEdit"], function (parkEdit){
			parkEdit.init();
		});
		/* var config=new Object();
		config.uploadId="parkFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "park_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=50* 1024;
		config.formName= "parkPhotoEditForm";
		//文件回显inputName
		config.inputName = "parkPicEditUrl";
		//错误提示span标签name
		config.spanName = "parkEditErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		}); */
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