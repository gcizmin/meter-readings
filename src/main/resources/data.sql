INSERT INTO ADDRESS (street_name, street_number, city)
    VALUES ('Magazinski trg', '342c', 'Zagreb');
INSERT INTO ADDRESS (street_name, street_number, city)
    VALUES ('Nehajeva', 'bb', 'Zadar');
INSERT INTO ADDRESS (street_name, street_number, city)
    VALUES ('Trpimirova', '2', 'Split');
INSERT INTO ADDRESS (street_name, street_number, city)
    VALUES ('Ruralna', '72c', 'Veliko Kolo');

INSERT INTO METER (id, serial_number)
    VALUES (1, '427xxB');
INSERT INTO METER (id, serial_number)
    VALUES (2, '327p61');
INSERT INTO METER (id, serial_number)
    VALUES (3, '333');
INSERT INTO METER (id, serial_number)
    VALUES (4, '274');

INSERT INTO CLIENT (id, name, address_id, meter_id)
    VALUES (1, 'Sekula d.o.o.', 1, 3);
INSERT INTO CLIENT (id, name, address_id, meter_id)
    VALUES (2, 'Ivana Bucalo', 2, 2);
INSERT INTO CLIENT (id, name, address_id, meter_id)
    VALUES (3, 'Josip Maric', 3, 1);
INSERT INTO CLIENT (id, name, address_id, meter_id)
    VALUES (4, 'Intertrade', 4, 4);

INSERT INTO METER_READING (id, meter_id, month, year, reading)
    VALUES (1, 1, 11, 2019, 111);
INSERT INTO METER_READING (id, meter_id, month, year, reading)
    VALUES (2, 1, 12, 2019, 222);
INSERT INTO METER_READING (id, meter_id, month, year, reading)
    VALUES (3, 1, 1, 2020, 444);
INSERT INTO METER_READING (id, meter_id, month, year, reading)
    VALUES (4, 2, 11, 2019, 111);
INSERT INTO METER_READING (id, meter_id, month, year, reading)
    VALUES (5, 2, 12, 2019, 222);
INSERT INTO METER_READING (id, meter_id, month, year, reading)
    VALUES (6, 3, 12, 2019, 111);
INSERT INTO METER_READING (id, meter_id, month, year, reading)
    VALUES (7, 3, 1, 2020, 222);
INSERT INTO METER_READING (id, meter_id, month, year, reading)
    VALUES (8, 3, 2, 2020, 444);
INSERT INTO METER_READING (id, meter_id, month, year, reading)
    VALUES (9, 4, 12, 2019, 111);