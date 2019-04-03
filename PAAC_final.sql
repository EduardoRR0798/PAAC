CREATE TABLE `producto` (
`idProducto` int NOT NULL AUTO_INCREMENT,
`anio` int NULL,
`estado_actual` varchar(50) NULL,
`idPais` int NULL,
`proposito` varchar(255) NULL,
`titulo` varchar(255) NULL,
PRIMARY KEY (`idProducto`) 
);

CREATE TABLE `prototipo` (
`idprototipo` int NOT NULL AUTO_INCREMENT,
`archivoPDF` blob NULL,
`autores` varchar(255) NULL,
`caracteristicas` varchar(255) NULL,
`institucionCreacion` varchar(150) NULL,
`objetivo` varchar(255) NULL,
`idProducto` int NOT NULL,
PRIMARY KEY (`idprototipo`) 
);

CREATE TABLE `libro` (
`idLibro` int NOT NULL AUTO_INCREMENT,
`autores` varchar(255) NULL,
`edicion` int NULL,
`editorial` varchar(255) NULL,
`isbn` varchar(30) NULL,
`numPaginas` int NULL,
`tiraje` int NULL,
`idProducto` int NULL,
PRIMARY KEY (`idLibro`) 
);

CREATE TABLE `memoria` (
`idMemoria` int NOT NULL AUTO_INCREMENT,
`ciudad` varchar(150) NULL,
`estado` varchar(150) NULL,
`fechaPublicacion` date NULL,
`rangoPaginas` varchar(20) NULL,
`idProducto` int NULL,
`nombreCongreso` varchar(150) NULL,
PRIMARY KEY (`idMemoria`) 
);

CREATE TABLE `articulo` (
`idArticulo` int NOT NULL AUTO_INCREMENT,
`autores` text NULL,
`editorial` varchar(50) NULL,
`issn` varchar(50) NULL,
`nombre_revista` varchar(80) NULL,
`rango_paginas` varchar(20) NULL,
`volumen` varchar(50) NULL,
`idProducto` int NULL,
PRIMARY KEY (`idArticulo`) 
);

CREATE TABLE `pais` (
`idPais` int NOT NULL AUTO_INCREMENT,
`nombre` varchar(150) NULL,
PRIMARY KEY (`idPais`) 
);

CREATE TABLE `articulo_indexado` (
`idArticuloIndexado` int NOT NULL AUTO_INCREMENT,
`descripcion` varchar(255) NULL,
`direccionElectronica` varchar(255) NULL,
`indice` int NULL,
`idArticulo` int NULL,
PRIMARY KEY (`idArticuloIndexado`) 
);

CREATE TABLE `tesis` (
`idTesis` int NOT NULL AUTO_INCREMENT,
`grado` varchar(150) NULL,
`no_hojas` int NULL,
`IdProducto` int NULL,
`numRegistro` int NULL,
`clasificacionInter` varchar(255) NULL,
`descripcion` varchar(255) NULL,
`numHojas` int(255) NULL,
`usuarioDirigido` varchar(255) NULL,
PRIMARY KEY (`idTesis`) 
);

CREATE TABLE `patente` (
`idPatente` int NOT NULL AUTO_INCREMENT,
`clasif_intl_patentes` varchar(255) NULL,
`descripcion` varchar(255) NULL,
`evidencia` blob NULL,
`tipo` varchar(255) NULL,
`idProducto` int NULL,
PRIMARY KEY (`idPatente`) 
);

CREATE TABLE `proyecto` (
`idProyecto` int NOT NULL AUTO_INCREMENT,
`cantidadApoyo` int NULL,
`descripcion` varchar(255) NULL,
`fechaFin` date NULL,
`fechaInicio` date NULL,
`idLGACApoyo` int NULL,
`nombre` varchar(255) NULL,
`tipoApoyo` varchar(255) NULL,
PRIMARY KEY (`idProyecto`) 
);

CREATE TABLE `producto_proyecto` (
`idProductoProyecto` int NOT NULL AUTO_INCREMENT,
`idProducto` int NOT NULL,
`idProyecto` int NOT NULL,
PRIMARY KEY (`idProductoProyecto`, `idProducto`, `idProyecto`) 
);

CREATE TABLE `LGAC` (
`idlgac` int NOT NULL AUTO_INCREMENT,
`nombre` varchar(255) NULL,
PRIMARY KEY (`idlgac`) 
);

CREATE TABLE `cuerpo_academico` (
`idCuerpoAcademico` int NOT NULL AUTO_INCREMENT,
`clave` varchar(255) NULL,
`gradoConsolidacion` varchar(50) NULL,
`ies` varchar(255) NULL,
`nombre` varchar(150) NULL,
PRIMARY KEY (`idCuerpoAcademico`) 
);

CREATE TABLE `colaborador_cuerpoAcademico` (
`idColaborador_ca` int NOT NULL AUTO_INCREMENT,
`idColaborador` int NOT NULL,
`idCA` int NOT NULL,
PRIMARY KEY (`idColaborador_ca`, `idColaborador`, `idCA`) 
);

