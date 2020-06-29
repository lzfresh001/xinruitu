


// 验证图号、名称、件号所选的值,获取长、宽、厚
function getTHWValue(drawing_no,part_no) {
    $.ajax({
        type:"GET",
        url:"/wx/getTHWValue",
        cache:false,
        dataType:"json",
        data:{
            drawing_no:drawing_no,
            part_no:part_no
        },
        success:function(data){
            if(data.status == "success"){
                var obj = data.data;
                if(null != obj){
                    $("#thickness").val(obj.thickness);
                    $("#height").val(obj.height);
                    $("#width").val(obj.width);
                } else {
                    $("#thickness").val("");
                    $("#height").val("");
                    $("#width").val("");
                }
            }else{
                alert(data.msg);
            }
        }
    });
}

// 选择图号、名称、件号触发
function dnChange() {
    var drawing_no = $("#drawing_no").val();
    //var ac_name = $("#ac_name").val();
    var part_no = $("#part_no").val();
    if(null != drawing_no && drawing_no != "" && null != part_no && part_no != ""){
        getTHWValue(drawing_no,part_no);
    }
}
function anChange() {
    var drawing_no = $("#drawing_no").val();
    var ac_name = $("#ac_name").val();
    var part_no = $("#part_no").val();
    if(null != drawing_no && drawing_no != "" && null != ac_name && ac_name != "" && null != part_no && part_no != ""){
        getTHWValue(drawing_no,ac_name,part_no);
    }
}
function pnoChange() {
    var drawing_no = $("#drawing_no").val();
    //var ac_name = $("#ac_name").val();
    var part_no = $("#part_no").val();
    if(null != drawing_no && drawing_no != "" && null != part_no && part_no != ""){
        getTHWValue(drawing_no,part_no);
    }
}

// 选择工序时触发
function ptChange() {
    var process_type = $("#process_type").val();
    if(process_type == "刨边"){
        $("#single_weight_div").show();
        $("#single_hole_div").hide();
        $("#single_length_div").hide();
    }
    if(process_type == "拼接板钻孔" || process_type == "杆件钻孔" || process_type == "磁力钻钻孔"
        || process_type == "其他一" || process_type == "其他二" || process_type == "其他三" || process_type == "其他四" || process_type == "其他五"){
        $("#single_weight_div").hide();
        $("#single_hole_div").show();
        $("#single_length_div").hide();
    }
   /* if(process_type == "拼接板钻孔" || process_type == "杆件钻孔" || process_type == "磁力钻钻孔"){
        $("#holeText").text("单件孔数");
    }
    if(process_type == "其他一" || process_type == "其他二" || process_type == "其他三" || process_type == "其他四" || process_type == "其他五"){
        $("#holeText").text("单件数量");
    }*/

    if(process_type == "焰切"){
        $("#single_weight_div").hide();
        $("#single_hole_div").hide();
        $("#single_length_div").show();
    }
}


function checkSearchDate(date1,date2) {

    var msel = Date.parse(date2) - Date.parse(date1);
    var day = Math.ceil(msel / (24 * 3600 * 1000));
    if(date1 > date2){
        alert("起始时间不能大于结束时间");
        return false;
    }
    if(day > 9){
        alert("时间区间不能超过10天");
        return false;
    }
    return true;
}


// 点击提交查询触发
function searchLog() {
    var prj_name_search = $("#prj_name_search").val();
    var prj_name_2nd_search = $("#prj_name_2nd_search").val();
    var part_no_search = $("#part_no_search").val();
    var add_user_search = $("#add_user_search").val();
    var process_type_search = $("#process_type_search").val();
    var status_search = $("#status_search").val();
    var searchDate = $("#searchDate").val();
    var searchDateL = $("#searchDateL").val();
    if(null != searchDate && null != searchDateL){
        if(checkSearchDate(searchDate,searchDateL)){
            location.href="/accountLog/list?prj_name=" + prj_name_search  + "&prj_name_2nd=" + prj_name_2nd_search + "&part_no=" + part_no_search + "&add_user=" + add_user_search +"&process_type=" + process_type_search + "&status=" + status_search + "&add_time=" + searchDate + "&add_time_l=" + searchDateL;
        }
    }else{
        location.href="/accountLog/list?prj_name=" + prj_name_search  + "&prj_name_2nd=" + prj_name_2nd_search + "&part_no=" + part_no_search + "&add_user=" + add_user_search +"&process_type=" + process_type_search + "&status=" + status_search + "&add_time=" + searchDate + "&add_time_l=" + searchDateL;
    }
}

