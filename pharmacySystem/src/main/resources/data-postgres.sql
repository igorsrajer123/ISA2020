INSERT INTO authority(name) VALUES ('ROLE_PATIENT');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACIST');
INSERT INTO authority(name) VALUES ('ROLE_DERMATOLOGIST');
INSERT INTO authority(name) VALUES ('ROLE_SUPPLIER');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACY_ADMIN');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACY_SYSTEM_ADMIN');

--user password: 123
-- system admin password: admin
-- pharmacy admin password: phadmin
--dermatologist password: derma
--supplier password: supp
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('isapsw1234@gmail.com', '$2a$10$M3.YOtEuBCSNbgzJ3hkQv.ZgOMGNSTataYMx1UU7OnqMrlC6Osgzm', 'Misa', 'Dimitrijevic', 'ROLE_PATIENT', true, true, false);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('admin@gmail.com', '$2a$10$cQSdC5wh2L6oZ2QFGNyD.OhnZ7akIGf7FEx0lmQ4RK6RiHyqz7cOC', 'Nikola', 'Stankovic', 'ROLE_PHARMACY_SYSTEM_ADMIN', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('phadmin@gmail.com', '$2a$10$n8yxFV7zy7PDResCzemMLOhcPcbwLuF7Npn5lHhxVg6pKkFOi8PE2', 'Sima', 'Savic', 'ROLE_PHARMACY_ADMIN', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('derma@gmail.com', '$2a$10$oelbk6QvYYQ507ltE2UHeO/q6a1Lc5KYUT0niU1GTbQezsIaxnCye', 'Sasa', 'Sakic', 'ROLE_DERMATOLOGIST', true, true, true);
INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled, first_login) VALUES ('supp@gmail.com', '$2a$10$2io/7xyfMDpHe4tkBse/VeuSYma/m4rt7lVb.MdslpFvJ/AGUGIvq', 'Sinisa', 'Bekic', 'ROLE_SUPPLIER', true, true, true);

INSERT INTO patient(address, phone_number, city, country, processed, user_id) VALUES ('adresica','1234567890', 'zrenjanin', 'serbia', true, 1);

INSERT INTO dermatologist(user_id) VALUES (4);

INSERT INTO pharmacy_system_administrator(user_id) VALUES (2);

INSERT INTO supplier(user_id) VALUES (5);

INSERT INTO medication(name, price) VALUES ('Rivotril', 1500);
INSERT INTO medication(name, price) VALUES ('Acetaminophen', 850);
INSERT INTO medication(name, price) VALUES ('Adderall', 999);
INSERT INTO medication(name, price) VALUES ('Entyvio', 2000);
INSERT INTO medication(name, price) VALUES ('Gabapentin', 566);
INSERT INTO medication(name, price) VALUES ('Gilenya', 555);
INSERT INTO medication(name, price) VALUES ('Humira', 1000);
INSERT INTO medication(name, price) VALUES ('Hydrochlorothiazide', 400);
INSERT INTO medication(name, price) VALUES ('Lexapro', 1200);
INSERT INTO medication(name, price) VALUES ('Melatonin', 890);
INSERT INTO medication(name, price) VALUES ('Meloxicam', 390);
INSERT INTO medication(name, price) VALUES ('Rybelsus', 525);
INSERT INTO medication(name, price) VALUES ('Tramadol', 1100);
INSERT INTO medication(name, price) VALUES ('Wellbutrin', 350);
INSERT INTO medication(name, price) VALUES ('Xanax', 3000);
INSERT INTO medication(name, price) VALUES ('Omeprazole', 220);

INSERT INTO pharmacy(pharmacy_name, address, city, rating, number_of_votes) VALUES ('Apoteka Jankovic', 'Mileticeva 57', 'Zrenjanin', 2.5, 55);
INSERT INTO pharmacy(pharmacy_name, address, city, rating, number_of_votes) VALUES ('Apoteka Sveti Jovan', 'Apatinska 21', 'Novi Sad', 3.2, 21);
INSERT INTO pharmacy(pharmacy_name, address, city, rating, number_of_votes) VALUES ('Apoteka Jankovic', 'Rumunski Drum 11', 'Beograd', 3.6, 11);
INSERT INTO pharmacy(pharmacy_name, address, city, rating, number_of_votes) VALUES ('Apoteka Sveti Petar', 'Mise Dimitrijevica 26', 'Novi Sad', 4.2, 101);
INSERT INTO pharmacy(pharmacy_name, address, city, rating, number_of_votes) VALUES ('Apoteka Crveni Krst', 'Vojvode Petra Bojovica 1B', 'Beograd', 3.9, 2);

INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (1, 2);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (1, 3);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (1, 4);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (1, 1);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (1, 5);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (2, 5);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (2, 7);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (2, 8);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (2, 9);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (2, 10);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (2, 3);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (2, 4);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (3, 1);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (3, 2);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (3, 8);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (3, 9);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (3, 7);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (3, 6);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (4, 8);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (4, 9);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (4, 10);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (4, 15);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (4, 14);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (5, 15);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (5, 16);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (5, 6);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (5, 7);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (5, 5);
INSERT INTO pharmacy_medications(pharmacy_id, medication_id) VALUES (5, 9);

INSERT INTO pharmacy_administrator(pharmacy_id, user_id) VALUES (1, 3);

INSERT INTO user_authority(user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority(user_id, authority_id) VALUES (2, 6);
INSERT INTO user_authority(user_id, authority_id) VALUES (3, 5);
INSERT INTO user_authority(user_id, authority_id) VALUES (4, 3);
INSERT INTO user_authority(user_id, authority_id) VALUES (5, 4);