CREATE TABLE `colaborador` (
`idColaborador` int NOT NULL AUTO_INCREMENT,
`nombre` varchar(255) NULL,
PRIMARY KEY (`idColaborador`) 
);

CREATE TABLE `miembro` (
`idMiembro` int NOT NULL AUTO_INCREMENT,
`estado` varchar(255) NULL,
`pe_impacta` varchar(255) NULL,
`promep` varchar(50) NULL,
`sni` varchar(100) NULL,
`tipo` int NULL,
PRIMARY KEY (`idMiembro`) 
);

CREATE TABLE `gradoAcademico` (
`idGradoAcademico` int NOT NULL AUTO_INCREMENT,
`areaDisciplinar` varchar(255) NULL,
`fechaInicio` date NULL,
`fechatitulacion` date NULL,
`institucion` varchar(255) NULL,
`institucionNoConsiderada` varchar(255) NULL,
`nivel` varchar(255) NULL,
`idPais` int NOT NULL,
`tema` varchar(255) NULL,
PRIMARY KEY (`idGradoAcademico`) 
);

CREATE TABLE `capitulo_libro` (
`idCapitulo_libro` int NOT NULL AUTO_INCREMENT,
`editorial` varchar(255) NOT NULL,
`edicion` int NULL,
`isbn` varchar(30) NULL,
`nombre_libro` varchar(80) NULL,
`rango_paginas` varchar(20) NULL,
`tiraje` int NULL,
`titulo_libro` varchar(80) NULL,
`idProducto` int NULL,
PRIMARY KEY (`idCapitulo_libro`) 
);

CREATE TABLE `datos_laborales` (
`idDatosLaborales` int NOT NULL AUTO_INCREMENT,
`cronologia` varchar(255) NULL,
`dedicacion` varchar(255) NULL,
`dependencia` varchar(255) NULL,
`inicioContrato` date NULL,
`finContrato` date NULL,
`ies` varchar(255) NULL,
`nombramiento` varchar(80) NULL,
`tipo` varchar(255) NULL,
`unidadAcademica` varchar(255) NULL,
`idMiembro` int NULL,
PRIMARY KEY (`idDatosLaborales`) 
);

CREATE TABLE `PE` (
`idPE` int NOT NULL AUTO_INCREMENT,
`archivo` blob NULL,
`gradoIntervecion` varchar(255) NULL,
`fechaImplementacion` date NULL,
`nombrePE` varchar(255) NULL,
`PEAcreditado` varchar(255) NULL,
`PEDentroEGEL` varchar(255) NULL,
`resultadoParticipacion` varchar(255) NULL,
PRIMARY KEY (`idPE`) 
);

CREATE TABLE `participacion` (
`idParticipacion` int NOT NULL AUTO_INCREMENT,
`descripcionColaboracion` varchar(255) NULL,
`descripcionCooperacion` varchar(225) NULL,
`fechaFin` date NULL,
`fechaInicio` date NULL,
`grupo` varchar(255) NULL,
`objetivoGrupo` varchar(255) NULL,
`tipo` int NULL,
`idCA` int NULL,
PRIMARY KEY (`idParticipacion`) 
);

CREATE TABLE `cuerpo_academico_PROMEP` (
`idPROMEP` int NOT NULL AUTO_INCREMENT,
`DES` varchar(80) NOT NULL,
`nombre` varchar(80) NOT NULL,
`idParticipacion` int NOT NULL,
PRIMARY KEY (`idPROMEP`) 
);

CREATE TABLE `cuerpoAcademicoExterno` (
`idCuerpoeExterno` int NOT NULL AUTO_INCREMENT,
`IES` varchar(255) NULL,
`nombre` varchar(80) NULL,
`idParticipacion` int NULL,
`idPais` int NULL,
PRIMARY KEY (`idCuerpoeExterno`) 
);

CREATE TABLE `proyecto_investigacionConjunto` (
`idProyectoInvestigacionConjunto` int NOT NULL AUTO_INCREMENT,
`actividades` varchar(255) NULL,
`archivo_pdf` blob NULL,
`fechaFin` date NULL,
`fechaInicio` date NULL,
`nombre` varchar(80) NULL,
`nombrePatrocinador` varchar(80) NULL,
`tipoPatrocinador` varchar(255) NULL,
PRIMARY KEY (`idProyectoInvestigacionConjunto`) 
);

CREATE TABLE `producto_colaborador` (
`idProductoColaborador` int NOT NULL AUTO_INCREMENT,
`idProducto` int NOT NULL,
`idColaborador` int NOT NULL,
PRIMARY KEY (`idProductoColaborador`, `idProducto`, `idColaborador`) 
);

