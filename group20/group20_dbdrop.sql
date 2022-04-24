-- Drop Triggers
PROMPT DROP Trigger;
DROP TRIGGER Borrow.BORROW_BOOK; 
DROP TRIGGER Borrow.RETURN_BOOK; 

-- Drop Tables
PROMPT DROP TABLES;
DROP TABLE Students CASCADE CONSTRAINT;
DROP TABLE Renew_Book CASCADE CONSTRAINT;
DROP TABLE Reserved CASCADE CONSTRAINT;
DROP TABLE Borrow CASCADE CONSTRAINT;
DROP TABLE Books CASCADE CONSTRAINT;