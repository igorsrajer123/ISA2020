INSERT INTO authority(name) VALUES ('ROLE_PATIENT');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACIST');
INSERT INTO authority(name) VALUES ('ROLE_DERMATOLOGIST');
INSERT INTO authority(name) VALUES ('ROLE_SUPPLIER');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACY_ADMIN');
INSERT INTO authority(name) VALUES ('ROLE_PHARMACY_SYSTEM_ADMIN');

INSERT INTO user_entity(email, password, first_name, last_name, type, activated, enabled) VALUES ('isapsw1234@gmail.com', '$2a$10$M3.YOtEuBCSNbgzJ3hkQv.ZgOMGNSTataYMx1UU7OnqMrlC6Osgzm', 'Misa', 'Dimitrijevic', 'ROLE_PATIENT', true, true);

INSERT INTO patient(address, phone_number, city, country, processed, user_id) VALUES ('adresica','1234567890', 'zrenjanin', 'serbia', true, 1);
INSERT INTO user_authority(user_id, authority_id) VALUES (1, 1);