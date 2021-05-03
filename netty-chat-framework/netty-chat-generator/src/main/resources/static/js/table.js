
let tableArray = [];
function checkboxOnClick(e) {
    if(e.checked===true){
        tableArray.push($(e).val())
    }else {
        for(let i in tableArray){ //input 取消
            if(tableArray[i] === $(e).val()){
                tableArray.splice(i,1); //移出此项内容
            }
        }
    }
    $("input[name=tables]").val(tableArray.join(','));
}

function getTables() {

    let ip = $("input[name=ip]").val().trim();
    let username = $("input[name=username]").val().trim();
    let password = $("input[name=password]").val().trim();
    let database = $("input[name=database]").val().trim();
    let port = $("input[name=port]").val().trim();

    if(!ip){
        alert("IP不能为空！");
    }
    if(!username){
        alert("用户名不能为空！");
    }
    if(!password){
        alert("密码不能为空！");
    }
    if(!database){
        alert("数据库不能为空！");
    }
    if(!port){
        alert("端口不能为空！");
    }

    let url = baseUrl + "generator/tables";
    $.ajax(url,{
        type : "get",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:{
            "ip":ip,
            "port":port,
            "database":database,
            "username":username,
            "password":password
        },
        success:function (ret) {
            console.log(ret);
            $(".tableDiv").html('');
            if(ret && ret.success){
                $.each(ret.data,function (index,value) {
                    $(".tableDiv").append(" <label><input type='checkbox' name='table' value='"+value+"' onclick='checkboxOnClick(this)'/>"+value+"</label>");
                });
            }else {
                $(".tableDiv").html(ret.message);
            }
        },
        error:function (err) {
            $(".tableDiv").html(err);
        }
    })
}