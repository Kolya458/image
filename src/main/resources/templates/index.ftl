<#import "parts/common.ftl" as c>
<@c.page>


<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/index" class="form-inline">
            <button type="submit" class="btn btn-primary mr-2">Search</button>
            <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Search"/>
        </form>
    </div>
</div>
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/index" class="form-inline">
            <input type="text" name="del" class="form-control" value="${del?ifExists}" placeholder="Delete" />
            <button type="submit" class="btn btn-danger ml-2">Delete</button>
        </form>
    </div>
</div>

<#include "parts/imageEdit.ftl" />

<#include "parts/imageList.ftl" />

</@c.page>

