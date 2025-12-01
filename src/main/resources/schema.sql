-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS ovpedb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 사용자 생성
CREATE USER 'ovpe'@'%' IDENTIFIED BY 'ovpe123';
GRANT ALL PRIVILEGES ON ovpedb.* TO 'ovpe'@'%';
FLUSH PRIVILEGES;

USE ovpedb;

-- 테이블 생성
CREATE TABLE `todos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text,
  `completed` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
);