<meta charset="utf-8">
<div class="container-fluid backgroundColor">
    <form name="gateMachineEditForm">
    <input type="hidden" name="gateNo" value="${gate.gateNo}">
        <div class="row hzlist">
            <table class="tab-table table table-border table-responsive">
                <thead class="tab-thead">
                    <tr>
                        <th colspan="4">编辑闸机</th>
                    </tr>
                </thead>
                <tbody class="tab-tbody">
                    <tr>
                        <td>
                            <label class=" control-label key"><span>*</span>闸机名称：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="gateName" value="${gate.gateName}" placeholder="请输入闸机名称"/>
                            <span id="gateMachineNameEdit"></span>
                        </td>
                        <td>
                            <label class=" control-label key"><span>*</span>闸机编码：</label>
                        </td>
                        <td>
                            <input class="form-control val" name="gateCode" value="${gate.gateCode}"  placeholder="请输入闸机编码"/>
                            <span id="gateMachineCodeEdit"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span>*</span>场站名称：</label>
                        </td>
                        <td>
                            <select name="parkingNo" id="parkingNo" class="form-control val">
                                 <#list park as p>
                                    <#if p.parkingStatus==0&&p.parkingType==0>
                                    <option value="${p.parkingNo}" <#if p.parkingNo==gate.parkingNo>selected</#if>>${p.parkingName}</option>
                                    </#if>
                                 </#list>
                            </select>
                            <span id="stationNoEdit"></span>
                        </td>
                        <td>
                            <label class=" control-label key"><span>*</span>状态：</label>
                        </td>
                        <td>
                            <div class="col-sm-7 val">
                                <input type="radio" name="gateStatus"  value="1" <#if gate.gateStatus==1> checked="checked"</#if>>降下</input>
                                <input type="radio" name="gateStatus"  value="0" <#if gate.gateStatus==0> checked="checked"</#if>>升起</input>
                                <span id="gateMachineStatusEdit"></span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class=" control-label key"><span>*</span>在线状态：</label>
                        </td>
                        <td>
                            <div class="col-sm-7 val">
                                <input type="radio" name="onlineStatus"  value="0" <#if gate.onlineStatus==0> checked="checked"</#if>>在线</input>
                                <input type="radio" name="onlineStatus"  value="1" <#if gate.onlineStatus==1> checked="checked"</#if>>离线</input>
                                <span id="gateMachineUsertypeEdit"></span>
                            </div>
                        </td>
                        
                       <td>
                            <label class=" control-label key"><span>*</span>可用状态：</label>
                        </td>
                        <td>
                            <select name="activeCondition" id="activeCondition_Edit" class="form-control val">
                                    <option value="0" <#if gate.activeCondition==0>selected</#if>>启用</option>
                                    <option value="1" <#if gate.activeCondition==1>selected</#if>>停用</option>
                            </select>
                            <span id="activeConditionEdit"></span>
                        </td>
                    </tr>
                </tbody>
                <tfoot class="tab-tfoot">
                    <tr>
                        <td colspan="2"><button type="button" id="savegateMachineEdit" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
                        <td colspan="2"><button type="button" id="closegateMachineEdit" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
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
            <script id="gateMachineEditBtnTpl" type="text/x-handlebars-template">
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
                    <table id="gateMachineEditList"
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
                "gateMachineEdit" : "res/js/mlpark/gateMachine_edit"
            }
        });
        require([ "gateMachineEdit" ], function(gateMachineEdit) {
            gateMachineEdit.init();
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