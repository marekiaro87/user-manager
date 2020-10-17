CREATE DATABASE IF NOT EXISTS user_manager;
USE user_manager;
CREATE TABLE IF NOT EXISTS USER (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME VARCHAR(256),
    PASSWORD VARCHAR(256),
    ADDRESS VARCHAR(256),
    EMAIL VARCHAR(256));