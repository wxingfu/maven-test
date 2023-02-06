<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8" />
    <title>核保意见通知函</title>
    <style type="text/css">
        body {
            font-family: SimSun, serif;
            font-size: 10pt;
        }

        p {
            margin: 0 auto;
            font-size: 10pt;
            font-family: SimSun, serif;
        }

        table, tbody {
            vertical-align: center;
            overflow: visible;
        }

        .footerTable {
            margin: 0;
            padding: 0;
            /*border: 0.1pt;*/
            border: 0;
            border-collapse: collapse;
            font-size: 10pt;
            font-family: SimSun, serif;
            width: 100%;
        }

        .footerTableTD {
            font-size: 10pt;
            font-family: SimSun, serif;
            border-collapse: collapse;
            border-spacing: 0;
            /*border: 0.1pt;*/
            border: 0;
            text-align: left;
            padding-top: 2pt;
            padding-bottom: 2pt;
        }

        .uwRateTable {
            margin: 5pt;
            border: 0.1pt;
            border-collapse: collapse;

            table-layout: fixed;
            word-break: break-word;
            width: 100%;

            font-size: 10pt;
            font-family: SimSun, serif;
        }

        .uwRateTableTD {
            border-collapse: collapse;
            border-spacing: 0;
            border: 0.1pt;
            text-align: left;
            padding-top: 5pt;
            padding-bottom: 5pt;
            font-size: 10pt;
            font-family: SimSun, serif;
        }

        .uwRateTableTH {
            border-collapse: collapse;
            border-spacing: 0;
            border: 0.1pt;
            text-align: left;
            font-size: 10pt;
            font-family: SimSun, serif;
        }
    </style>
</head>
<body>
<#-- 注意 这个点不能删 目的是让main大于一页时自动分页 我也不知道是为什么 -->
.
<div class="main">
    <#-- 文档标题 -->
    <div style="text-align: center">
        <p style="font-weight: bold;font-size: large"><span>核 保 意 见 通 知 函</span></p>
    </div>
    <#-- 条形码 -->
    <div>
        <p style="text-align: right;padding-top: 20pt">
            <#--<img width="280" height="38" alt="image" src="E:\MyWork\uw\Image_001.gif"/>-->
            <img width="300" height="45" alt="image" src="${barCodeUrl}"/>
        </p>
    </div>
    <#-- 开头文字 用户告知 -->
    <div style="text-align: left;padding-top: 10pt">
        <#--<p style="padding-bottom: 2pt;">尊敬的肖肥虾先生：</p>-->
        <p style="padding-bottom: 2pt;">尊敬的${appntName}<#if gender == "0">先生<#elseif gender == "1">女士</#if>：</p>
        <p style="text-indent: 2em;padding-bottom: 2pt;">您好！非常感谢您对华贵人寿保险股份有限公司的支持和信赖。</p>
        <#--<p style="text-indent: 2em;padding-bottom: 2pt;">您2022年03月20日为什么时投保的人身保险申请资料（投保单号109011686902121），经审核，按照如下约定对您提供保障服务。</p>-->
        <p style="text-indent: 2em;padding-bottom: 2pt;">您${polApplyDate}为${insuredName}投保的人身保险申请资料（投保单号${prtNo}），经审核，按照如下约定对您提供保障服务。</p>
        <p style="text-indent: 2em;padding-bottom: 2pt;">本函是保险合同的组成部分，为使您的利益不受损害，务必请投保人、被保险人或其监护人亲笔签名，使之具有法律效力。</p>
    </div>
    <div style="text-align: left;padding-top: 20pt">
        <p style="padding-left: 380pt;text-indent: -22pt;">华贵人寿保险股份有限公司</p>
        <#--<p style="padding-left: 400pt;text-indent: -22pt;">2022年03月20日</p>-->
        <p style="padding-left: 400pt;text-indent: -22pt;">${sendDate}</p>
    </div>
    <#-- 分割线 这里不能去掉div包裹，水平线位置会变化-->
    <div>
        <hr style="height: 0"/>
    </div>
    <#-- 固定文字 -->
    <div style="text-align: left">
        <#--<p>由于被保人：1、客户存在健康异常的原因，本单核保评估意见如下：</p>-->
        <p>由于${UWIdea}，本单核保评估意见如下：</p>
    </div>
    <div>
        <#-- 核保评估意见表部分 -->
        <table class="uwRateTable">
            <tr>
                <th class="uwRateTableTH" style="width:5%;">
                    <p>险种名称</p>
                </th>
                <th class="uwRateTableTH" style="width:12%;">
                    <p>核保意见</p>
                </th>
                <th class="uwRateTableTH" style="width:5%;">
                    <p>保险责任</p>
                </th>
                <th class="uwRateTableTH" style="width:18%;">
                    <p>保险金额(元)</p>
                </th>
                <th class="uwRateTableTH" style="width:8%;">
                    <p>交费期限</p>
                </th>
                <th class="uwRateTableTH" style="width:18%;">
                    <p>标准保费(元)</p>
                </th>
                <th class="uwRateTableTH" style="width:12%;">
                    <p>加费(元)</p>
                </th>
                <th class="uwRateTableTH" style="width:20%;">
                    <p>限额后保额(元)</p>
                </th>
                <th class="uwRateTableTH" style="width:20%;">
                    <p>调整后保费(元)</p>
                </th>
            </tr>

