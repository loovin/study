
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) default NULL,
  `username` varchar(255) default NULL,
  `password` varchar(255) default NULL
);

INSERT INTO `users` VALUES ('1', 'java1234', '123456');
