/***
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);
}

function comment2target(targetId, type, content) {
    if (!content || content.trim() == ""){
        alert("不能回复空内容");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:'application/json',
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (res) {
            console.log(res);
            if(res.code==200){
                $("#comment_section").hide();
                window.location.reload();
            }else{
                if (res.code == 2003){
                    var isAccepted = confirm(res.message);
                    if (isAccepted){
                        window.localStorage.setItem("closable", true);
                        window.open("https://github.com/login/oauth/authorize?client_id=54050158b4b1731e749e&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                    }
                }else{
                    alert(res.message);
                }
            }

        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId,2, content);
}

window.onload=function () {
    //实现页面加自动使用function---collapseComments
    var es = $(".menu-id");
    //console.log(es);
    for (var i=0;i<es.length;i++){
        //console.log(es[i].children[1]);
        collapseComments(es[i].children[1]);
    }
}
/***
 *显示/隐藏二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    //获取二级评论展开状态
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else{
        $.getJSON("/comment/"+id, function (data) {
            //console.log(data);
            var subCommentContainer = $("#comment-"+id);

            $.each(data.data,function (index, comment) {
                var c = $("<div/>",{
                    "class": "col-xs-12 comments",
                    html: comment.content
                });
                subCommentContainer.prepend(c);
            });

            //展开二级评论
            comments.addClass("in");
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        });
    }

    //也可以用hasClass的方式
    // if(comments.hasClass("in")){
    //     comments.removeClass("in");
    // }else{
    //     comments.addClass("in");
    // }
}