// 点击添加触发
function goAddPage() {
    $("#accLogModelHead").text("添加日志");
    $("#url").val("/accountLog/add");
    $("input[name='id']").val("");
    $("#prj_name").val("");
    $("#prj_name_2nd").val("");
    $("#drawing_no").val("");
    $("#ac_name").val("");
    $("#part_no").val("");
    $("#process_type").val("");
    $("input[name='count']").val("");
    $("input[name='thickness']").val("");
    $("input[name='height']").val("");
    $("input[name='width']").val("");
    $("input[name='single_weight']").val("");
    $("input[name='single_hole']").val("");
    $("input[name='single_length']").val("");
    $("input[name='total']").val("");
    $("input[name='coefficient']").val("");
    $("#accountLogModal").modal("show");
}

//点击保存按钮触发
function saveLog() {

    //alert(999);
    var url = $("#url").val();
    var id = $("input[name='id']").val();
    var prj_name = $("#prj_name").val();
    var prj_name_2nd = $("#prj_name_2nd").val();
    var drawing_no = $("#drawing_no").val();
    var ac_name = $("#ac_name").val();
    var part_no = $("#part_no").val();
    var process_type = $("#process_type").val();
    var count = $("input[name='count']").val();
    var thickness = $("input[name='thickness']").val();
    var height = $("input[name='height']").val();
    var width = $("input[name='width']").val();
    var single_weight = $("input[name='single_weight']").val();
    var single_hole = $("input[name='single_hole']").val();
    var single_length;
    var total;
    var coefficient = $("input[name='coefficient']").val();

    if(null == prj_name || prj_name == ""){
        alert("项目名称不能为空");
        return false;
    }
    if(null == count || count == ""){
        alert("数量不能为空");
        return false;
    }
    if(null == coefficient || coefficient == ""){
        alert("系数不能为空");
        return false;
    }

    if(process_type != "拼接板钻孔" && process_type != "杆件钻孔" && process_type != "磁力钻钻孔" && process_type != "刨边" && process_type != "焰切"
        && process_type != "其他一" && process_type != "其他二" && process_type != "其他三" && process_type != "其他四" && process_type != "其他五"){
        alert("请选择正确的工序");
        return false;
    }

    if(process_type == "焰切"){
        single_length = (parseFloat(height) + parseFloat(width)) * 2;
        total = parseFloat(single_length) * parseFloat(count);
        //$("#single_length").val(single_length);
    }else if(process_type == "刨边"){
        total = parseFloat(single_weight) * parseFloat(count);
    } else {
        total = parseFloat(single_hole) * parseFloat(count);
    }

    $.ajax({
        type:"post",
        url:_ctx+url,
        cache:false,
        dataType:"json",
        data:{
            id:id,
            prj_name:prj_name,
            prj_name_2nd:prj_name_2nd,
            drawing_no:drawing_no,
            ac_name:ac_name,
            part_no:part_no,
            process_type:process_type,
            count:count,
            thickness:thickness,
            height:height,
            width:width,
            single_weight:single_weight,
            single_hole:single_hole,
            single_length:single_length,
            total:total,
            coefficient:coefficient
        },
        success:function(data){
            if(data.status == 'success'){
                $("#accountLogModal").modal("hide");
                location.reload();
            }else{
                alert(data.msg);
            }
        }
    });
}


