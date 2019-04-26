function getDetail(data) {
    var obj = $('#st').datagrid('selectRow',data);
    console.log(obj);
}

$(function(){
    //创建data_grid
    $("#st").datagrid({
        
        url:'/easyUI', //数据来源
        //冻结列
        //列的定义
        columns:[[ 

            {field:'id',title:'id',width:50},
            {field:'title',title:'title',width:50},
            {field:'src',title:'src',width:50,align:'right'},
            {field:'author',title:'作者',width:50,align:'center'},
            {field:'oper',title:'操作',width:50,align:'right',
                formatter:function(val,row,index){

                    return '<div class="td-lnks"><a id="delete" title="删除" class="toggle-tooltip"><span class="glyphicon glyphicon-trash"></span></a><a title="查看" class="toggle-tooltip" onclick="getDetail('+index+')"><span class="glyphicon glyphicon-search"></span></a></div>';
                }
            }
        ]],
        
        fitColumns:true,//列自适应宽度，不能和冻结列同时设置为true
        striped:false,//斑马线效果
        idField:'id',//主键列
        rownumbers:true,//显示行号
        singleSelect:false,//是否单选
        pagination:true,//显示分页栏
        pageList:[10,20,50,100],//每页行数选择列表
        pageSize:10,//每页行数
        remoteSort:true,//是否服务器端排序，设成false，才能客户端排序
        sortName:'',//设置排序列
        sortOrder:'desc',//排序方式
        queryParams: {
            title: $("#title").val(),
            subject: $("#topic").val(),
        }
    });

})
$("#delete").on('click',function (e) {
    e.preventDefault();
    var row = $('#st').datagrid('getSelected');
    console.log(row);
    console.log(row.title)

})
