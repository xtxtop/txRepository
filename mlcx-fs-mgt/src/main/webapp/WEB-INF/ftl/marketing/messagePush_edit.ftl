<meta charset="utf-8">
<style>
    .btn-new {
	    display: inline-block;
	    padding: 6px 12px;
	    margin-bottom: 0;
	    font-size: 14px;
	    font-weight: normal;
	    line-height: 1.42857143;
	    text-align: center;
	    white-space: nowrap;
	    vertical-align: middle;
	    -ms-touch-action: manipulation;
	    touch-action: manipulation;
	    cursor: pointer;
	    -webkit-user-select: none;
	    -moz-user-select: none;
	    -ms-user-select: none;
	    user-select: none;
	    background-image: none;
	    border: 1px solid transparent;
	    border-radius: 4px;
	}
    
    .btn-sm-new {
	    height: 30px;
		padding: 5px 10px;
	    font-size: 12px;
	    line-height: 1.5;
	    border-radius: 3px;
	}
	
	.btn-default-new {
	    color: #333;
	    background-color: #fff;
	    border-color: #ccc;
	}
	
</style>
<div class="container-fluid backgroundColor">
	<form name="messagePushEditFrom">
		<input type="hidden" name="id" value="${messagePush.id}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">消息推送编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>推送方式：</label>
						</td>
						<td>
							<select name="pushPattern" class="form-control val">
									  <option  value="1" <#if messagePush.pushPattern==1>selected="selected"</#if> >多个用户</option>
									  <option  value="2" <#if messagePush.pushPattern==2>selected="selected"</#if> >全部用户</option>
								</select>
							<span name="messagePushTypeEdit"></span>
						</td>
						<td class="form-group  memberName-div"  <#if messagePush.pushPattern!=1>hidden="hidden"</#if>>
							<label class=" control-label key"><span>*</span>推送会员：</label>
						</td>
						<td class="form-group  memberName-div"  <#if messagePush.pushPattern!=1>hidden="hidden"</#if>>
							<input type="hidden" class="form-control val" name="memberNo" value="${messagePush.memberNo}"/>
								<input class="form-control val" name="memberName" readonly="readonly" value="${messagePush.memberName}" />
								<button type="button" class="btn btn-default-new" id="memberPushEditModalBtn">会员列表</button>
								<span name="memberNameEdit"></span>
						</td>
					</tr>
					<tr>

						<td>
							<label class=" control-label key"><span>*</span>标题：</label>
						</td>
						<td>
							<input class="form-control val" name="title" value="${messagePush.title}" maxlength="25"/>
							<span name="titleEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>内容：</label>
						</td>
						<td>
							<textarea name="content" class="form-control val" maxlength="255">${messagePush.content}</textarea>
							<span name="contentEdit"></span>
						</td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editMessagePush" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="closeEditMessagePush" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                关闭
            </button></td>

					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<div class="modal fade" id="memberPushEditModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   		<div class="with-border">
					<div class="title-new-details">选择会员列表</div>
				</div>
	       <!--定义操作列按钮模板-->
	       <script id="memberPushEditBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
	       <div class="box">
		        <div class="box-body box-body-change-padding">
		        	<input type="hidden" name="dataTemp" />
		         	<table id="memberPushEditLists" class="table table-bordered table-hover" width="100%">
			        </table>
			   	</div><!-- /.box-body -->
			   	<div class="carRedPacketAddParkTool-bullet" id="memberPushEditTools">
					</div>
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"messagePushEdit":"res/js/marketing/messagePush_edit"}});
		require(["messagePushEdit"], function (messagePushEdit){
			messagePushEdit.init();
		});  
    });
</script>
