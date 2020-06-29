var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: 0
		}
	}
};

//树 对象
function menuTree(id,pId,name,checked){
	this.id=id;
	this.pId=pId;
	this.name=name;
	this.checked=checked;
}

//权限处理
function roleMenu(rid,qx,title){
	var zNodes =[];
	reloadQXModal(rid,qx,title);
	$.ajax({
		type:"POST",
		url:_ctx+"/role/qx",
		cache:false,
		data:{time:new Date().getTime(),qx:qx,role_id:rid},
		dataType:"json",
		success:function(data){
			if(data.status == 'success'){
				var rultData = data.data;
				for(var i=0;i<rultData.length;i++){
					var childNode = [];
					var obj = rultData[i];
					zNodes.push(new menuTree(obj.menu_ID,obj.parent_ID,obj.menu_NAME,obj.hasMenu));
					if(obj.subMenu.length > 0){
						for(var j = 0;j<obj.subMenu.length;j++){
							var subm = obj.subMenu[j];
							zNodes.push(new menuTree(subm.menu_ID,subm.parent_ID,subm.menu_NAME,subm.hasMenu));
						}
					}
				}
				//初始化 数
				$.fn.zTree.init($("#roleTree"), setting,zNodes);
			}else{
				alert(data.msg);
			}
		}
	});
	$("#qxModal").modal("show");
}

//删除按钮
function delRole(role_id){
	if(confirm("你确定要删除吗？如果删除，拥有该角色的用户将失去此角色的权限。")){
		$.ajax({
			type:"POST",
			url:_ctx+"/role/del/"+role_id,
			cache:false,
			dataType:"json",
			data:{time:new Date().getTime()},
			success:function(data){
				if(data.status == 'success'){
                    loadCurPage();
				}else{
					alert(data.msg);
				}
			}
		});
	}
}

//编辑角色信息
function editRole(roleId,role_name,role_desc){
	reloadRoleModal(roleId,_ctx+"/role/edit",role_name,role_desc,"编辑角色","更改");
	$("#roleModal").modal("show");
}

//修改模态框的数据
function reloadQXModal(roleId,qx,title){
	$("input[name='qx']").val(qx);
	$("input[name='role_id']").val(roleId);
	$("#modelHead").text(title);
}
function reloadRoleModal(roleId,url,role_name,role_desc,title,btnText){
	$("input[name='url']").val(url);
	$("input[name='role_id']").val(roleId);
	$("input[name='role_name']").val(role_name);
	$("textarea[name='role_desc']").val(role_desc);
	$("#rolemodelHead").text(title);
	$("#submit-roleBtn").text(btnText);
}

//更改权限
function updateRole(rid,ids,qx){
	$.ajax({
		type:"POST",
		url:_ctx+"/role/edit",
		cache:false,
		dataType:"json",
		data:{role_id:rid,ids:ids,qx:qx},
		success:function(data){
			if(data.status == 'success'){
				$("#qxModal").modal("hide");
			}else{
				alert(data.msg);
			}
		}
	});
}


function loadCurPage() {
    _table.draw(false);
}

function searchData(){
    if ($("#roleList").data("qxquery") == '1'){
        _table.draw();
    } else {
        $("#roleList tbody").html("<td colspan=\"9\" align=\"center\"><h2>此用户无权限查看该页面</h2></td>");
    }
}

