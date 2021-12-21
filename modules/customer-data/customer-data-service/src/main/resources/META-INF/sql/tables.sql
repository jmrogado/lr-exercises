create table MYAPP_Customer (
	uuid_ VARCHAR(75) null,
	id_ LONG not null primary key,
	companyId LONG,
	groupId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	firstName VARCHAR(75) null,
	lastName VARCHAR(75) null,
	birthDate DATE null,
	emailAddress VARCHAR(75) null
);