// 点击查看/编辑触发
function checkLog(id) {
    //alert(890);
    $("#url").val("/accountLog/edit");
    $.ajax({
        type:"GET",
        url:"/wx/findLog",
        data:{id:id},
        dataType:"json",
        cache:false,
        success:function(data){
            if(data.status == "success"){
                var aLobj = data.data;
               /* if(aLobj.status == "0"){
                    //$("#imgSubmit").hide();
                    //$("#subBtn").show();
                    $("#prj_name").remove("disabled");
                    $("#prj_name_2nd").remove("disabled");
                    $("#drawing_no").remove("disabled");
                    $("#ac_name").remove("disabled");
                    $("#part_no").remove("disabled");
                    $("#process_type").remove("disabled");
                    $("input[name='count']").remove("readonly");
                    $("input[name='thickness']").remove("readonly");
                    $("input[name='height']").remove("readonly");
                    $("input[name='width']").remove("readonly");
                    $("input[name='single_weight']").remove("readonly");
                    $("input[name='single_hole']").remove("readonly");
                    $("input[name='single_length']").remove("readonly");
                    $("input[name='coefficient']").remove("readonly");
                }*/
                /*if(aLobj.status == "1"){
                    $("#imgSubmit").hide();
                    //$("#subBtn").hide();
                    $("#prj_name").attr("disabled","disabled");
                    $("#prj_name_2nd").attr("disabled","disabled");
                    $("#drawing_no").attr("disabled","disabled");
                    $("#ac_name").attr("disabled","disabled");
                    $("#part_no").attr("disabled","disabled");
                    $("#process_type").attr("disabled","disabled");
                    $("input[name='count']").attr("readonly","readonly");
                    $("input[name='thickness']").attr("readonly","readonly");
                    $("input[name='height']").attr("readonly","readonly");
                    $("input[name='width']").attr("readonly","readonly");
                    $("input[name='single_weight']").attr("readonly","readonly");
                    $("input[name='single_hole']").attr("readonly","readonly");
                    $("input[name='single_length']").attr("readonly","readonly");
                    $("input[name='coefficient']").attr("readonly","readonly");
                }*/
                if(aLobj.process_type == "刨边"){
                    $("#single_weight_div").show();
                    $("#single_hole_div").hide();
                    $("#single_length_div").hide();
                }
                if(aLobj.process_type == "拼接板钻孔" || aLobj.process_type == "杆件钻孔" || aLobj.process_type == "磁力钻钻孔"
                    || aLobj.process_type == "其他一" || aLobj.process_type == "其他二" || aLobj.process_type == "其他三" || aLobj.process_type == "其他四" || aLobj.process_type == "其他五"){
                    $("#single_weight_div").hide();
                    $("#single_hole_div").show();
                    $("#single_length_div").hide();
                }
               /* if(aLobj.process_type == "拼接板钻孔" || aLobj.process_type == "杆件钻孔" || aLobj.process_type == "磁力钻钻孔"){
                    $("#holeText").text("单件孔数");
                }
                if(aLobj.process_type == "其他一" || aLobj.process_type == "其他二" || aLobj.process_type == "其他三" || aLobj.process_type == "其他四" || aLobj.process_type == "其他五"){
                    $("#holeText").text("单件数量");
                }*/

                if(aLobj.process_type == "焰切"){
                    $("#single_weight_div").hide();
                    $("#single_hole_div").hide();
                    $("#single_length_div").show();
                }
                $("#accLogModelHead").text("编辑日志");
                $("input[name='id']").val(aLobj.id);
                $("#prj_name").val(aLobj.prj_name);
                $("#prj_name_2nd").val(aLobj.prj_name_2nd);
                $("#drawing_no").val(aLobj.drawing_no);
                $("#ac_name").val(aLobj.ac_name);
                $("#part_no").val(aLobj.part_no);
                $("#process_type").val(aLobj.process_type);
                $("input[name='count']").val(aLobj.count);
                $("input[name='thickness']").val(aLobj.thickness);
                $("input[name='height']").val(aLobj.height);
                $("input[name='width']").val(aLobj.width);
                $("input[name='single_weight']").val(aLobj.single_weight);
                $("input[name='single_hole']").val(aLobj.single_hole);
                $("input[name='single_length']").val(aLobj.single_length);
                $("input[name='total']").val(aLobj.total);
                $("input[name='coefficient']").val(aLobj.coefficient);
                //$("textarea[name='reply']").val(aLobj.reply);
                $("#accountLogModal").modal("show");
            }else{
                alert(data.msg);
            }
        }
    });
}


