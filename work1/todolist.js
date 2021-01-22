let date = new Date();
document.getElementsByClassName("main-date")[0].innerHTML=date.toDateString();
//存放任务的集合
let taskData = new Array();
//任务构造方法
function Task(isDelete,content){
    this.isDelete = isDelete;
    this.content = content;
}
//提示框个数
let alterNumber = 0;

/*$('.task-in').mouseout(function(){
    $('.botton1').css("display","none");
})
$('.task-in').mouseover(function(){
    $('.botton1').css("display","inline-block");
})*/
//刷新css样式
/*function flashCss(){
    $('.task-in').css({'width':'100%',
        'background-color': 'aquamarine',
        'padding-left': '10px',
        'padding-top': '10px',
        'padding-bottom': '10px',
        'float': 'left',
        'margin-bottom': '10px'})
    $('.task-in input').css({'height':'40px',
        'width':'73%',
        'font-size': '25px',
        'border': '0',
        'background-color': 'aquamarine',
        'outline': 'white' })
    $('.bottom-input').css({'position': 'relative',
        'height':'40px',
        'margin-top': '-10px',
        'display': 'none'})
}*/
//刷新监听器
function flashOn(i){
    $('#task-in'+i).mouseout(function(){
        $('.botton'+i).css("display","none");
    })
    $('#task-in'+i).mouseover(function(){
        $('.botton'+i).css("display","inline-block");
    })
    $('.botton'+i+'[modify]').click(function(){
        taskData[i].content = $('#task-in'+i+' input').val();
        alerts("修改成功");
    })
    $('.botton'+i+'[complete]').click(function(){
        taskData[i].isDelete = true;
        flashTask();
        alerts("该任务完成");
    })
    $('.botton'+i+'[delete]').click(function(){
        taskData.splice(i,1);
        flashTask();
        alerts("删除成功");
    })
}
//刷新方法
function flashTask(){
    /*
    if(taskData.length==0){
        $(".none-title").css("'display':'none'")
        return;
    }else{
        $(".none-title").css("'display':'none'");
    }*/

    //删除之前的元素
    $('#task-header').empty();
    //是否为空
    if(taskData.length==0){
        let el = $('<span class="none-title">请添加任务</span>');
        $('#task-header').append(el);
        return;
    }
    //添加未被删去的元素
    for(let i =0;i<taskData.length;i++){
        if(taskData[i].isDelete==false){
            let el = $('<div id="task-in'+i+'" class="task-in">'+
            '<form >'+
                '<input  value="'+taskData[i].content+'" type="text"> '+
                '<button type="button" modify class="botton'+i+ ' bottom-input btn btn-warning">修改</button>'+
                '<button type="button" complete class="botton'+i+ ' bottom-input btn btn-danger">完成</button>'+
            '</form>'+
        '</div>');
        $('#task-header').append(el);
        flashOn(i);
        }
    }
    //添加已经完成的元素
    for(let i =0;i<taskData.length;i++){
        if(taskData[i].isDelete!=false){
            let el = $('<div completed id="task-in'+i+'" class="task-in">'+
            '<form >'+
                '<input  value="'+taskData[i].content+'" type="text"> '+
                '<button type="button" modify class="botton'+i+ ' bottom-input btn btn-warning">修改</button>'+
                '<button type="button" delete class="botton'+i+' bottom-input btn btn-danger">删除</button>'+
            '</form>'+
        '</div>');
        $('#task-header').append(el);
        flashOn(i);
        }
    }
}
$(".botton-input").click(function(){
    let input = $("#to-task");
    //如果输入结果为空返回
    if(input.val()==""){
        return;
    }
    taskData.push(new Task(false,input.val()));
    flashTask();
    input.val("");
})
//设置提示框
function alerts(content){
    alterNumber++;
    let el = $('<div id="alter'+alterNumber+'" class="alert alert-success">'+content+'</div>');
    $('body').append(el);
    $('#alter'+alterNumber).fadeOut(3000);
}

