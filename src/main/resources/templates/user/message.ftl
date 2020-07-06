<#include "/inc/layout.ftl"/>
<@layout "我的消息">

<div class="layui-container fly-marginTop fly-user-main">
  <@centerLeft level=3></@centerLeft>

  <div class="site-tree-mobile layui-hide">
    <i class="layui-icon">&#xe602;</i>
  </div>
  <div class="site-mobile-shade"></div>
  
  <div class="site-tree-mobile layui-hide">
    <i class="layui-icon">&#xe602;</i>
  </div>
  <div class="site-mobile-shade"></div>
  
  
  <div class="fly-panel fly-panel-user" pad20>
	  <div class="layui-tab layui-tab-brief" lay-filter="user" id="LAY_msg" style="margin-top: 15px;">
	    <button class="layui-btn layui-btn-danger" id="LAY_delallmsg">清空全部消息</button>
	    <div  id="LAY_minemsg" style="margin-top: 10px;">
        <!--<div class="fly-none">您暂时没有最新消息</div>-->
        <ul class="mine-msg">
          <#list pageData.records as mess >
            <#if mess.type==0>
              <li data-id="${mess.id}">
                <blockquote class="layui-elem-quote">
                  系统消息：${mess.content}
                </blockquote>
                <p><span>${timeAgo(mess.created)}</span>
                  <a href="javascript:;" class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a>
                </p>
              </li>
            </#if>
            <#if mess.type==1>
              <li data-id="${mess.id}">
                <blockquote class="layui-elem-quote">
                  <a href="/user/home/${mess.fromUserId}" target="_blank"><cite>${mess.fromUserName}</cite></a>回答了您的文章<a target="_blank" href="/post/detail/${mess.postId}"><cite>${mess.postTitle}</cite></a>
                  <span>${mess.content}</span>
                </blockquote>
                <p><span>${timeAgo(mess.created)}</span>
                  <a href="javascript:;" class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a>
                </p>
              </li>
            </#if>
            <#if mess.type==2>
              <li data-id="${mess.id}">
                <blockquote class="layui-elem-quote">
                  <a href="/user/home/${mess.fromUserId}" target="_blank"><cite>${mess.fromUserName}</cite></a>回复了您的<a target="_blank" href="/post/detail/${mess.postId}"><cite>${mess.postTitle}</cite></a>
                  <span>${mess.content}</span>
                </blockquote>
                <p><span>${timeAgo(mess.created)}</span>
                  <a href="javascript:;" class="layui-btn layui-btn-small layui-btn-danger fly-delete">删除</a>
                </p>
              </li>
            </#if>
          </#list>
        </ul>
          <#if pageData.records?size gt 0>
            <@paging pageData></@paging>
          </#if>
      </div>
	  </div>
	</div>

</div>

  <script>
    layui.cache.page = 'user';
  </script>
</@layout>