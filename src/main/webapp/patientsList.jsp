<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundles/messages"/>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/doctorPage.css">
    <link rel="stylesheet" href="/css/pagination.css">


    <title><fmt:message key="patientList.title"/></title>


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

<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-2 sidenav">


        </div>
        <div class="col-sm-8 text-left container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-4">
                            <h2><fmt:message key="doctor.page.patientsList.title"/></h2>
                        </div>
                        <div class="col-sm-8">
                            <a href="/patientsList?recordsPerPage=${requestScope.recordsPerPage}&page=${requestScope.page}"
                               class="btn btn-primary"><i class="material-icons">&#xE863;</i>
                                <span><fmt:message key="doctor.page.patientsList.refresh"/></span></a>
                        </div>
                    </div>
                </div>
                <div class="table-filter">
                    <div class="row">
                        <div class="col-sm-3">
                            <div class="show-entries">
                                <span><fmt:message key="pagination.show"/></span>
                                <div class="dropdown">
                                    <button class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
                                            type="button" data-toggle="dropdown">${recordsPerPage}</button>
                                    <ul class="dropdown-menu ">
                                        <li><a class="dropdown-item"
                                               href="/patientsList?page=${requestScope.page}&recordsPerPage=5">5</a></li>
                                        <li><a class="dropdown-item"
                                               href="/patientsList?page=${requestScope.page}&recordsPerPage=10">10</a></li>
                                        <li><a class="dropdown-item"
                                               href="/patientsList?page=${requestScope.page}&recordsPerPage=15">15</a></li>
                                        <li><a class="dropdown-item"
                                               href="/patientsList?page=${requestScope.page}&recordsPerPage=20">20</a></li>
                                    </ul>
                                </div>
                                <span> <fmt:message key="pagination.entries"/></span>
                            </div>
                        </div>
                        <div class="col-sm-9">
                            <button type="button" class="btn btn-primary"><i class="fa fa-search"></i></button>
                            <div class="filter-group">
                                <label>Name</label>
                                <input type="text" class="form-control">
                            </div>

                            <div class="filter-group">

                            </div>
                        </div>
                    </div>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th><fmt:message key="doctor.page.patientsList.id"/></th>
                        <th><fmt:message key="doctor.page.patientsList.patient.name"/></th>
                        <th><fmt:message key="doctor.page.patientsList.patient.email"/></th>
                        <th><fmt:message key="doctor.page.patientsList.patient.lastDiagnosis"/></th>
                        <th><fmt:message key="doctor.page.patientsList.patient.details"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.patients}" var="patient">
                        <tr>
                            <th><c:out value="${patient.id}"/></th>
                            <th><c:out value="${patient.name}"/> <c:out value="${patient.patronymic}"/> <c:out
                                    value="${patient.surname}"/></th>
                            <th><c:out value="${patient.email}"/></th>
                            <th><c:out value="${patient.lastDiagnosisName}"/></th>
                            <th><a class="btn btn-primary" href="/patient/${patient.id}/" role="button">
                                <fmt:message key="doctor.page.patientsList.open"/></a>
                            </th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>


                <div class="clearfix">
                    <div class="hint-text"><fmt:message key="pagination.label.showing"/> <b><c:out
                            value="${fn:length(requestScope.patients)}"/></b> <fmt:message key="pagination.label.outOf"/>
                        <b>${requestScope.records}</b> <fmt:message key="pagination.label.entries"/></div>


                    <ul class="pagination">
                        <c:if test="${1 ne requestScope.page}">
                            <li class="page-item"><a class="page-link"
                                                     href="/patientsList?page=${requestScope.page - 1}&recordsPerPage=${requestScope.recordsPerPage}">
                                <fmt:message key="pagination.previous"/></a>
                            </li>
                        </c:if>

                        <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
                            <c:choose>
                                <c:when test="${requestScope.page + 1 eq i}">
                                    <li class="page-item active"><a class="page-link">
                                            ${i} <span class="sr-only"><fmt:message key="pagination.current"/></span></a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link"
                                                             href="/patientsList?page=${i}&recordsPerPage=${requestScope.recordsPerPage}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${requestScope.page lt requestScope.numberOfPages}">
                            <li class="page-item"><a class="page-link"
                                                     href="/patientsList?page=${requestScope.page + 1}&recordsPerPage=${requestScope.recordsPerPage}"><fmt:message key="pagination.next"/></a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-sm-2 sidenav">


        </div>
    </div>
</div>

<footer class="container-fluid text-center">

</footer>

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
