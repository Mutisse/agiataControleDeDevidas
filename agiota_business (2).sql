-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 06-Maio-2024 às 07:21
-- Versão do servidor: 10.4.32-MariaDB
-- versão do PHP: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `agiota_business`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbacesso`
--

CREATE TABLE `tbacesso` (
  `id_acesso` int(11) NOT NULL,
  `perfil` enum('Administrador','Gestor','Supervisor') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tbacesso`
--

INSERT INTO `tbacesso` (`id_acesso`, `perfil`) VALUES
(1, 'Administrador'),
(2, 'Gestor'),
(3, 'Supervisor');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbcliente`
--

CREATE TABLE `tbcliente` (
  `id` int(11) NOT NULL,
  `nomeProprio` varchar(50) DEFAULT NULL,
  `apelido` varchar(50) DEFAULT NULL,
  `contacto` varchar(20) DEFAULT NULL,
  `id_sexo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tbcliente`
--

INSERT INTO `tbcliente` (`id`, `nomeProprio`, `apelido`, `contacto`, `id_sexo`) VALUES
(1, 'João', 'Silva', '123456789', 2),
(2, 'Maria', 'Santos', '987654321', 1),
(3, 'Carlos', 'Ferreira', '111222333', 2),
(4, 'Ana', 'Ribeiro', '444555666', 1),
(5, 'Pedro', 'Oliveira', '777888999', 2),
(6, 'João', 'Silva', '123456789', 2),
(7, 'Maria', 'Santos', '987654321', 1),
(8, 'Ana', 'Ferreira', '456789123', 1),
(9, 'Carlos', 'Oliveira', '321987654', 2),
(10, 'Juliana', 'Souza', '789123456', 1),
(11, 'Fernanda', 'Lima', '111222333', 2),
(12, 'Pedro', 'Rocha', '444555666', 1),
(13, 'Carolina', 'Alves', '777888999', 2),
(14, 'Lucas', 'Pereira', '123456789', 1),
(15, 'Mariana', 'Costa', '987654321', 2);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbdividas`
--