<#if riskInfo?size gt 0>
    <#list riskInfo as risk>
            <tr>
                <td class="uwRateTableTD" style="width:45pt;" rowspan="${risk.riskDutyInfoVOList?size}">
                    <p>${risk.riskName}</p>
                </td>
                <td class="uwRateTableTD" style="width:35pt;" rowspan="${risk.riskDutyInfoVOList?size}">
                    <p>${risk.uwIdea}</p>
                </td>
                <td class="uwRateTableTD" style="width:92pt;">
                    <p>${risk.riskDutyInfoVOList[0].riskDutyName}</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>${risk.riskDutyInfoVOList[0].insuranceAmount}</p>
                </td>
                <td class="uwRateTableTD" style="width:43pt;" rowspan="${risk.riskDutyInfoVOList?size}">
                    <p>${risk.payEndYear}</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>${risk.riskDutyInfoVOList[0].standPrem}</p>
                </td>
                <td class="uwRateTableTD" style="width:50pt;">
                    <p>${risk.riskDutyInfoVOList[0].addPrem}</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>${risk.riskDutyInfoVOList[0].quotaAmount}</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>${risk.riskDutyInfoVOList[0].finalPrem}</p>
                </td>
            </tr>
        <#list risk.riskDutyInfoVOList as duty>
            <#-- 第一条数据上个tr已渲染，故跳过。版本太低不支持continue指令 -->
            <#if duty_index != 0>
            <tr>
                <td class="uwRateTableTD" style="width:92pt;">
                    <p>${duty.riskDutyName}</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>${duty.insuranceAmount}</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>${duty.standPrem}</p>
                </td>
                <td class="uwRateTableTD" style="width:50pt;">
                    <p>${duty.addPrem}</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>${duty.quotaAmount}</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>${duty.finalPrem}</p>
                </td>
            </tr>
            </#if>
        </#list>
    </#list>