CREATE TABLE `miembro_LGAC` (
`idMiembroLGAC` int NOT NULL AUTO_INCREMENT,
`idMiembro` int NOT NULL,
`idLGAC` int NOT NULL,
PRIMARY KEY (`idMiembroLGAC`, `idMiembro`, `idLGAC`) 
);


ALTER TABLE `prototipo` ADD CONSTRAINT `fk_prototipo_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);
ALTER TABLE `libro` ADD CONSTRAINT `fk_libro_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);
ALTER TABLE `memoria` ADD CONSTRAINT `fk_memoria_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);
ALTER TABLE `articulo` ADD CONSTRAINT `fk_articulo_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);
ALTER TABLE `producto` ADD CONSTRAINT `fk_pais_1` FOREIGN KEY (`idPais`) REFERENCES `pais` (`idPais`);
ALTER TABLE `articulo_indexado` ADD CONSTRAINT `fk_articuloIndexado_1` FOREIGN KEY (`idArticulo`) REFERENCES `articulo` (`idArticulo`);
ALTER TABLE `tesis` ADD CONSTRAINT `fk_tesis_1` FOREIGN KEY (`IdProducto`) REFERENCES `producto` (`idProducto`);
ALTER TABLE `patente` ADD CONSTRAINT `fk_patente_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);
ALTER TABLE `producto_proyecto` ADD CONSTRAINT `fk_producto_proyecto_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);
ALTER TABLE `producto_proyecto` ADD CONSTRAINT `fk_proyecto_producto_2` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`);
ALTER TABLE `proyecto` ADD CONSTRAINT `fk_proyecto_1` FOREIGN KEY (`idLGACApoyo`) REFERENCES `LGAC` (`idlgac`);
ALTER TABLE `colaborador_cuerpoAcademico` ADD CONSTRAINT `fk_colaborador_cuerpoAcademico_1` FOREIGN KEY (`idColaborador`) REFERENCES `colaborador` (`idColaborador`);
ALTER TABLE `colaborador_cuerpoAcademico` ADD CONSTRAINT `fk_colaborador_cuerpoAcademico_2` FOREIGN KEY (`idCA`) REFERENCES `cuerpo_academico` (`idCuerpoAcademico`);
ALTER TABLE `capitulo_libro` ADD CONSTRAINT `fk_capitulo_kibro` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);
ALTER TABLE `cuerpo_academico_PROMEP` ADD CONSTRAINT `fk-CuerpoAcademicoPRODEMP` FOREIGN KEY (`idParticipacion`) REFERENCES `participacion` (`idParticipacion`);
ALTER TABLE `cuerpoAcademicoExterno` ADD CONSTRAINT `fk_cuerpoAcademicoExterno` FOREIGN KEY (`idPais`) REFERENCES `pais` (`idPais`);
ALTER TABLE `proyecto_investigacionConjunto` ADD CONSTRAINT `fk_proyectoInveestigacion` FOREIGN KEY (`idProyectoInvestigacionConjunto`) REFERENCES `cuerpo_academico` (`idCuerpoAcademico`);
ALTER TABLE `PE` ADD CONSTRAINT `fk_idPE` FOREIGN KEY (`idPE`) REFERENCES `cuerpo_academico` (`idCuerpoAcademico`);
ALTER TABLE `datos_laborales` ADD CONSTRAINT `fk_idDatosLaborales` FOREIGN KEY (`idMiembro`) REFERENCES `miembro` (`idMiembro`);
ALTER TABLE `gradoAcademico` ADD CONSTRAINT `fk_idGradoAcademico` FOREIGN KEY (`idGradoAcademico`) REFERENCES `miembro` (`idMiembro`);
ALTER TABLE `producto_colaborador` ADD CONSTRAINT `fk_producto_colaborador_1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);
ALTER TABLE `producto_colaborador` ADD CONSTRAINT `fk_producto_colaborador_2` FOREIGN KEY (`idColaborador`) REFERENCES `colaborador` (`idColaborador`);
ALTER TABLE `gradoAcademico` ADD CONSTRAINT `fk_gradoAcademico_2` FOREIGN KEY (`idPais`) REFERENCES `pais` (`idPais`);
ALTER TABLE `miembro_LGAC` ADD CONSTRAINT `fk_miembro_LGAC_1` FOREIGN KEY (`idMiembro`) REFERENCES `miembro` (`idMiembro`);
ALTER TABLE `miembro_LGAC` ADD CONSTRAINT `fk_miembro_LGAC_2` FOREIGN KEY (`idLGAC`) REFERENCES `LGAC` (`idlgac`);
ALTER TABLE `cuerpoAcademicoExterno` ADD CONSTRAINT `fk_cuerpoAcademicoExterno_2` FOREIGN KEY (`idParticipacion`) REFERENCES `participacion` (`idParticipacion`);
ALTER TABLE `participacion` ADD CONSTRAINT `fk_participacion` FOREIGN KEY (`idCA`) REFERENCES `cuerpo_academico` (`idCuerpoAcademico`);

