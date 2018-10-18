<#import "parts/common.ftl" as c>
<@c.page>
<#if isCurrentUser>
    <#include "parts/imageEdit.ftl" />
</#if>
    <#include "parts/imageList.ftl" />
</@c.page>