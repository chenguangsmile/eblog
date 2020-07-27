layui.use('layim', function(layim){
    //先来个客服模式压压精
    // layim.config({
    //     brief: true //是否简约模式（如果true则不显示主面板）
    // }).chat({
    //     name: '客服姐姐'
    //     ,type: 'friend'
    //     ,avatar: 'http://tp1.sinaimg.cn/5619439268/180/40030060651/1'
    //     ,id: -2
    // });
    var $ = layui.jquery;
    layim.config({
        brief: true //是否简约模式（如果true则不显示主面板）
        ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatlog.html'
    })
    var tioWs = new tio.ws($,layim);

    //获取个人信息，打开群聊
    tioWs.openChatWindow();
    //建立ws链接
    tioWs.connect()

    //获取历史聊天记录
    tioWs.initHistoryMsg()

    //发送消息
    layim.on('sendMessage', function(res){
        tioWs.sendChatMsg(res)
    })

    //接收消息


    //心跳，断开重连机制


});