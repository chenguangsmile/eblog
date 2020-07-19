<#include "/inc/layout.ftl"/>
<@layout "搜索 - ${q}">

    <#include "/inc/header-panel.ftl" />
    <div class="layui-container">
        <div class="layui-row layui-col-space15">


            <div class="layui-col-md8">
                <div class="fly-panel">
                    <div class="fly-panel-title fly-filter">
                        <a>您正在搜索关键字 “${q}” - 共有${(pageData.totals)!'0'}条记录</a>
                        <a href="#signin" class="layui-hide-sm layui-show-xs-block fly-right" id="LAY_goSignin" style="color: #FF5722;">去签到</a>
                    </div>
                    <ul class="fly-list">
                        <#if (pageData.records)??>
                            <#list pageData.records as post>
                                <@plisting post></@plisting>
                            </#list>
                        </#if>
                    </ul>
                    <#if (pageData.records)??>
                        <@paging pageData></@paging>
                    </#if>
                </div>
            </div>
            <#include "/inc/right.ftl" />
        </div>
    </div>
</@layout>
