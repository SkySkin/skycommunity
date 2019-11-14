//提交回复
function post(postId, type) {
    var comment_section = "";
    comment_section = "comment_section-" + postId;
    if (postId == 0) {
        //当不存在id时，自动获取问题的id
        postId = $("#question_id").val();
        comment_section = "comment_section";
    }
    var content = null;
    if (type == 1) {
        content = $("#comment_content").val();
    } else {
        content = $("#comment_content-" + postId + "").val();
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": postId,
            "content": content,
            "type": type
        }),
        success: function (response) {

            //如果返回200则正常，吧评论框隐藏
            if (response.code == 200) {
                $("#" + comment_section + "").hide();
                createNewComment(postId, type, content);

            } else {
                //如果1003 ,则需要进行登陆
                if (response.code == 1003) {
                    //将要登陆的信息提示处理
                    var b = confirm(response.message);
                    //如果选择true
                    if (b) {
                        var pathname = window.location.pathname;
                        var search = window.location.search;
                        var subpath = "";
                        window.open("https://github.com/login/oauth/authorize?client_id=1d26332cf6655bb56e0e&scope=user&state=1&redirect_uri=http://localhost:8889/callback?uri=" + subpath + "");
                        window.localStorage.setItem("closeable", true);
                    }
                } else {
                    alert(response.message);

                }
            }
            // console.log(response);
        },
        dataType: "json",
        contentType: "application/json"
    });
    // console.log(questionId);
    // console.log(content);
}


function createNewComment(postId, type, content) {
    //如果为1的话我们对以及评论进行设置值
    if (type == 1) {
        //把值设置进去
        $("#write_comment").text(content);
        //取消隐藏
        $("#comment_hide").removeClass("hide");
    } else {
        //如果是二，则进行二级评论的数据增加
        //把值设置进去
        $("#write_comment-" + postId + "").text(content);
        //取消隐藏
        $("#comment_hide-" + postId + "").removeClass("hide");
    }


}


//展开二级评论

function collapseComments(e) {
    //当点击时进行data-id的获取
    var mark = e.getAttribute("mark");
    var id = e.getAttribute("data-id");
    //通过ID得到comment
    var comment = $("#comment-" + id);
    //判断是否打开二级评论
    if ("close" == mark) {
        //判断子元素数量是否大于2，如果大于2则不需要再去请求数据
        if (comment.children().length > 2) {
            e.setAttribute("mark", "open");
            comment.fadeIn("slow");
            $(e).removeClass("twocontent")
            $(e).addClass("fadeIn");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                var commenttwolist = $("#comment-" + id);
                // var length =0;
                // console("length1"+length1)
                var items = [];
                // commenttwolist
                $.each(data.data, function (key, val) {
                    // console.log(val)
                    // console.log(val.user.avatarUrl)
                    // console.log(val.user.name)
                    // console.log(timestampToTime(val.gmtCreate))
                    // console.log(val.content)
                    items.push('<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 commentitem">\n' +
                        '    <div class="media two-content-media">\n' +
                        '    <div class="media-left">\n' +
                        '    <span href="#">\n' +
                        '    <img class="media-object img-thumbnail" src="' + val.user.avatarUrl + '">\n' +
                        '    </span>\n' +
                        '    </div>\n' +
                        '    <div class="media-body">\n' +
                        '    <small class="media-heading">\n' +
                        '    <span style="font-size: 13px;">' + val.user.name + '</span>\n' +
                        '    <!--时间-->\n' +
                        '    <span class="pull-right" style="color: #999">' + timestampToTime(val.gmtCreate) + '</span>\n' +
                        '    </small>\n' +
                        '    <br>\n' +
                        '    <!--评论内容-->\n' +
                        '    <div style="font-size: 13px;color: #303030;" class="comment_contex">' + val.content + '\n' +
                        '    </div>\n' +
                        '    </div>\n' +
                        '    </div>\n' +
                        '    <hr class="col-lg-12 col-md-12 col-sm-12 col-xs-12">\n' +
                        '    </div>');
                });
                commenttwolist.prepend(items.join(""))
            });
        }
        e.setAttribute("mark", "open");
        comment.fadeIn("slow");
        $(e).removeClass("twocontent")
        $(e).addClass("fadeIn");
    } else {
        comment.fadeOut("slow");
        e.setAttribute("mark", "close");
        $(e).addClass("twocontent")
        $(e).removeClass("fadeIn");
    }

}


//进行登陆
function logsub() {
    var pathname = window.location.pathname;
    var search = window.location.search;
    var subpath = pathname + search;
    window.close();
    window.open("https://github.com/login/oauth/authorize?client_id=1d26332cf6655bb56e0e&scope=user&state=1&redirect_uri=http://localhost:8889/callback?uri=" + subpath + "");
    // window.localStorage.setItem("closeable", true);

}

function timestampToTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = date.getDate() + ' ';
    var h = date.getHours() + ':';
    var m = date.getMinutes();
    // var s = date.getSeconds();
    return Y + M + D + h + m;
}

// function createNewComment(content) {
//     var commentItem = $("#comment_item");
//     var userName = "";
//     var userAvatarUrl = "";
//     $.ajax({
//         type: "POST",
//         url: "/addCommentInHtml",
//         success: function (response) {
//             userName = response.name;
//             userAvatarUrl = response.avatarUrl;
//             console.log(response);
//             console.log(response.name);
//             console.log(response.avatarUrl);
//         }
//     })
//     commentItem.append(' <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">' +
//         '<div class="media">\n' +
//         '    <div class="media-left">\n' +
//         '                        <span href="#">\n' +
//         '                            <img class="media-object img-thumbnail"\n' +
//         '                                 src="'+userAvatarUrl+'">\n' +
//         '                        </span>\n' +
//         '    </div>\n' +
//         '    <div class="media-body">\n' +
//         '        <small class="media-heading">\n' +
//         '            <span style="font-size: 13px;" >'+userName+'</span>\n' +
//         '        </small>\n' +
//         '        <br>\n' +
//         '        <!--评论内容-->\n' +
//         '        <div style="font-size: 13px;color: #303030;"  class="comment_contex">'+content+'\n' +
//         '        </div>\n' +
//         '        <!--点赞  回复-->\n' +
//         '        <div class="comment_list">\n' +
//         '            <!--点赞-->\n' +
//         '            <span class="operate">\n' +
//         '                                <a class="agree"><i class="glyphicon glyphicon-thumbs-up "\n' +
//         '                                                    data-original-title="赞同回复"></i> <b class="count">0</b></a>\n' +
//         '                                <a class="disagree "><i class="glyphicon glyphicon-thumbs-down "\n' +
//         '                                                        data-original-title="对回复持反对意见"></i></a>\n' +
//         '                            </span>\n' +
//         '            <span class="operate"><a><i class="glyphicon glyphicon-comment"></i><b class="count">&nbsp;0</b></a></span>\n' +
//         '            <span class="pull-right">1秒前</span>\n' +
//         '        </div>\n' +
//         '    </div>\n' +
//         '</div>\n' +
//         '<hr class="col-lg-12 col-md-12 col-sm-12 col-xs-12">');


// }