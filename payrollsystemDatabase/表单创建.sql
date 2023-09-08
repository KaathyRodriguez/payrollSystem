/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2023/5/9 21:34:40                            */
/*==============================================================*/


 /*==============================================================*/
/* Table:用户                                                    */
/*==============================================================*/
create table user
(
   id char(8) NOT NULL ,
   username varchar(50) not NULL,
   password varchar(50) not NULL,
   nickname varchar(50) NULL DEFAULT NULL,
   email varchar(50)  NULL DEFAULT NULL ,
   phone varchar(20)  NULL DEFAULT NULL,
   address varchar(255) NULL DEFAULT NULL ,
   createTime timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
	 role  enum('会计员','管理员')not NULL DEFAULT('会计员'),
  PRIMARY KEY (id)
);

drop table user;


/*==============================================================*/
/* Table:教职工表                                                */
/*==============================================================*/

create table staff
(
	staffNo	char(8) not null,
	staffName varchar(30) not null,
	staffPost varchar(30) not null,
	staffTitle varchar(30) not null,
	primary key (staffNo)
);

drop table staff;

/*==============================================================*/
/* Table:人事数据表                                                */
/*==============================================================*/

create table personnelData
(
  personnelDataNo char(8) not null,
	staffNo char(8) not null,
	baseSalary float(8,2),
	livingAllowances float(8,2),
	bookFee float(8,2),
	commutingFee float(8,2),
	cleanFee float(8,2),
	workHours	float(8,2),
	hourlyEarnings  float(8,2),
	postAllowance float(8,2),
	individualIncomeTax float(8,2),
	housingFund float(8,2),
	premium float(8,2),
  primary key (personnelDataNo)
);

drop table personnelData;


/*==============================================================*/
/* Table:工资表
 */
/*==============================================================*/

create table Salary
(
   salaryNo             char(8) not null,
   staffNo              char(8),
	 staffName 					  varchar(30) not null,
	 staffPost 						varchar(30) not null,
	 staffTitle 					varchar(30) not null,
   baseSalary           float(8,2) ,
   income    					  float(8,2) ,
   expend         			float(8,2) ,
   netPayroll           float(8,2) ,
   primary key (salaryNo)
);

drop table salary;
/*==============================================================*/
/* Table:财务报表                                                */
/*==============================================================*/

create table FinancialStatement
(
   statementNo          char(8) not null,
   statementDate        date not null,
   monthIncome          float(8,2) not null,
   monthExpend          float(8,2) not null,
   monthProfit          float(8,2) not null,
   auditor              varchar(50) not null,
   note                 text,
   primary key (statementNo)
);
drop table FinancialStatement;

/*==============================================================*/
/* Table:年度数据表                                                */
/*==============================================================*/


