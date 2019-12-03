<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group">
							<label class="col-sm-12 control-label"><h4>场站参数配置</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-8 form-horizontal">
					<form name="parkEditForm" class="form-horizontal">
						<div class="form-group col-sm-6">
							<label class="col-sm-4 control-label">*&nbsp;&nbsp;编号：</label>
							<div class="col-sm-7">
							    <input class="form-control" name="parkNo" value="${park.parkNo}" readonly="true"/>
							</div>
						</div>
						<div class="form-group col-sm-6">
							<label class="col-sm-4 control-label">*&nbsp;&nbsp;名称：</label>
							<div class="col-sm-7">
							    <input class="form-control" name="parkName" value="${park.parkName}"/>
							</div>
							<div style="margin-top:7px;"><span id="parkNameEdit"></span></div>
						</div>
						<div class="form-group col-sm-6">
							<label class="col-sm-4 control-label">*&nbsp;&nbsp;城市：</label>
							<div class="col-sm-7">
								<select name="cityId" class="form-control">
								 <#list cities as citie>
									 <option <#if park.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
							</div>
						</div>
						<div class="form-group col-sm-6">
							<label class="col-sm-4 control-label">*&nbsp;&nbsp;启用时间：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${park.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</lable>
							</div>
						</div>
				
						<div class="form-group">
							<div class="col-sm-7">
							<button type="button" id="closePark" class="btn btn-default pull-right " >关闭</button> 										
					        <button type="button" id="savePark" class="btn btn-default pull-right  " >保存</button> 			
							</div>	
						</div>
					</div>	
        		</div><!-- /.row -->
    
</div>
<script type="text/javascript">
    $(function () {
      require.config({paths: {"parkEdit":"res/js/resource/park_edit"}});
		require(["parkEdit"], function (parkEdit){
			parkEdit.init();
		});
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
