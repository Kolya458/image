<#import "parts/common.ftl" as c>

<@c.page>
User editor

<form action="/user" method="post">
    <input type="text" name="username" value="${user.username}">
    <#list role as role>
    <div>
        <label><input type="checkbox" name="${role}" ${user.role?seq_contains(role)?string("cheked", "")}>${role}</label>
    </div>
    </#list>
    <input type="hidden" value="${user.id}" name="userId" >
    <input type="hidden" value="${_csrf.token}" name="_csrf" >
    <button type="submit">Save</button>
</form>
</@c.page>