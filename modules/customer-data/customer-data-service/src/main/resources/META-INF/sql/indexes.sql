create unique index IX_58CECE1A on MYAPP_Customer (emailAddress[$COLUMN_LENGTH:75$]);
create index IX_597FEC62 on MYAPP_Customer (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_17C2364 on MYAPP_Customer (uuid_[$COLUMN_LENGTH:75$], groupId);