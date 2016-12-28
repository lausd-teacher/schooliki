<#if RequestParameters.forgot??>
    <#assign title="Shiro on GAE Change password">
    <#assign header="<h1>Change <small>your password</small></h1>">
<#else>
    <#assign title="Shiro on GAE Register">
    <#assign header="<h1>Register <small>your account</small></h1>">
</#if>
<#assign style="substyle.css">

<!DOCTYPE html>
<html lang="en">
<head>
    <#include "inc/_head.ftl">
</head>

<body>

<<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">GAEShiro</a>
    </div>
    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
          <li><a href="/index.html">Home</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</div>

<div class="container">

    <div class="content">
        <section>
            <div class="page-header">
                ${header}
            </div>
            <div class="row">
               <div class="col-md-10">
                    <form class="form-horizontal" id="registration-form" action="${userBaseUrl}/register" method="POST">
                        <fieldset>
                            <legend>Please enter your Email</legend>
                            <div class="form-group">
                                <label for="username" class="col-sm-2 control-label">Email Address</label>
                                <div class="col-sm-8">
                                    <input class="form-control required email" id="username" name="username"
                                           size="30" type="text"/>
                                </div>
                            </div>

                        </fieldset>
                        <div id="stage1" class="actions">
                        <#if RequestParameters.forgot??>
                            <button id="register" type="submit" class="btn btn-primary">Change Password</button>
                        <#else>
                            <button id="register" type="submit" class="btn btn-primary">Register</button>
                        </#if>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row" id="stage2" style="display:none">
                <div class="col-md-10">
                    <p>Please wait for an email which will
                       provide you with a key, and a link.  Enter the key and choose a password
                       to complete your registration.</p>
                    <form id="passwordSetForm" class="form-horizontal" action="${userBaseUrl}/confirm" method="POST">
                        <fieldset>
                            <legend>Please fill in the code, password and the retype field</legend>
                            <div class="form-group">
                                <label for="confirmCode" class="col-sm-3 control-label">Code received</label>

                                <div class="col-sm-8">
                                    <input class="form-control required" id="confirmCode" name="confirmCode"
                                           type="text"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password" class="col-sm-3 control-label">Password</label>
                                <div class="col-sm-8">
                                    <input class="form-control required" id="password" name="password" type="password"/>
                                </div>
                            </div>
                            <!-- /clearfix -->
                            <div class="form-group">
                                <label for="checkPassword" class="col-sm-3 control-label">Retype Password</label>
                                <div class="col-sm-8">
                                    <input class="form-control" id="checkPassword" name="checkPassword"
                                           type="password"/>
                                </div>
                            </div>
                            <!-- /clearfix -->
                        </fieldset>
                        <div class="actions" id="resetButton">
                            <#if RequestParameters.forgot??>
                              <button id="register" type="submit" class="btn btn-primary">Reset</button>
                             <#else>
                               <button id="register" type="submit" class="btn btn-primary">Register</button>
                            </#if>
                        </div>
                    </form>

                </div>
            </div>
            <div class="row" id="stage3" style="display:none">
                <div class="span12">
                <#if RequestParameters.forgot??>
                  <p>Your password has been reset</p>
                 <#else>
                   <p>You have been registered</p>
                </#if>
                </div>
            </div>
        </section>
    </div>

<#include "inc/footer.ftl">

</div>


<#include "inc/warning-template.ftl" parse="false">
<#include "inc/modal-login-template.ftl" parse="false">

<#include "inc/_foot.ftl">

<script>
     $.template("warning", $("#warning-template"));
</script>

<script src="js/register.js"></script>

<script>
    <#if RequestParameters.forgot??>
        shiro.forgot = true;
    <#else>
        shiro.forgot = false;
    </#if>
</script>


</body>
</html>