CREATE TABLE `tbdividas` (
  `id` varchar(10) NOT NULL,
  `valorDivida` decimal(10,2) DEFAULT NULL,
  `valorAPagar` decimal(10,2) DEFAULT NULL,
  `remanescente` decimal(10,2) DEFAULT NULL,
  `data` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `id_cliente` int(11) DEFAULT NULL,
  `theStatus` enum('Liquidada','Parcial','Pendente') NOT NULL DEFAULT 'Pendente',
  `isdeleted` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tbdividas`
--

INSERT INTO `tbdividas` (`id`, `valorDivida`, `valorAPagar`, `remanescente`, `data`, `id_cliente`, `theStatus`, `isdeleted`) VALUES
('D0111', 2500.00, 2000.00, 500.00, '2024-05-05 22:57:33', 5, 'Parcial', 1),
('D0129', 5000.00, 3000.00, 2000.00, '2024-05-05 22:58:35', 4, 'Parcial', 0),
('D0146', 1000.00, 500.00, 500.00, '2024-05-05 23:00:31', 1, 'Liquidada', 0),
('D0204', 500.00, 200.00, 300.00, '2024-05-05 22:59:25', 1, 'Pendente', 0),
('D0215', 800.00, 400.00, 400.00, '2024-05-05 22:59:59', 2, 'Pendente', 0),
('D0265', 1500.00, 1000.00, 500.00, '2024-05-05 22:59:33', 5, 'Pendente', 1),
('D0310', 2500.00, 2000.00, 500.00, '2024-05-05 22:57:24', 10, 'Liquidada', 0),
('D0312', 1000.00, 600.00, 400.00, '2024-05-05 22:59:51', 3, 'Pendente', 0),
('D0313', 1500.00, 500.00, 1000.00, '2024-05-05 22:58:06', 7, 'Parcial', 0),
('D0317', 2000.00, 1500.00, 500.00, '2024-05-05 22:59:14', 2, 'Parcial', 0),
('D0344', 1200.00, 800.00, 400.00, '2024-05-05 22:59:41', 4, 'Parcial', 0),
('D0384', 1500.00, 1000.00, 500.00, '2024-05-05 23:00:41', 3, 'Pendente', 0),
('D0412', 3000.00, 2000.00, 1000.00, '2024-05-05 22:57:44', 6, 'Liquidada', 0),
('D0414', 2000.00, 1000.00, 1000.00, '2024-05-05 22:58:14', 8, 'Pendente', 0),
('D0415', 1000.00, 700.00, 300.00, '2024-05-05 23:00:10', 9, 'Liquidada', 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbgenero`
--

CREATE TABLE `tbgenero` (
  `id_sexo` int(11) NOT NULL,
  `thisSexo` enum('F','M','Indefinido') NOT NULL DEFAULT 'Indefinido'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tbgenero`
--

INSERT INTO `tbgenero` (`id_sexo`, `thisSexo`) VALUES
(1, 'F'),
(2, 'M');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbusuario`
--

CREATE TABLE `tbusuario` (
  `id` varchar(10) NOT NULL,
  `nomeProprio` varchar(50) DEFAULT NULL,
  `apelido` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `senha` varchar(100) DEFAULT NULL,
  `theStatus` enum('Autorizado','Não autorizado','Excluído') DEFAULT 'Autorizado',
  `id_sexo` int(11) DEFAULT NULL,
  `id_acesso` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tbusuario`
--

INSERT INTO `tbusuario` (`id`, `nomeProprio`, `apelido`, `email`, `senha`, `theStatus`, `id_sexo`, `id_acesso`) VALUES
('U098', 'João', 'Silva', 'joao@email.com', 'senha123', 'Autorizado', 2, 2),
('U152', 'Maria', 'Santos', 'maria@email.com', 'senha456', 'Autorizado', 1, 3),
('U345', 'Carlos', 'Ferreira', 'carlos@email.com', 'senha789', 'Autorizado', 2, 1),
('U423', 'Juliana', 'Souza', 'juliana@example.com', 'senha1213', 'Autorizado', 2, 2),
('U556', 'Fernando', 'Silveira', 'fernando@example.com', 'senha1415', 'Excluído', 2, 3),
('U665', 'Ana', 'Ferreira', 'ana@example.com', 'senha789', 'Autorizado', 1, 3),
('U734', 'Carlos', 'Oliveira', 'carlos@example.com', 'senha1011', 'Autorizado', 2, 1),
('U834', 'Camila', 'Martins', 'camila@example.com', 'senha1617', 'Autorizado', 1, 2),
('U965', 'Rafael', 'Almeida', 'rafael@example.com', 'senha1819', 'Não autorizado', 2, 2);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `tbacesso`
--
ALTER TABLE `tbacesso`
  ADD PRIMARY KEY (`id_acesso`);

--
-- Índices para tabela `tbcliente`
--
ALTER TABLE `tbcliente`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_tbcliente_thisSexo` (`id_sexo`);

--
-- Índices para tabela `tbdividas`
--
ALTER TABLE `tbdividas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_cliente` (`id_cliente`);

--
-- Índices para tabela `tbgenero`
--
ALTER TABLE `tbgenero`
  ADD PRIMARY KEY (`id_sexo`);

--
-- Índices para tabela `tbusuario`
--
ALTER TABLE `tbusuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_id_sexo` (`id_sexo`),
  ADD KEY `fk_id_acesso` (`id_acesso`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `tbacesso`
--
ALTER TABLE `tbacesso`
  MODIFY `id_acesso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `tbcliente`
--
ALTER TABLE `tbcliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `tbgenero`
--
ALTER TABLE `tbgenero`
  MODIFY `id_sexo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `tbcliente`
--
ALTER TABLE `tbcliente`
  ADD CONSTRAINT `fk_tbcliente_thisSexo` FOREIGN KEY (`id_sexo`) REFERENCES `tbgenero` (`id_sexo`);

--
-- Limitadores para a tabela `tbdividas`
--
ALTER TABLE `tbdividas`
  ADD CONSTRAINT `tbdividas_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `tbcliente` (`id`);

--
-- Limitadores para a tabela `tbusuario`
--
ALTER TABLE `tbusuario`
  ADD CONSTRAINT `fk_id_acesso` FOREIGN KEY (`id_acesso`) REFERENCES `tbacesso` (`id_acesso`),
  ADD CONSTRAINT `fk_id_sexo` FOREIGN KEY (`id_sexo`) REFERENCES `tbgenero` (`id_sexo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
