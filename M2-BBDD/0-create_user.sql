alter session set container = FREEPDB1;
create User planetWars identified by planetWars;
grant connect to planetWars;
grant create session to planetWars;
grant unlimited tablespace to planetWars;
grant dba to planetWars;
grant create view to planetWars;
commit;