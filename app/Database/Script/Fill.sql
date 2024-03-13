INSERT INTO USERS ("NICKNAME", "PASSWORD", "EMAIL", "TOKEN") SELECT "NICKNAME", "PASSWORD", "EMAIL", "TOKEN" FROM CSVREAD('Database\Script\users.csv');
INSERT INTO IMAGES("NAME", "DESCRIPTION", "IMAGE", "USERID", "PUBLIC") SELECT "NAME", "DESCRIPTION", "IMAGE", "USERID", "PUBLIC" FROM CSVREAD('Database\Script\images.csv');
INSERT INTO PLACES ("LATITUDE", "LONGITUDE", "NAME", "DESCRIPTION", "IMAGEID", "USERID") SELECT "LAT", "LNG", "NAME", "DESCRIPTION", "IMAGE", "USER" FROM CSVREAD('Database\Script\places.csv');
INSERT INTO TAGS ("TITLE", "DESCRIPTION", "USERID") SELECT "TITLE", "DESCRIPTION", "USERID" FROM CSVREAD('Database\Script\tags.csv');
INSERT INTO PLACESTAGS ("PLACEID", "TAGID") SELECT "PLACE", "TAG" FROM CSVREAD('Database\Script\places_tags.csv');
INSERT INTO TOKENS ("TOKEN", "ACCESSRIGHT", "USERID", "TAGID", "CREATORID") SELECT "TOKEN", "RIGHT", "USER", "TAG", "CREATORID" FROM CSVREAD('Database\Script\tokens.csv');