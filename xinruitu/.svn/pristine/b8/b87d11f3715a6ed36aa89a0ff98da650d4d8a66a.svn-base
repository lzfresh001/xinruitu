

//加载模态框的数据
function reloadModelData(url, title, btntext, prj_id, prj_code, prj_name, prj_name_2nd,type) {
    $("#projectModal #prjModelHead").text(title);
    $("#projectModal #imgSubmit").text(btntext);
    $("input[name='url']").val(url);
    $("input[name='prj_id']").val(prj_id);
    $("input[name='prj_code']").val(prj_code);
    $("input[name='prj_name']").val(prj_name);
    $("input[name='prj_name_2nd']").val(prj_name_2nd);
}

//验证数据
function checkValue(prj_name,prj_name_2nd){
    if(prj_name == ''){
        $("input[name='prj_name']").focus();
        alert("项目名称不能为空");
        return false;
    }
    if(prj_name_2nd == ''){
        $("input[name='prj_name_2nd']").focus();
        alert("二级名称不能为空");
        return false;
    }
    return true;
}

// 点击添加项目按钮触发
$("#addData").click(function(){
    $.ajax({
        type:"GET",
        url:_ctx+"/project/createPrjCode",
        cache:false,
        dataType:"json",
        data:{},
        success:function(data){
            if(data.status == 'success'){
                reloadModelData(_ctx+"/project/add","添加项目","添加","",data.data,"","","add");
                $("#projectModal").modal("show");
            }else{
                alert(data.msg);
            }
        }
    });
});

//点击新增或修改的模态框确认按钮触发
$("#imgSubmit").click(function(){
    var url = $("input[name='url']").val();
    var prj_id = $("input[name='prj_id']").val();
    var prj_code= $("input[name='prj_code']").val();
    var prj_name= $("input[name='prj_name']").val();
    var prj_name_2nd= $("input[name='prj_name_2nd']").val();
    if(checkValue(prj_name,prj_name_2nd)){
        $.ajax({
            type:"post",
            url:url,
            cache:false,
            dataType:"json",
            data:{
                prj_id:prj_id,
                prj_code:prj_code,
                prj_name:prj_name,
                prj_name_2nd:prj_name_2nd
            },
            success:function(data){
                if(data.status == 'success'){
                    $("#projectModal").modal("hide");
                    location.reload();
                }else{
                    alert(data.msg);
                }
            }
        });
    }
});

// 点击修改按钮触发
function editProject(prj_id){
   $.ajax({
        type:"GET",
        url:_ctx+"/project/find",
        data:{prj_id:prj_id},
        dataType:"json",
        cache:false,
        success:function(data){
            if(data.status == "success"){
                var obj = data.data;
                reloadModelData(_ctx+"/project/edit","编辑项目","更新",obj.prj_id,obj.prj_code,obj.prj_name,obj.prj_name_2nd,"edit");
                $("#projectModal").modal("show");
            }else{
                alert(data.msg);
            }
        }
    });
}

// 点击查询按钮触发
$("#btn_search").click(function () {
    var prj_name=$("#prj_name_search").val();
    location.href=_ctx+"/project/list?prj_name="+prj_name;
});