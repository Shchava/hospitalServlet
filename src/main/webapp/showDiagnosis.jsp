<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${pageContext.response.locale}"/>
<fmt:setBundle basename="bundles/messages"/>

<c:set var="dateFormat">
    <fmt:message key="dateFormat"/>
</c:set>
<c:set var="formatter" value='${DateTimeFormatter.ofPattern(dateFormat)}'/>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/doctorPageMarkup.css"/>
    <link rel="stylesheet" href="/css/pagination.css"/>
    <link rel="stylesheet" href="/css/showDiagnosis.css"/>
    <title>${diagnosis.name}</title>

    <c:set var="showMedicineAdditionalInfo">
        <fmt:message key="doctor.showDiagnosis.showTherapy.openButton"/>
    </c:set>

    <c:set var="therapyDescription">
        <fmt:message key="doctor.showDiagnosis.showTherapy.description"/>
    </c:set>

    <c:set var="therapyDoctorEmail">
        <fmt:message key="doctor.showDiagnosis.showTherapy.doctorEmail"/>
    </c:set>

    <c:set var="medicineRefill">
        <fmt:message key="doctor.showDiagnosis.showMedicine.refill"/>
    </c:set>

    <c:set var="procedureAppointmentDates">
        <fmt:message key="doctor.showDiagnosis.showProcedure.appointMentDates"/>
    </c:set>

    <c:set var="JSdateFormat">
        <fmt:message key="JS.dateFormat"/>
    </c:set>
    
    <c:set var="removeDate">
        <fmt:message key="doctor.showDiagnosis.removeDateField"/>
    </c:set>

    <style>
        .medicine-container {
            display: none;
        }

        .procedure-container {
            display: none;
        }

        .show-button {
            margin-bottom: 10px;
        }

        .medicineAdditionalInfo {
            display: none;
        }

        .procedureAdditionalInfo {
            display: none;
        }

        .addNewMedicine {
            display: none;
        }

        .addNewProcedure {
            display: none;
        }

        .addNewSurgery {
            display: none;
        }

        .fieldError {
            display: none;
        }

        .notification {
            display: none;
        }

        .pageContainer {
            width: max-content;
            max-width: 600px;
        }
    </style>
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
                            <h2>${diagnosis.name}</h2>
                        </div>
                        <div class="col-sm-3">
                            <c:if test="${sessionScope.LoggedUser.role == 'DOCTOR' and empty diagnosis.cured}">
                                <button id="closeDiagnosisButton" class="btn btn-primary">
                                    <span><fmt:message key="doctor.closeDiagnosis"/></span></button>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div id="closed" class="alert alert-info notification" role="alert"><fmt:message key="diagnosis.closeDiagnosis.closed" /></div>

                <div id="cantClose" class="alert alert-danger notification" role="alert"><fmt:message key="diagnosis.closeDiagnosis.cantClose" /></div>

                <div>
                    <h6><fmt:message key="doctor.showDiagnosis.description"/></h6>
                    <p>${diagnosis.description}</p>
                    <h6><fmt:message key="doctor.showDiagnosis.diagnosed"/></h6>
                    <p>${diagnosis.assigned.format(formatter)}</p>
                    <h6><fmt:message key="doctor.showDiagnosis.diagnosedBy"/></h6>
                    <p>${diagnosis.doctor.surname} ${diagnosis.doctor.name} ${diagnosis.doctor.patronymic}</p>
                    <c:if test="${!empty diagnosis.cured}">
                        <h6><fmt:message key="doctor.showDiagnosis.cured"/></h6>
                        <p>${diagnosis.cured.format(formatter)}</p>
                    </c:if>
                </div>

                <button id="showMedicine" role="button" class="btn btn-primary btn-lg btn-block show-button">
                    <fmt:message key="doctor.showDiagnosis.showMedicine"/></button>
                <div class="clearfix"></div>
                <div id="medicineContainer" class="medicine-container">
                    <div class="table-wrapper">

                        <div id="medicineCreated" class="alert alert-info notification" role="alert">
                            <fmt:message key="doctor.showDiagnosis.medicineCreated"/></div>
                        <div id="medicineCreationError" class="alert alert-danger fieldError" role="alert"></div>

                        <div class="table-filter">
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="show-entries">
                                        <span><fmt:message key="pagination.show"/></span>
                                        <div id="medicineEntriesDropdown" class="dropdown">
                                            <button id="medicineEntriesDropdownButton"
                                                    class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
                                                    type="button" data-toggle="dropdown">${page.size}</button>
                                            <ul class="dropdown-menu ">
                                                <li><a class="dropdown-item">5</a>
                                                </li>
                                                <li><a class="dropdown-item">10</a>
                                                </li>
                                                <li><a class="dropdown-item">15</a>
                                                </li>
                                                <li><a class="dropdown-item">20</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <span> <fmt:message key="pagination.entries"/></span>
                                    </div>
                                </div>


                                <div class="col-sm-9">

                                </div>
                            </div>
                        </div>

                        <table id="medicineTable" class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.id"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.name"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.assigned"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.assignedBy"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showMedicine.count"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.open"/></th>
                            </tr>
                            </thead>
                            <tbody id="medicineTbody">
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="hint-text"><fmt:message key="pagination.label.showing"/> <b
                                    id="medicinePageEntries"></b> <fmt:message key="pagination.label.outOf"/>
                                <b id="medicineTotalEntries"></b> <fmt:message key="pagination.label.entries"/>
                            </div>


                            <ul class="pagination">

                                <li id="medicinePreviousPage" class="page-item">
                                    <a class="page-link"><fmt:message key="pagination.previous"/></a>
                                </li>
                                <ul id="medicinePages" class="pagination"></ul>
                                <li id="medicineNextPage" class="page-item">
                                    <a class="page-link"><fmt:message key="pagination.next"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <c:if test="${sessionScope.LoggedUser.role == 'DOCTOR' || sessionScope.LoggedUser.role == 'NURSE'}">
                        <div id="addMedicine" class="addNewMedicine">
                            <form id="addMedicineForm" action="/doctor/diagnosis${diagnosis.idDiagnosis}/addMedicine"
                                  method="POST" enctype="utf8">
                                <div class="form-group">
                                    <label><fmt:message key="doctor.showDiagnosis.addTherapy.name"/></label>
                                    <div id="medicineNameFieldError" class="alert alert-danger fieldError" role="alert"
                                         required="required"></div>
                                    <input type="text" name="name" class="form-control" value=""/>
                                </div>
                                <div class="form-group">
                                    <label><fmt:message key="doctor.showDiagnosis.addTherapy.description"/></label>
                                    <textarea type="text" name="description" class="form-control  input-description"
                                              value=""></textarea>
                                </div>
                                <div class="form-group">
                                    <label><fmt:message key="doctor.showDiagnosis.addMedicine.count"/></label>
                                    <div id="medicineCountFieldError" class="alert alert-danger fieldError"
                                         role="alert"></div>
                                    <input type="number" class="form-control" name="count" value="" required="required"/>
                                </div>
                                <div class="form-group">
                                    <label><fmt:message key="doctor.showDiagnosis.addMedicine.refill"/></label>
                                    <input type='date' name="refill" class="form-control"/>
                                </div>
                            </form>
                        </div>
                        <button id="addMedicineBtn" role="button" class="btn btn-primary btn-lg btn-block show-button">
                            <fmt:message key="doctor.showDiagnosis.addMedicine.button"/></button>
                    </c:if>
                </div>

                <button id="showProcedures" role="button" class="btn btn-primary btn-lg btn-block show-button">
                    <fmt:message key="doctor.showDiagnosis.showProcedures"/></button>

                <div class="clearfix"></div>

                <div id="procedureContainer" class="procedure-container">
                    <div class="table-wrapper">
                        <div class="table-filter">

                            <div id="procedureCreated" class="alert alert-info notification" role="alert">
                                <fmt:message key="doctor.showDiagnosis.procedureCreated"/></div>
                            <div id="procedureCreationError" class="alert alert-danger fieldError" role="alert"></div>

                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="show-entries">
                                        <span><fmt:message key="pagination.show"/></span>
                                        <div id="procedureEntriesDropdown" class="dropdown">
                                            <button id="procedureEntriesDropdownButton"
                                                    class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
                                                    type="button" data-toggle="dropdown">${page.size}</button>
                                            <ul class="dropdown-menu ">
                                                <li><a class="dropdown-item">5</a>
                                                </li>
                                                <li><a class="dropdown-item">10</a>
                                                </li>
                                                <li><a class="dropdown-item">15</a>
                                                </li>
                                                <li><a class="dropdown-item">20</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <span> <fmt:message key="pagination.entries"/></span>
                                    </div>
                                </div>


                                <div class="col-sm-9">

                                </div>
                            </div>
                        </div>

                        <table id="procedureTable" class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.id"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.name"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.assigned"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.assignedBy"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showProcedure.room"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.open"/></th>
                            </tr>
                            </thead>
                            <tbody id="procedureTbody">
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="hint-text"><fmt:message key="pagination.label.showing"/> <b
                                    id="procedurePageEntries"></b> <fmt:message key="pagination.label.outOf"/>
                                <b id="procedureTotalEntries"></b> <fmt:message key="pagination.label.entries"/>
                            </div>


                            <ul class="pagination">

                                <li id="procedurePreviousPage" class="page-item">
                                    <a class="page-link"><fmt:message key="pagination.previous"/></a>
                                </li>

                                <ul id="procedurePages" class="pagination"></ul>

                                <li id="procedureNextPage" class="page-item">
                                    <a class="page-link"><fmt:message key="pagination.next"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <c:if test="${sessionScope.LoggedUser.role == 'DOCTOR' || sessionScope.LoggedUser.role == 'NURSE'}">
                        <div id="addProcedure" class="addNewProcedure">
                            <form id="addProcedureForm" method="POST" enctype="utf8">
                                <div class="form-group">
                                    <label><fmt:message key="doctor.showDiagnosis.addTherapy.name"/></label>
                                    <div id="procedureNameFieldError" class="alert alert-danger fieldError" role="alert"
                                         required="required"></div>
                                    <input type="text" name="name" class="form-control" value=""/>
                                </div>
                                <div class="form-group">
                                    <label><fmt:message key="doctor.showDiagnosis.addTherapy.description"/></label>
                                    <textarea type="text" name="description" class="form-control  input-description"
                                              value=""></textarea>
                                </div>

                                <div class="form-group">
                                    <label><fmt:message key="doctor.showDiagnosis.addProcedure.room"/></label>
                                    <div id="procedureRoomFieldError" class="alert alert-danger fieldError"
                                         role="alert"></div>
                                    <input type="number" class="form-control" name="room" value="" required="required"/>
                                </div>

                                <div id="appointmentDatesDiv" class="form-group assignedDatesWrap">
                                    <label><fmt:message key="doctor.showDiagnosis.addProcedure.apponitmentdates"/></label>
                                    <button id="addAssignedDateBtn" class="add_field_button"><fmt:message key="doctor.showDiagnosis.addProcedure.addNewDate"/></button>
                                </div>

                            </form>
                        </div>

                        <button id="addProcedureBtn" role="button" class="btn btn-primary btn-lg btn-block show-button">
                            <fmt:message key="doctor.showDiagnosis.addProcedure.button"/></button>
                    </c:if>
                </div>
                <button id="showSurgeries" role="button" class="btn btn-primary btn-lg btn-block"><fmt:message key="doctor.showDiagnosis.showSurgeries"/></button>
                <div class="clearfix"></div>
                <div id="surgeryContainer" class="procedure-container">
                    <div class="table-wrapper">


                        <div id="surgeryCreated" class="alert alert-info notification" role="alert">
                            <fmt:message key="doctor.showDiagnosis.surgeryCreated"/></div>
                        <div id="surgeryCreationError" class="alert alert-danger fieldError" role="alert"></div>


                        <div class="table-filter">
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="show-entries">
                                        <span><fmt:message key="pagination.show"/></span>
                                        <div id="surgeryEntriesDropdown" class="dropdown">
                                            <button id="surgeryEntriesDropdownButton"
                                                    class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
                                                    type="button" data-toggle="dropdown">${page.size}</button>
                                            <ul class="dropdown-menu ">
                                                <li><a class="dropdown-item">5</a>
                                                </li>
                                                <li><a class="dropdown-item">10</a>
                                                </li>
                                                <li><a class="dropdown-item">15</a>
                                                </li>
                                                <li><a class="dropdown-item">20</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <span> <fmt:message key="pagination.entries"/></span>
                                    </div>
                                </div>


                                <div class="col-sm-9">


                                </div>
                            </div>
                        </div>

                        <table id="surgeryTable" class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.id"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.name"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.assigned"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.assignedBy"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showSurgery.date"/></th>
                                <th><fmt:message key="doctor.showDiagnosis.showTherapy.open"/></th>
                            </tr>
                            </thead>
                            <tbody id="surgeryTbody">
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="hint-text"><fmt:message key="pagination.label.showing"/> <b
                                    id="surgeryPageEntries"></b> <fmt:message key="pagination.label.outOf"/>
                                <b id="surgeryTotalEntries"></b> <fmt:message key="pagination.label.entries"/></div>


                            <ul class="pagination">

                                <li id="surgeryPreviousPage" class="page-item">
                                    <a class="page-link"><fmt:message key="pagination.previous"/></a>
                                </li>

                                <ul id="surgeryPages" class="pagination"></ul>

                                <li id="surgeryNextPage" class="page-item">
                                    <a class="page-link"><fmt:message key="pagination.next"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <c:if test="${sessionScope.LoggedUser.role == 'DOCTOR'}">
                        <div id="addSurgery" class="addNewProcedure">
                            <form id="addSurgeryForm" method="POST" enctype="utf8">
                                <div class="form-group">
                                    <label><fmt:message key="doctor.showDiagnosis.addTherapy.name"/></label>
                                    <div id="surgeryNameFieldError" class="alert alert-danger fieldError" role="alert"
                                         required="required"></div>
                                    <input type="text" name="name" class="form-control" value=""/>
                                </div>
                                <div class="form-group">
                                    <label><fmt:message key="doctor.showDiagnosis.addTherapy.description"/></label>
                                    <textarea type="text" name="description" class="form-control  input-description"
                                              value=""></textarea>
                                </div>
                                <div class="form-group">
                                    <label><fmt:message key="doctor.showDiagnosis.addSurgery.date"/></label>
                                    <div id="surgeryDateFieldError" class="alert alert-danger fieldError"
                                         role="alert"></div>
                                    <input id="surgeryDateField" type="datetime-local" name="surgeryDate"
                                           max="2100-06-14T00:00"/>
                                </div>
                            </form>
                        </div>
                        <button id="addSurgeryBtn" role="button" class="btn btn-primary btn-lg btn-block show-button">
                            <fmt:message key="doctor.showSurgery.addSurgery.button"/></button>
                    </c:if>
                </div>
            </div>
            <div><h1>.</h1></div>
        </div>
        <div class="col-sm-2 sidenav">


        </div>
    </div>
