
function show() {
    if(keys.length===0) return;
    let html = '';
    html+='<div>'+keys[index]+'</div>';
    html+='<div>'+values[index]+'</div>';
    $("#text ul").append(html);
    let scrollHeight = $("#text").prop("scrollHeight");
    $("#text").scrollTop(scrollHeight,400);
    index = index + 1;
    if(index>=keys.length){
        clearInterval(timer);
    }
}

let keys = [];
let values = [];
let index = 0;
let timer;

function submit() {

    let ip = $("input[name=ip]").val().trim();
    let username = $("input[name=username]").val().trim();
    let password = $("input[name=password]").val().trim();
    let database = $("input[name=database]").val().trim();
    let port = $("input[name=port]").val().trim();

    let tablePrefix = $("input[name=tablePrefix]").val().trim();
    let tables = $("input[name=tables]").val().trim();

    let parentModule = $("input[name=parentModule]").val().trim();
    let consoleModule = $("input[name=consoleModule]").val().trim();
    let crudModule = $("input[name=crudModule]").val().trim();
    let apiModule = $("input[name=apiModule]").val().trim();

    let parentPackage = $("input[name=parentPackage]").val().trim();
    let consolePackage = $("input[name=consolePackage]").val().trim();
    let crudPackage = $("input[name=crudPackage]").val().trim();
    let apiPackage = $("input[name=apiPackage]").val().trim();

    let override = $("input[name=override]").val().trim();
    let removeColumn = $("input[name=removeColumn]").val().trim();

    let body = {
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
            "apiModule":apiModule
        },
        "pack":{
            "parentPackage":parentPackage,
            "consolePackage":consolePackage,
            "crudPackage":crudPackage,
            "apiPackage":apiPackage
        },
        "global":{
            "override":override
        }

    };

    let url = baseUrl + "generator/custom";
    $.ajax(url,{
        type : "post",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify(body),
        success:function (ret) {
            console.log(ret);
            if(ret && ret.success){
                if(ret.data){
                    clearInterval(timer);
                    $("#text ul").html('');
                    keys = [];
                    values = [];
                    index = 0;
                    timer = setInterval('show()',1000);
                    $.each(ret.data,function (k,v) {
                        keys.push(k);
                        values.push(v);
                    });
                }else {
                    $("#text ul").html(ret.message);
                }
            }else {
                $("#text ul").html(ret.message);
            }
        },
        error:function (err) {
            $("#text ul").html(err);
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
    $("input[name=apiModule]").val('');

    $("input[name=parentPackage]").val('');
    $("input[name=consolePackage]").val('');
    $("input[name=crudPackage]").val('');
    $("input[name=apiPackage]").val('');

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
    $("input[name=apiModule]").val('netty-chat-generator');

    $("input[name=parentPackage]").val('com.flx.netty.chat.user');
    $("input[name=consolePackage]").val('console');
    $("input[name=crudPackage]").val('crud');
    $("input[name=apiPackage]").val('api');

    $("input[name=override]").val('true');
    $("input[name=removeColumn]").val('id,state,create_user,create_time,update_user,update_time');

}