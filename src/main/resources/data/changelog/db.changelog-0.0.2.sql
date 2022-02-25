--liquibase formatted sql

--changeset tipikae:8
/*update passwords*/
UPDATE users SET password = '$2a$12$OM9rcaCOaq4FUG6O2ED/aOge6Zf3QEswMov1d3f0oJ.DkeRjcv47.' WHERE username = 'admin';
UPDATE users SET password = '$2a$12$iqLAgr5/GpTN0cDlfw.lm.VQxXcBllduQ.HO18egFy21BjFF1oJ9S' WHERE username = 'user';