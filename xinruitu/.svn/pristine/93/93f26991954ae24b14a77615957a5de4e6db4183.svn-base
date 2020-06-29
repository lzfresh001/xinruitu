$(function() {

    var setting = {
        view: {
            selectedMulti: false
        },
        edit: {
            enable: true,
            showRemoveBtn: false,
            showRenameBtn: false
        },
        data: {
            keep: {
                parent:true,
                leaf:true
            },
            simpleData: {
                enable: true
            }
        }
    };
    $.ajax({
        type: "get",
        url:_ctx+'/sysdata/lists',
        cache: false,
        dataType: "json",
        success: function (data) {
            if (data.status == 'success') {
               var datas=data.data;
               var zNodes=new Array();
               for(var i=0;i<datas.length;i++){
                   var node={id:datas[i].id,pId:datas[i].pid,name:datas[i].name+"["+datas[i].id+"]",tid:datas[i].priority};
                   zNodes[i]=node;
               }
               $.fn.zTree.init($("#treeDemo"), setting,zNodes);
            } else {
                alert(data.msg);
            }
        },error:function(XMLHttpRequest, textStatus, errorThrown){
            alert(errorThrown);
        }

    });
//新增节点
    $("#addData").click(function () {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getSelectedNodes();
            if(nodes.length == 0){
                alert("请先选择一个节点");
                return;
            }
        reloadModelData(_ctx + "/sysdata/add", "添加数据", "添加", "", "", "", "");
        $("#dataModal").modal("show");
    });

    //新增/修改 确认按钮
    $("#submitBtn").click(function () {
        var id = $("input[name='id']").val();
        var name = $("input[name='name']").val();
        var priority = $("input[name='priority']").val();
        var url=$("input[name='url']").val();
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getSelectedNodes(),
            treeNode = nodes[0];
        var pid=treeNode.id;
        if (url == _ctx + '/sysdata/add') {
            if (!checkAccount(name,priority)) {
                return false;
            }
        }else{
            pid=treeNode.pId;
        }
        $.ajax({
            type: "post",
            url: url,
            cache: false,
            dataType: "json",
            data:{id:id,name:name,priority:priority,pid:pid},
            success: function (data) {
                if (data.status == 'success') {
                    $("#dataModal").modal("hide");
                    if(url == _ctx + '/sysdata/add'){
                        treeNode.isParent=true;
                        zTree.addNodes(treeNode, {id:id,pId:treeNode.id,isParent:false,name:name,tid:priority});
                        window.location.href=window.location.href;
                    }else{
                        treeNode.name=name;
                        treeNode.tid=priority;
                        zTree.editName(treeNode);
                        zTree.cancelEditName();
                        window.location.href=window.location.href;
                    }
                } else {
                    alert(data.msg);
                }
            },error:function(XMLHttpRequest, textStatus, errorThrown){
                alert(errorThrown);
            }

        });
    });

//删除节点
    $("#delData").click(function () {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getSelectedNodes(),
            treeNode = nodes[0],
            isParent=treeNode.isParent;
        if(nodes.length == 0){
            alert("请先选择一个节点");
            return;
        }
        if(isParent){
            alert("删除的数据下还有其他数据");
            return;
        }
        if (confirm("你确定要删除此数据吗？")) {
            $.ajax({
                type: "POST",
                url: _ctx + "/sysdata/del",
                dataType: "json",
                data: {id:treeNode.id},
                cache: false,
                success: function (data) {
                    if (data.status == 'success') {
                        zTree.removeNode(treeNode,false);
                        window.location.href=window.location.href;
                    } else {
                        alert(data.msg);
                    }
                }
            });
        }
    });

//编辑节点
    $("#editData").click(function () {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getSelectedNodes(),
            treeNode = nodes[0];
        if(nodes.length == 0){
            alert("请先选择一个节点");
            return;
        }
        reloadModelData(_ctx + "/sysdata/edit", "编辑用户", "更新", treeNode.id,treeNode.name, treeNode.tid);
        $("#dataModal").modal("show");
    });

//验证数据
    function checkAccount(name, priority) {
        if (name == '') {
            $("input[name='name']").focus();
            alert("名称不能为空");
            return false;
        }
        if (priority == '') {
            $("input[name='priority']").focus();
            alert("排序不能为空");
            return false;
        }
        return true;
    }

//加载模态框的数据
    function reloadModelData(url, title, btntext, id, name, priority) {
        $("#dataModal #datamodelHead").text(title);
        $("#dataModal #imgSubmit").text(btntext);
        $("input[name='url']").val(url);
        $("input[name='id']").val(id);
        $("input[name='name']").val(name);
        $("input[name='priority']").val(priority);
    }
});