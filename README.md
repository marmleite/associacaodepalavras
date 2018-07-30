# associacaodepalavras
Projeto entregue como Trabalho de Conclusão de Curso para o curso de graduação Tecnologia em Análise e Desenvolvimento de Sistema da Universidade Federal do Paraná.

O projeto é uma versão automatizada do teste psicológico de associação de palavras. O foco foi utilizar tecnologias modernas para aplicá-lo e fazer com que este identifique qual dentre três papéis foi inicialmente atribuído a um sujeito experimental. Para tanto, foi  feito o uso de conceitos de ludificação para tentar aumentar o engajamento do usuário ao teste, assemelhando-o em sua proposta a um jogo que o desafia a enganar a correção automatizada, utilizando uma api de reconhecimento de voz para capturar as respostas do sujeito e o tempo de reação aos estímulos realizados através de palavras pronunciadas pelo sistema. Estes dados são submetidos a uma rede neural artificial para que esta realize a sugestão de qual seria o papel do sujeito. Possui uma interface gráfica para permitir a análise de dados dos testes de forma ágil, imediata e comparativa. Com isso procura-se demonstrar a utilização de um sistema computacional como alternativa à resolução do problema da complexidade apresentado pelo teste psicológico e como este pode auxiliar na pesquisa científica e análise de seus resultados.


Instalação da versão automatizada do teste de associação de palavras:

Antes de realizar a instalação completa do sistema, você deverá ter um servidor capaz de lidar com um programa em java/web e programa em R/Shinny (podendo ser dois separados) e se atentar para a compatibilidade entre a versão atual da biblioteca speech recognition (https://developer.mozilla.org/en-US/docs/Web/API/SpeechRecognition) com a versão utilizada no projeto (de 21/5/2018).
Com isso, é necessário criar um banco de dados MySQL (preferencialmente) e configurar com os dados do banco de dados criados três arquivos “BD.r” dentro da pasta “Shinny/total”,  “Shinny/teste” e  “Shinny/rede”, assim como o  ConnectionFactory.java, na pasta “util” do arquivo java web.
Feito isso, basta hospedar ambos os programas no servidor respectivo e acessar a página inicial para fazer uso deste.


Scripts do banco de dados

[Sistema de Teste e Analise de Dados]:

CREATE DATABASE  IF NOT EXISTS `associacaodepalavras` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `associacaodepalavras`;

CREATE TABLE `escolaridade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `historia` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `texto` longtext NOT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `palavra` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `texto` varchar(45) NOT NULL,
  `caminhoAudio` varchar(100) NOT NULL,
  `mediaReacao` double DEFAULT NULL,
  `desvioPadrao` double DEFAULT NULL,
  `percentualErros` double DEFAULT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `pessoa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dataNascimento` date NOT NULL,
  `idEscolaridade` int(11) NOT NULL,
  `idSocioeconomico` int(11) NOT NULL,
  `idSexo` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_DadosPessoais_sexo_idx` (`idSexo`),
  KEY `fk_DadosPessoais_escolaridade_idx` (`idEscolaridade`),
  KEY `fk_DadosPessoais_socioeconomico_idx` (`idSocioeconomico`),
  CONSTRAINT `fk_DadosPessoais_escolaridade` FOREIGN KEY (`idEscolaridade`) REFERENCES `escolaridade` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_DadosPessoais_sexo` FOREIGN KEY (`idSexo`) REFERENCES `sexo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_DadosPessoais_socioeconomico` FOREIGN KEY (`idSocioeconomico`) REFERENCES `socioeconomico` (`id`)

CREATE TABLE `resposta` (
  `idTeste` int(11) NOT NULL,
  `idPalavra` int(11) NOT NULL,
  `resposta` varchar(45) DEFAULT NULL,
  `erro` tinyint(1) DEFAULT NULL,
  `repeticao` tinyint(1) DEFAULT NULL,
  `erroRepeticao` tinyint(1) NOT NULL DEFAULT '0',
  `midiaResposta` varchar(100) DEFAULT NULL,
  `midiaRepeticao` varchar(100) DEFAULT NULL,
  `posicao` int(11) NOT NULL,
  `tempoReacao` time DEFAULT NULL,
  KEY `Resposta_Teste_FK` (`idTeste`),
  KEY `Resposta_Palavra_FK` (`idPalavra`),
  CONSTRAINT `Resposta_Palavra_FK` FOREIGN KEY (`idPalavra`) REFERENCES `palavra` (`id`),
  CONSTRAINT `Resposta_Teste_FK` FOREIGN KEY (`idTeste`) REFERENCES `teste` (`id`)

CREATE TABLE `sexo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
)


CREATE TABLE `socioeconomico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE TABLE `teste` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dataAplicacao` datetime NOT NULL,
  `mediaReacao` double DEFAULT NULL,
  `desvioPadrao` double DEFAULT NULL,
  `repeticoesErradas` double DEFAULT NULL,
  `incompleto` tinyint(1) DEFAULT '0',
  `idHistoria` int(11) DEFAULT NULL,
  `idPessoa` int(11) DEFAULT NULL,
  `idHistoriaSugerida` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Teste_Historia_idx` (`idHistoria`),
  KEY `fk_Teste_DadosPessoais_idx` (`idPessoa`),
  KEY `fk_Teste_HistoriaSugerida_idx` (`idHistoriaSugerida`),
  CONSTRAINT `fk_Teste_DadosPessoais` FOREIGN KEY (`idPessoa`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
 CONSTRAINT `fk_Teste_Historia` FOREIGN KEY (`idHistoria`) REFERENCES `historia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
CONSTRAINT `fk_Teste_HistoriaSugerida` FOREIGN KEY (`idHistoriaSugerida`) REFERENCES `historia` (`id`)
----------------------------------------------------------------------------------------------------
[Scripts do Sistema da Rede Neural]

CREATE DATABASE  IF NOT EXISTS `associacaodepalavras` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `redeneural`;


CREATE TABLE `pesos1` (
  `ID` int(11) NOT NULL,
  `peso` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
)

ROP TABLE IF EXISTS `pesos2`;

CREATE TABLE `pesos2` (
  `ID` int(11) NOT NULL,
  `peso` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
)

CREATE TABLE `pesosEntradaErros` (
  `ID` int(11) NOT NULL,
  `peso` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
)

CREATE TABLE `pesosEntradaRepeticao` (
  `ID` int(11) NOT NULL,
  `peso` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
)

CREATE TABLE `pesosEntradaTempo` (
  `ID` int(11) NOT NULL,
  `peso` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
)

CREATE TABLE `pesosSaida` (
CREATE TABLE `bias1` (
  `ID` int(11) NOT NULL,
  `peso` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
)

CREATE TABLE `bias2` (
  `ID` int(11) NOT NULL,
  `peso` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
)

DROP TABLE IF EXISTS `bias3`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bias3` (
  `ID` int(11) NOT NULL,
  `peso` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
)

  `ID` int(11) NOT NULL,
  `peso` double DEFAULT NULL,
  PRIMARY KEY (`ID`)

----------------------------------------------------------------------------------------------------
