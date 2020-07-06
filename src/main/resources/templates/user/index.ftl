<#include "/inc/layout.ftl"/>
<@layout "用户中心">



<div class="layui-container fly-marginTop fly-user-main">
  <@centerLeft level=1></@centerLeft>

  <div class="site-tree-mobile layui-hide">
    <i class="layui-icon">&#xe602;</i>
  </div>
  <div class="site-mobile-shade"></div>
  
  <div class="site-tree-mobile layui-hide">
    <i class="layui-icon">&#xe602;</i>
  </div>
  <div class="site-mobile-shade"></div>
  
  
  <div class="fly-panel fly-panel-user" pad20>
    <!--
    <div class="fly-msg" style="margin-top: 15px;">
      您的邮箱尚未验证，这比较影响您的帐号安全，<a href="activate.html">立即去激活？</a>
    </div>
    -->
    <div class="layui-tab layui-tab-brief" lay-filter="user">
      <ul class="layui-tab-title" id="LAY_mine">
        <li data-type="mine-jie" lay-id="index" class="layui-this">我发的帖（<span>${postTotal}</span>）</li>
        <li data-type="collection" >我收藏的帖（<span>${collectTotal}</span>）</li>
      </ul>
      <div class="layui-tab-content" style="padding: 20px 0;">
        <div class="layui-tab-item layui-show">
          <ul class="mine-view jie-row" id="fabu">

          </ul>
          <div id="LAY_page"></div>
        </div>
        <div class="layui-tab-item">
          <ul class="mine-view jie-row" id="collection">

          </ul>
          <div id="LAY_page1"></div>
        </div>
      </div>
    </div>
  </div>
</div>
  <script id="tpl-fabu" type="text/html">
    <li>
      <a class="jie-title" href="/post/detail/{{d.id}}" target="_blank">{{d.title}}</a>
      <i>{{layui.util.toDateString(d.created,'yyyy-MM-dd HH:mm:ss')}}</i>
      <a class="mine-edit" href="/post/edit/{{d.id}}">编辑</a>
      <em>{{d.viewCount}}阅/{{d.commentCount}}答</em>
    </li>
  </script>
  <script id="tpl-collection" type="text/html">
    <li>
      <a class="jie-title" href="/post/detail/{{d.id}}" target="_blank">{{d.title}}</a>
      <i>{{layui.util.timeAgo(d.created, true)}}</i>
    </li>
  </script>

  <script>
    layui.cache.page = 'user';
    layui.use(['flow','laytpl','util'], function(){
      var $ = layui.jquery;
      var flow = layui.flow;
      var laytpl = layui.laytpl;
      var util = layui.util;

      //我发布的
      flow.load({
        elem: '#fabu'
        ,isAuto: false
        ,done: function(page, next){
          var lis = [];
          $.get('/user/public?pageNum='+page, function(res){
            layui.each(res.data.records, function(index, item){
              var tpl = $("#tpl-fabu").html();
              laytpl(tpl).render(item, function(html){
                lis.push(html)
              });
            });
            next(lis.join(''), page < res.data.pages);
          });
        }
      });
      //我收藏的
      flow.load({
        elem: '#collection'
        ,isAuto: false
        ,done: function(page, next){
          var lis = [];
          $.get('/user/collection?pageNum='+page, function(res){
            layui.each(res.data.records, function(index, item){
              var tpl = $("#tpl-collection").html();
              laytpl(tpl).render(item, function(html){
                lis.push(html)
              });
            });
            next(lis.join(''), page < res.data.pages);
          });
        }
      });

    });

  </script>
</@layout>