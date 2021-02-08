INSERT INTO authority(name) VALUES ('ROLE_PATIENT');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACIST');
INSERT INTO authority(name) VALUES ('ROLE_DERMATOLOGIST');
INSERT INTO authority(name) VALUES ('ROLE_SUPPLIER');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACY_ADMIN');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACY_SYSTEM_ADMIN');

INSERT INTO medication_type(name) VALUES ('Analgesic');
INSERT INTO medication_type(name) VALUES ('Anesthetic');
INSERT INTO medication_type(name) VALUES ('Antibiotic');
INSERT INTO medication_type(name) VALUES ('Antihistamine');
INSERT INTO medication_type(name) VALUES ('Androgen');
INSERT INTO medication_type(name) VALUES ('Antiandrogens');
INSERT INTO medication_type(name) VALUES ('Estrogen');
INSERT INTO medication_type(name) VALUES ('Antiseptic');
INSERT INTO medication_type(name) VALUES ('Local Anesthetic');
INSERT INTO medication_type(name) VALUES ('Antifungal');
INSERT INTO medication_type(name) VALUES ('Insulin');
INSERT INTO medication_type(name) VALUES ('Antidiabetic');
INSERT INTO medication_type(name) VALUES ('Alkalinizing agent');
INSERT INTO medication_type(name) VALUES ('Oestrogen');
INSERT INTO medication_type(name) VALUES ('Antimalarial');
INSERT INTO medication_type(name) VALUES ('Antiviral');
INSERT INTO medication_type(name) VALUES ('Antitoxin');

