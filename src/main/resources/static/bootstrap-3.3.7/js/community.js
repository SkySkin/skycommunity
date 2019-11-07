function post() {
    var questionId=$("#question_id").val();
    var content=$("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (response) {

            //如何返回200则正常，吧评论框隐藏
            if (response.code == 200) {
                $("#comment_section").hide();
            }else {
                //如果1003 ,则需要进行登陆
                if (response.code == 1003) {
                    //将要登陆的信息提示处理
                    var b = confirm(response.message);
                    //如果选择true
                    if (b) {
                        window.open("https://github.com/login/oauth/authorize?client_id=1d26332cf6655bb56e0e&redirect_uri=http://192.168.1.202:8889/callback&scope=user&state=1");
                        window.localStorage.setItem("closeable",true);
                    }
                }else {
                    alert(response.message);

                }
            }
            console.log(response);
        },
        dataType: "json",
        contentType:"application/json"
    });
    console.log(questionId);
    console.log(content);
}