</div>


<script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
        integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
        crossorigin="anonymous"></script>
<script src="/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $("#showMedicine").click(function () {
            if ($("#medicineContainer").is(":visible")) {
                $("#medicineContainer").hide();
            } else {
                $("#medicineContainer").show();
                loadMedicine(0, 10);
            }
        });

        <c:if test="${sessionScope.LoggedUser.role == 'DOCTOR' || sessionScope.LoggedUser.role == 'NURSE'}">
            $("#addMedicineBtn").click(function () {
                if ($("#addMedicine").is(":visible")) {
                    sendAddMedicine();
                } else {
                    $("#addMedicine").show();
                }
            });
        </c:if>

        $("#showProcedures").click(function () {
            if ($("#procedureContainer").is(":visible")) {
                $("#procedureContainer").hide();
            } else {
                $("#procedureContainer").show();
                loadProcedures(0, 10);
            }
        });

        <c:if test="${sessionScope.LoggedUser.role == 'DOCTOR' || sessionScope.LoggedUser.role == 'NURSE'}">
            $("#addProcedureBtn").click(function () {
                if ($("#addProcedure").is(":visible")) {
                    sendAddProcedure();
                } else {
                    $("#addProcedure").show();
                }
            });
        </c:if>

        $("#showSurgeries").click(function () {
            if ($("#surgeryContainer").is(":visible")) {
                $("#surgeryContainer").hide();
            } else {
                $("#surgeryContainer").show();
                loadSurgeries(0, 10);
            }
        });

        <c:if test="${sessionScope.LoggedUser.role == 'DOCTOR'}">

        $("#addSurgeryBtn").click(function () {
            if ($("#addSurgery").is(":visible")) {
                sendAddSurgery();

            } else {
                $("#addSurgery").show();
            }
        });

        $("#surgeryDateField").val(new Date().toJSON().slice(0, 19));
        $("#surgeryDateField").attr("min", (new Date().toJSON().slice(0, 19)));

        $("#closeDiagnosisButton").click(function () {
            closeDiagnosis();
        });

        </c:if>

        var x;
        $("#addAssignedDateBtn").click(function (e) {
            e.preventDefault();

            x++;
            $("#appointmentDatesDiv").append('<div>' +
                '<input type="datetime-local" name="appointmentDates" ' +
                'value="' + new Date().toJSON().slice(0, 19) +
                '" min="' + new Date().toJSON().slice(0, 19) +
                '" max="2100-06-14T00:00"/>' +
                '<a href="#" class="remove_field">${removeDate}</a></div>');

        });

        $("#appointmentDatesDiv").on("click", ".remove_field", function (e) {
            e.preventDefault();
            $(this).parent('div').remove();
            x--;
        })
    });

    //Loading medicine

    function loadMedicine(page, recordsPerPage) {

        console.log(page);
        $.ajax({
            type: 'GET',
            url: "/patient/${requestScope.diagnosis.patient.id}/diagnosis/${requestScope.diagnosis.idDiagnosis}/getMedicine/?page=" + (page + 1)+ "&recordsPerPage=" + recordsPerPage,
            contentType: "text/plain",
            dataType: 'json',
            success: function (data) {
                populateMedicineDataTable(data);
                setMedicinePaginationData(data)
            },
            error: function (e) {
                console.log("There was an error with your request...");
                console.log("error: " + JSON.stringify(e));
            }
        });
    }

    function setMedicinePaginationData(data) {
        console.log(data);
        var page = data.pageNumber;
        var recordsPerPage = data.pageSize;

        $('#medicineEntriesDropdownButton').html(recordsPerPage);
        $('#medicineEntriesDropdown a').click(function (e) {
            e.preventDefault(); // cancel the link behaviour
            var x = $(this).text();
            console.log(x + "fsf");
            loadMedicine(page, x);
        });
        $('#medicinePageEntries').html(data.numberOfElements);
        $('#medicineTotalEntries').html(data.totalElements);

        if (data.first) {
            $('#medicinePreviousPage').hide();
        } else {
            var prevButton = $('#medicinePreviousPage').show();
            prevButton.off("click");
            prevButton.click(function (e) {
                loadMedicine(page - 1, recordsPerPage);
            })
        }

        if (data.last) {
            $('#medicineNextPage').hide();
        } else {
            var nextButton = $('#medicineNextPage');
            nextButton.show();
            nextButton.off("click");
            nextButton.click(function (e) {
                loadMedicine(page + 1, recordsPerPage);
            })
        }

        $("#medicinePages").html("");
        for (var i = 1; i <= data.totalPages; i++) {
            if (i - 1 == data.number) {
                $("#medicinePages").append(
                    "<li class='page-item active'>" +
                    "<a class='page-link'>" + i + "</a>" +
                    "</li>");
            } else {
                $("#medicinePages").append(
                    "<li class='page-item'>" +
                    "<a id='medicinePage" + i + "' class='page-link'>" + i + "</a>" +
                    "</li>");

                $('#medicinePage' + i).click(function (e) {
                    var pag = $(this).text() - 1;
                    loadMedicine(pag, recordsPerPage);
                })
            }
        }


    }

    function populateMedicineDataTable(data) {
        $("#medicineTbody").html("");
        for (var i = 0; i < data.numberOfElements; i++) {
            addMedicineRow(data.content[i]);
        }
    }

    function addMedicineRow(dataEntry) {

        var refillDate;
        if(!dataEntry.refill || dataEntry.refill === null || dataEntry.refill === ""){
            refillDate = " "
        }else{
            refillDate = "<h6>${medicineRefill}</h6>" +
                "<p>" + new Date(dataEntry.refill).toLocaleString().slice(0, 10) + "</p>";
        }

        var row =
            "<tr>" +
            "<th>" + dataEntry.idTherapy + "</th>" +
            "<th>" + dataEntry.name + "</th>" +
            "<th>" + new Date(dataEntry.assigned).toLocaleString() + "</th>" +
            "<th>" + dataEntry.assignedBy.surname + " " + dataEntry.assignedBy.name + " " + dataEntry.assignedBy.patronymic + "</th>" +
            "<th>" + dataEntry.count + "</th>" +
            "<th>" + "<button id='showMedicineData" + dataEntry.idTherapy + "' class='btn btn-primary'>${showMedicineAdditionalInfo}</button> </th>" +
            "</tr>" +
            "<tr id='mAd" + dataEntry.idTherapy + "' class='medicineAdditionalInfo'>" +
            "<th colspan='6'>" +
            "<h6>${therapyDescription}</h6>" +
            "<p>" + dataEntry.description + "</p>" +
            "<h6>${therapyDoctorEmail}</h6>" +
            "<p>" + dataEntry.assignedBy.email + "</p>" +
            refillDate +
            "</th>" +
            "</tr>";


        $("#medicineTbody").append(row);

        var dataContainer = $("#mAd" + dataEntry.idTherapy);
        $("#showMedicineData" + dataEntry.idTherapy).click(function () {
            if (dataContainer.is(":visible")) {
                dataContainer.hide();
            } else {
                dataContainer.toggle();
            }
        });
    }

    //Loading procedures


    function loadProcedures(page, recordsPerPage) {
        $.ajax({
            type: 'GET',
            url: "/patient/${requestScope.diagnosis.patient.id}/diagnosis/${requestScope.diagnosis.idDiagnosis}/getProcedures/?page=" + (page + 1)+ "&recordsPerPage=" + recordsPerPage,
            contentType: "text/plain",
            dataType: 'json',
            success: function (data) {
                populateProceduresDataTable(data);
                setProceduresPaginationData(data)
            },
            error: function (e) {
                console.log("There was an error with your request...");
                console.log("error: " + JSON.stringify(e));
            }
        });
    }

    function setProceduresPaginationData(data) {
        console.log(data);
        var page = data.pageNumber;
        var recordsPerPage = data.pageSize;

        $('#procedureEntriesDropdownButton').html(recordsPerPage);
        $('#procedureEntriesDropdown a').click(function (e) {
            e.preventDefault(); // cancel the link behaviour
            var x = $(this).text();
            console.log(x + "fsf");
            loadProcedures(page, x);
        });
        $('#procedurePageEntries').html(data.numberOfElements);
        $('#procedureTotalEntries').html(data.totalElements);

        if (data.first) {
            $('#procedurePreviousPage').hide();
        } else {
            var prevButton = $('#procedurePreviousPage').show();
            prevButton.off("click");
            prevButton.click(function (e) {
                loadProcedures(page - 1, recordsPerPage);
            })
        }

        if (data.last) {
            $('#procedureNextPage').hide();
        } else {
            var nextButton = $('#procedureNextPage');
            nextButton.show();
            nextButton.off("click");
            nextButton.click(function (e) {
                loadProcedures(page + 1, recordsPerPage);
            })
        }

        $("#procedurePages").html("");
        for (var i = 1; i <= data.totalPages; i++) {
            if (i - 1 == data.number) {
                $("#procedurePages").append(
                    "<li class='page-item active'>" +
                    "<a class='page-link'>" + i + "</a>" +
                    "</li>");
            } else {
                $("#procedurePages").append(
                    "<li class='page-item'>" +
                    "<a id='procedurePage" + i + "' class='page-link'>" + i + "</a>" +
                    "</li>");

                $('#procedurePage' + i).click(function (e) {
                    var pag = $(this).text() - 1;
                    loadProcedures(pag, recordsPerPage);
                })
            }
        }


    }

    function populateProceduresDataTable(data) {
        $("#procedureTbody").html("");
        for (var i = 0; i < data.numberOfElements; i++) {
            addProcedureRow(data.content[i]);
        }
    }

    function addProcedureRow(dataEntry) {
        var dates = "";
        for (var i = 0; i < dataEntry.appointmentDates.length; i++) {
            dates += "<p>" + new Date(dataEntry.appointmentDates[i]).toLocaleString() + "</p>";
        }

        var row =
            "<tr>" +
            "<th>" + dataEntry.idTherapy + "</th>" +
            "<th>" + dataEntry.name + "</th>" +
            "<th>" + new Date(dataEntry.assigned).toLocaleString() + "</th>" +
            "<th>" + dataEntry.assignedBy.surname + " " + dataEntry.assignedBy.name + " " + dataEntry.assignedBy.patronymic + "</th>" +
            "<th>" + dataEntry.room + "</th>" +
            "<th>" + "<button id='showProcedureData" + dataEntry.idTherapy + "' class='btn btn-primary'>${showMedicineAdditionalInfo}</button> </th>" +
            "</tr>" +
            "<tr id='prAd" + dataEntry.idTherapy + "' class='procedureAdditionalInfo'>" +
            "<th colspan='6'>" +
            "<h6>${therapyDescription}</h6>" +
            "<p>" + dataEntry.description + "</p>" +
            "<h6>${therapyDoctorEmail}</h6>" +
            "<p>" + dataEntry.assignedBy.email + "</p>" +
            "<h6>${procedureAppointmentDates}</h6>" +
            dates +
            "</th>" +
            "</tr>";


        $("#procedureTbody").append(row);

        var dataContainer = $("#prAd" + dataEntry.idTherapy);
        $("#showProcedureData" + dataEntry.idTherapy).click(function () {
            if (dataContainer.is(":visible")) {
                dataContainer.hide();
            } else {
                dataContainer.toggle();
            }
        });
    }


    //Loading surgeries


    function loadSurgeries(page, recordsPerPage) {
        $.ajax({
            type: 'GET',
            url: "/patient/${requestScope.diagnosis.patient.id}/diagnosis/${requestScope.diagnosis.idDiagnosis}/getSurgeries/?page=" + (page + 1)+ "&recordsPerPage=" + recordsPerPage,
            contentType: "text/plain",
            dataType: 'json',
            success: function (data) {
                populateSurgeryDataTable(data);
                setSurgeryPaginationData(data)
            },
            error: function (e) {
                console.log("There was an error with your request...");
                console.log("error: " + JSON.stringify(e));
            }
        });
    }

    function setSurgeryPaginationData(data) {
        console.log(data);
        var page = data.pageNumber;
        var recordsPerPage = data.pageSize;

        $('#surgeryEntriesDropdownButton').html(recordsPerPage);
        $('#surgeryEntriesDropdown a').click(function (e) {
            e.preventDefault(); // cancel the link behaviour
            var x = $(this).text();
            console.log(x + "fsf");
            loadSurgeries(page, x);
        });
        $('#surgeryPageEntries').html(data.numberOfElements);
        $('#surgeryTotalEntries').html(data.totalElements);

        if (data.first) {
            $('#surgeryPreviousPage').hide();
        } else {
            var prevButton = $('#surgeryPreviousPage').show();
            prevButton.off("click");
            prevButton.click(function (e) {
                loadSurgeries(page - 1, recordsPerPage);
            })
        }

        if (data.last) {
            $('#surgeryNextPage').hide();
        } else {
            var nextButton = $('#surgeryNextPage');
            nextButton.show();
            nextButton.off("click");
            nextButton.click.click(function (e) {
                loadSurgeries(page + 1, recordsPerPage);
            })
        }

        $("#surgeryPages").html("");
        for (var i = 1; i <= data.totalPages; i++) {
            if (i - 1 == data.number) {
                $("#surgeryPages").append(
                    "<li class='page-item active'>" +
                    "<a class='page-link'>" + i + "</a>" +
                    "</li>");
            } else {
                $("#surgeryPages").append(
                    "<li class='page-item'>" +
                    "<a id='surgeryPage" + i + "' class='page-link'>" + i + "</a>" +
                    "</li>");

                $('#surgeryPage' + i).click(function (e) {
                    var pag = $(this).text() - 1;
                    loadSurgeries(pag, recordsPerPage);
                })
            }
        }


    }

    function populateSurgeryDataTable(data) {
        $("#surgeryTbody").html("");
        for (var i = 0; i < data.numberOfElements; i++) {
            addSurgeryRow(data.content[i]);
        }
    }

    function addSurgeryRow(dataEntry) {
        var row =
            "<tr>" +
            "<th>" + dataEntry.idTherapy + "</th>" +
            "<th>" + dataEntry.name + "</th>" +
            "<th>" + new Date(dataEntry.assigned).toLocaleString() + "</th>" +
            "<th>" + dataEntry.assignedBy.surname + " " + dataEntry.assignedBy.name + " " + dataEntry.assignedBy.patronymic + "</th>" +
            "<th>" + new Date(dataEntry.date).toLocaleString() + "</th>" +
            "<th>" + "<button id='showSurgeryData" + dataEntry.idTherapy + "' class='btn btn-primary'>${showMedicineAdditionalInfo}</button> </th>" +
            "</tr>" +
            "<tr id='suAd" + dataEntry.idTherapy + "' class='medicineAdditionalInfo'>" +
            "<th colspan='6'>" +
            "<h6>${therapyDescription}</h6>" +
            "<p>" + dataEntry.description + "</p>" +
            "<h6>${therapyDoctorEmail}</h6>" +
            "<p>" + dataEntry.assignedBy.email + "</p>" +
            "</th>" +
            "</tr>";
        console.log(row);


        $("#surgeryTbody").append(row);

        var dataContainer = $("#suAd" + dataEntry.idTherapy);
        $("#showSurgeryData" + dataEntry.idTherapy).click(function () {
            if (dataContainer.is(":visible")) {
                dataContainer.hide();
            } else {
                dataContainer.toggle();
            }
        });
    }

    //add new therapy functions
    <c:if test="${sessionScope.LoggedUser.role == 'DOCTOR' || sessionScope.LoggedUser.role == 'NURSE'}">
        function sendAddMedicine() {
        $("#medicineCreated").hide();

        var formData = getFormData($('#addMedicineForm'));
        console.log(formData);

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            type: 'POST',
            url: "/patient/${requestScope.diagnosis.patient.id}/diagnosis/${requestScope.diagnosis.idDiagnosis}/addMedicine/",
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (data) {
                console.log(data);
                $("#addMedicine").hide();
                hideMedicineNotifications();
                if (data.message === "created") {
                    $("#medicineCreated").show();
                } else {
                    $("#medicineCreationError").html(data.message);
                    $("#medicineCreationError").show();
                }

            },
            error: function (data) {
                console.log(data);
                hideMedicineNotifications();
                data.responseJSON.errors.forEach(function (error) {
                    console.log(error);
                    if (error.cause === "name") {
                        var errMessage = $("#medicineNameFieldError");
                        errMessage.html(error.message);
                        errMessage.show();

                    } else if (error.cause === "count") {
                        var errMessage = $("#medicineCountFieldError");
                        errMessage.html(error.message);
                        errMessage.show();
                    } else if (error.cause === "object") {
                        var errMessage = $("#medicineCreationError");
                        errMessage.html(error.message);
                        errMessage.show();
                    }else {
                        var errMessage = $("#medicineCreationError");
                        errMessage.html(error.message);
                        errMessage.show();
                    }
                });
            }
        });

    }


    function hideMedicineNotifications() {
        $("#medicineNameFieldError").hide();
        $("#medicineCountFieldError").hide();
        $("#medicineCreationError").hide();
        $("#medicineCreated").hide();
    }

    function sendAddProcedure() {

        $("#procedureCreated").hide();

        var formData = getFormData($("#addProcedureForm"));
        console.log(formData);
        console.log(JSON.stringify(formData));

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            type: 'POST',
            url: "/patient/${requestScope.diagnosis.patient.id}/diagnosis/${requestScope.diagnosis.idDiagnosis}/addProcedure/",
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (data) {
                console.log(data);
                $("#addProcedure").hide();
                hideProcedureNotifications();
                if (data.message === "created") {
                    $("#procedureCreated").show();
                } else {
                    $("#procedureCreationError").html(data.message);
                    $("#procedureCreationError").show();
                }

            },
            error: function (data) {
                console.log(data);
                hideProcedureNotifications();
                if (data.responseJSON.errors !== 'undefined' && data.responseJSON.errors.length > 0) {
                    data.responseJSON.errors.forEach(function (error) {
                        if (error.cause === "name") {
                            var errMessage = $("#procedureNameFieldError");
                            errMessage.html(error.message);
                            errMessage.show();

                        } else if (error.cause === "room") {
                            var errMessage = $("#procedureRoomFieldError");
                            errMessage.html(error.message);
                            errMessage.show();
                        } else if (error.cause === "object") {
                            var errMessage = $("#procedureCreationError");
                            errMessage.html(error.message);
                            errMessage.show();
                        } else {
                            var errMessage = $("#procedureCreationError");
                            errMessage.html(data.responseJSON.message);
                            errMessage.show();
                        }
                    });
                } else {
                    var errMessage = $("#procedureCreationError");
                    errMessage.html(data.responseJSON.message);
                    errMessage.show();
                }
            }
        });

    }


    function hideProcedureNotifications() {
        $("#procedureNameFieldError").hide();
        $("#procedureRoomFieldError").hide();
        $("#procedureCreationError").hide();
        $("#procedureCreated").hide();
    }
    </c:if>
    <c:if test="${sessionScope.LoggedUser.role == 'DOCTOR'}">

    function sendAddSurgery() {
        $("#surgeryCreated").hide();

        var formData = getFormData($("#addSurgeryForm"));
        console.log(formData);
        console.log(JSON.stringify(formData));

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            type: 'POST',
            url: "/patient/${requestScope.diagnosis.patient.id}/diagnosis/${requestScope.diagnosis.idDiagnosis}/addSurgery/",
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (data) {
                console.log(data);
                $("#addSurgery").hide();
                hideSurgeryNotifications();
                console.log(data);
                if (data.message === "created") {
                    $("#surgeryCreated").show();
                } else {
                    $("#surgeryCreationError").html(data.message);
                    $("#surgeryCreationError").show();
                }

            },
            error: function (data) {
                console.log(data);
                hideSurgeryNotifications();
                if (data.responseJSON.errors !== 'undefined' && data.responseJSON.errors.length > 0) {
                    data.responseJSON.errors.forEach(function (error) {
                        if (error.cause === "name") {
                            var errMessage = $("#surgeryNameFieldError");
                            errMessage.html(error.message);
                            errMessage.show();

                        } else if (error.cause === "surgeryDate") {
                            var errMessage = $("#surgeryDateFieldError");
                            errMessage.html(error.message);
                            errMessage.show();
                        } else if (error.cause === "object") {
                            var errMessage = $("#surgeryCreationError");
                            errMessage.html(error.message);
                            errMessage.show();
                        } else {
                            var errMessage = $("#surgeryCreationError");
                            errMessage.html(data.responseJSON.message);
                            errMessage.show();
                        }
                    });
                } else {
                    var errMessage = $("#surgeryCreationError");
                    errMessage.html(data.responseJSON.message);
                    errMessage.show();
                }
            }
        });
    }

    function hideSurgeryNotifications() {
        $("#surgeryNameFieldError").hide();
        $("#surgeryDateFieldError").hide();
        $("#surgeryCreationError").hide();
        $("#surgeryCreated").hide();
    }
    </c:if>

    function getFormData($form) {
        var unindexed_array = $form.serializeArray();

        console.log(unindexed_array);

        var indexed_array = {};

        var dateIndex = 0;
        $.map(unindexed_array, function (n, i) {
            if (n['name'] === "appointmentDates") {
                console.log(n);
                if (dateIndex === 0) {
                    indexed_array['appointmentDates'] = [];
                }
                indexed_array.appointmentDates[(dateIndex++)] = n['value'];
            } else {
                indexed_array[n['name']] = n['value'];
            }
        });
        return indexed_array;
    }

    function closeDiagnosis() {


        $.ajax({
            type: 'POST',
            url: "/patient/${requestScope.diagnosis.patient.id}/diagnosis/${diagnosis.idDiagnosis}/closeDiagnosis/",
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                console.log(data);
                if (data.response === "closed") {
                    $("#closed").show();
                    $("#closeDiagnosisButton").hide();
                    $("#cantClose").hide();
                } else {
                    $("#closed").hide();
                    $("#cantClose").show();
                }
            },
            error: function (data) {
                console.log(data);
                $("#closed").hide();
                $("#cantClose").show();
            }
        });
    }
</script>
</body>
</html>
