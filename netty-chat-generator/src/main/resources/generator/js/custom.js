
var baseUrl = "http://127.0.0.1:8080/";

function submit() {

    var ip = $("input[name=ip]").val().trim();
    var username = $("input[name=username]").val().trim();
    var password = $("input[name=password]").val().trim();
    var database = $("input[name=database]").val().trim();
    var port = $("input[name=port]").val().trim();

    var tablePrefix = $("input[name=tablePrefix]").val().trim();
    var tables = $("input[name=tables]").val().trim();

    var parentModule = $("input[name=parentModule]").val().trim();
    var consoleModule = $("input[name=consoleModule]").val().trim();
    var crudModule = $("input[name=crudModule]").val().trim();
    var clientModule = $("input[name=clientModule]").val().trim();

    var parentPackage = $("input[name=parentPackage]").val().trim();
    var consolePackage = $("input[name=consolePackage]").val().trim();
    var crudPackage = $("input[name=crudPackage]").val().trim();
    var clientPackage = $("input[name=clientPackage]").val().trim();

    var override = $("input[name=override]").val().trim();
    var removeColumn = $("input[name=removeColumn]").val().trim();

    var body = {
        "dataSource":{
            "ip":ip,
            "port":port,
            "username":username,
            "password":password,
            "database":database
        },
        "table":{
            "tables":tables,
            "tablePrefix":tablePrefix,
            "removeColumn":removeColumn
        },
        "module":{
            "parentModule":parentModule,
            "consoleModule":consoleModule,
            "crudModule":crudModule,
            "clientModule":clientModule
        },
        "pack":{
            "parentPackage":parentPackage,
            "consolePackage":consolePackage,
            "crudPackage":crudPackage,
            "clientPackage":clientPackage
        },
        "global":{
            "override":override
        }

    };

    var url = baseUrl + "generator/custom";
    $.ajax(url,{
        type : "post",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify(body),
        success:function (ret) {
            console.log(ret);
            if(ret && ret.success){
                if(ret.data){
                    var html = '';
                    $.each(ret.data,function (k,v) {
                        html+='<div>'+k+'</div>';
                        html+='<div>'+v+'</div>';
                    });
                    $("#logPan").html(html);
                }else {
                    $("#logPan").html(ret.message);
                }
            }else {
                $("#logPan").html(ret.message);
            }
        },
        error:function (err) {
            $("#logPan").html(err);
        }
    })

}

function reset() {

    $("input[name=ip]").val('');
    $("input[name=username]").val('');
    $("input[name=password]").val('');
    $("input[name=database]").val('');
    $("input[name=port]").val('');

    $("input[name=tablePrefix]").val('');
    $("input[name=tables]").val('');

    $("input[name=parentModule]").val('');
    $("input[name=consoleModule]").val('');
    $("input[name=crudModule]").val('');
    $("input[name=clientModule]").val('');

    $("input[name=parentPackage]").val('');
    $("input[name=consolePackage]").val('');
    $("input[name=crudPackage]").val('');
    $("input[name=clientPackage]").val('');

    $("input[name=override]").val('');
    $("input[name=removeColumn]").val('');

}

function fill() {

    $("input[name=ip]").val('127.0.0.1');
    $("input[name=username]").val('root');
    $("input[name=password]").val('root123');
    $("input[name=database]").val('web_chat');
    $("input[name=port]").val('3306');

    $("input[name=tablePrefix]").val('web_');
    $("input[name=tables]").val('web_group_user,web_group_message');

    $("input[name=parentModule]").val('');
    $("input[name=consoleModule]").val('netty-chat-generator');
    $("input[name=crudModule]").val('netty-chat-generator');
    $("input[name=clientModule]").val('netty-chat-generator');

    $("input[name=parentPackage]").val('com.flx.netty.chat');
    $("input[name=consolePackage]").val('user');
    $("input[name=crudPackage]").val('user');
    $("input[name=clientPackage]").val('user');

    $("input[name=override]").val('true');
    $("input[name=removeColumn]").val('id,state,create_user,create_time,update_user,update_time');

}