// 点击继续添加触发
function goReAddPage() {

    $("input[name='id']").val("");
    $("#url").val("/accountLog/add");
    $("#prj_name").val("");
    $("#prj_name_2nd").val("");
    $("#drawing_no").val("");
    $("#ac_name").val("");
    $("#part_no").val("");
    $("#process_type").val("");
    $("input[name='count']").val("");
    $("input[name='thickness']").val("");
    $("input[name='height']").val("");
    $("input[name='width']").val("");
    $("input[name='single_weight']").val("");
    $("input[name='single_hole']").val("");
    $("input[name='single_length']").val("");
    $("input[name='coefficient']").val("");

    $.ajax({
        type:"GET",
        url:"/accountLog/lastLogByUid",
        data:{},
        dataType:"json",
        cache:false,
        success:function(data){
            if(data.status == "success"){
                var LastObj = data.data;
                $("#accLogModelHead").text("添加日志");
                if(null != LastObj){
                    $("#prj_name").val(LastObj.prj_name);
                    $("#prj_name_2nd").val(LastObj.prj_name_2nd);
                    $("#ac_name").val(LastObj.ac_name);
                    $("#process_type").val(LastObj.process_type);
                    $("input[name='coefficient']").val(LastObj.coefficient);
                    if(LastObj.process_type == "刨边"){
                        $("#single_weight_div").show();
                        $("#single_hole_div").hide();
                        $("#single_length_div").hide();
                    }
                    if(LastObj.process_type == "拼接板钻孔" || LastObj.process_type == "杆件钻孔" || LastObj.process_type == "磁力钻钻孔"
                        || LastObj.process_type == "其他一" || LastObj.process_type == "其他二" || LastObj.process_type == "其他三" || LastObj.process_type == "其他四" || LastObj.process_type == "其他五"){
                        $("#single_weight_div").hide();
                        $("#single_hole_div").show();
                        $("#single_length_div").hide();
                    }
                    /*if(LastObj.process_type == "拼接板钻孔" || LastObj.process_type == "杆件钻孔" || LastObj.process_type == "磁力钻钻孔"){
                        $("#holeText").text("单件孔数");
                    }
                    if(LastObj.process_type == "其他一" || LastObj.process_type == "其他二" || LastObj.process_type == "其他三" || LastObj.process_type == "其他四" || LastObj.process_type == "其他五"){
                        $("#holeText").text("单件数量");
                    }*/

                    if(LastObj.process_type == "焰切"){
                        $("#single_weight_div").hide();
                        $("#single_hole_div").hide();
                        $("#single_length_div").show();
                    }
                }

                $("#accountLogModal").modal("show");
            }else{
                alert(data.msg);
            }
        }
    });
}

// 点击全选触发
$("#checkAll").click(function () {
    if($(this).is(":checked")){
        $("input[name='chooseInfo']").each(function () {
            $(this).prop("checked",true);
        });
    }else{
        $("input[name='chooseInfo']").each(function () {
            $(this).prop("checked",false);
        });
    }
});

// 点击审核触发
function goCheck() {
    var ids = "";
    $("input[name='chooseInfo']").each(function () {
        if($(this).is(":checked")){
            ids += $(this).val() + ",";
        }
    });
    if(null == ids || ids == ""){
        alert("请选择需要审核记录");
        return false;
    }
    var endIndex = ids.length - 1;
    ids = ids.substring(0,endIndex);
    $.ajax({
        type:"POST",
        url:"/accountLog/checkAccLog",
        dataType:"json",
        data:{ids:ids},
        cache:false,
        success:function(data){
            if(data.status == 'success'){
                location.reload();
            }else{
                alert(data.msg);
            }
        }
    });
}


