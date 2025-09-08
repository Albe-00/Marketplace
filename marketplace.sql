--
-- Database: `marketplace`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `ordine`
--

CREATE TABLE `ordine` (
  `id_ordine` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_servizio` int(11) NOT NULL,
  `data_ordine` date NOT NULL DEFAULT curdate(),
  `data_consegna` date DEFAULT NULL,
  `stato_ordine` varchar(50) NOT NULL DEFAULT 'IN ATTESA',
  `prezzo` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `ordine`
--

INSERT INTO `ordine` (`id_ordine`, `id_cliente`, `id_servizio`, `data_ordine`, `data_consegna`, `stato_ordine`, `prezzo`) VALUES
(1, 4, 1, '2025-02-01', '2025-02-05', 'COMPLETATO', 120),
(2, 5, 2, '2025-02-02', '2025-02-06', 'COMPLETATO', 80),
(3, 6, 3, '2025-02-03', '2025-02-07', 'COMPLETATO', 50),
(4, 8, 4, '2025-02-04', NULL, 'IN LAVORAZIONE', 300),
(5, 10, 5, '2025-02-05', NULL, 'IN LAVORAZIONE', 500),
(6, 4, 6, '2025-02-06', '2025-02-09', 'COMPLETATO', 60),
(7, 5, 7, '2025-02-07', NULL, 'IN LAVORAZIONE', 40),
(8, 6, 8, '2025-02-08', NULL, 'IN ATTESA', 800),
(9, 8, 1, '2025-02-09', '2025-02-12', 'COMPLETATO', 120),
(10, 10, 2, '2025-02-10', NULL, 'IN LAVORAZIONE', 80),
(11, 4, 3, '2025-02-11', '2025-02-15', 'COMPLETATO', 50),
(12, 5, 4, '2025-02-12', NULL, 'IN ATTESA', 300),
(13, 6, 5, '2025-02-13', NULL, 'RIFIUTATO', 500),
(14, 8, 6, '2025-02-14', '2025-02-17', 'COMPLETATO', 60),
(15, 10, 7, '2025-02-15', NULL, 'IN LAVORAZIONE', 40),
(16, 4, 8, '2025-02-16', NULL, 'IN LAVORAZIONE', 800),
(17, 5, 1, '2025-02-17', '2025-02-20', 'COMPLETATO', 120),
(18, 6, 2, '2025-02-18', '2025-02-22', 'COMPLETATO', 80),
(19, 8, 3, '2025-02-19', NULL, 'IN ATTESA', 50),
(20, 10, 4, '2025-02-20', NULL, 'IN LAVORAZIONE', 300),
(21, 4, 5, '2025-02-21', '2025-02-25', 'COMPLETATO', 500),
(22, 5, 6, '2025-02-22', '2025-02-26', 'COMPLETATO', 60),
(23, 6, 7, '2025-02-23', NULL, 'IN LAVORAZIONE', 40),
(24, 8, 8, '2025-02-24', NULL, 'IN LAVORAZIONE', 800),
(25, 10, 1, '2025-02-25', '2025-02-28', 'COMPLETATO', 120),
(26, 4, 2, '2025-02-26', NULL, 'IN LAVORAZIONE', 80),
(27, 5, 3, '2025-02-27', '2025-03-02', 'COMPLETATO', 50),
(28, 6, 4, '2025-02-28', NULL, 'IN ATTESA', 300),
(29, 8, 5, '2025-03-01', NULL, 'IN LAVORAZIONE', 500),
(30, 10, 6, '2025-03-02', '2025-03-06', 'COMPLETATO', 60),
(32, 5, 4, '2025-08-19', NULL, 'IN ATTESA', 300),
(33, 5, 8, '2025-08-20', NULL, 'IN ATTESA', 800),
(34, 4, 8, '2025-09-03', NULL, 'ANNULLATO', 800);

-- --------------------------------------------------------

--
-- Struttura della tabella `recensione`
--

CREATE TABLE `recensione` (
  `id_recensione` int(11) NOT NULL,
  `id_autore` int(11) NOT NULL,
  `id_venditore` int(11) NOT NULL,
  `voto` int(11) NOT NULL CHECK (`voto` >= 1 and `voto` <= 5),
  `testo` text DEFAULT NULL,
  `data` date NOT NULL DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `recensione`
--

INSERT INTO `recensione` (`id_recensione`, `id_autore`, `id_venditore`, `voto`, `testo`, `data`) VALUES
(1, 4, 1, 5, 'Servizio eccellente e puntuale!', '2025-09-04'),
(2, 5, 2, 4, 'Logo molto bello, ma tempi un po’ lunghi.', '2025-09-04'),
(3, 6, 3, 5, 'Traduzione perfetta e rapida.', '2025-09-04'),
(4, 8, 7, 5, 'Ottimo sito web, molto soddisfatto!', '2025-09-04'),
(5, 10, 9, 5, 'Foto spettacolari, raccomandato!', '2025-09-04'),
(6, 4, 2, 4, 'Buona comunicazione e risultato valido.', '2025-09-04'),
(7, 5, 7, 5, 'E-commerce realizzato alla perfezione.', '2025-09-04'),
(8, 6, 1, 3, 'Assistenza PC discreta, ma migliorabile.', '2025-09-04');

-- --------------------------------------------------------

--
-- Struttura della tabella `servizio`
--

CREATE TABLE `servizio` (
  `id_servizio` int(11) NOT NULL,
  `id_venditore` int(11) NOT NULL,
  `titolo` varchar(100) NOT NULL,
  `descrizione` text DEFAULT NULL,
  `prezzo` float(10,2) NOT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `data_pubblicazione` date NOT NULL DEFAULT curdate(),
  `visibile` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `servizio`
--

INSERT INTO `servizio` (`id_servizio`, `id_venditore`, `titolo`, `descrizione`, `prezzo`, `categoria`, `data_pubblicazione`, `visibile`) VALUES
(1, 1, 'Consulenza IT', 'Supporto tecnico e consulenza in ambito informatico', 120.00, 'Consulenza', '2025-01-10', 1),
(2, 2, 'Logo Design', 'Creazione loghi personalizzati e originali', 80.00, 'Grafica', '2025-01-15', 1),
(3, 3, 'Traduzione Inglese-Italiano', 'Traduzioni professionali e certificate', 50.00, 'Traduzione', '2025-01-20', 1),
(4, 7, 'Sito Web vetrina', 'Realizzazione sito web responsive', 300.00, 'Web Development', '2025-02-01', 1),
(5, 9, 'Servizio fotografico eventi', 'Foto professionali per matrimoni e cerimonie', 500.00, 'Fotografia', '2025-02-05', 1),
(6, 1, 'Assistenza PC', 'Riparazioni e manutenzione computer', 60.00, 'Consulenza', '2025-02-10', 1),
(7, 2, 'Biglietti da visita', 'Design e stampa biglietti da visita', 40.00, 'Grafica', '2025-02-15', 1),
(8, 7, 'E-commerce completo', 'Realizzazione negozio online con pagamento integrato', 800.00, 'Web Development', '2025-02-20', 1),
(10, 5, 'analisi statistica', 'analizzo i dati della vostra azienda', 140.00, 'data analisys', '2025-08-20', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `id_utente` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `venditore` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`id_utente`, `nome`, `cognome`, `email`, `password`, `telefono`, `venditore`) VALUES
(1, 'Mario', 'Rossi', 'mario.rossi@example.com', 'pwd123', '3331112222', 1),
(2, 'Luigi', 'Bianchi', 'luigi.bianchi@example.com', 'pwd456', '3332223333', 1),
(3, 'Carla', 'Verdi', 'carla.verdi@example.com', 'pwd789', '3333334444', 1),
(4, 'Anna', 'Galli', 'anna.galli@example.com', 'pwd321', '3334445555', 1),
(5, 'Paolo', 'Neri', 'paolo.neri@example.com', 'pwd654', '3335556666', 1),
(6, 'Laura', 'Esposito', 'laura.esposito@example.com', 'pwd987', '3336667777', 0),
(7, 'Giorgio', 'Ferrari', 'giorgio.ferrari@example.com', 'pwdabc', '3337778888', 1),
(8, 'Chiara', 'Romano', 'chiara.romano@example.com', 'pwddef', '3338889999', 0),
(9, 'Luca', 'Marini', 'luca.marini@example.com', 'pwdghi', '3339990000', 1),
(10, 'Francesca', 'Fontana', 'francesca.fontana@example.com', 'pwdjkl', '3330001111', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `venditore`
--

CREATE TABLE `venditore` (
  `id_venditore` int(11) NOT NULL,
  `descrizione` text NOT NULL,
  `rating` float DEFAULT NULL CHECK (`rating` >= 0 and `rating` <= 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `venditore`
--

INSERT INTO `venditore` (`id_venditore`, `descrizione`, `rating`) VALUES
(1, 'Specialista in consulenze informatiche', 4),
(2, 'Graphic designer freelance', 4),
(3, 'Traduttrice professionale', 5),
(5, 'ciao ,sono nuovo qui', 0),
(7, 'Sviluppatore web e app mobile', 4),
(9, 'Fotografo freelance per eventi', 5);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `ordine`
--
ALTER TABLE `ordine`
  ADD PRIMARY KEY (`id_ordine`),
  ADD KEY `id_cliente` (`id_cliente`),
  ADD KEY `id_servizio` (`id_servizio`);

--
-- Indici per le tabelle `recensione`
--
ALTER TABLE `recensione`
  ADD PRIMARY KEY (`id_recensione`),
  ADD KEY `id_autore` (`id_autore`),
  ADD KEY `id_venditore` (`id_venditore`);

--
-- Indici per le tabelle `servizio`
--
ALTER TABLE `servizio`
  ADD PRIMARY KEY (`id_servizio`),
  ADD KEY `id_venditore` (`id_venditore`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`id_utente`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indici per le tabelle `venditore`
--
ALTER TABLE `venditore`
  ADD PRIMARY KEY (`id_venditore`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `ordine`
--
ALTER TABLE `ordine`
  MODIFY `id_ordine` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT per la tabella `recensione`
--
ALTER TABLE `recensione`
  MODIFY `id_recensione` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT per la tabella `servizio`
--
ALTER TABLE `servizio`
  MODIFY `id_servizio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT per la tabella `utente`
--
ALTER TABLE `utente`
  MODIFY `id_utente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Vincoli per la tabella `ordine`
--
ALTER TABLE `ordine`
  ADD CONSTRAINT `ordine_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `utente` (`id_utente`) ON DELETE CASCADE,
  ADD CONSTRAINT `ordine_ibfk_2` FOREIGN KEY (`id_servizio`) REFERENCES `servizio` (`id_servizio`) ON DELETE CASCADE;

--
-- Vincoli per la tabella `recensione`
--
ALTER TABLE `recensione`
  ADD CONSTRAINT `recensione_ibfk_1` FOREIGN KEY (`id_autore`) REFERENCES `utente` (`id_utente`) ON DELETE CASCADE,
  ADD CONSTRAINT `recensione_ibfk_2` FOREIGN KEY (`id_venditore`) REFERENCES `venditore` (`id_venditore`) ON DELETE CASCADE;

--
-- Vincoli per la tabella `servizio`
--
ALTER TABLE `servizio`
  ADD CONSTRAINT `servizio_ibfk_1` FOREIGN KEY (`id_venditore`) REFERENCES `venditore` (`id_venditore`) ON DELETE CASCADE;

--
-- Vincoli per la tabella `venditore`
--
ALTER TABLE `venditore`
  ADD CONSTRAINT `fk_venditore_utente` FOREIGN KEY (`id_venditore`) REFERENCES `utente` (`id_utente`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `venditore_ibfk_1` FOREIGN KEY (`id_venditore`) REFERENCES `utente` (`id_utente`);
COMMIT;


