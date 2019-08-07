<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundles/messages"/>
<fmt:setBundle basename="bundles/ValidationMessages" var="validation"/>
<%--, bundles/ValidationMessages--%>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link rel="stylesheet" href="/css/forms.css">
    <link rel="stylesheet" href="/css/register.css">
    <title><fmt:message key="registration.title"/></title>

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
                    <a class="nav-link" href="/index.jsp"><fmt:message key="header.message"/><span class="sr-only">(current)</span></a>
                </li>
            </ul>
            <ul class="navbar-nav ">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">${pageContext.response.locale.country}</a>
                    <div class="dropdown-menu" aria-labelledby="dropdown">
                        <a class="dropdown-item"
                           href="?lang=<fmt:message key="header.language.UA.tag"/>"><fmt:message key="header.language.UA"/></a>
                        <a class="dropdown-item"
                           href="?lang=<fmt:message key="header.language.EN.tag"/>"><fmt:message key="header.language.EN"/></a>
                    </div>
                </li>
                <li>
                    <a class="nav-link" href="/logout"><fmt:message key="header.logout"/><span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
    </nav>
</div>

<div class="container">
    <div class="signup-form">
        <form method="POST">
            <h2><fmt:message key="registration.label"/></h2>
            <c:if test="${!empty requestScope.registrationError}">
                <div class="alert-danger error-message" >
                    <fmt:message key="registration.error" bundle="${validation}"/>
                </div>
            </c:if>
            <div class="form-group">
                <label><fmt:message key="registration.name"/></label>
                <c:if test="${!empty requestScope.nameEmpty}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="name.empty" bundle="${validation}"/>
                    </div>
                </c:if>
                <c:if test="${!empty requestScope.nameWrong}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="name.validation" bundle="${validation}"/>
                    </div>
                </c:if>
                <input type="text" class="form-control" name="name" required="required" value="${values.name}"/>
            </div>
            <div class="form-group">
                <label><fmt:message key="registration.surname"/></label>
                <c:if test="${!empty requestScope.surnameEmpty}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="surname.empty" bundle="${validation}"/>
                    </div>
                </c:if>
                <c:if test="${!empty requestScope.surnameWrong}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="surname.validation" bundle="${validation}"/>
                    </div>
                </c:if>
                <input type="text" class="form-control" name="surname" required="required" value="${values.surname}"/>
            </div>
            <div class="form-group">
                <label><fmt:message key="registration.patronymic"/></label>
                <c:if test="${!empty requestScope.patronymicEmpty}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="patronymic.empty" bundle="${validation}"/>
                    </div>
                </c:if>
                <c:if test="${!empty requestScope.patronymicWrong}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="patronymic.validation" bundle="${validation}"/>
                    </div>
                </c:if>
                <input type="text" class="form-control" name="patronymic" required="required" value="${values.patronymic}"/>

            </div>
            <div class="form-group">
                <label><fmt:message key="registration.email"/></label>
                <c:if test="${!empty requestScope.emailEmpty}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="email.empty" bundle="${validation}"/>
                    </div>
                </c:if>
                <c:if test="${!empty requestScope.emailWrong}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="email.validation" bundle="${validation}"/>
                    </div>
                </c:if>
                <c:if test="${!empty requestScope.emailExists}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="email.exists" bundle="${validation}"/>
                    </div>
                </c:if>
                <input type="email" class="form-control" name="email" required="required" value="${values.email}"/>

            </div>
            <div class="form-group">
                <label><fmt:message key="registration.password"/></label>
                <c:if test="${!empty requestScope.passwordEmpty}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="password.empty" bundle="${validation}"/>
                    </div>
                </c:if>
                <c:if test="${!empty requestScope.passwordWrong}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="password.validation" bundle="${validation}"/>
                    </div>
                </c:if>
                <input type="password" class="form-control" name="password" required="required" value="${values.password}"/>

            </div>
            <div class="form-group">
                <label><fmt:message key="registration.passwordСonfirmation"/></label>
                <c:if test="${!empty requestScope.passwordsNotEqual}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="password.matches.validation" bundle="${validation}"/>
                    </div>
                </c:if>
                <input type="password" class="form-control" name="confirmPassword" required="required" value="${values.confirmPassword}"/>

            </div>

            <div class="form-group">
                <label><fmt:message key="registration.role"/></label>
                <c:if test="${!empty requestScope.roleNull}">
                    <div class="alert-danger error-message" >
                        <fmt:message key="role.null" bundle="${validation}"/>
                    </div>
                </c:if>
                <select name="role" class="form-control">
                    <option value="PATIENT"><fmt:message key="registration.patient"/></option>
                    <option value="NURSE"><fmt:message key="registration.nurse"/></option>
                    <option value="DOCTOR"><fmt:message key="registration.doctor"/></option>
                </select>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block btn-lg"><fmt:message key="registration.signUp"/></button>
            </div>

            <p class="small text-center"><fmt:message key="registration.agreement"/></p>

        </form>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>