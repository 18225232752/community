$(function () {
    $("#publishBtn").click(publish);
});

function publish() {
    $("#publishModal").modal("hide");

    // 发送ajax请求之前，将csrf_token设置到请求头中
    // let token = $("meta[name='_csrf']").attr("content");
    // let header = $("meta[name='_csrf_header']").attr("content");
    // $(document).ajaxSend(function (e, xhr, options) {
    //     xhr.setRequestHeader(header, token);
    // })

    // 获取帖子标题与内容
    let title = $('#recipient-name').val();
    let content = $('#message-text').val();
    // 发送异步请求
    $.post(
        CONTEXT_PATH + "/discuss/add",
        {"title":title, "content":content},
        function (data) {
            data = $.parseJSON(data);
            // 在提示框显示返回消息
            $('#hintBody').text(data.msg);
            // 显示提示框
            $("#hintModal").modal("show");
            // 2s后隐藏提示框
            setTimeout(function () {
                $("#hintModal").modal("hide");
                // 刷新页面
                if (data.code === 0) {
					window.location.reload();
                }
            }, 2000);
        }
    )


}