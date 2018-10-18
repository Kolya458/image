<a class="btn btn-primary my-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Banner Editor
</a>
<div class="collapse <#if image??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">

            <div class="form-group">
                <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')}"
                       value="<#if image??>${image.name}</#if>" name="name" placeholder="Input the name of banner" />
                <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
                </#if>
            </div>

            <div class="form-group">
                <div class="custom-file">
                    <label for="Browse" class="custom-file-label" >Choose image..</label>
                    <input type="file" class="custom-file-input ${(fileError??)?string('is-invalid', '')}"
                           id="Browse" name="file" />
                    <#if fileError??>
                <div class="invalid-feedback">
                    ${fileError}
                </div>
                    </#if>
                </div>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}" />


            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </form>
    </div>
</div>