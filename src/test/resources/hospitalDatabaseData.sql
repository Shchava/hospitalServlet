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

INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill)
VALUES ('medicineName1','medicineDescription1',{ts '2019-07-17 18:47:44.69'},1,30,{d '2019-08-17'});
INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill)
VALUES ('medicineName2','medicineDescription2',{ts '2019-07-18 17:44:52.11'},2,40,{d '2019-09-11'});
INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill)
VALUES ('medicineName3','medicineDescription3',{ts '2019-07-19 12:41:52.12'},1,60,{d '2019-08-01'});
INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill)
VALUES ('medicineName4','medicineDescription4',{ts '2019-07-16 11:45:12.66'},4,10,{d '2019-08-12'});
INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill)
VALUES ('medicineName5','medicineDescription5',{ts '2019-07-15 14:17:25.69'},5,5,{d '2019-09-13'});
INSERT INTO MEDICINE(name,description, assigned, assigned_by_id_user, count, refill)
VALUES ('medicineName6','medicineDescription6',{ts '2019-07-19 13:01:12.78'},4,60,{d '2019-09-14'});

INSERT INTO OPERATION(name,description, assigned, assigned_by_id_user, date)
VALUES ('operationName1','operationDescription1',{ts '2019-07-05 17:45:44.21'},1,{ts '2019-08-15 10:30'});
INSERT INTO OPERATION(name,description, assigned, assigned_by_id_user, date)
VALUES ('operationName2','operationDescription2',{ts '2019-07-21 12:44:12.11'},2,{ts '2019-09-05 11:00'});
INSERT INTO OPERATION(name,description, assigned, assigned_by_id_user, date)
VALUES ('operationName3','operationDescription3',{ts '2019-07-19 13:12:31.11'},1,{ts '2019-08-07 16:00'});
INSERT INTO OPERATION(name,description, assigned, assigned_by_id_user, date)
VALUES ('operationName4','operationDescription4',{ts '2019-07-18 14:23:44.66'},4,{ts '2019-08-12 13:00'});
INSERT INTO OPERATION(name,description, assigned, assigned_by_id_user, date)
VALUES ('operationName5','operationDescription5',{ts '2019-07-21 11:21:55.43'},5,{ts '2019-08-29 9:00'});
INSERT INTO OPERATION(name,description, assigned, assigned_by_id_user, date)
VALUES ('operationName6','operationDescription6',{ts '2019-07-10 13:04:11.21'},4,{ts '2019-09-14 14:45'});