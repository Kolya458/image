<#include "security.ftl">
<#import "pager.ftl" as p>

<@p.pager url page />

<div class="card-columns">
    <#list page.content as image>
        <div class="card my-3">
            <div>
                <#if image.filename??>
                    <img src="/img/${image.filename}" class="card-img-top">
                </#if>
            </div>
            <div class="m-2">
                <span><i>${image.name}</i></span><br/>
            </div>

            <div class="card-footer text-muted">
                <a href="/user-images/${image.author.id}"> ${image.author.username} </a>
                <#if image.author.id == currentUserId>
                     <a class="btn btn-primary  ml-2" href="/user-images/${image.author.id}?image=${image.id}">
                        Edit
                     </a>
                </#if>
            </div>


        </div>
<#else>
    No images
</#list>
</div>

<@p.pager url page />