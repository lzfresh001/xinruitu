// 点击查询按钮触发
$("#btn_search").click(function () {
    var start_date = $("#start_date_search").val();
    var end_date = $("#end_date_search").val();
    location.href=_ctx+"/statement/four?start_date="+start_date+"&end_date="+end_date+"&process_type="+"其他四";

});