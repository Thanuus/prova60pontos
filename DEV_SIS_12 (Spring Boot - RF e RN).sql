-- MySQL 8 | Execute no banco usado pela aplicação Spring Boot.
-- ATENÇÃO: apaga TODOS os dados atuais de emprestimo, livro e aluno.
-- Pare a aplicação antes de executar. Rode o script inteiro, uma única vez.
-- Não execute no console do H2: a sintaxe abaixo é para MySQL.

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE emprestimo;
TRUNCATE TABLE livro;
TRUNCATE TABLE aluno;
SET FOREIGN_KEY_CHECKS = 1;

select * from emprestimo;

-- IDs explícitos: toda a turma usará os mesmos números no Postman.
INSERT INTO aluno (id, nome, email) VALUES
(1, 'Ana Costa', 'ana.costa@exemplo.com'),
(2, 'Bruno Lima', 'bruno.lima@exemplo.com'),
(3, 'Carla Souza', 'carla.souza@exemplo.com'),
(4, 'Diego Alves', 'diego.alves@exemplo.com'),
(5, 'Elisa Rocha', 'elisa.rocha@exemplo.com'),
(6, 'Felipe Martins', 'felipe.martins@exemplo.com'),
(7, 'Gabriela Nunes', 'gabriela.nunes@exemplo.com'),
(8, 'Henrique Dias', 'henrique.dias@exemplo.com'),
(9, 'Isabela Ferreira', 'isabela.ferreira@exemplo.com'),
(10, 'Joao Pereira', 'joao.pereira@exemplo.com');

INSERT INTO livro (id, titulo, autor, ano_publicacao) VALUES
(1, 'Algoritmos para Iniciantes', 'Marina Ribeiro', 2021),
(2, 'Redes de Computadores na Pratica', 'Paulo Mendes', 2020),
(3, 'Banco de Dados Essencial', 'Lucia Andrade', 2022),
(4, 'Fundamentos de Java', 'Rafael Castro', 2023),
(5, 'Introducao a Web', 'Beatriz Melo', 2019),
(6, 'Seguranca da Informacao', 'Daniel Moraes', 2024),
(7, 'Arquitetura de Computadores', 'Helena Araujo', 2020),
(8, 'Sistemas Operacionais', 'Andre Vieira', 2018),
(9, 'Logica de Programacao', 'Camila Freitas', 2022),
(10, 'Eletronica Basica', 'Sergio Carvalho', 2017),
(11, 'APIs com Spring Boot', 'Larissa Gomes', 2025),
(12, 'Projetos de Software', 'Renato Barros', 2021),
(13, 'Computacao em Nuvem', 'Patricia Neves', 2023),
(14, 'Testes de Software', 'Marcos Teixeira', 2024),
(15, 'Redes sem Fio', 'Viviane Lopes', 2022),
(16, 'Estruturas de Dados', 'Rodrigo Batista', 2019),
(17, 'SQL na Pratica', 'Aline Moreira', 2020),
(18, 'Internet das Coisas', 'Fabio Duarte', 2024);

-- Para os casos de teste, use intervalos relativos a CURDATE().
-- Assim o roteiro funciona em qualquer dia da aula e os números de atraso se mantêm.
-- O histórico repete livros somente DEPOIS de uma devolução.
INSERT INTO emprestimo
  (id, aluno_id, livro_id, data_emprestimo, data_prevista_devolucao, data_devolucao) VALUES
-- Ativos com prazo futuro: diasAtraso = 0; multa = 0.
(1,  1,  1, DATE_SUB(CURDATE(), INTERVAL 3 DAY),  DATE_ADD(CURDATE(), INTERVAL 7 DAY),  NULL),
(2,  2,  2, DATE_SUB(CURDATE(), INTERVAL 1 DAY),  DATE_ADD(CURDATE(), INTERVAL 14 DAY), NULL),
(3,  1,  3, DATE_SUB(CURDATE(), INTERVAL 8 DAY),  DATE_ADD(CURDATE(), INTERVAL 2 DAY),  NULL),
-- Hoje ainda não é atraso: a consulta de atrasados não deve incluir ID 4.
(4,  3,  4, DATE_SUB(CURDATE(), INTERVAL 10 DAY), CURDATE(),                             NULL),
-- Ativos vencidos: 1, 3, 7 e 30 dias de atraso.
(5,  4,  5, DATE_SUB(CURDATE(), INTERVAL 11 DAY), DATE_SUB(CURDATE(), INTERVAL 1 DAY),  NULL),
(6,  5,  6, DATE_SUB(CURDATE(), INTERVAL 17 DAY), DATE_SUB(CURDATE(), INTERVAL 3 DAY),  NULL),
(7,  2,  7, DATE_SUB(CURDATE(), INTERVAL 22 DAY), DATE_SUB(CURDATE(), INTERVAL 7 DAY),  NULL),
(8,  6,  8, DATE_SUB(CURDATE(), INTERVAL 45 DAY), DATE_SUB(CURDATE(), INTERVAL 30 DAY), NULL),
-- Já devolvidos: antes do prazo, no prazo e com 2, 5 e 12 dias de atraso.
(9,  3,  9, DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_SUB(CURDATE(), INTERVAL 20 DAY), DATE_SUB(CURDATE(), INTERVAL 23 DAY)),
(10, 4, 10, DATE_SUB(CURDATE(), INTERVAL 25 DAY), DATE_SUB(CURDATE(), INTERVAL 15 DAY), DATE_SUB(CURDATE(), INTERVAL 15 DAY)),
(11, 7, 11, DATE_SUB(CURDATE(), INTERVAL 24 DAY), DATE_SUB(CURDATE(), INTERVAL 14 DAY), DATE_SUB(CURDATE(), INTERVAL 12 DAY)),
(12, 8, 12, DATE_SUB(CURDATE(), INTERVAL 29 DAY), DATE_SUB(CURDATE(), INTERVAL 19 DAY), DATE_SUB(CURDATE(), INTERVAL 14 DAY)),
(13, 5, 13, DATE_SUB(CURDATE(), INTERVAL 50 DAY), DATE_SUB(CURDATE(), INTERVAL 35 DAY), DATE_SUB(CURDATE(), INTERVAL 23 DAY)),
-- Registro antigo: não há previsão; teste SEM_PREVISAO, diasAtraso = 0.
(14, 6, 14, DATE_SUB(CURDATE(), INTERVAL 18 DAY), NULL, NULL),
-- Histórico do mesmo livro: devolvido, depois emprestado a outro aluno.
(15, 7, 15, DATE_SUB(CURDATE(), INTERVAL 32 DAY), DATE_SUB(CURDATE(), INTERVAL 20 DAY), DATE_SUB(CURDATE(), INTERVAL 22 DAY)),
(16, 8, 15, DATE_SUB(CURDATE(), INTERVAL 12 DAY), DATE_ADD(CURDATE(), INTERVAL 3 DAY), NULL),
-- Mais dados para histórico de alunos e futuras consultas.
(17, 1, 16, DATE_SUB(CURDATE(), INTERVAL 42 DAY), DATE_SUB(CURDATE(), INTERVAL 28 DAY), DATE_SUB(CURDATE(), INTERVAL 28 DAY)),
(18, 9, 17, DATE_SUB(CURDATE(), INTERVAL 4 DAY),  DATE_ADD(CURDATE(), INTERVAL 10 DAY), NULL);


