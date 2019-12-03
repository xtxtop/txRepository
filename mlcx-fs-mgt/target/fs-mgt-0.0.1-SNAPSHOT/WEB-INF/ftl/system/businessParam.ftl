<meta charset="utf-8">
<style>
.panel-body, .panel-body-noheader, .panel-body-noborder {
    background: #fff;
}
</style>
<input type="hidden" name="tab" id="businessParamType">
<!-- 导航区 -->
<ul class="nav nav-tabs" id="businessParamTabs">
</ul>
<!-- 面板区 -->  
<div class="tab-content" id="businessParamContent">   
</div>
<!-- 更改参数 -->
<div class="modal fade" id="businessParamModalEdit">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">更改参数</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" name="businessParamForm">
					<input type="hidden" name="paramId" id="businessParamId">
					<input type="hidden" name="paramValueType" id="businessParamValueType">
                    <div class="form-group">
                        <label for="inputEmail3" class="col-sm-3 control-label key" id="businessParamName"></label>
                        <div class="col-sm-8" id="businessParamValueDiv">
                        </div>
                    </div>       
		            <div class="modal-footer">
		                <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" class="btn btn-default pull-right btncolora" id="businessParamEditBtn">保存</button>
		            </div>              
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript" src="${basePath!'' }/res/js/system/main.js"></script>
<script type="text/javascript" src="${basePath!'' }/res/js/common/cronValidate.js"></script>
