if(typeof (tio) == "undefined"){
    tio = {};
}
tio.ws = {};
//构造函数
tio.ws = function($,layim) {

    var self = this;

    var url = "ws://localhost:9326"
    this.connect = function () {
        var  socket = new WebSocket(url);
        self.socket = socket;
        socket.onopen = function (ev) {
            console.log("tio ws 启动 --"+ev)
            console.log(ev)
        }
        socket.onmessage = function (res) {
            console.log("接收消息！")
            console.log(res)
        }

        // socket.onclose = function (ev) {
        //     console.log("tio ws 关闭 --"+ev)
        //     console.log(ev)
        // }


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

    }

}