/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : paac

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-04-10 19:04:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `articulo`
-- ----------------------------
DROP TABLE IF EXISTS `articulo`;
CREATE TABLE `articulo` (
  `idArticulo` int(11) NOT NULL AUTO_INCREMENT,
  `editorial` varchar(50) DEFAULT NULL,
  `issn` varchar(50) DEFAULT NULL,
  `nombre_revista` varchar(80) DEFAULT NULL,
  `rango_paginas` varchar(20) DEFAULT NULL,
  `volumen` varchar(50) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  PRIMARY KEY (`idArticulo`),
  KEY `fk_articulo_1` (`idProducto`),
  CONSTRAINT `fk_articulo_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of articulo
-- ----------------------------

-- ----------------------------
-- Table structure for `articulo_indexado`
-- ----------------------------
DROP TABLE IF EXISTS `articulo_indexado`;
CREATE TABLE `articulo_indexado` (
  `idArticuloIndexado` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `direccionElectronica` varchar(255) DEFAULT NULL,
  `indice` int(11) DEFAULT NULL,
  `idArticulo` int(11) DEFAULT NULL,
  PRIMARY KEY (`idArticuloIndexado`),
  KEY `fk_articuloIndexado_1` (`idArticulo`),
  CONSTRAINT `fk_articuloIndexado_1` FOREIGN KEY (`idArticulo`) REFERENCES `articulo` (`idArticulo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of articulo_indexado
-- ----------------------------

-- ----------------------------
-- Table structure for `capitulo_libro`
-- ----------------------------
DROP TABLE IF EXISTS `capitulo_libro`;
CREATE TABLE `capitulo_libro` (
  `idCapitulo_libro` int(11) NOT NULL AUTO_INCREMENT,
  `editorial` varchar(255) NOT NULL,
  `edicion` int(11) DEFAULT NULL,
  `isbn` varchar(30) DEFAULT NULL,
  `nombre_libro` varchar(80) DEFAULT NULL,
  `rango_paginas` varchar(20) DEFAULT NULL,
  `tiraje` int(11) DEFAULT NULL,
  `titulo_libro` varchar(80) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCapitulo_libro`),
  KEY `fk_capitulo_kibro` (`idProducto`),
  CONSTRAINT `fk_capitulo_kibro` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of capitulo_libro
-- ----------------------------

-- ----------------------------
-- Table structure for `colaborador`
-- ----------------------------
DROP TABLE IF EXISTS `colaborador`;
CREATE TABLE `colaborador` (
  `idColaborador` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idColaborador`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of colaborador
-- ----------------------------
INSERT INTO `colaborador` VALUES ('1', 'Colaborador de Prueba 1');
INSERT INTO `colaborador` VALUES ('2', 'Colaborador de Prueba 2');
INSERT INTO `colaborador` VALUES ('3', 'Colaborador 3');

-- ----------------------------
-- Table structure for `colaborador_cuerpoacademico`
-- ----------------------------
DROP TABLE IF EXISTS `colaborador_cuerpoacademico`;
CREATE TABLE `colaborador_cuerpoacademico` (
  `idColaborador_ca` int(11) NOT NULL AUTO_INCREMENT,
  `idColaborador` int(11) NOT NULL,
  `idCA` int(11) NOT NULL,
  PRIMARY KEY (`idColaborador_ca`,`idColaborador`,`idCA`),
  KEY `fk_colaborador_cuerpoAcademico_1` (`idColaborador`),
  KEY `fk_colaborador_cuerpoAcademico_2` (`idCA`),
  CONSTRAINT `fk_colaborador_cuerpoAcademico_1` FOREIGN KEY (`idColaborador`) REFERENCES `colaborador` (`idColaborador`),
  CONSTRAINT `fk_colaborador_cuerpoAcademico_2` FOREIGN KEY (`idCA`) REFERENCES `cuerpo_academico` (`idCuerpoAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of colaborador_cuerpoacademico
-- ----------------------------

-- ----------------------------
-- Table structure for `cuerpoacademicoexterno`
-- ----------------------------
DROP TABLE IF EXISTS `cuerpoacademicoexterno`;
CREATE TABLE `cuerpoacademicoexterno` (
  `idCuerpoeExterno` int(11) NOT NULL AUTO_INCREMENT,
  `IES` varchar(255) DEFAULT NULL,
  `nombre` varchar(80) DEFAULT NULL,
  `idParticipacion` int(11) DEFAULT NULL,
  `idPais` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCuerpoeExterno`),
  KEY `fk_cuerpoAcademicoExterno` (`idPais`),
  KEY `fk_cuerpoAcademicoExterno_2` (`idParticipacion`),
  CONSTRAINT `fk_cuerpoAcademicoExterno` FOREIGN KEY (`idPais`) REFERENCES `pais` (`idPais`),
  CONSTRAINT `fk_cuerpoAcademicoExterno_2` FOREIGN KEY (`idParticipacion`) REFERENCES `participacion` (`idParticipacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cuerpoacademicoexterno
-- ----------------------------

-- ----------------------------
-- Table structure for `cuerpo_academico`
-- ----------------------------
DROP TABLE IF EXISTS `cuerpo_academico`;
CREATE TABLE `cuerpo_academico` (
  `idCuerpoAcademico` int(11) NOT NULL AUTO_INCREMENT,
  `clave` varchar(255) DEFAULT NULL,
  `gradoConsolidacion` varchar(50) DEFAULT NULL,
  `ies` varchar(255) DEFAULT NULL,
  `nombre` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idCuerpoAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cuerpo_academico
-- ----------------------------

-- ----------------------------
-- Table structure for `cuerpo_academico_promep`
-- ----------------------------
DROP TABLE IF EXISTS `cuerpo_academico_promep`;
CREATE TABLE `cuerpo_academico_promep` (
  `idPROMEP` int(11) NOT NULL AUTO_INCREMENT,
  `DES` varchar(80) NOT NULL,
  `nombre` varchar(80) NOT NULL,
  `idParticipacion` int(11) NOT NULL,
  PRIMARY KEY (`idPROMEP`),
  KEY `fk-CuerpoAcademicoPRODEMP` (`idParticipacion`),
  CONSTRAINT `fk-CuerpoAcademicoPRODEMP` FOREIGN KEY (`idParticipacion`) REFERENCES `participacion` (`idParticipacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cuerpo_academico_promep
-- ----------------------------

-- ----------------------------
-- Table structure for `datos_laborales`
-- ----------------------------
DROP TABLE IF EXISTS `datos_laborales`;
CREATE TABLE `datos_laborales` (
  `idDatosLaborales` int(11) NOT NULL AUTO_INCREMENT,
  `cronologia` varchar(255) DEFAULT NULL,
  `dedicacion` varchar(255) DEFAULT NULL,
  `dependencia` varchar(255) DEFAULT NULL,
  `inicioContrato` date DEFAULT NULL,
  `finContrato` date DEFAULT NULL,
  `ies` varchar(255) DEFAULT NULL,
  `nombramiento` varchar(80) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `unidadAcademica` varchar(255) DEFAULT NULL,
  `idMiembro` int(11) DEFAULT NULL,
  PRIMARY KEY (`idDatosLaborales`),
  KEY `fk_idDatosLaborales` (`idMiembro`),
  CONSTRAINT `fk_idDatosLaborales` FOREIGN KEY (`idMiembro`) REFERENCES `miembro` (`idMiembro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of datos_laborales
-- ----------------------------

-- ----------------------------
-- Table structure for `gradoacademico`
-- ----------------------------
DROP TABLE IF EXISTS `gradoacademico`;
CREATE TABLE `gradoacademico` (
  `idGradoAcademico` int(11) NOT NULL AUTO_INCREMENT,
  `areaDisciplinar` varchar(255) DEFAULT NULL,
  `fechaInicio` date DEFAULT NULL,
  `fechatitulacion` date DEFAULT NULL,
  `institucion` varchar(255) DEFAULT NULL,
  `institucionNoConsiderada` varchar(255) DEFAULT NULL,
  `nivel` varchar(255) DEFAULT NULL,
  `idPais` int(11) NOT NULL,
  `tema` varchar(255) DEFAULT NULL,
  `idMiembro` int(11) NOT NULL,
  PRIMARY KEY (`idGradoAcademico`),
  KEY `fk_gradoAcademico_2` (`idPais`),
  KEY `fk_gradoacademico_3` (`idMiembro`),
  CONSTRAINT `fk_gradoAcademico_2` FOREIGN KEY (`idPais`) REFERENCES `pais` (`idPais`),
  CONSTRAINT `fk_gradoacademico_3` FOREIGN KEY (`idMiembro`) REFERENCES `miembro` (`idMiembro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gradoacademico
-- ----------------------------

-- ----------------------------
-- Table structure for `lgac`
-- ----------------------------
DROP TABLE IF EXISTS `lgac`;
CREATE TABLE `lgac` (
  `idlgac` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idlgac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lgac
-- ----------------------------

-- ----------------------------
-- Table structure for `libro`
-- ----------------------------
DROP TABLE IF EXISTS `libro`;
CREATE TABLE `libro` (
  `idLibro` int(11) NOT NULL AUTO_INCREMENT,
  `edicion` int(11) DEFAULT NULL,
  `editorial` varchar(255) DEFAULT NULL,
  `isbn` varchar(30) DEFAULT NULL,
  `numPaginas` int(11) DEFAULT NULL,
  `tiraje` int(11) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  PRIMARY KEY (`idLibro`),
  KEY `fk_libro_1` (`idProducto`),
  CONSTRAINT `fk_libro_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of libro
-- ----------------------------
INSERT INTO `libro` VALUES ('1', '1', '1234567891', '1234567891', '1234', '1334', null);
INSERT INTO `libro` VALUES ('2', '1', '1112223334', '1112223334', '156', '100', null);
INSERT INTO `libro` VALUES ('3', '12', '1112223334', '1112223334', '123', '12', null);
INSERT INTO `libro` VALUES ('4', '12', '1112223334', '1112223334', '123', '12', null);
INSERT INTO `libro` VALUES ('5', '12', '1112223334', '1112223334', '123', '12', null);
INSERT INTO `libro` VALUES ('6', '12', '1112223334', '1112223334', '123', '12', null);
INSERT INTO `libro` VALUES ('7', '1', '1112223334', '1112223334', '12', '100', null);

-- ----------------------------
-- Table structure for `memoria`
-- ----------------------------
DROP TABLE IF EXISTS `memoria`;
CREATE TABLE `memoria` (
  `idMemoria` int(11) NOT NULL AUTO_INCREMENT,
  `ciudad` varchar(150) DEFAULT NULL,
  `estado` varchar(150) DEFAULT NULL,
  `rangoPaginas` varchar(20) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  `nombreCongreso` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idMemoria`),
  KEY `fk_memoria_1` (`idProducto`),
  CONSTRAINT `fk_memoria_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of memoria
-- ----------------------------
INSERT INTO `memoria` VALUES ('1', 'Xalapa', 'Veracruz', '1-2', '1', 'UV');
INSERT INTO `memoria` VALUES ('2', 'Xalapa', 'Veracruz', '1-67', '3', 'UV');
INSERT INTO `memoria` VALUES ('3', 'Xalapa', 'Veracruz', '1-2', '4', 'UV');
INSERT INTO `memoria` VALUES ('4', 'Xalapa', 'Vercruz', '1-2', '5', 'UV');
INSERT INTO `memoria` VALUES ('5', 'Xalapa', 'Veracruz', '1-3', '8', 'Congreso I');
INSERT INTO `memoria` VALUES ('6', 'Xalapa', 'Veracruz', '1-2', '9', '');
INSERT INTO `memoria` VALUES ('7', 'Xalapa', 'Veracruz', '1-2', '10', '');
INSERT INTO `memoria` VALUES ('8', 'Xalapa', 'Veracruz', '1-2', '11', '');
INSERT INTO `memoria` VALUES ('9', 'Xalapa', 'Veracruz', '1-2', '12', 'uu');
INSERT INTO `memoria` VALUES ('10', 'Xalapa', 'Veracruz', '123..45', '13', 'uu');
INSERT INTO `memoria` VALUES ('11', 'Xalapa', 'Veracruz', '20-2', '14', 'uu');
INSERT INTO `memoria` VALUES ('12', 'Xalapa', 'Veracruz', '1-2', '15', 'Congreso');
INSERT INTO `memoria` VALUES ('13', 'Xalapa', 'Veracruz', '1-2', '16', '');
INSERT INTO `memoria` VALUES ('14', 'Xalapa', 'Veracruz', '1-2', '17', '');
INSERT INTO `memoria` VALUES ('15', 'Xalapa', 'Veracruz', '1...56', '18', '');
INSERT INTO `memoria` VALUES ('16', 'Xalapa', 'Veracruz', '1-2', '27', 'Congreso de la union');
INSERT INTO `memoria` VALUES ('17', 'Xalapa', 'Veracruz', '1-2', '28', 'Universidad Veracruzana');
INSERT INTO `memoria` VALUES ('18', 'Xalapa', 'Veracruz', '20-1', '29', 'Congreso de la Union');
INSERT INTO `memoria` VALUES ('19', 'Xalapa', 'Veracruz', '1-2', '30', 'Congreso I');
INSERT INTO `memoria` VALUES ('20', 'Xalapa', 'Veracruz', '1-2', '31', 'Congreso I');

-- ----------------------------
-- Table structure for `miembro`
-- ----------------------------
DROP TABLE IF EXISTS `miembro`;
CREATE TABLE `miembro` (
  `idMiembro` int(11) NOT NULL AUTO_INCREMENT,
  `estado` varchar(255) DEFAULT NULL,
  `pe_impacta` varchar(255) DEFAULT NULL,
  `promep` varchar(50) DEFAULT NULL,
  `sni` varchar(100) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `usuario` varchar(30) DEFAULT NULL,
  `contrasenia` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`idMiembro`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of miembro
-- ----------------------------
INSERT INTO `miembro` VALUES ('1', '2', 'Ingenieria de Software', 'SI', '1234', '1', 'Miembro de Prueba 1', 'miembroPRUEBA1', 'M13MBR0');

-- ----------------------------
-- Table structure for `miembro_lgac`
-- ----------------------------
DROP TABLE IF EXISTS `miembro_lgac`;
CREATE TABLE `miembro_lgac` (
  `idMiembroLGAC` int(11) NOT NULL AUTO_INCREMENT,
  `idMiembro` int(11) NOT NULL,
  `idLGAC` int(11) NOT NULL,
  PRIMARY KEY (`idMiembroLGAC`,`idMiembro`,`idLGAC`),
  KEY `fk_miembro_LGAC_1` (`idMiembro`),
  KEY `fk_miembro_LGAC_2` (`idLGAC`),
  CONSTRAINT `fk_miembro_LGAC_1` FOREIGN KEY (`idMiembro`) REFERENCES `miembro` (`idMiembro`),
  CONSTRAINT `fk_miembro_LGAC_2` FOREIGN KEY (`idLGAC`) REFERENCES `lgac` (`idlgac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of miembro_lgac
-- ----------------------------

-- ----------------------------
-- Table structure for `pais`
-- ----------------------------
DROP TABLE IF EXISTS `pais`;
CREATE TABLE `pais` (
  `idPais` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idPais`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pais
-- ----------------------------
INSERT INTO `pais` VALUES ('1', 'Afganistán');
INSERT INTO `pais` VALUES ('2', 'Albania');
INSERT INTO `pais` VALUES ('3', 'Alemania');
INSERT INTO `pais` VALUES ('4', 'Andorra');
INSERT INTO `pais` VALUES ('5', 'Angola');
INSERT INTO `pais` VALUES ('6', 'Antigua y Barbuda');
INSERT INTO `pais` VALUES ('7', 'Arabia Saudita');
INSERT INTO `pais` VALUES ('8', 'Argelia');
INSERT INTO `pais` VALUES ('9', 'Argentina');
INSERT INTO `pais` VALUES ('10', 'Armenia');
INSERT INTO `pais` VALUES ('11', 'Australia');
INSERT INTO `pais` VALUES ('12', 'Austria');
INSERT INTO `pais` VALUES ('13', 'Azerbaiyán');
INSERT INTO `pais` VALUES ('14', 'Bahamas');
INSERT INTO `pais` VALUES ('15', 'Bangladés');
INSERT INTO `pais` VALUES ('16', 'Barbados');
INSERT INTO `pais` VALUES ('17', 'Baréin');
INSERT INTO `pais` VALUES ('18', 'Bélgica');
INSERT INTO `pais` VALUES ('19', 'Belice');
INSERT INTO `pais` VALUES ('20', 'Benín');
INSERT INTO `pais` VALUES ('21', 'Bielorrusia');
INSERT INTO `pais` VALUES ('22', 'Birmania');
INSERT INTO `pais` VALUES ('23', 'Bolivia');
INSERT INTO `pais` VALUES ('24', 'Bosnia y Herzegovina');
INSERT INTO `pais` VALUES ('25', 'Botsuana');
INSERT INTO `pais` VALUES ('26', 'Brasil');
INSERT INTO `pais` VALUES ('27', 'Brunéi');
INSERT INTO `pais` VALUES ('28', 'Bulgaria');
INSERT INTO `pais` VALUES ('29', 'Burkina Faso');
INSERT INTO `pais` VALUES ('30', 'Burundi');
INSERT INTO `pais` VALUES ('31', 'Bután');
INSERT INTO `pais` VALUES ('32', 'Cabo Verde');
INSERT INTO `pais` VALUES ('33', 'Camboya');
INSERT INTO `pais` VALUES ('34', 'Camerún');
INSERT INTO `pais` VALUES ('35', 'Canadá');
INSERT INTO `pais` VALUES ('36', 'Catar');
INSERT INTO `pais` VALUES ('37', 'Chad');
INSERT INTO `pais` VALUES ('38', 'Chile');
INSERT INTO `pais` VALUES ('39', 'China');
INSERT INTO `pais` VALUES ('40', 'Chipre');
INSERT INTO `pais` VALUES ('41', 'Ciudad del Vaticano');
INSERT INTO `pais` VALUES ('42', 'Colombia');
INSERT INTO `pais` VALUES ('43', 'Comoras');
INSERT INTO `pais` VALUES ('44', 'Corea del Norte');
INSERT INTO `pais` VALUES ('45', 'Corea del Sur');
INSERT INTO `pais` VALUES ('46', 'Costa de Marfil');
INSERT INTO `pais` VALUES ('47', 'Costa Rica');
INSERT INTO `pais` VALUES ('48', 'Croacia');
INSERT INTO `pais` VALUES ('49', 'Cuba');
INSERT INTO `pais` VALUES ('50', 'Dinamarca');
INSERT INTO `pais` VALUES ('51', 'Dominica');
INSERT INTO `pais` VALUES ('52', 'Ecuador');
INSERT INTO `pais` VALUES ('53', 'Egipto');
INSERT INTO `pais` VALUES ('54', 'El Salvador');
INSERT INTO `pais` VALUES ('55', 'Emiratos Árabes Unidos');
INSERT INTO `pais` VALUES ('56', 'Eritrea');
INSERT INTO `pais` VALUES ('57', 'Eslovaquia');
INSERT INTO `pais` VALUES ('58', 'Eslovenia');
INSERT INTO `pais` VALUES ('59', 'España');
INSERT INTO `pais` VALUES ('60', 'Estados Unidos');
INSERT INTO `pais` VALUES ('61', 'Estonia');
INSERT INTO `pais` VALUES ('62', 'Etiopía');
INSERT INTO `pais` VALUES ('63', 'Filipinas');
INSERT INTO `pais` VALUES ('64', 'Finlandia');
INSERT INTO `pais` VALUES ('65', 'Fiyi');
INSERT INTO `pais` VALUES ('66', 'Francia');
INSERT INTO `pais` VALUES ('67', 'Gabón');
INSERT INTO `pais` VALUES ('68', 'Gambia');
INSERT INTO `pais` VALUES ('69', 'Georgia');
INSERT INTO `pais` VALUES ('70', 'Ghana');
INSERT INTO `pais` VALUES ('71', 'Granada');
INSERT INTO `pais` VALUES ('72', 'Grecia');
INSERT INTO `pais` VALUES ('73', 'Guatemala');
INSERT INTO `pais` VALUES ('74', 'Guyana');
INSERT INTO `pais` VALUES ('75', 'Guinea');
INSERT INTO `pais` VALUES ('76', 'Guinea ecuatorial');
INSERT INTO `pais` VALUES ('77', 'Guinea-Bisáu');
INSERT INTO `pais` VALUES ('78', 'Haití');
INSERT INTO `pais` VALUES ('79', 'Honduras');
INSERT INTO `pais` VALUES ('80', 'Hungría');
INSERT INTO `pais` VALUES ('81', 'India');
INSERT INTO `pais` VALUES ('82', 'Indonesia');
INSERT INTO `pais` VALUES ('83', 'Irak');
INSERT INTO `pais` VALUES ('84', 'Irán');
INSERT INTO `pais` VALUES ('85', 'Irlanda');
INSERT INTO `pais` VALUES ('86', 'Islandia');
INSERT INTO `pais` VALUES ('87', 'Islas Marshall');
INSERT INTO `pais` VALUES ('88', 'Islas Salomón');
INSERT INTO `pais` VALUES ('89', 'Israel');
INSERT INTO `pais` VALUES ('90', 'Italia');
INSERT INTO `pais` VALUES ('91', 'Jamaica');
INSERT INTO `pais` VALUES ('92', 'Japón');
INSERT INTO `pais` VALUES ('93', 'Jordania');
INSERT INTO `pais` VALUES ('94', 'Kazajistán');
INSERT INTO `pais` VALUES ('95', 'Kenia');
INSERT INTO `pais` VALUES ('96', 'Kirguistán');
INSERT INTO `pais` VALUES ('97', 'Kiribati');
INSERT INTO `pais` VALUES ('98', 'Kuwait');
INSERT INTO `pais` VALUES ('99', 'Laos');
INSERT INTO `pais` VALUES ('100', 'Lesoto');
INSERT INTO `pais` VALUES ('101', 'Letonia');
INSERT INTO `pais` VALUES ('102', 'Líbano');
INSERT INTO `pais` VALUES ('103', 'Liberia');
INSERT INTO `pais` VALUES ('104', 'Libia');
INSERT INTO `pais` VALUES ('105', 'Liechtenstein');
INSERT INTO `pais` VALUES ('106', 'Lituania');
INSERT INTO `pais` VALUES ('107', 'Luxemburgo');
INSERT INTO `pais` VALUES ('108', 'Madagascar');
INSERT INTO `pais` VALUES ('109', 'Malasia');
INSERT INTO `pais` VALUES ('110', 'Malaui');
INSERT INTO `pais` VALUES ('111', 'Maldivas');
INSERT INTO `pais` VALUES ('112', 'Malí');
INSERT INTO `pais` VALUES ('113', 'Malta');
INSERT INTO `pais` VALUES ('114', 'Marruecos');
INSERT INTO `pais` VALUES ('115', 'Mauricio');
INSERT INTO `pais` VALUES ('116', 'Mauritania');
INSERT INTO `pais` VALUES ('117', 'México');
INSERT INTO `pais` VALUES ('118', 'Micronesia');
INSERT INTO `pais` VALUES ('119', 'Moldavia');
INSERT INTO `pais` VALUES ('120', 'Mónaco');
INSERT INTO `pais` VALUES ('121', 'Mongolia');
INSERT INTO `pais` VALUES ('122', 'Montenegro');
INSERT INTO `pais` VALUES ('123', 'Mozambique');
INSERT INTO `pais` VALUES ('124', 'Namibia');
INSERT INTO `pais` VALUES ('125', 'Nauru');
INSERT INTO `pais` VALUES ('126', 'Nepal');
INSERT INTO `pais` VALUES ('127', 'Nicaragua');
INSERT INTO `pais` VALUES ('128', 'Níger');
INSERT INTO `pais` VALUES ('129', 'Nigeria');
INSERT INTO `pais` VALUES ('130', 'Noruega');
INSERT INTO `pais` VALUES ('131', 'Nueva Zelanda');
INSERT INTO `pais` VALUES ('132', 'Omán');
INSERT INTO `pais` VALUES ('133', 'Países Bajos');
INSERT INTO `pais` VALUES ('134', 'Pakistán');
INSERT INTO `pais` VALUES ('135', 'Palaos');
INSERT INTO `pais` VALUES ('136', 'Panamá');
INSERT INTO `pais` VALUES ('137', 'Papúa Nueva Guinea');
INSERT INTO `pais` VALUES ('138', 'Paraguay');
INSERT INTO `pais` VALUES ('139', 'Perú');
INSERT INTO `pais` VALUES ('140', 'Polonia');
INSERT INTO `pais` VALUES ('141', 'Portugal');
INSERT INTO `pais` VALUES ('142', 'Reino Unido');
INSERT INTO `pais` VALUES ('143', 'República Centroafricana');
INSERT INTO `pais` VALUES ('144', 'República Checa');
INSERT INTO `pais` VALUES ('145', 'República de Macedonia');
INSERT INTO `pais` VALUES ('146', 'República del Congo');
INSERT INTO `pais` VALUES ('147', 'República Democrática del Congo');
INSERT INTO `pais` VALUES ('148', 'República Dominicana');
INSERT INTO `pais` VALUES ('149', 'República Sudafricana');
INSERT INTO `pais` VALUES ('150', 'Ruanda');
INSERT INTO `pais` VALUES ('151', 'Rumanía');
INSERT INTO `pais` VALUES ('152', 'Rusia');
INSERT INTO `pais` VALUES ('153', 'Samoa');
INSERT INTO `pais` VALUES ('154', 'San Cristóbal y Nieves');
INSERT INTO `pais` VALUES ('155', 'San Marino');
INSERT INTO `pais` VALUES ('156', 'San Vicente y las Granadinas');
INSERT INTO `pais` VALUES ('157', 'Santa Lucía');
INSERT INTO `pais` VALUES ('158', 'Santo Tomé y Príncipe');
INSERT INTO `pais` VALUES ('159', 'Senegal');
INSERT INTO `pais` VALUES ('160', 'Serbia');
INSERT INTO `pais` VALUES ('161', 'Seychelles');
INSERT INTO `pais` VALUES ('162', 'Sierra Leona');
INSERT INTO `pais` VALUES ('163', 'Singapur');
INSERT INTO `pais` VALUES ('164', 'Siria');
INSERT INTO `pais` VALUES ('165', 'Somalia');
INSERT INTO `pais` VALUES ('166', 'Sri Lanka');
INSERT INTO `pais` VALUES ('167', 'Suazilandia');
INSERT INTO `pais` VALUES ('168', 'Sudán');
INSERT INTO `pais` VALUES ('169', 'Sudán del Sur');
INSERT INTO `pais` VALUES ('170', 'Suecia');
INSERT INTO `pais` VALUES ('171', 'Suiza');
INSERT INTO `pais` VALUES ('172', 'Surinam');
INSERT INTO `pais` VALUES ('173', 'Tailandia');
INSERT INTO `pais` VALUES ('174', 'Tanzania');
INSERT INTO `pais` VALUES ('175', 'Tayikistán');
INSERT INTO `pais` VALUES ('176', 'Timor Oriental');
INSERT INTO `pais` VALUES ('177', 'Togo');
INSERT INTO `pais` VALUES ('178', 'Tonga');
INSERT INTO `pais` VALUES ('179', 'Trinidad y Tobago');
INSERT INTO `pais` VALUES ('180', 'Túnez');
INSERT INTO `pais` VALUES ('181', 'Turkmenistán');
INSERT INTO `pais` VALUES ('182', 'Turquía');
INSERT INTO `pais` VALUES ('183', 'Tuvalu');
INSERT INTO `pais` VALUES ('184', 'Ucrania');
INSERT INTO `pais` VALUES ('185', 'Uganda');
INSERT INTO `pais` VALUES ('186', 'Uruguay');
INSERT INTO `pais` VALUES ('187', 'Uzbekistán');
INSERT INTO `pais` VALUES ('188', 'Vanuatu');
INSERT INTO `pais` VALUES ('189', 'Venezuela');
INSERT INTO `pais` VALUES ('190', 'Vietnam');
INSERT INTO `pais` VALUES ('191', 'Yemen');
INSERT INTO `pais` VALUES ('192', 'Yibuti');
INSERT INTO `pais` VALUES ('193', 'Zambia');
INSERT INTO `pais` VALUES ('194', 'Zimbabue');

-- ----------------------------
-- Table structure for `participacion`
-- ----------------------------
DROP TABLE IF EXISTS `participacion`;
CREATE TABLE `participacion` (
  `idParticipacion` int(11) NOT NULL AUTO_INCREMENT,
  `descripcionColaboracion` varchar(255) DEFAULT NULL,
  `descripcionCooperacion` varchar(225) DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  `fechaInicio` date DEFAULT NULL,
  `grupo` varchar(255) DEFAULT NULL,
  `objetivoGrupo` varchar(255) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL,
  `idCA` int(11) DEFAULT NULL,
  PRIMARY KEY (`idParticipacion`),
  KEY `fk_participacion` (`idCA`),
  CONSTRAINT `fk_participacion` FOREIGN KEY (`idCA`) REFERENCES `cuerpo_academico` (`idCuerpoAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of participacion
-- ----------------------------

-- ----------------------------
-- Table structure for `patente`
-- ----------------------------
DROP TABLE IF EXISTS `patente`;
CREATE TABLE `patente` (
  `idPatente` int(11) NOT NULL AUTO_INCREMENT,
  `clasif_intl_patentes` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `evidencia` blob,
  `tipo` varchar(255) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  `nombreEvidencia` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idPatente`),
  KEY `fk_patente_1` (`idProducto`),
  CONSTRAINT `fk_patente_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of patente
-- ----------------------------

-- ----------------------------
-- Table structure for `pe`
-- ----------------------------
DROP TABLE IF EXISTS `pe`;
CREATE TABLE `pe` (
  `idPE` int(11) NOT NULL AUTO_INCREMENT,
  `archivo` blob,
  `gradoIntervecion` varchar(255) DEFAULT NULL,
  `fechaImplementacion` date DEFAULT NULL,
  `nombrePE` varchar(255) DEFAULT NULL,
  `PEAcreditado` varchar(255) DEFAULT NULL,
  `PEDentroEGEL` varchar(255) DEFAULT NULL,
  `resultadoParticipacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idPE`),
  CONSTRAINT `fk_idPE` FOREIGN KEY (`idPE`) REFERENCES `cuerpo_academico` (`idCuerpoAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pe
-- ----------------------------

-- ----------------------------
-- Table structure for `producto`
-- ----------------------------
DROP TABLE IF EXISTS `producto`;
CREATE TABLE `producto` (
  `idProducto` int(11) NOT NULL AUTO_INCREMENT,
  `anio` int(11) DEFAULT NULL,
  `estado_actual` varchar(50) DEFAULT NULL,
  `idPais` int(11) DEFAULT NULL,
  `proposito` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `idCuerpoAcademico` int(11) DEFAULT NULL,
  `archivoPDF` mediumblob,
  `nombrePDF` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idProducto`),
  KEY `fk_pais_1` (`idPais`),
  KEY `fk_pais_2` (`idCuerpoAcademico`),
  CONSTRAINT `fk_pais_1` FOREIGN KEY (`idPais`) REFERENCES `pais` (`idPais`),
  CONSTRAINT `fk_pais_2` FOREIGN KEY (`idCuerpoAcademico`) REFERENCES `cuerpo_academico` (`idCuerpoAcademico`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of producto
-- ----------------------------
INSERT INTO `producto` VALUES ('1', '2018', 'En Progreso', '117', 'pruebas', 'Memoria 1', null, null, null);
INSERT INTO `producto` VALUES ('2', '2018', null, '1', 'Pruebas', 'Libro 1', null, null, null);
INSERT INTO `producto` VALUES ('3', '2018', '0', '117', '', 'Memoria 2', null, null, null);
INSERT INTO `producto` VALUES ('4', '2018', 'En progreso', '117', 'Pruebas', 'Memoria 3', null, null, null);
INSERT INTO `producto` VALUES ('5', '2018', 'En proceso', '117', 'Pruebas', 'Memoria 4', null, null, null);
INSERT INTO `producto` VALUES ('6', '2018', 'En proceso', '117', 'Probar', 'Prototipo 1', null, null, null);
INSERT INTO `producto` VALUES ('7', '2019', 'En proceso', '117', 'Probar', 'Tesis 1', null, null, null);
INSERT INTO `producto` VALUES ('8', '2018', 'En proceso', '117', 'Generar Conocimiento', 'Memoria nueva', null, null, null);
INSERT INTO `producto` VALUES ('9', '2018', 'En proceso', null, 'Generar', 'Titulo', null, null, null);
INSERT INTO `producto` VALUES ('10', '2018', 'En proceso', null, 'Generar', 'Titulo', null, null, null);
INSERT INTO `producto` VALUES ('11', '2018', 'En proceso', '1', 'Generar', 'Titulo', null, null, null);
INSERT INTO `producto` VALUES ('12', '20', 'En proceso', '1', 'Generar', 'Titulo', null, null, null);
INSERT INTO `producto` VALUES ('13', '2018', 'En proceso', '1', 'Generar', 'Titulo', null, null, null);
INSERT INTO `producto` VALUES ('14', '2018', 'En proceso', '1', 'Generar', 'Titulo', null, null, null);
INSERT INTO `producto` VALUES ('15', '2018', 'En proceso', null, 'Proposito', 'Titulo', null, null, null);
INSERT INTO `producto` VALUES ('16', '2018', 'En proceso', null, 'Proposito', 'Titulo', null, null, null);
INSERT INTO `producto` VALUES ('17', '2018', 'En proceso', null, '', 'Titulo', null, null, null);
INSERT INTO `producto` VALUES ('18', '2018', 'En proceso', null, '', 'Titulo', null, null, null);
INSERT INTO `producto` VALUES ('19', '2018', 'En proceso', '1', 'Generar conocimiento', 'Prototipo Nuevo', null, null, null);
INSERT INTO `producto` VALUES ('20', '2019', null, '23', 'Proposito', 'Titulo', null, null, null);
INSERT INTO `producto` VALUES ('21', '2020', null, null, 'cfgc', 'Biologia', null, null, null);
INSERT INTO `producto` VALUES ('22', '2020', null, null, 'cfgc', 'Biologia', null, null, null);
INSERT INTO `producto` VALUES ('23', '2020', null, '3', 'cfgc', 'Biologia', null, null, null);
INSERT INTO `producto` VALUES ('24', '2020', null, '3', 'cfgc', 'Biologia', null, null, null);
INSERT INTO `producto` VALUES ('25', '2098', null, '2', 'qetrq', 'tqe', null, null, null);
INSERT INTO `producto` VALUES ('26', '2019', 'qwe', '2', 'qwer', 'rewq', null, null, null);
INSERT INTO `producto` VALUES ('27', '2019', 'En proceso', '1', 'Fines de pruebas', 'Memoria prueba 2', null, null, null);
INSERT INTO `producto` VALUES ('28', '2018', 'En proceso', '2', 'Generar conocimiento', 'Memoria de prueba', null, null, null);
INSERT INTO `producto` VALUES ('29', '2019', 'En proceso', '117', 'Final', 'Memoria final', null, null, null);
INSERT INTO `producto` VALUES ('30', '2018', 'En proceso', '117', 'Proposito', 'Memo', null, null, null);
INSERT INTO `producto` VALUES ('31', '2019', 'En proceso', '117', 'Generar Conocimiento', 'Memoria Maestra', null, null, null);

-- ----------------------------
-- Table structure for `producto_colaborador`
-- ----------------------------
DROP TABLE IF EXISTS `producto_colaborador`;
CREATE TABLE `producto_colaborador` (
  `idProductoColaborador` int(11) NOT NULL AUTO_INCREMENT,
  `idProducto` int(11) NOT NULL,
  `idColaborador` int(11) NOT NULL,
  PRIMARY KEY (`idProductoColaborador`,`idProducto`,`idColaborador`),
  KEY `fk_producto_colaborador_1` (`idProducto`),
  KEY `fk_producto_colaborador_2` (`idColaborador`),
  CONSTRAINT `fk_producto_colaborador_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`),
  CONSTRAINT `fk_producto_colaborador_2` FOREIGN KEY (`idColaborador`) REFERENCES `colaborador` (`idColaborador`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of producto_colaborador
-- ----------------------------
INSERT INTO `producto_colaborador` VALUES ('1', '1', '1');
INSERT INTO `producto_colaborador` VALUES ('2', '5', '1');
INSERT INTO `producto_colaborador` VALUES ('3', '6', '2');
INSERT INTO `producto_colaborador` VALUES ('4', '6', '1');
INSERT INTO `producto_colaborador` VALUES ('5', '7', '1');
INSERT INTO `producto_colaborador` VALUES ('6', '8', '1');
INSERT INTO `producto_colaborador` VALUES ('7', '19', '1');
INSERT INTO `producto_colaborador` VALUES ('8', '19', '2');
INSERT INTO `producto_colaborador` VALUES ('9', '29', '1');
INSERT INTO `producto_colaborador` VALUES ('10', '29', '2');
INSERT INTO `producto_colaborador` VALUES ('11', '30', '1');
INSERT INTO `producto_colaborador` VALUES ('12', '31', '1');
INSERT INTO `producto_colaborador` VALUES ('13', '31', '2');

-- ----------------------------
-- Table structure for `producto_miembro`
-- ----------------------------
DROP TABLE IF EXISTS `producto_miembro`;
CREATE TABLE `producto_miembro` (
  `idMiembroProducto` int(11) NOT NULL AUTO_INCREMENT,
  `idProducto` int(11) DEFAULT NULL,
  `idMiembro` int(11) DEFAULT NULL,
  PRIMARY KEY (`idMiembroProducto`),
  KEY `fk_producto_Miembro_1` (`idProducto`),
  KEY `fk_priducto_Miembro` (`idMiembro`),
  CONSTRAINT `fk_priducto_Miembro` FOREIGN KEY (`idMiembro`) REFERENCES `miembro` (`idMiembro`),
  CONSTRAINT `fk_producto_Miembro_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of producto_miembro
-- ----------------------------
INSERT INTO `producto_miembro` VALUES ('1', '1', '1');
INSERT INTO `producto_miembro` VALUES ('2', '5', '1');
INSERT INTO `producto_miembro` VALUES ('3', '6', '1');
INSERT INTO `producto_miembro` VALUES ('4', '7', '1');
INSERT INTO `producto_miembro` VALUES ('5', '8', '1');
INSERT INTO `producto_miembro` VALUES ('6', '29', '1');
INSERT INTO `producto_miembro` VALUES ('7', '31', '1');

-- ----------------------------
-- Table structure for `producto_proyecto`
-- ----------------------------
DROP TABLE IF EXISTS `producto_proyecto`;
CREATE TABLE `producto_proyecto` (
  `idProductoProyecto` int(11) NOT NULL AUTO_INCREMENT,
  `idProducto` int(11) NOT NULL,
  `idProyecto` int(11) NOT NULL,
  PRIMARY KEY (`idProductoProyecto`,`idProducto`,`idProyecto`),
  KEY `fk_producto_proyecto_1` (`idProducto`),
  KEY `fk_proyecto_producto_2` (`idProyecto`),
  CONSTRAINT `fk_producto_proyecto_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`),
  CONSTRAINT `fk_proyecto_producto_2` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of producto_proyecto
-- ----------------------------

-- ----------------------------
-- Table structure for `prototipo`
-- ----------------------------
DROP TABLE IF EXISTS `prototipo`;
CREATE TABLE `prototipo` (
  `idprototipo` int(11) NOT NULL AUTO_INCREMENT,
  `caracteristicas` varchar(255) DEFAULT NULL,
  `institucionCreacion` varchar(150) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  PRIMARY KEY (`idprototipo`),
  KEY `fk_prototipo_1` (`idProducto`),
  CONSTRAINT `fk_prototipo_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of prototipo
-- ----------------------------
INSERT INTO `prototipo` VALUES ('1', 'Esta bonito', 'UV', '6');
INSERT INTO `prototipo` VALUES ('2', 'Prototipo nuevo', 'UV', '19');

-- ----------------------------
-- Table structure for `proyecto`
-- ----------------------------
DROP TABLE IF EXISTS `proyecto`;
CREATE TABLE `proyecto` (
  `idProyecto` int(11) NOT NULL AUTO_INCREMENT,
  `cantidadApoyo` int(11) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fechaFin` date DEFAULT NULL,
  `fechaInicio` date DEFAULT NULL,
  `idLGACApoyo` int(11) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `tipoApoyo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idProyecto`),
  KEY `fk_proyecto_1` (`idLGACApoyo`),
  CONSTRAINT `fk_proyecto_1` FOREIGN KEY (`idLGACApoyo`) REFERENCES `lgac` (`idlgac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of proyecto
-- ----------------------------

-- ----------------------------
-- Table structure for `proyecto_investigacionconjunto`
-- ----------------------------
DROP TABLE IF EXISTS `proyecto_investigacionconjunto`;
CREATE TABLE `proyecto_investigacionconjunto` (
  `idProyectoInvestigacionConjunto` int(11) NOT NULL AUTO_INCREMENT,
  `actividades` varchar(255) DEFAULT NULL,
  `archivo_pdf` blob,
  `fechaFin` date DEFAULT NULL,
  `fechaInicio` date DEFAULT NULL,
  `nombre` varchar(80) DEFAULT NULL,
  `nombrePatrocinador` varchar(80) DEFAULT NULL,
  `tipoPatrocinador` varchar(255) DEFAULT NULL,
  `nombrePDF` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idProyectoInvestigacionConjunto`),
  CONSTRAINT `fk_proyectoInveestigacion` FOREIGN KEY (`idProyectoInvestigacionConjunto`) REFERENCES `cuerpo_academico` (`idCuerpoAcademico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of proyecto_investigacionconjunto
-- ----------------------------

-- ----------------------------
-- Table structure for `tesis`
-- ----------------------------
DROP TABLE IF EXISTS `tesis`;
CREATE TABLE `tesis` (
  `idTesis` int(11) NOT NULL AUTO_INCREMENT,
  `grado` varchar(150) DEFAULT NULL,
  `IdProducto` int(11) DEFAULT NULL,
  `numRegistro` int(11) DEFAULT NULL,
  `clasificacionInter` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `numHojas` int(255) DEFAULT NULL,
  `usuarioDirigido` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idTesis`),
  KEY `fk_tesis_1` (`IdProducto`),
  CONSTRAINT `fk_tesis_1` FOREIGN KEY (`IdProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tesis
-- ----------------------------
INSERT INTO `tesis` VALUES ('1', '1', null, '123', '12', '2', null, 'Si');
INSERT INTO `tesis` VALUES ('2', '12', null, '1234', 'qwert', 'qweq', null, 'qwe');
