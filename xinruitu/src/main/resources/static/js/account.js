
//var selections = document.getElementById("prj_id");



//加载模态框的数据
function reloadModelData(url, title, btntext, ac_id, prj_name,prj_name_2nd, drawing_no,part_no,thickness,height,width,count,type) {
    $("#accountModal #accModelHead").text(title);
    $("#accountModal #imgSubmit").text(btntext);
    $("input[name='url']").val(url);
    $("input[name='ac_id']").val(ac_id);
    //$("select[name='prj_id']").val(prj_id);
    $("#prj_name").val(prj_name);
    $("#prj_name_2nd").val(prj_name_2nd);
    $("input[name='drawing_no']").val(drawing_no);
    //$("input[name='ac_name']").val(ac_name);
    $("input[name='part_no']").val(part_no);
    $("input[name='thickness']").val(thickness);
    $("input[name='height']").val(height);
    $("input[name='width']").val(width);
    $("input[name='count']").val(count);
}


// 获取所有项目的ID和名称
$(function () {
    //showPrjList();

   /* $("#upload_btn").click(function () {
        var formData = new FormData($("#upload_from")[0]);//获取表单中的文件
        alert(formData);
        $.ajax({
            url:"uploadData",//后台的接口地址
            type:"post",//post请求方式
            data:formData,//参数
            cache: false,
            processData: false,
            contentType: false,
            success:function (data) {
                if(data.status == 'success'){
                    alert("上传成功！");
                    location.reload();
                }else{
                    alert(data.msg);
                }
            },
            error:function () {
                alert("操作失败~");
            }

        })
    });*/

})

/*function importBtn() {
    var file_upload = $("#file_upload").val();
    $.ajax({
        type:"post",
        url:_ctx+"/account/uploadData",
        cache:false,
        dataType:"json",
        data:{
            file_upload:file_upload
        },
        success:function(data){
            if(data.status == 'success'){
                alert("上传成功！");
                location.reload();
            }else{
                alert(data.msg);
            }
        }
    });
}*/

/*function showPrjList(){

    $.ajax({
        type:"GET",
        url:_ctx+"/project/getPrjName",
        cache:false,
        dataType:"json",
        data:{},
        success:function(data){
            if(data.status == 'success'){
                var list = data.data;
                if(null != list){
                    $.each(list,function (i,obj) {
                        var option = document.createElement("option");
                        option.value = obj.prj_id;
                        option.text= obj.prj_name;
                        selections.options.add(option);
                    })
                }
            }else{
                alert(data.msg);
            }
        }
    });
}*/

//验证数据
function checkValue(prj_id, drawing_no, ac_name,part_no,thickness,height,width,count){
    if(prj_id == ''){
        $("select[name='prj_id']").focus();
        alert("请选择项目");
        return false;
    }
    if(drawing_no == ''){
        $("input[name='drawing_no']").focus();
        alert("图号不能为空");
        return false;
    }
    if(ac_name == ''){
        $("input[name='ac_name']").focus();
        alert("名称不能为空");
        return false;
    }
    if(part_no == ''){
        $("input[name='part_no']").focus();
        alert("件号不能为空");
        return false;
    }
    if(!/^\d+$/.test(thickness)){
        $("input[name='thickness']").focus();
        alert("请填写厚度(只能为正整数)");
        return false;
    }
    if(!/^\d+$/.test(height)){
        $("input[name='height']").focus();
        alert("请填写宽度(只能为正整数)");
        return false;
    }
    if(!/^\d+$/.test(width)){
        $("input[name='width']").focus();
        alert("请填写长度(只能为正整数)");
        return false;
    }
    if(!/^\d+$/.test(count)){
        $("input[name='count']").focus();
        alert("请填写数量(只能为正整数)");
        return false;
    }
    return true;
}


// 点击添加按钮触发
$("#addData").click(function(){
    reloadModelData(_ctx+"/account/add","添加大账","添加","","","","","","","","","","add");
    $("#accountModal").modal("show");
});

// 点击上传按钮触发
$("#importData").click(function () {
    $("#uploadModal").modal("show");
});

//点击新增或修改的模态框确认按钮触发
$("#imgSubmit").click(function(){
    var url = $("input[name='url']").val();
    var ac_id= $("input[name='ac_id']").val();
    var prj_name = $("#prj_name").val();
    var prj_name_2nd = $("#prj_name_2nd").val();
    var drawing_no= $("input[name='drawing_no']").val();
    //var ac_name= $("input[name='ac_name']").val();
    var part_no= $("input[name='part_no']").val();
    var thickness= $("input[name='thickness']").val();
    var height= $("input[name='height']").val();
    var width= $("input[name='width']").val();
    var count= $("input[name='count']").val();


    $.ajax({
        type:"post",
        url:url,
        cache:false,
        dataType:"json",
        data:{
            ac_id:ac_id,
            prj_name:prj_name,
            prj_name_2nd:prj_name_2nd,
            drawing_no:drawing_no,
            part_no:part_no,
            thickness:thickness,
            height:height,
            width:width,
            count:count
        },
        success:function(data){
            if(data.status == 'success'){
                $("#accountModal").modal("hide");
                location.reload();
            }else{
                alert(data.msg);
            }
        }
    });

});

// 点击修改按钮触发
function editAccount(ac_id){
    $.ajax({
        type:"GET",
        url:_ctx+"/account/find",
        data:{ac_id:ac_id},
        dataType:"json",
        cache:false,
        success:function(data){
            if(data.status == "success"){
                var obj = data.data;
                reloadModelData(_ctx+"/account/edit","编辑台账","更新",obj.ac_id,obj.prj_name,obj.prj_name_2nd,obj.drawing_no,obj.part_no,obj.thickness,obj.height,obj.width,obj.count,"edit");
                $("#accountModal").modal("show");
            }else{
                alert(data.msg);
            }
        }
    });
}

// 点击查询按钮触发
function searchAccount(){
    var prj_name_search = $("#prj_name_search").val();
    var prj_name_2nd_search = $("#prj_name_2nd_search").val();
    //var ac_name_search = $("#ac_name_search").val();
    var drawing_no_search = $("#drawing_no_search").val();
    var part_no_search = $("#part_no_search").val();
    var searchDate = $("#searchDate").val();
    location.href=_ctx+"/account/list?pageNo=1&prj_name="+prj_name_search+"&prj_name_2nd="+prj_name_2nd_search+"&drawing_no="+drawing_no_search+"&part_no="+part_no_search+"&add_time="+searchDate;
}