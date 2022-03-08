CREATE TABLE `customer` (
  `id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `dob` date NOT NULL,
  `address` varchar(45) NOT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `kycType` varchar(15) NOT NULL,
  `kycNo` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `customer_id` (
  `next_val` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`next_val`)
) ENGINE=InnoDB AUTO_INCREMENT=1002022030 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
