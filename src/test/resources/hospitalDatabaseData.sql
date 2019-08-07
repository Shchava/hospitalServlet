INSERT INTO USER(name, surname, patronymic, email, password_hash, role)
VALUES ('userName1', 'userSurname1', 'userPatronymic1', 'email1@example.com', 'someHash1', 'DOCTOR');
INSERT INTO USER(name, surname, patronymic, email, password_hash, role)
VALUES ('userName2', 'userSurname2', 'userPatronymic2', 'email2@example.com', 'someHash2', 'NURSE');
INSERT INTO USER(name, surname, patronymic, email, password_hash, role)
VALUES ('userName3', 'userSurname3', 'userPatronymic3', 'email3@example.com', 'someHash3', 'PATIENT');
INSERT INTO USER(name, surname, patronymic, email, password_hash, role)
VALUES ('userName4', 'userSurname4', 'userPatronymic4', 'email4@example.com', 'someHash4', 'DOCTOR');
INSERT INTO USER(name, surname, patronymic, email, password_hash, role)
VALUES ('userName5', 'userSurname5', 'userPatronymic5', 'email5@example.com', 'someHash5', 'NURSE');
INSERT INTO USER(name, surname, patronymic, email, password_hash, role)
VALUES ('userName6', 'userSurname6', 'userPatronymic6', 'email6@example.com', 'someHash6', 'PATIENT');

INSERT INTO DIAGNOSIS(name, description, assigned, cured,  doctor_id_user, patient_id_user)
VALUES ('diagnosisName1','diagnosisDescription1',{ts '2019-07-17 18:47:44.69'},{ts '2019-08-01 13:43:12.31'},4,3);
INSERT INTO DIAGNOSIS(name, description, assigned, cured,  doctor_id_user, patient_id_user)
VALUES ('diagnosisName2','diagnosisDescription2',{ts '2019-06-17 18:47:44.69'},NULL,4,6);
INSERT INTO DIAGNOSIS(name, description, assigned, cured,  doctor_id_user, patient_id_user)
VALUES ('diagnosisName3','diagnosisDescription3',{ts '2019-07-16 18:47:44.69'},{ts '2019-07-31 15:21:12.43'},1,3);
INSERT INTO DIAGNOSIS(name, description, assigned, cured,  doctor_id_user, patient_id_user)
VALUES ('diagnosisName4','diagnosisDescription4',{ts '2019-06-21 18:47:44.69'},NULL,1,6);
INSERT INTO DIAGNOSIS(name, description, assigned, cured,  doctor_id_user, patient_id_user)
VALUES ('diagnosisName5','diagnosisDescription5',{ts '2019-07-31 18:47:44.69'},{ts '2019-08-07 11:35:54.32'},4,3);
INSERT INTO DIAGNOSIS(name, description, assigned, cured,  doctor_id_user, patient_id_user)
VALUES ('diagnosisName6','diagnosisDescription6',{ts '2019-05-21 18:47:44.69'},NULL,1,6);

INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill,diagnosis)
VALUES ('medicineName1','medicineDescription1',{ts '2019-07-17 18:47:44.69'},1,30,{d '2019-08-17'},1);
INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill,diagnosis)
VALUES ('medicineName2','medicineDescription2',{ts '2019-07-18 17:44:52.11'},2,40,{d '2019-09-11'},2);
INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill,diagnosis)
VALUES ('medicineName3','medicineDescription3',{ts '2019-07-19 12:41:52.12'},1,60,{d '2019-08-01'},2);
INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill,diagnosis)
VALUES ('medicineName4','medicineDescription4',{ts '2019-07-16 11:45:12.66'},4,10,{d '2019-08-12'},3);
INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill,diagnosis)
VALUES ('medicineName5','medicineDescription5',{ts '2019-07-15 14:17:25.69'},5,5,{d '2019-09-13'},3);
INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill,diagnosis)
VALUES ('medicineName6','medicineDescription6',{ts '2019-07-19 13:01:12.78'},4,60,{d '2019-09-14'},3);