--user password: 123
-- system admin password: admin
-- pharmacy admin password: phadmin
--dermatologist 1 password: derma
--supplier password: supp
--dermatologist 2 password: derma2
--dermatologist 3 password: derma3
--pharmacist 1 password: pharma
--pharmacist 2 password: pharma2
--dermatologist 4 password: derma4
--pharmacy admin2 password: phadmin2
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('isapsw1234@gmail.com', '$2a$10$M3.YOtEuBCSNbgzJ3hkQv.ZgOMGNSTataYMx1UU7OnqMrlC6Osgzm', 'Misa', 'Dimitrijevic', 'ROLE_PATIENT', true, true, false);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('admin@gmail.com', '$2a$10$cQSdC5wh2L6oZ2QFGNyD.OhnZ7akIGf7FEx0lmQ4RK6RiHyqz7cOC', 'Nikola', 'Stankovic', 'ROLE_PHARMACY_SYSTEM_ADMIN', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('phadmin@gmail.com', '$2a$10$n8yxFV7zy7PDResCzemMLOhcPcbwLuF7Npn5lHhxVg6pKkFOi8PE2', 'Sima', 'Savic', 'ROLE_PHARMACY_ADMIN', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('supp@gmail.com', '$2a$10$2io/7xyfMDpHe4tkBse/VeuSYma/m4rt7lVb.MdslpFvJ/AGUGIvq', 'Sinisa', 'Bekic', 'ROLE_SUPPLIER', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('derma2@gmail.com', '$2a$10$crj2ORyKLiXLB.YZaLJw8eNY.ZZCoFrMotcIfMNKNUEHnBDpg6YiW', 'Pera', 'Detlic', 'ROLE_DERMATOLOGIST', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('derma@gmail.com', '$2a$10$oelbk6QvYYQ507ltE2UHeO/q6a1Lc5KYUT0niU1GTbQezsIaxnCye', 'Sasa', 'Sakic', 'ROLE_DERMATOLOGIST', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('derma3@gmail.com', '$2a$10$nRKPXolFTWacoiwT/gwr7.UHnfTFobcwAnFVazTjQOQWMgsbbFDCW', 'Marko', 'Savkovic', 'ROLE_DERMATOLOGIST', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('pharma@gmail.com', '$2a$10$xAZAZSPxDkZwEK6M5CdRl.s..ehMpu8buh.30afnLFw.7MhsKaAHu', 'Mateja', 'Kezman', 'ROLE_PHARMACIST', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('pharma2@gmail.com', '$2a$10$RcH9987wE/Ki5A9Jcy4WxOjwO.f/Lg7qb0l3AC3a/.kYxSEDAauKO', 'Boro', 'Drljaca', 'ROLE_PHARMACIST', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('derma4@gmail.com', '$2a$10$hnftkRIbDohiKU/DioxxTuuAT435oXq3Lj2sobLJ7bco/UsIqfMlK', 'Keba', 'Kraba', 'ROLE_DERMATOLOGIST', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('phadmin2@gmail.com', '$2a$10$/tn3byVZBK1iDkiiH7Qj.uj6nWIlAib7SHI5cASgAbj5dob33aOgK', 'Bosko', 'Buha', 'ROLE_PHARMACY_ADMIN', true, true, true);

INSERT INTO patient(address, phone_number, city, country, processed, user_id) VALUES ('adresica','1234567890', 'zrenjanin', 'serbia', true, 1);

INSERT INTO pharmacy_system_administrator(user_id) VALUES (2);

INSERT INTO supplier(user_id) VALUES (5);

INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Rivotril', '#123', 100, 'Caughing Blood', 'diklofenak-kalium 50 mg', 17);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Acetaminophen', '#222', 50, 'Coryza', 'silicium-dioxide', 16);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Adderall', '#333', 75, 'Dry Caugh', 'povidon K-30', 15);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Entyvio', '#444', 50, '', 'magnezium-stearat', 14);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Gabapentin', '#555', 50, 'Crying', 'Cochenillerotlack E124 C.I. 16255', 13);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Gilenya', '#655', 50, 'Headache', 'makrogol 6000', 12);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Humira', '#657', 80, 'Sore throat', 'Croton tiglium', 11);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Hydrochlorothiazide', '#666', 100, 'Diarrhea', 'Cypripedium pubescens', 10);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Lexapro', '#442', 50, 'Muscle or body aches', 'Ferrum phosphoricum', 9);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Melatonin', '#338', 120, 'Shortness of breath or difficulty breathing', 'Hepar sulfuris', 8);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Meloxicam', '#675', 60, 'New loss of taste or smell', 'Kalijum fosfat', 7);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Rybelsus', '#776', 70, 'Congestion or runny nose', 'Lac defloratum', 6);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Tramadol', '#880', 90, 'Nausea or vomiting', 'Veratrum album', 5);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Wellbutrin', '#920', 50, 'Trouble breathing', 'makrogol 4700', 4);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Xanax', '#121', 100, 'Bluish lips or face', 'diklofenak-kalium 50 mg', 3);
INSERT INTO medication(name, code, daily_intake, side_effects, chemical_composition, medication_type_id) VALUES ('Omeprazole', '#662', 100, 'Inability to wake or stay awake', 'silicium-dioxide E', 2);

INSERT INTO pharmacy(pharmacy_name, address, city, rating, number_of_votes, description, counseling_price) VALUES ('Apoteka Jankovic', 'Mileticeva 57', 'Zrenjanin', 2.5, 55, 'Opis apoteke 1', 1000);
INSERT INTO pharmacy(pharmacy_name, address, city, rating, number_of_votes, description, counseling_price) VALUES ('Apoteka Sveti Jovan', 'Apatinska 21', 'Novi Sad', 3.2, 21, 'Opis apoteke 2', 3000);
INSERT INTO pharmacy(pharmacy_name, address, city, rating, number_of_votes, description, counseling_price) VALUES ('Apoteka Jankovic', 'Rumunski Drum 11', 'Beograd', 3.6, 11, 'Opis apoteke 3', 800);
INSERT INTO pharmacy(pharmacy_name, address, city, rating, number_of_votes, description, counseling_price) VALUES ('Apoteka Sveti Petar', 'Mise Dimitrijevica 26', 'Novi Sad', 4.2, 101, 'Opis apoteke 4', 490);
INSERT INTO pharmacy(pharmacy_name, address, city, rating, number_of_votes, description, counseling_price) VALUES ('Apoteka Crveni Krst', 'Vojvode Petra Bojovica 1B', 'Beograd', 3.9, 2, 'Opis apoteke 5', 1500);

INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (200, 1000, 1, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (152, 700, 2, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (666, 700, 3, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (213, 670, 4, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (541, 255, 5, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (333, 555, 6, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (224, 120, 7, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (115, 800, 8, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (111, 800, 9, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (152, 220, 13, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (152, 999, 12, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (152, 870, 11, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (12, 1800, 10, 1, false);
INSERT INTO medications_pharmacies(amount, price, medication_id, pharmacy_id, deleted) VALUES (2, 1300, 1, 4, false);

INSERT INTO dermatologist(number_of_votes, rating, user_id) VALUES (2, 5, 5);
INSERT INTO dermatologist(number_of_votes, rating, user_id) VALUES (5, 3.2, 6);
INSERT INTO dermatologist(number_of_votes, rating, user_id) VALUES (11, 2.2, 7);
INSERT INTO dermatologist(number_of_votes, rating, user_id) VALUES (7, 4.2, 10);

INSERT INTO pharmacist(number_of_votes, deleted, working_from, working_to, rating, pharmacy_id, user_id) VALUES (12, false, 8, 16, 3.5, 1, 8);
INSERT INTO pharmacist(number_of_votes, deleted, working_from, working_to,rating, pharmacy_id, user_id) VALUES (22, false, 8, 16, 2.9, 1, 9);

INSERT INTO pharmacy_dermatologists(pharmacy_id, dermatologist_id) VALUES (1, 1);
INSERT INTO pharmacy_dermatologists(pharmacy_id, dermatologist_id) VALUES (1, 2);
INSERT INTO pharmacy_dermatologists(pharmacy_id, dermatologist_id) VALUES (1, 3);
INSERT INTO pharmacy_dermatologists(pharmacy_id, dermatologist_id) VALUES (2, 4);
INSERT INTO pharmacy_dermatologists(pharmacy_id, dermatologist_id) VALUES (1, 4);

INSERT INTO pharmacy_administrator(pharmacy_id, user_id) VALUES (1, 3);
INSERT INTO pharmacy_administrator(pharmacy_id, user_id) VALUES (2, 11);

INSERT INTO user_authority(user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority(user_id, authority_id) VALUES (2, 6);
INSERT INTO user_authority(user_id, authority_id) VALUES (3, 5);
INSERT INTO user_authority(user_id, authority_id) VALUES (5, 3);
INSERT INTO user_authority(user_id, authority_id) VALUES (5, 4);
INSERT INTO user_authority(user_id, authority_id) VALUES (6, 3);
INSERT INTO user_authority(user_id, authority_id) VALUES (7, 3);
INSERT INTO user_authority(user_id, authority_id) VALUES (8, 2);
INSERT INTO user_authority(user_id, authority_id) VALUES (9, 2);
INSERT INTO user_authority(user_id, authority_id) VALUES (10, 3);
INSERT INTO user_authority(user_id, authority_id) VALUES (11, 5);

INSERT INTO dermatologist_pharmacy_hours(working_from, deleted, working_to, dermatologist_id, pharmacy_id) VALUES (12, false, 16, 4, 2);
INSERT INTO dermatologist_pharmacy_hours(working_from, deleted, working_to, dermatologist_id, pharmacy_id) VALUES (8, false, 13, 1, 1);
INSERT INTO dermatologist_pharmacy_hours(working_from, deleted, working_to, dermatologist_id, pharmacy_id) VALUES (10, false, 14, 2, 1);
INSERT INTO dermatologist_pharmacy_hours(working_from, deleted, working_to, dermatologist_id, pharmacy_id) VALUES (14, false, 15, 3, 1);
INSERT INTO dermatologist_pharmacy_hours(working_from, deleted, working_to, dermatologist_id, pharmacy_id) VALUES (8, false, 11, 4, 1);

INSERT INTO examination(date, duration, status, time, dermatologist_id, patient_id, pharmacy_id, price) VALUES ('2020-05-05', 1, 'FREE', '01:00', 1, null, 1, 155.0);
INSERT INTO examination(date, duration, status, time, dermatologist_id, patient_id, pharmacy_id, price) VALUES ('2020-01-12', 1, 'FREE', '03:00', 1, null, 1, 155.0);
INSERT INTO examination(date, duration, status, time, dermatologist_id, patient_id, pharmacy_id, price) VALUES ('2020-02-25', 1, 'ACTIVE', '02:00', 1, null, 1, 155.0);
INSERT INTO examination(date, duration, status, time, dermatologist_id, patient_id, pharmacy_id, price) VALUES ('2020-06-30', 1, 'FREE', '04:00', 1, null, 1, 155.0);
INSERT INTO examination(date, duration, status, time, dermatologist_id, patient_id, pharmacy_id, price) VALUES ('2021-12-02', 0.5, 'FREE', '08:00', 4, null, 1, 155.0);
INSERT INTO examination(date, duration, status, time, dermatologist_id, patient_id, pharmacy_id, price) VALUES ('2021-02-7', 1, 'ACTIVE', '10:00', 4, 1, 1, 155.0);

INSERT INTO counseling(date, status, start_hour, end_hour, patient_id, pharmacist_id) VALUES ('2021-02-09', 'ACTIVE', 10, 11, 1, 1);
INSERT INTO counseling(date, status, start_hour, end_hour, patient_id, pharmacist_id) VALUES ('2021-02-09', 'ACTIVE', 11, 13, 1, 1);
INSERT INTO counseling(date, status, start_hour, end_hour, patient_id, pharmacist_id) VALUES ('2021-02-09', 'ACTIVE', 13, 15, 1, 1);
INSERT INTO counseling(date, status, start_hour, end_hour, patient_id, pharmacist_id) VALUES ('2021-02-09', 'CANCELLED', 8, 10, 1, 2);
INSERT INTO counseling(date, status, start_hour, end_hour, patient_id, pharmacist_id) VALUES ('2021-02-09', 'CANCELLED', 11, 14, 1, 2);

