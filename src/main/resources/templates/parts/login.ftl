<#macro login path isRegisterForm>
<form action="${path}" method="post">
    <div class="form-group">
        <label> User Name : </label>
        <input type="text" name="username" value="<#if user??>${user.username}</#if>"
               class="form-control ${(usernameError??)?string('is-invalid', '')}"
               placeholder="Username" />
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
    </div>

    <div class="form-group">
        <label> Password: </label>
        <input type="password" name="password"
               class="form-control ${(passwordError??)?string('is-invalid', '')}"
               placeholder="Password" />
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div>
        <button type="submit" class="btn btn-primary">
            <#if isRegisterForm>Sign up<#else>Sign in</#if>
        </button>
    </div>
    <#if !isRegisterForm> <a href="/sign_up">Add new user </a> </#if>

</form>
</#macro>


<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Sign out</button>
    </form>
</#macro>