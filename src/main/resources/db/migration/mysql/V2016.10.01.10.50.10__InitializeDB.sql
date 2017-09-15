


CREATE TABLE FICT.Legal_Entity_Type (
  LEGAL_ENTITY_TYPE_ID   TINYINT UNSIGNED NOT NULL,
  LEGAL_ENTITY_TYPE_TEXT  VARCHAR(30)      NOT NULL,
  PRIMARY KEY (`LEGAL_ENTITY_TYPE_ID`)
);


CREATE TABLE FICT.Legal_Entity (
  LEGAL_ENTITY_ID  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  LEGAL_ENTITY_TYPE_ID   TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`LEGAL_ENTITY_ID`)
)
  AUTO_INCREMENT = 1
;


ALTER TABLE FICT.Legal_Entity  ADD FOREIGN KEY Fk_On_Legal_Entity_From_Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID)
REFERENCES FICT.Legal_Entity_Type(LEGAL_ENTITY_TYPE_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


CREATE TABLE FICT.Name_Type (
NAME_TYPE_ID    TINYINT UNSIGNED NOT NULL,
NAME_TYPE_TEXT  VARCHAR(30)      NOT NULL,
PRIMARY KEY (`NAME_TYPE_ID`)
);


CREATE TABLE FICT.Entity_Name (
  ENTITY_NAME_ID  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  NAME_TYPE_ID TINYINT UNSIGNED NOT NULL,
  LEGAL_ENTITY_ID INT UNSIGNED  NOT NULL,

  NAME  VARCHAR(30)      NOT NULL,
  PRIMARY KEY (`ENTITY_NAME_ID`)
)
  AUTO_INCREMENT = 1
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
  PRIMARY KEY (`ADDRESS_TYPE_ID`)
)
;



CREATE TABLE FICT.Entity_Address (
  ENTITY_ADDRESS_ID     INT UNSIGNED NOT NULL AUTO_INCREMENT,
  LEGAL_ENTITY_ID INT UNSIGNED  NOT NULL,
  ADDRESS_TYPE_ID TINYINT UNSIGNED NOT NULL,
  ADDRESS_ID  INT UNSIGNED  NOT NULL,

  PRIMARY KEY (`ENTITY_ADDRESS_ID`)
)
  AUTO_INCREMENT = 1
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





CREATE TABLE FICT.Address (
  ADDRESS_ID  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  STREET      VARCHAR(40)      NOT NULL,
  CITY        VARCHAR(30)      NOT NULL,
  STATE       VARCHAR(2)       NOT NULL,
  ZIP_CODE    VARCHAR(5)       NOT NULL,
  PRIMARY KEY (`ADDRESS_ID`)
)
  AUTO_INCREMENT = 1
;

ALTER TABLE FICT.Entity_Address  ADD FOREIGN KEY Fk_On_Entity_Address_From_Address(ADDRESS_ID)
REFERENCES FICT.Address(ADDRESS_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION ;



INSERT INTO FICT.Address_Type (ADDRESS_TYPE_ID, ADDRESS_TYPE_TEXT) VALUES (1, 'Residence');
INSERT INTO FICT.Address_Type (ADDRESS_TYPE_ID, ADDRESS_TYPE_TEXT) VALUES (2, 'Work');
INSERT INTO FICT.Address_Type (ADDRESS_TYPE_ID, ADDRESS_TYPE_TEXT) VALUES (3, 'Mailing');
INSERT INTO FICT.Address_Type (ADDRESS_TYPE_ID, ADDRESS_TYPE_TEXT) VALUES (4, 'Branch');
INSERT INTO FICT.Address_Type (ADDRESS_TYPE_ID, ADDRESS_TYPE_TEXT) VALUES (5, 'Headquarters');
INSERT INTO FICT.Address_Type (ADDRESS_TYPE_ID, ADDRESS_TYPE_TEXT) VALUES (6, 'Warehouse');
INSERT INTO FICT.Address_Type (ADDRESS_TYPE_ID, ADDRESS_TYPE_TEXT) VALUES (7, 'Divisional Headquarters');




INSERT INTO FICT.Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID, LEGAL_ENTITY_TYPE_TEXT) VALUES (1, 'Individual');
INSERT INTO FICT.Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID, LEGAL_ENTITY_TYPE_TEXT) VALUES (2, 'Family Trust');
INSERT INTO FICT.Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID, LEGAL_ENTITY_TYPE_TEXT) VALUES (3, 'Living Trust');
INSERT INTO FICT.Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID, LEGAL_ENTITY_TYPE_TEXT) VALUES (4, 'Corporation');
INSERT INTO FICT.Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID, LEGAL_ENTITY_TYPE_TEXT) VALUES (5, 'Nonprofit Organization');
INSERT INTO FICT.Legal_Entity_Type (LEGAL_ENTITY_TYPE_ID, LEGAL_ENTITY_TYPE_TEXT) VALUES (6, 'Government');


INSERT INTO FICT.Name_Type (NAME_TYPE_ID, NAME_TYPE_TEXT) VALUES (1, 'First Name');
INSERT INTO FICT.Name_Type (NAME_TYPE_ID, NAME_TYPE_TEXT) VALUES (2, 'Last Name');
INSERT INTO FICT.Name_Type (NAME_TYPE_ID, NAME_TYPE_TEXT) VALUES (3, 'Middle Name');
INSERT INTO FICT.Name_Type (NAME_TYPE_ID, NAME_TYPE_TEXT) VALUES (4, 'Title');
INSERT INTO FICT.Name_Type (NAME_TYPE_ID, NAME_TYPE_TEXT) VALUES (5, 'Organization Name');








INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (1, 1);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (2, 1);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (3, 2);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (4, 3);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (5, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (6, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (7, 5);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (8, 6);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (9, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (10, 4);

INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (11, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (12, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (13, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (14, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (15, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (16, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (17, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (18, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (19, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (20, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (21, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (22, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (23, 4);
INSERT INTO FICT.Legal_Entity (LEGAL_ENTITY_ID, LEGAL_ENTITY_TYPE_ID) VALUES (24, 4);




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
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 8,  "IRS");

INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 9,  "Marcus");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 10,  "Trust Bank");

INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 11,  "Acacia Comm.");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 12,  "Acacti Pharma");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 13,  "Accelerate Diagnostics");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 14,  "Exa Corporation");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 15,  "Eye Gate Pharma");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 16,  "Fibrogen Inc");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 17,  "Fifth Street Finance");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 18,  "Heat Biologics");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 19,  "Heritage Commerce");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 20,  "Highway Holdings");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 21,  "Iridium Communications");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 22,  "Moneygram International");
INSERT INTO FICT.Entity_Name (NAME_TYPE_ID, LEGAL_ENTITY_ID, NAME) VALUES (5, 23,  "Twin Disc Inc");










