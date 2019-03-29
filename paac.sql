/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : paac

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-03-28 22:19:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `articulo`
-- ----------------------------
DROP TABLE IF EXISTS `articulo`;
CREATE TABLE `articulo` (
  `idArticulo` int(11) NOT NULL AUTO_INCREMENT,
  `autores` text,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of colaborador
-- ----------------------------

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
  PRIMARY KEY (`idGradoAcademico`),
  KEY `fk_gradoAcademico_2` (`idPais`),
  CONSTRAINT `fk_gradoAcademico_2` FOREIGN KEY (`idPais`) REFERENCES `pais` (`idPais`),
  CONSTRAINT `fk_idGradoAcademico` FOREIGN KEY (`idGradoAcademico`) REFERENCES `miembro` (`idMiembro`)
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
  `autores` varchar(255) DEFAULT NULL,
  `edicion` int(11) DEFAULT NULL,
  `editorial` varchar(255) DEFAULT NULL,
  `isbn` varchar(30) DEFAULT NULL,
  `numPaginas` int(11) DEFAULT NULL,
  `tiraje` int(11) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  PRIMARY KEY (`idLibro`),
  KEY `fk_libro_1` (`idProducto`),
  CONSTRAINT `fk_libro_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of libro
-- ----------------------------

-- ----------------------------
-- Table structure for `memoria`
-- ----------------------------
DROP TABLE IF EXISTS `memoria`;
CREATE TABLE `memoria` (
  `idMemoria` int(11) NOT NULL AUTO_INCREMENT,
  `ciudad` varchar(150) DEFAULT NULL,
  `estado` varchar(150) DEFAULT NULL,
  `fechaPublicacion` date DEFAULT NULL,
  `rangoPaginas` varchar(20) DEFAULT NULL,
  `idProducto` int(11) DEFAULT NULL,
  `nombreCongreso` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idMemoria`),
  KEY `fk_memoria_1` (`idProducto`),
  CONSTRAINT `fk_memoria_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of memoria
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of miembro
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pais
-- ----------------------------

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
  PRIMARY KEY (`idProducto`),
  KEY `fk_pais_1` (`idPais`),
  CONSTRAINT `fk_pais_1` FOREIGN KEY (`idPais`) REFERENCES `pais` (`idPais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of producto
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of producto_colaborador
-- ----------------------------

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
  `archivoPDF` blob,
  `autores` varchar(255) DEFAULT NULL,
  `caracteristicas` varchar(255) DEFAULT NULL,
  `institucionCreacion` varchar(150) DEFAULT NULL,
  `objetivo` varchar(255) DEFAULT NULL,
  `idProducto` int(11) NOT NULL,
  `nombrePDF` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idprototipo`),
  KEY `fk_prototipo_1` (`idProducto`),
  CONSTRAINT `fk_prototipo_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of prototipo
-- ----------------------------

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
  `no_hojas` int(11) DEFAULT NULL,
  `IdProducto` int(11) DEFAULT NULL,
  `numRegistro` int(11) DEFAULT NULL,
  `clasificacionInter` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `numHojas` int(255) DEFAULT NULL,
  `usuarioDirigido` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idTesis`),
  KEY `fk_tesis_1` (`IdProducto`),
  CONSTRAINT `fk_tesis_1` FOREIGN KEY (`IdProducto`) REFERENCES `producto` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tesis
-- ----------------------------
