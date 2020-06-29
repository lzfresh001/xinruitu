  $("#btn_search").click(
      function () {
          var username=$("#username-search").val();
          var name=$("#name-search").val();
          var status=$("#status-search").val();
          location.href=_ctx+"/user/list?username="+username+"&name="+name+"&status="+status;
      }
  );
  $("#addData").click(function(){
      $.ajax({
          type:"GET",
          url:_ctx+"/user/createUsername",
          cache:false,
          dataType:"json",
          data:{},
          success:function(data){
              if(data.status == 'success'){
                  reloadModelData(_ctx+"/user/add","添加用户","添加","",data.data,"","",data.data,"1","add");
                  $("#userModal").modal("show");
              }else{
                  alert(data.msg);
              }
          }
      });
    });

    //新增或修改的模态框确认按钮
    $("#imgSubmit").click(function(){
        var userId = $("input[name='user_id']").val();
        var url = $("input[name='url']").val();
        var username= $("input[name='username']").val();
        var name= $("input[name='name']").val();
        var phone= $("input[name='phone']").val();
        var password= $("input[name='password']").val();
        var usertype= $("select[name='usertype']").val();
        if(url == _ctx+'/user/add' || url == _ctx+'/user/edit'){
            if(!checkAccount(username,name,phone,password,usertype)){
                return false;
            }
        }
        $.ajax({
            type:"post",
            url:url,
            cache:false,
            dataType:"json",
            data:{user_id:userId,username:username,password:password,name:name,phone:phone,usertype:usertype,time:new Date().getTime()},
            success:function(data){
                if(data.status == 'success'){
                    $("#userModal").modal("hide");
                    location.reload();
                }else{
                    alert(data.msg);
                }
            }
        });
    });

//禁用用户
function delUser(userId){
    if(confirm("你确定要禁用此用户吗？")){
        $.ajax({
            type:"POST",
            url:_ctx+"/user/del",
            dataType:"json",
            data:{user_id:userId},
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
}
//编辑用户
function editUser(userId){
    $.ajax({
        type:"GET",
        url:_ctx+"/user/find",
        data:{user_id:userId},
        dataType:"json",
        cache:false,
        success:function(data){
            if(data.status == "success"){
                var obj = data.data;
                reloadModelData(_ctx+"/user/edit","编辑用户","更新",obj.id,obj.username,obj.name,obj.phone,"",obj.usertype,"edit");
                $("#userModal").modal("show");
            }else{
                alert(data.msg);
            }
        }
    });
}

//验证数据
function checkAccount(username,name,phone,password){
    if(username == ''){
        $("input[name='username']").focus();
        alert("用户名不能为空");
        return false;
    }
    if(name == ''){
        $("input[name='name']").focus();
        alert("昵称不能为空");
        return false;
    }
    if(phone == ''){
        $("input[name='phone']").focus();
        alert("手机号码不能为空");
        return false;
    }
    if(password == ''){
        $("input[name='password']").focus();
        alert("密码不能为空");
        return false;
    }else if(password.length < 4){
        $("input[name='password']").focus();
        alert("密码个数不能少于4位");
        return false;
    }
    return true;
}

//加载模态框的数据
function reloadModelData(url,title,btntext,user_id,username,name,phone,password,usertype,type){
    $("#userModal #usermodelHead").text(title);
    $("#userModal #imgSubmit").text(btntext);
    $("input[name='url']").val(url);
    $("input[name='user_id']").val(user_id);
    $("input[name='username']").val(username);
    $("input[name='name']").val(name);
    $("input[name='phone']").val(phone);
    $("input[name='password']").val(password);
    $("select[name='usertype'] option[value="+usertype+"]").attr("selected", true);
    if(type == 'add'){
        $("input[name='username']").attr("disabled",false);
    }else{
        $("input[name='username']").attr("disabled",true);
    }
}

// 点击启用按钮触发
function reUser(userId) {
    if(confirm("你确定要启用此用户吗？")){
        $.ajax({
            type:"POST",
            url:_ctx+"/user/reUser",
            dataType:"json",
            data:{user_id:userId},
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
}

// 点击转为普通工触发
  function cutOfEmp(id) {
      if(confirm("你确定要将该管理员转普通员工吗？")){
          $.ajax({
              type:"POST",
              url:_ctx+"/user/cutOfEmp",
              dataType:"json",
              data:{id:id},
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
  }

  // 点击转为管理员触发
  function cutOfAdmin(id) {
      if(confirm("你确定要将该普通员工转管理员吗？")){
          $.ajax({
              type:"POST",
              url:_ctx+"/user/cutOfAdmin",
              dataType:"json",
              data:{id:id},
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
  }