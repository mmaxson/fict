drop database FICT;


create database FICT;


CREATE TABLE FICT.Legal_Entity_Type (
  LEGAL_ENTITY_TYPE_ID   TINYINT UNSIGNED NOT NULL,
  LEGAL_ENTITY_TYPE_TEXT  VARCHAR(30)      NOT NULL,
  PRIMARY KEY (`LEGAL_ENTITY_TYPE_ID`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
;


CREATE TABLE FICT.Legal_Entity (
  LEGAL_ENTITY_ID  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  LEGAL_ENTITY_TYPE_ID   TINYINT UNSIGNED NOT NULL,


  PRIMARY KEY (`LEGAL_ENTITY_ID`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;
;


ALTER TABLE FICT.Legal_Entity  ADD FOREIGN KEY Fk_On_Legal_Entity_From_Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID)
REFERENCES FICT.Legal_Entity_Type(LEGAL_ENTITY_TYPE_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


CREATE TABLE FICT.Name_Type (
NAME_TYPE_ID    TINYINT UNSIGNED NOT NULL,
NAME_TYPE_TEXT  VARCHAR(30)      NOT NULL,
PRIMARY KEY (`NAME_TYPE_ID`) USING BTREE
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8;
;


CREATE TABLE FICT.Entity_Name (
  ENTITY_NAME_ID  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  NAME_TYPE_ID TINYINT UNSIGNED NOT NULL,
  LEGAL_ENTITY_ID INT UNSIGNED  NOT NULL,

  NAME  VARCHAR(30)      NOT NULL,
  PRIMARY KEY (`ENTITY_NAME_ID`) USING BTREE
)
  AUTO_INCREMENT = 1
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
;

ALTER TABLE FICT.Entity_Name ADD INDEX Index_On_Entity_Name_Legal_Entity_Id (LEGAL_ENTITY_ID);

ALTER TABLE FICT.Entity_Name  ADD FOREIGN KEY Fk_On_Entity_Name_From_Legal_Entity(LEGAL_ENTITY_ID)
REFERENCES FICT.Legal_Entity(LEGAL_ENTITY_ID)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE FICT.Entity_Name  ADD FOREIGN KEY Fk_On_Entity_Name_From_Name_Type(NAME_TYPE_ID)
REFERENCES FICT.Name_Type(NAME_TYPE_ID)
  ON DELETE CASCADE
  ON UPDATE CASCADE;



CREATE TABLE FICT.Address_Type (
  ADDRESS_TYPE_ID    TINYINT UNSIGNED NOT NULL,
  ADDRESS_TYPE_TEXT  VARCHAR(30)      NOT NULL,
  PRIMARY KEY (`ADDRESS_TYPE_ID`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
;



CREATE TABLE FICT.Entity_Address (
  ENTITY_ADDRESS_ID     INT UNSIGNED NOT NULL AUTO_INCREMENT,
  LEGAL_ENTITY_ID INT UNSIGNED  NOT NULL,
  ADDRESS_TYPE_ID TINYINT UNSIGNED NOT NULL,
  ADDRESS_ID  INT UNSIGNED  NOT NULL,

  PRIMARY KEY (`ENTITY_ADDRESS_ID`) USING BTREE
)
  AUTO_INCREMENT = 1
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
;

/*ALTER TABLE FICT.Entity_Address ADD INDEX Index_On_Entity_Address_Legal_Entity_Id (LEGAL_ENTITY_ID);
ALTER TABLE FICT.Entity_Address ADD INDEX Index_On_Entity_Address_Address_Type_Id (ADDRESS_TYPE_ID);
ALTER TABLE FICT.Entity_Address ADD INDEX Index_On_Entity_Address_Address_Id (ADDRESS_ID);
*/


ALTER TABLE FICT.Entity_Address  ADD FOREIGN KEY Fk_On_Entity_Address_From_Legal_Entity(LEGAL_ENTITY_ID)
REFERENCES FICT.Legal_Entity(LEGAL_ENTITY_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION ;


ALTER TABLE FICT.Entity_Address  ADD FOREIGN KEY Fk_On_Entity_Address_From_Address_Type(ADDRESS_TYPE_ID)
REFERENCES FICT.Address_Type(ADDRESS_TYPE_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION ;


ALTER TABLE FICT.Entity_Address  ADD FOREIGN KEY Fk_On_Entity_Address_From_Address(ADDRESS_ID)
REFERENCES FICT.Address(ADDRESS_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION ;


CREATE TABLE FICT.Address (
  ADDRESS_ID  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  STREET      VARCHAR(30)      NOT NULL,
  CITY        VARCHAR(20)      NOT NULL,
  STATE       VARCHAR(2)       NOT NULL,
  ZIP_CODE    VARCHAR(5)       NOT NULL,
  PRIMARY KEY (`ADDRESS_ID`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;
;

INSERT INTO FICT.Address_Type (ADDRESS_TYPE_ID, TEXT) VALUES (1, 'Residence');
INSERT INTO FICT.Address_Type (ADDRESS_TYPE_ID, TEXT) VALUES (2, 'Work');
INSERT INTO FICT.Address_Type (ADDRESS_TYPE_ID, TEXT) VALUES (3, 'Mailing');




INSERT INTO FICT.Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID, TEXT) VALUES (1, 'Individual');
INSERT INTO FICT.Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID, TEXT) VALUES (2, 'Family Trust');
INSERT INTO FICT.Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID, TEXT) VALUES (3, 'Living Trust');
INSERT INTO FICT.Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID, TEXT) VALUES (4, 'Corporation');
INSERT INTO FICT.Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID, TEXT) VALUES (5, 'Nonprofit Organization');

INSERT INTO FICT.Name_Type (NAME_TYPE_ID, TEXT) VALUES (1, 'First');
INSERT INTO FICT.Name_Type (NAME_TYPE_ID, TEXT) VALUES (2, 'Last');
INSERT INTO FICT.Name_Type (NAME_TYPE_ID, TEXT) VALUES (3, 'Middle');
INSERT INTO FICT.Name_Type (NAME_TYPE_ID, TEXT) VALUES (4, 'Title');
INSERT INTO FICT.Name_Type (NAME_TYPE_ID, TEXT) VALUES (5, 'Organization Name');



INSERT INTO FICT.Address (ADDRESS_ID, STREET, CITY, STATE, ZIP_CODE) VALUES (1, '1934 11th Street', 'Santa Monica', 'CA', '90401');
INSERT INTO FICT.Address (STREET, CITY, STATE, ZIP_CODE) VALUES ('650 S.Hill Street', 'Los Angeles', 'CA', '90014');
INSERT INTO FICT.Address (STREET, CITY, STATE, ZIP_CODE) VALUES ('2001 Santa Monica Blvd', 'Santa Monica', 'CA', '90402');
INSERT INTO FICT.Address (STREET, CITY, STATE, ZIP_CODE) VALUES ('17030 Nordoff St', 'Northridge', 'CA', '91325');
INSERT INTO FICT.Address (STREET, CITY, STATE, ZIP_CODE) VALUES ('11963 Walnut Ln.', 'Santa Monica', 'CA', '90025');
INSERT INTO FICT.Address (STREET, CITY, STATE, ZIP_CODE) VALUES ('22000 Dumetz Road', 'Woodland Hills', 'CA', '91364');





INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (1, 1);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_TYPE_ID) VALUES (1);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_TYPE_ID) VALUES (2);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_TYPE_ID) VALUES (3);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_TYPE_ID) VALUES (4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_TYPE_ID) VALUES (4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_TYPE_ID) VALUES (5);




INSERT INTO FICT.Entity_Name (ENTITY_NAME_ID, NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (1, 1, 1, "Mark");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (2, 1,  "Hoffman");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (3, 1,  "K");

INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (1, 2,  "Martin");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (2, 2,  "Mcgee");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (4, 2,  "Jr.");

INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 3,  "Lawrence Family Trust");

INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 4,  "Alex Cunnings Living Trust");

INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 5,  "IBM");

INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 6,  "Amazon");

INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 7,  "Salvation Army");







INSERT INTO FICT.Entity_Address (ENTITY_ADDRESS_ID, LEGAL_ENTITY_ID, ADDRESS_TYPE_ID, ADDRESS_ID) VALUES(1, 1, 1, 1);
INSERT INTO FICT.Entity_Address (LEGAL_ENTITY_ID, ADDRESS_TYPE_ID, ADDRESS_ID) VALUES(1, 3, 2);
INSERT INTO FICT.Entity_Address (LEGAL_ENTITY_ID, ADDRESS_TYPE_ID, ADDRESS_ID) VALUES(1, 2, 3);

INSERT INTO FICT.Entity_Address (LEGAL_ENTITY_ID, ADDRESS_TYPE_ID, ADDRESS_ID) VALUES(5, 2, 4);
INSERT INTO FICT.Entity_Address (LEGAL_ENTITY_ID, ADDRESS_TYPE_ID, ADDRESS_ID) VALUES(5, 2, 5);
INSERT INTO FICT.Entity_Address (LEGAL_ENTITY_ID, ADDRESS_TYPE_ID, ADDRESS_ID) VALUES(5, 2, 6);




COMMIT;

SELECT * FROM FICT.Address

