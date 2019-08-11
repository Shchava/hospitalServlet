<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundles/messages"/>
<fmt:setBundle basename="bundles/ValidationMessages" var="validation"/>

<c:set var="dateFormat">
    <fmt:message key="dateFormat"/>
</c:set>
<c:set var="formatter" value='${DateTimeFormatter.ofPattern(dateFormat)}'/>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css"/>
    <link rel="stylesheet" href="/css/doctorPageMarkup.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/pagination.css"/>
    <link rel="stylesheet" href="/css/showPatient.css"/>
    <title>${patient.surname} ${patient.name} ${patient.patronymic}</title>
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
                        <div class="col-sm-9">
                            <h2>${patient.surname} ${patient.name} ${patient.patronymic}</h2>
                        </div>
                        <div class="col-sm-3">
                            <a href="/patient/${patient.id}/?recordsPerPage=${requestScope.recordsPerPage}&page=${requestScope.page}"
                               class="btn btn-primary"><i class="material-icons">&#xE863;</i>
                                <span><fmt:message key="doctor.page.patientsList.refresh"/></span></a>
                        </div>
                    </div>
                </div>

                <div>
                    <h6><fmt:message key="doctor.showPatient.patientEmail"/></h6>
                    <p>${patient.email}</p>
                    <h6><fmt:message key="doctor.showPatient.personalInfo"/></h6>
                    <p>${patient.info}</p>
                </div>

                <div class="table-filter">
                    <div class="row">
                        <div class="col-sm-3">
                            <div class="show-entries">
                                <span><fmt:message key="pagination.show"/></span>
                                <div class="dropdown">
                                    <button class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
                                            type="button" data-toggle="dropdown">${requestScope.recordsPerPage}</button>
                                    <ul class="dropdown-menu ">
                                        <li><a class="dropdown-item"
                                               href="/patient/${patient.id}/?page=${requestScope.page}&recordsPerPage=5">5</a>
                                        </li>
                                        <li><a class="dropdown-item"
                                               href="/patient/${patient.id}/?page=${requestScope.page}&recordsPerPage=5">10</a>
                                        </li>
                                        <li><a class="dropdown-item"
                                               href="/patient/${patient.id}/?page=${requestScope.page}&recordsPerPage=5">15</a>
                                        </li>
                                        <li><a class="dropdown-item"
                                               href="/patient/${patient.id}/?page=${requestScope.page}&recordsPerPage=5">20</a>
                                        </li>
                                    </ul>
                                </div>
                                <span> <fmt:message key="pagination.entries"/></span>
                            </div>
                        </div>
                        <div class="col-sm-3"><h3 class="diagnosesTitle"><fmt:message key="showPatient.diagnosesTable"/></h3></div>

                        <div class="col-sm-6">
                            <button type="button" class="btn btn-primary"><i class="fa fa-search"></i></button>
                            <div class="filter-group">
                                <label>Name</label>
                                <input type="text" class="form-control">
                            </div>

                        </div>
                    </div>
                </div>

                <c:if test="${!empty requestScope.created}">
                    <div class="alert alert-info" role="alert"><fmt:message key="doctor.showPatient.newDiagnosis.created"/></div>
                </c:if>

                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th><fmt:message key="doctor.showPatient.diagnoses.id"/></th>
                        <th><fmt:message key="doctor.showPatient.diagnoses.name"/></th>
                        <th><fmt:message key="doctor.showPatient.diagnoses.assigned"/></th>
                        <th><fmt:message key="doctor.showPatient.diagnoses.assignedBy"/></th>
                        <th><fmt:message key="doctor.showPatient.diagnoses.cured"/></th>
                        <th><fmt:message key="doctor.showPatient.diagnoses.open"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.diagnoses}" var="diagnosis">
                        <tr>
                            <th><c:out value="${diagnosis.idDiagnosis}"/></th>
                            <th><c:out value="${diagnosis.name}"/>
                            <th><c:out value="${diagnosis.assigned.format(formatter)}"/></th>
                            <th><c:out value="${diagnosis.doctor.surname}"/> <c:out
                                    value="${diagnosis.doctor.name}"/></th>
                            <th>
                                <c:out value="${diagnosis.cured.format(formatter)}"/>
                            </th>
                            <th><a class="btn btn-primary" href="/patient/${patient.id}/diagnosis/${diagnosis.idDiagnosis}/" role="button"><fmt:message key="doctor.showPatient.diagnosesList.open"/></a>
                            </th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:if test="${sessionScope.LoggedUser.role == 'DOCTOR'}">
                    <c:choose>
                        <c:when  test="${not empty requestScope.values}">
                            <div id="addDiagnosis">
                        </c:when>
                        <c:otherwise>
                            <div id="addDiagnosis" class="hidden-form">
                        </c:otherwise>
                    </c:choose>

                        <c:if test="${!empty requestScope.creationError}">
                            <div class="alert-danger error-message" >
                                <fmt:message key="diagnosis.creationError" bundle="${validation}"/>
                            </div>
                        </c:if>

                        <form id="addDiagnosisForm" method="POST"  action="/patient/${patient.id}/addDiagnosis/">
                            <input type="hidden" name="pageNumber" value="${requestScope.page}">
                            <input type="hidden" name="recordsPerPage" value="${requestScope.recordsPerPage}">
                            <div class="form-group">
                                <label><fmt:message key="doctor.showPatient.newDiagnosis.name"/></label>
                                <c:if test="${!empty requestScope.nameEmpty}">
                                    <div class="alert alert-danger" >
                                        <fmt:message key="diagnosis.name.empty" bundle="${validation}"/>
                                    </div>
                                </c:if>
                                <input name="name" type="text" class="form-control" required="required" value="${requestScope.values.name}"/>
                            </div>
                            <div class="form-group">
                                <label><fmt:message key="doctor.showPatient.newDiagnosis.description"/></label>
                                <input name="description" type="text" class="form-control input-description" value="${requestScope.values.description}"/>
                            </div>
                        </form>
                    </div>

                    <button id="showAddDiagnosisForm" role="button" class="btn btn-primary btn-lg btn-block"><fmt:message key="showPatient.addDiagnosis"/></button>
                </c:if>
                <div class="clearfix">
                    <div class="hint-text"><fmt:message key="pagination.label.showing"/>
                        <b><c:out value="${fn:length(requestScope.diagnoses)}"/></b> <fmt:message key="pagination.label.outOf"/>
                        <b>${requestScope.records}</b> <fmt:message key="pagination.label.entries"/></div>


                    <ul class="pagination">
                        <c:if test="${1 ne requestScope.page}">
                            <li class="page-item">
                                <a class="page-link" href="/patient/${patient.id}/?page=${requestScope.page - 1}&recordsPerPage=${requestScope.recordsPerPage}">
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
                                                             href="/patient/${patient.id}/?page=${i}&recordsPerPage=${requestScope.recordsPerPage}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${requestScope.page lt requestScope.numberOfPages}">
                            <li class="page-item"><a class="page-link"
                                                     href="/patient/${patient.id}/?page=${requestScope.page + 1}&recordsPerPage=${requestScope.recordsPerPage}"><fmt:message key="pagination.next"/></a>
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

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<c:if test="${sessionScope.LoggedUser.role == 'DOCTOR'}">
    <script>
        $(document).ready(function () {

            $("#showAddDiagnosisForm").click(function () {
                if ($("#addDiagnosis").is(":visible")) {
                    $("#addDiagnosisForm").submit();
                } else {
                    $("#addDiagnosis").show()
                }
            });
        });
    </script>
</c:if>
</body>
</html>