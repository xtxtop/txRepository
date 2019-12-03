<meta charset="utf-8">
<div class="container-fluid backgroundColor">
    <form name="gateMachineAddForm">
        <div class="row hzlist">
            <table class="tab-table table table-border table-responsive">
                <thead class="tab-thead">
                    <tr>
                        <th colspan="4">新增闸机</th>
                    </tr>
                </thead>
                <tbody class="tab-tbody">
                    <tr>
                        <td>
                            <label class=" control-label key"><span>*</span>闸机名称：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="gateName" placeholder="请输入闸机名称"/>
                            <span id="gateMachineNameAdd"></span>
                        </td>
                        <td>
                            <label class=" control-label key"><span>*</span>闸机编码：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="gateCode" placeholder="请输入闸机编码"/>
                            <span id="gateMachineCodeAdd"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span>*</span>场站名称：</label>
                        </td>
                        <td>
                            <select name="parkingNo" id="parkingNo" class="form-control val">
                                    <option value="">请选择</option>
                                 <#list park as p>
                                    <#if p.parkingStatus==0&&p.parkingType==0>
                                    <option value="${p.parkingNo}">${p.parkingName}</option>
                                    </#if>
                                 </#list>
                            </select>
                            <span id="stationNoAdd"></span>
                        </td>
                        <td>
                            <label class=" control-label key"><span>*</span>状态：</label>
                        </td>
                        <td>
                            <div class="col-sm-7 val">
                                <input type="radio" name="gateStatus"  value="1" checked="checked">降下</input>
                                <input type="radio" name="gateStatus"  value="0" >升起</input>
                                <span id="gateMachineStatusAdd"></span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span>*</span>在线状态：</label>
                        </td>
                        <td>
                            <div class="col-sm-7 val">
                                <input type="radio" name="onlineStatus"  value="0" checked="checked">在线</input>
                                <input type="radio" name="onlineStatus"  value="1" >离线</input>
                                <span id="gateMachineUsertypeAdd"></span>
                            </div>
                        </td>
                        
                       <td>
                            <label class=" control-label key"><span>*</span>可用状态：</label>
                        </td>
                        <td>
                            <select name="activeCondition" id="activeCondition_add" class="form-control val">
                                    <option value="">请选择</option>
                                    <option value="0">启用</option>
                                    <option value="1">停用</option>
                            </select>
                            <span id="activeConditionAdd"></span>
                        </td>
                    </tr>
                </tbody>
                <tfoot class="tab-tfoot">
                    <tr>
                        <td colspan="2"><button type="button" id="savegateMachineAdd" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
                        <td colspan="2"><button type="button" id="closegateMachineAdd" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

                    </tr>
                </tfoot>
            </table>
        </div>
    </form>
</div>
<!-- /.modal -->


<div class="modal fade" id="checkUserModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--定义操作列按钮模板-->
            <script id="gateMachineAddBtnTpl" type="text/x-handlebars-template">
                {{#each func}}
                <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
                {{/each}}
               </script>
            <div class="box">
                <div class="box-header">
                    <!-- <h3 class="box-title">数据列</h3> -->
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <table id="gateMachineAddList"
                        class="table table-bordered table-striped table-hover"
                        width="100%">
                    </table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript">
    $(function() {
        require.config({
            paths : {
                "gateMachineAdd" : "res/js/mlpark/gateMachine_add"
            }
        });
        require([ "gateMachineAdd" ], function(gateMachineAdd) {
            gateMachineAdd.init();
        });
    })
    $(function() {
        $(".datepicker").datepicker({
            language : "zh-CN",
            autoclose : true,//选中之后自动隐藏日期选择框
            clearBtn : true,//清除按钮
            todayBtn : "linked",//今日按钮
            format : "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>