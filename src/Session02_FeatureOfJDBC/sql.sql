create database session02
create table batch(batchID varchar(10) not null, batchName varchar(20) not null, primary key(batchID))

insert into batch values 
('batch130','Batch 130'),
('batch131','Batch 131'),
('batch132','Batch 132'),
('batch133','Batch 133'),
('batch134','Batch 134'),
('batch135','Batch 135');

create table student1 (studentID nvarchar(10) not null, batchID varchar(10) not null, name nvarchar(50)not null ,primary key(studentID),FOREIGN KEY (batchID) REFERENCES batch(batchID))

insert into student1 values 
('softech001','batch130','Nguyen Anh Tan'),
('softech002','batch130','Nguyen Hong Hai'),
('softech003','batch130','Phan Phuong Nam'),
('softech004','batch131','Nguyen Huu Hiep'),
('softech005','batch131','Vo Ngoc Hai'),
('softech006','batch131','Tran Anh Tuan');

create table testImage(fname varchar(100),img image)

-- Create the test table with a BLOB column
CREATE TABLE Image (ID INTEGER PRIMARY KEY IDENTITY, Subject VARCHAR(256) NOT NULL, Body VARBINARY(MAX));

-- Create the test table with a CLOB column
CREATE TABLE Article (ID INTEGER PRIMARY KEY IDENTITY, Subject VARCHAR(256) NOT NULL, Body VARCHAR(MAX));