</#if>
<#--            <tr style="height:15pt">
                <td class="uwRateTableTD" style="width:45pt;" rowspan="3">
                    <p>华贵大麦旗舰版定期寿险( 互联网专属）</p>
                </td>
                <td class="uwRateTableTD" style="width:35pt;" rowspan="3">
                    <p>限额承保</p>
                </td>
                <td class="uwRateTableTD" style="width:92pt;">
                    <p>身故或身体全残保险金</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>3000000</p>
                </td>
                <td class="uwRateTableTD" style="width:43pt;" rowspan="3">
                    <p>30年</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>1214.34</p>
                </td>
                <td class="uwRateTableTD" style="width:50pt;">
                    <p>0.00</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>2000000</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>1014.34</p>
                </td>
            </tr>
            <tr>
                <td class="uwRateTableTD" style="width:92pt;">
                    <p>航空意外身故或身体全残保险金</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>10000000</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>23.34</p>
                </td>
                <td class="uwRateTableTD" style="width:50pt;">
                    <p>0.00</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>3000000</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>13.34</p>
                </td>
            </tr>
            <tr>
                <td class="uwRateTableTD" style="width:92pt;">
                    <p>水陆公共交通意外身故或身体全残保险金</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>6000000</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>13.25</p>
                </td>
                <td class="uwRateTableTD" style="width:50pt;">
                    <p>0.00</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>2000000</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>11.25</p>
                </td>
            </tr>
            <tr>
                <td class="uwRateTableTD" style="width:45pt;" rowspan="1">
                    <p>华贵附加麦芽糖失能收入损失保险（互联网专属）</p>
                </td>
                <td class="uwRateTableTD" style="width:35pt;" rowspan="1">
                    <p>拒保</p>
                </td>
                <td class="uwRateTableTD" style="width:92pt;">
                    <p>附加失能保险</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>10000</p>
                </td>
                <td class="uwRateTableTD" style="width:43pt;" rowspan="1">
                    <p>30年</p>
                </td>
                <td class="uwRateTableTD" style="width:65pt;">
                    <p>1214.34</p>
                </td>
                <td class="uwRateTableTD" style="width:50pt;">
                    <p>0.00</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>7500</p>
                </td>
                <td class="uwRateTableTD" style="width:70pt;">
                    <p>1014.34</p>
                </td>
            </tr>-->
        </table>
    </div>
    <div>
        <#-- 选择意见并签字部分 -->
        <div style="padding-left: 10pt;padding-top: 5pt">
            <#-- 意见选择部分 这里使用了padding-left和text-indent缩进实现对齐 -->
            <div>
                <p style="text-align: left;font-weight: bold;padding-bottom: 5pt">请选择您的意见：</p>
                <p style="text-align: left;padding-left: 80pt;text-indent: -80pt;"><span style="font-weight: bold;">整单意见：</span><span>□</span><span>&ensp;&ensp;同意（本人同意且接受贵司上述核保决定通知函所提议的调整内容，并声明本人健康状况自投保单签署之日或自体检之日起迄今并无任何变化）</span></p>
                <p class="s4" style="text-align: left;padding-top: 5pt;padding-left: 50pt;"><span>□</span><span>&ensp;&ensp;不同意（若勾选本项，本次投保申请将终止）</span></p>
            </div>
            <#-- 这里使用了div分栏 -->
            <div style="margin: 0 auto;width: 100%;padding-top: 8pt;padding-bottom: 3pt">
                <div style="width: 30%;height: 45pt;float: left;">
                    <p>投保人签名：</p>
                    <br/>
                    <p>签字日期：</p>
                </div>
                <div style="width: 80%;height: 45pt;float: right;">
                    <p>被保险人/监护人签名：</p>
                    <br/>
                    <p>签字日期：</p>
                </div>
            </div>
        </div>
        <#-- 分割线 -->
        <hr style="height: 0"/>
        <#-- 注意事项部分 -->
        <div style="margin: 0; padding-left: 10pt;padding-top: 0; padding-bottom: 3pt">
            <p style="text-align: left;">温馨提示：</p>
            <#--<p style="padding-top: 5pt;text-align: left;">1、请您在2020年03月27日之前给予回复，如果逾期，本次投保申请将被终止。</p>-->
            <p style="padding-top: 5pt;text-align: left;">1、请您在${replyDate}之前给予回复，如果逾期，本次投保申请将被终止。</p>
            <p style="padding-top: 5pt;text-align: left;">2、对本函件的回复以书面为准，请您在客户签名处签名确认相关内容。</p>
        </div>
        <#-- 分割线 -->
        <hr style="height: 0"/>
        <#-- 落款部分 这里使用table来实现三栏布局 -->
    <#if isZJ == false>
        <div style="margin: 0; padding: 0">
            <table class="footerTable">
                <tr>
                    <#--<td class="footerTableTD"><p>打印时间：2022年09月20日</p></td>-->
                    <td class="footerTableTD"><p>打印时间：${sysDate}</p></td>
                    <#--<td class="footerTableTD"><p>业务人员姓名：网销虚拟代理人</p></td>-->
                    <td class="footerTableTD"><p>业务人员姓名：${agentName}</p></td>
                    <#--<td class="footerTableTD"><p>业务人员代码：520099E000</p></td>-->
                    <td class="footerTableTD"><p>业务人员代码：${agentCode}</p></td>
                </tr>
                <tr>
                    <#--<td class="footerTableTD"><p>管理机构：总公司营业总部</p></td>-->
                    <td class="footerTableTD"><p>管理机构：${manageCom}</p></td>
                    <#--<td class="footerTableTD"><p>业务人员电话：</p></td>-->
                    <td class="footerTableTD"><p>业务人员电话：${agentPhone}</p></td>
                    <#--<td class="footerTableTD"><p>客户电话：</p></td>-->
                    <td class="footerTableTD"><p>客户电话：${appntPhone}</p></td>
                </tr>
                <tr>
                    <#--<td class="footerTableTD" colspan="2"><p>公司服务地址：贵州省贵阳市高新区长岭南路178号茅台国际商务中心A栋13层</p></td>-->
                    <td class="footerTableTD" colspan="2"><p>公司服务地址：${comAddress}</p></td>
                    <#--<td class="footerTableTD"><p>客户服务热线：400-684-1888</p></td>-->
                    <td class="footerTableTD"><p>客户服务热线：${serviceHotline}</p></td>
                </tr>
            </table>
        </div>
    <#elseif isZJ == true>
        <div style="margin: 0; padding: 0">
            <table class="footerTable">
                <tr>
                    <#--<td class="footerTableTD"><p>打印时间：2022年09月20日</p></td>-->
                    <td class="footerTableTD"><p>打印时间：${sysDate}</p></td>
                    <#--<td class="footerTableTD"><p>业务人员姓名：网销虚拟代理人</p></td>-->
                    <td class="footerTableTD"><p>业务人员姓名：${ZJAgentName}</p></td>
                    <#--<td class="footerTableTD"><p>业务人员代码：520099E000</p></td>-->
                    <td class="footerTableTD"><p>业务人员代码：${ZJAgentCode}</p></td>
                </tr>
                <tr>
                    <#--<td class="footerTableTD"><p>管理机构：总公司营业总部</p></td>-->
                    <td class="footerTableTD"><p>管理机构：${manageCom}</p></td>
                    <#--<td class="footerTableTD"><p>业务人员电话：</p></td>-->
                    <td class="footerTableTD"><p>业务人员电话：${ZJAgentPhone}</p></td>
                    <#--<td class="footerTableTD"><p>客户电话：</p></td>-->
                    <td class="footerTableTD"><p>客户电话：${appntPhone}</p></td>
                </tr>
                <tr>
                    <#--<td class="footerTableTD" colspan="2"><p>公司服务地址：贵州省贵阳市高新区长岭南路178号茅台国际商务中心A栋13层</p></td>-->
                    <td class="footerTableTD" colspan="2"><p>公司服务地址：${comAddress}</p></td>
                    <#--<td class="footerTableTD"><p>客户服务热线：400-684-1888</p></td>-->
                    <td class="footerTableTD"><p>客户服务热线：${serviceHotline}</p></td>
                </tr>
                <tr>
                    <td class="footerTableTD" colspan="2"><p>中介机构：${ZJAgentComName}</p></td>
                </tr>
            </table>
        </div>
    </#if>
    </div>
</div>
</body>

</html>