INSERT INTO authority(name) VALUES ('ROLE_PATIENT');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACIST');
INSERT INTO authority(name) VALUES ('ROLE_DERMATOLOGIST');
INSERT INTO authority(name) VALUES ('ROLE_SUPPLIER');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACY_ADMIN');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACY_SYSTEM_ADMIN');

INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled) VALUES ('isapsw1234@gmail.com', '$2a$10$M3.YOtEuBCSNbgzJ3hkQv.ZgOMGNSTataYMx1UU7OnqMrlC6Osgzm', 'Misa', 'Dimitrijevic', 'ROLE_PATIENT', true, true);

INSERT INTO patient(address, phone_number, city, country, processed, user_id) VALUES ('adresica','1234567890', 'zrenjanin', 'serbia', true, 1);

INSERT INTO user_authority(user_id, authority_id) VALUES (1, 1);

INSERT INTO medication(name, price) VALUES ('Rivotril', 1500);
INSERT INTO medication(name, price) VALUES ('Acetaminophen', 850);
INSERT INTO medication(name, price) VALUES ('Adderall', 999);
INSERT INTO medication(name, price) VALUES ('Entyvio', 2000);
INSERT INTO medication(name, price) VALUES ('Fentanyl Patch', 770);
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
