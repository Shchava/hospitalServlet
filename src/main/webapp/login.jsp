<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundles/messages"/>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

    <title><fmt:message key="login.title"/></title>
</head>


<body>

<div>
    <nav class="navbar navbar-expand-sm navbar-dark bg-dark">
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item passive">
                    <label class="navbar-brand">${sessionScope.LoggedUser.name}</label>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/"><fmt:message key="header.message"/><span class="sr-only">(current)</span></a>
                </li>
            </ul>
            <ul class="navbar-nav ">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${pageContext.response.locale.country}</a>
                    <div class="dropdown-menu" aria-labelledby="dropdown">
                        <a class="dropdown-item" href="?lang=<fmt:message key="header.language.UA.tag"/>"><fmt:message key="header.language.UA"/></a>
                        <a class="dropdown-item" href="?lang=<fmt:message key="header.language.EN.tag"/>"><fmt:message key="header.language.EN"/></a>
                    </div>
                </li>
                <li>
                    <a class="nav-link" href="/logout"><fmt:message key="header.logout"/><span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
    </nav>
</div>
<div class="container login-container">
    <div class="row">
        <div class="col login-form-1">
            <h3><fmt:message key="login.label" /></h3>

            <c:if test="${!empty requestScope.registered}">
                <div class="alert alert-info" role="alert"><fmt:message key="login.registered" /></div>
            </c:if>
            <c:if test="${!empty requestScope.logout}">
                <div class="alert alert-info" role="alert"><fmt:message key="login.logoutMessage" /></div>
            </c:if>
            <c:if test="${!empty requestScope.error}">
                <div class="alert alert-danger" role="alert"><fmt:message key="login.loginError" /></div>
            </c:if>
            <form method="POST" action="<c:url value="/login"/>">

                <c:if test="${!empty requestScope.requestedUrl}">
                    <input type="hidden" name="requestedUrl" value="${requestScope.requestedUrl}">
                </c:if>
                <div class="form-group">
                    <input type="text" name="email" class="form-control" placeholder="<fmt:message key="login.email" />" value="" />
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="<fmt:message key="login.password" />" value="" />
                </div>
                <div class="form-group">
                    <input type="submit" class="form-control"  value="<fmt:message key="login.login.button" />" />
                </div>
                    <p class="text-center"><fmt:message key="login.registrationInvitation"/>
                        <a href="/registration"><fmt:message key="login.registrationLink"/></a></p>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
