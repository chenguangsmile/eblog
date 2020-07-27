if(typeof (tio) == "undefined"){
    tio = {};
}
tio.ws = {};
//构造函数
tio.ws = function($,layim) {

    var self = this;

    this.connect = function () {
        var url = "ws://172.17.30.21:9326?userId="+self.userId
        var  socket = new WebSocket(url);
        self.socket = socket;
        socket.onopen = function (ev) {
            console.log("tio ws 启动 --"+ev)
            console.log(ev)
        }
        socket.onclose = function (ev) {
            console.log("tio ws 关闭 --"+ev)
            console.log(ev)
        }
        socket.onmessage = function (res) {
            console.log("接收消息！")
            console.log(res)

            // res = JSON.parse(res);
            res = eval('('+res.data+')');

            if(res.emit === 'chatMessage'){
                layim.getMessage(res.data); //res.data即你发送消息传递的数据（阅读：监听发送的消息）
            }
        }



    }

    this.openChatWindow = function () {
        //获取个人信息，群聊信息
        $.ajax({
            url: "/chat/getMineAndGroup",
            type: "post",
            async: false,
            success: function (res) {
                self.group = res.data.group;
                self.mine = res.data.mine;
                self.userId = res.data.mine.id;
            }
        })
        var cache =  layui.layim.cache();
        cache.mine = self.mine
        //打开聊天窗口
        layim.chat(self.group)
        layim.setChatMin(); //收缩聊天面板
    }

    this.sendChatMsg = function (res) {
        //监听到上述消息后，就可以轻松地发送socket了，如：
        self.socket.send(JSON.stringify({
            type: 'chatMessage' //随便定义，用于在服务端区分消息类型
            ,data: res
        }));
    }

    this.initHistoryMsg = function () {
        localStorage.clear();
        $.ajax({url:"/chat/getGroupHistoryMsg",type:"post"
            ,success:function (res) {
                var data = res.data;
                if(data.length<1){
                    return;
                }
                for (var i in data){
                    layim.getMessage(data[i])
                }

            }
        })
    }

}