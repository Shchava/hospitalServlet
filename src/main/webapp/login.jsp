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
<div class="container login-container">
    <div class="row">
        <div class="col login-form-1">
            <h3><fmt:message key="login.label" /></h3>

            <c:if test="${!empty requestScope.logout}">
                <div class="alert alert-info" role="alert">You've been logged out successfully.</div>
            </c:if>
            <c:if test="${!empty requestScope.error}">
                <div class="alert alert-danger" role="alert">Invalid Username or Password!</div>
            </c:if>
            <form method="POST" action="/login/">
                <div class="form-group">
                    <input type="text" name="email" class="form-control" placeholder="<fmt:message key="login.email" />" value="" />
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="<fmt:message key="login.password" />" value="" />
                </div>
                <div class="form-group">
                    <input type="submit" class="form-control"  value="<fmt:message key="login.login.button" />" />
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