INSERT INTO SURGERY(name,description, assigned, assigned_by_id_user, date,diagnosis)
VALUES ('operationName1','operationDescription1',{ts '2019-07-05 17:45:44.21'},1,{ts '2019-08-15 10:30'},4);
INSERT INTO SURGERY(name,description, assigned, assigned_by_id_user, date,diagnosis)
VALUES ('operationName2','operationDescription2',{ts '2019-07-21 12:44:12.11'},2,{ts '2019-09-05 11:00'},4);
INSERT INTO SURGERY(name,description, assigned, assigned_by_id_user, date,diagnosis)
VALUES ('operationName3','operationDescription3',{ts '2019-07-19 13:12:31.11'},1,{ts '2019-08-07 16:00'},4);
INSERT INTO SURGERY(name,description, assigned, assigned_by_id_user, date,diagnosis)
VALUES ('operationName4','operationDescription4',{ts '2019-07-18 14:23:44.66'},4,{ts '2019-08-12 13:00'},5);
INSERT INTO SURGERY(name,description, assigned, assigned_by_id_user, date,diagnosis)
VALUES ('operationName5','operationDescription5',{ts '2019-07-21 11:21:55.43'},5,{ts '2019-08-29 9:00'},5);
INSERT INTO SURGERY(name,description, assigned, assigned_by_id_user, date,diagnosis)
VALUES ('operationName6','operationDescription6',{ts '2019-07-10 13:04:11.21'},4,{ts '2019-09-14 14:45'},5);

INSERT INTO TREATMENT(name,description, assigned, assigned_by_id_user, room,diagnosis)
VALUES ('procedureName1','procedureDescription1',{ts '2019-07-09 12:43:31.65'},1,112,6);
INSERT INTO TREATMENT(name,description, assigned, assigned_by_id_user, room,diagnosis)
VALUES ('procedureName2','procedureDescription2',{ts '2019-07-30 15:11:23.43'},2,511,6);
INSERT INTO TREATMENT(name,description, assigned, assigned_by_id_user, room,diagnosis)
VALUES ('procedureName3','procedureDescription3',{ts '2019-07-21 17:32:54.23'},1,912,1);
INSERT INTO TREATMENT(name,description, assigned, assigned_by_id_user, room,diagnosis)
VALUES ('procedureName4','procedureDescription4',{ts '2019-07-27 10:11:09.12'},4,120,1);
INSERT INTO TREATMENT(name,description, assigned, assigned_by_id_user, room,diagnosis)
VALUES ('procedureName5','procedureDescription5',{ts '2019-07-25 14:43:12.46'},5,301,2);
INSERT INTO TREATMENT(name,description, assigned, assigned_by_id_user, room,diagnosis)
VALUES ('procedureName6','procedureDescription6',{ts '2019-07-03 16:31:34.32'},4,325,1);

INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (1,{ts '2019-07-09 10:49:57.50'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (1,{ts '2019-07-02 18:47:54.04'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (1,{ts '2019-07-01 12:34:27.37'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (1,{ts '2019-07-29 13:50:39.03'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (2,{ts '2019-07-12 17:30:19.11'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (2,{ts '2019-07-10 17:54:40.13'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (2,{ts '2019-07-29 11:19:53.52'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (2,{ts '2019-07-22 15:17:15.38'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (3,{ts '2019-07-07 11:08:52.45'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (3,{ts '2019-07-24 08:19:51.03'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (3,{ts '2019-07-05 14:46:39.59'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (3,{ts '2019-07-04 13:07:08.27'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (3,{ts '2019-07-18 11:13:20.17'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (4,{ts '2019-07-09 16:27:28.54'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (4,{ts '2019-07-22 12:55:15.33'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (4,{ts '2019-07-01 17:05:39.12'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (5,{ts '2019-07-08 12:54:14.23'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (5,{ts '2019-07-20 16:19:13.41'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (5,{ts '2019-07-27 18:49:13.27'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (5,{ts '2019-07-15 17:36:03.53'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (5,{ts '2019-07-07 14:29:02.11'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (6,{ts '2019-07-15 12:35:12.59'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (6,{ts '2019-07-29 18:45:39.47'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (6,{ts '2019-07-08 12:48:44.18'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (6,{ts '2019-07-03 08:03:47.20'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (6,{ts '2019-07-24 18:38:01.50'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (6,{ts '2019-07-08 14:18:45.54'});
INSERT INTO PROCEDURE_APPOINTMENT_DATES(procedure_id_therapy, appointment_dates) VALUES (6,{ts '2019-07-06 12:54:14.23'});