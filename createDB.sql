CREATE DATABASE bombermandb;
USE bombermandb;
CREATE TABLE highscore (nickname VARCHAR(50) PRIMARY KEY NOT NULL, n_victory INTEGER);
CREATE USER 'bomberuser'@'localhost' IDENTIFIED BY 'bombermdp';
CREATE USER 'bomberuser'@'%' IDENTIFIED BY 'bombermdp';
GRANT ALL ON bombermandb.* TO 'bomberuser'@'localhost';
GRANT ALL ON bombermandb.* TO 'bomberuser'@'%';
FLUSH PRIVILEGES;