var _table;
$(function(){
    var $table = $("#roleList");
    if($("#roleList").data("qxquery") == '1'){
        //表格分页
        _table = $table.dataTable({
            'paging'      : true,
            'lengthChange': true,
            'searching'   : false,
            'ordering'    : false,
            'info'        : true,
            'autoWidth'   : false,
            "scrollX"	  : true,
            "pagingType"  : "full_numbers",
            "pageLength"  : 10,
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": _ctx+"/role/listByPage",
                "type": 'GET',
                "dataType": "json",
                "data": function (d) {
                    var param = {};
                    param.draw = d.draw;
                    param.start = d.start;
                    param.length = d.length;

                    param.role_name = $("#name-search").val();
                    return param;
                },
                "dataSrc": function (data) {
                    return data.data;
                },
                "error": function(XMLHttpRequest, textStatus, errorThrown) {
                    alert("查询失败！！！");
                }
            },
            "columns": [
                {"data": null,
                "render" : function(data, type, full, meta){
                    return meta.row + 1 + meta.settings._iDisplayStart;}
                },
                {"data": "role_name"},
                {"data": "role_desc"},
                {"data": null},
                {"data": null},
                {"data": null},
                {"data": null},
                {"data": null},
                {"data": null}
            ],
            columnDefs: [
                {
                    targets: 3,
                    render:  function ( row, type, val, meta ) {
                        if($("#roleList").data("qxedit") == '1'){
                            return "<span data-toggle=\"tooltip\" data-placement=\"left\" title=\"赋予角色的菜单权限\" class=\"btn btn-xs btn-default\" onclick=\"roleMenu('" + val.role_id + "','rights','编辑菜单权限');\"><i class=\"fa fa-list\"></i> 菜单权限</span>";
                        } else {
                            return "<span data-toggle=\"tooltip\" title=\"权限不够\"><i class=\"fa fa-lock\"></i></span>";
                        }
                    }
                },
                {
                    targets: 4,
                    render:  function ( row, type, val, meta ) {
                        if($("#roleList").data("qxedit") == '1'){
                            return "<span data-toggle=\"tooltip\" data-placement=\"left\" title=\"赋予菜单添加的权限\" class=\"btn btn-xs btn-default\" onclick=\"roleMenu('" + val.role_id + "','add_qx','编辑添加权限');\"><i class=\"fa fa-plus\"></i> 添加权限</span>";
                        } else {
                            return "<span data-toggle=\"tooltip\" title=\"权限不够\"><i class=\"fa fa-lock\"></i></span>";
                        }
                    }
                },
                {
                    targets: 5,
                    render:  function ( row, type, val, meta ) {
                        if($("#roleList").data("qxedit") == '1'){
                            return "<span data-toggle=\"tooltip\" data-placement=\"left\" title=\"赋予菜单删除的权限\" class=\"btn btn-xs btn-default\" onclick=\"roleMenu('" + val.role_id + "','del_qx','编辑删除权限');\"><i class=\"fa fa-trash-o\"></i> 删除权限</span>";
                        } else {
                            return "<span data-toggle=\"tooltip\" title=\"权限不够\"><i class=\"fa fa-lock\"></i></span>";
                        }
                    }
                },
                {
                    targets: 6,
                    render:  function ( row, type, val, meta ) {
                        if($("#roleList").data("qxedit") == '1'){
                            return "<span data-toggle=\"tooltip\" data-placement=\"left\" title=\"赋予菜单编辑的权限\" class=\"btn btn-xs btn-default\" onclick=\"roleMenu('" + val.role_id + "','edit_qx','编辑修改权限');\"><i class=\"fa fa-edit\"></i> 修改权限</span>";
                        } else {
                            return "<span data-toggle=\"tooltip\" title=\"权限不够\"><i class=\"fa fa-lock\"></i></span>";
                        }
                    }
                },
                {
                    targets: 7,
                    render:  function ( row, type, val, meta ) {
                        if($("#roleList").data("qxedit") == '1'){
                            return "<span data-toggle=\"tooltip\" data-placement=\"left\" title=\"赋予菜单查看的权限\" class=\"btn btn-xs btn-default\" onclick=\"roleMenu('" + val.role_id + "','query_qx','编辑查看权限');\"><i class=\"fa fa-eye\"></i> 查看权限</span>";
                        } else {
                            return "<span data-toggle=\"tooltip\" title=\"权限不够\"><i class=\"fa fa-lock\"></i></span>";
                        }
                    }
                },
                {
                    targets: -1,
                    render:  function ( row, type, val, meta ) {
                        var delStr = "";
                        var editStr = "";
                        if($("#roleList").data("qxdel") != '1' && $("#roleList").data("qxedit") != '1'){
                            return "<span data-toggle=\"tooltip\" title=\"权限不够\"><i class=\"fa fa-lock\"></i> </span>";
                        }
                        if($("#roleList").data("qxdel") == '1'){
                            delStr = "<span data-toggle=\"tooltip\" title=\"删除角色信息\" class=\"btn btn-xs btn-danger\" onclick=\"delRole('" + val.role_id + "');\"><i class=\"fa fa-trash-o\"></i> </span>";
                        }
                        if ($("#roleList").data("qxedit") == '1'){
                            editStr = "<span data-toggle=\"tooltip\" title=\"编辑角色信息\" class=\"btn btn-xs btn-info\" onclick=\"editRole('" + val.role_id + "','"+ val.role_name+"','"+ val.role_desc +"');\"><i class=\"fa fa-edit\"></i> </span>";
                        }
                        return editStr + "  " + delStr;
                    }
                }
            ],
            "drawCallback": function( settings ) {
                $.getScript("/js/tip.js");
            },
            // 国际化
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                // 排序方式
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            }
        }).api();
    } else {
        $("#roleList tbody").html("<td colspan=\"9\" align=\"center\"><h2>此用户无权限查看该页面</h2></td>");
    }


    //查询
    $("#btn_search").click(searchData);
    $("#name-search").on('keyup',function(event){
        if(event.keyCode == 13){
            $("#btn_search").trigger("click");
        }
    });


	//点击模态框更改按钮
	$("#submit-qxBtn").click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("roleTree");
		var nodes = treeObj.getCheckedNodes(true);
		var ids = "";
		for(var i=0;i<nodes.length;i++){
			var node = nodes[i];
			if(i != nodes.length -1){
				ids =ids+node.id+",";
			}else{
				ids = ids +node.id;
			}
		}
		var roleId = $("input[name='role_id']").val();
		var qx = $("input[name='qx']").val();
		updateRole(roleId,ids,qx)
	});
	
	$("#addRole").click(function(){
		reloadRoleModal("",_ctx+"/role/add","","","新增角色","新增");
		$("#roleModal").modal("show");
	});
	
	//角色模态框的提交按钮
	$("#submit-roleBtn").click(function(){
		var url = $("input[name='url']").val();
		var roleId = $("input[name='role_id']").val();
		var roleName = $("input[name='role_name']").val();
		//回车或换行再次编辑的时候出现问题，怀疑是editRole方法的单引号问题。双引号转义老失败,
		var roleDesc = $("textarea[name='role_desc']").val().replace(/\n/g,"、").replace(/\s/g,"、");
		$.ajax({
			type:"POST",
			cache:false,
			url:url,
			dataType:"json",
			data:{role_id:roleId,role_name:roleName,role_desc:roleDesc,time:new Date().getTime()},
			success:function(data){
				if(data.status == 'success'){
                    loadCurPage();
					$("#roleModal").modal("hide");
				}else{
					alert(data.msg);
				}
			}
		});
	});
});