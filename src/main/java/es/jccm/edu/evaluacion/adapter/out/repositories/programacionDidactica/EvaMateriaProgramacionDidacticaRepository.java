package es.jccm.edu.evaluacion.adapter.out.repositories.programacionDidactica;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.programacionAula.projection.NivelCurricularProgramacionAulaProjection;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.EvaMateriaProgramacionDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.QEvaMateriaProgramacionDidactica;
import es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection.EvaMateriaProgramacionDidacticaProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaMateriaProgramacionDidacticaRepository
		extends AbstractRepository<EvaMateriaProgramacionDidactica, Long, QEvaMateriaProgramacionDidactica> {

	@Query(value = "SELECT DISTINCT  *  " +
			"FROM (select matofe.x_materiaomg idMateriaOmg, mc.d_materiac || ' - ' || mc.T_ABREV materia, ofc.x_ofertamatric idcurso, ofc.x_ofertamatrig idOfertaMatrig,    " +
			"matofec.X_DEPARTCEN idDepartamento , " +
			"(SELECT depden.D_DEPARTAMENTO FROM TLDEPDENGEN depden, tldepartcen dep  WHERE depden.X_DEPDENGEN = dep.X_DEPDENGEN AND dep.X_DEPARTCEN = matofec.X_DEPARTCEN AND dep.X_CENTRO = c.x_centro  ) nombreDepartamento, " +
			"(select ofg.d_ofertamatrig from tlofematrgen ofg where ofg.x_ofertamatrig=ofc.x_ofertamatrig) curso, mc.T_ABREV abreviatura, null abreviaturaAcnee,  " +
			"ciclo.x_etapa, ciclo.d_etapa ciclo_etapa, NULL nivelCurricular, NULL idNivelCurricular,  " +
			"decode((select DISTINCT pd.lg_cerrada from eva_progdidac pd where pd.x_materiaomg=matofe.x_materiaomg and pd.x_CENTRO= OFC.X_CENTRO AND PD.NU_ANNO = :anyo AND PD.X_NIVEADAP IS NULL),null,'No iniciada',0,'Iniciada',1,'Cerrada') Estado,  " +
			"(SELECT prdi.ID_PROGDIDAC FROM EVA_PROGDIDAC prdi WHERE prdi.X_MATERIAOMG = matofe.X_MATERIAOMG AND prdi.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG AND prdi.X_CENTRO = ofc.X_CENTRO AND prdi.NU_ANNO = :anyo AND prdi.X_NIVEADAP IS NULL) idProgramacionDidactica,  " +
			"(SELECT COUNT(*) FROM EVA_RELPROGUNIDAD WHERE ID_PROGDIDAC = (SELECT prdi.id_progdidac FROM eva_progdidac prdi WHERE prdi.x_materiaomg = matofe.x_materiaomg AND prdi.x_ofertamatrig = ofc.x_ofertamatrig AND prdi.x_centro = ofc.x_centro AND prdi.nu_anno = :anyo AND prdi.X_NIVEADAP IS NULL)) AS countUnidadesProgramacion,  " +
			"(SELECT LISTAGG(uniprog.TX_NOMBRE , ', ' ON OVERFLOW TRUNCATE '...') WITHIN GROUP (ORDER BY uniprog.NU_ORDENPRES, uniprog.TX_ABREV, uniprog.TX_NOMBRE) FROM EVA_RELPROGUNIDAD rel, EVA_UNIDADPROG uniprog  WHERE rel.ID_UNIDADPROG = uniprog.ID_UNIDADPROG  AND rel.ID_PROGDIDAC = (SELECT prdi.id_progdidac FROM eva_progdidac prdi WHERE prdi.x_materiaomg = matofe.x_materiaomg AND prdi.x_ofertamatrig = ofc.x_ofertamatrig AND prdi.x_centro = ofc.x_centro AND prdi.nu_anno = :anyo AND prdi.X_NIVEADAP IS NULL)) AS nombresUnidadesProgramacion,  " +
			"(SELECT usu.X_EMPLEADO FROM DELPHOS_SEGEDU.TLUSUARIOS usu WHERE usu.usuario = TO_CHAR((SELECT prdi.C_USUCREACION FROM eva_progdidac prdi WHERE prdi.x_materiaomg = matofe.x_materiaomg AND prdi.x_ofertamatrig = ofc.x_ofertamatrig AND prdi.x_centro = ofc.x_centro AND prdi.nu_anno = :anyo AND prdi.X_NIVEADAP IS NULL))) AS idEmpleado,  " +
			"(SELECT e.nombre || ' ' || e.apellido1 || ' ' || e.apellido2  from tlempleados e WHERE X_EMPLEADO = (SELECT usu.X_EMPLEADO FROM DELPHOS_SEGEDU.TLUSUARIOS usu WHERE usu.usuario = TO_CHAR((SELECT prdi.C_USUCREACION FROM eva_progdidac prdi WHERE prdi.x_materiaomg = matofe.x_materiaomg AND prdi.x_ofertamatrig = ofc.x_ofertamatrig AND prdi.x_centro = ofc.x_centro AND prdi.nu_anno = :anyo AND prdi.X_NIVEADAP IS NULL)))) AS nombreEmpleado,  " +
			"(SELECT eepd.X_EMPLEADO FROM EVA_EMPEDITPROGDID eepd WHERE eepd.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG AND eepd.X_MATERIAOMG = matofe.X_MATERIAOMG AND eepd.X_CENTRO = c.X_CENTRO AND eepd.NU_ANNO = DELPHOS.TLF_CURSO_ACADEMICO_CUADERNO_EVALUACION() AND eepd.LG_ACTIVO = 1 AND eepd.X_NIVEADAP IS NULL) idEmpleadoResponsableActual, " +
			"(select ID_DOCHIS_RODAL FROM (select adj.ID_DOCHIS_RODAL from dgc_documentos doc, dgc_documento_historial his, dgc_historial_adjuntos adj, eva_progdidac eva  " +
			"where eva.id_progdidac = (SELECT prdi.ID_PROGDIDAC FROM EVA_PROGDIDAC prdi WHERE prdi.X_MATERIAOMG = matofe.X_MATERIAOMG AND prdi.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG  " +
			"AND prdi.X_CENTRO = ofc.X_CENTRO AND prdi.NU_ANNO = :anyo AND prdi.X_NIVEADAP IS NULL) AND doc.id_progdidac = eva.id_progdidac AND his.id_documento = doc.id_documento  " +
			"AND adj.id_historial = his.id_historial order by his.fh_registro desc) where rownum = 1) idRodal,  " +
			"(select TX_DOCHIS_FICHERO FROM (select adj.TX_DOCHIS_FICHERO from dgc_documentos doc, dgc_documento_historial his, dgc_historial_adjuntos adj, eva_progdidac eva  " +
			"where eva.id_progdidac = (SELECT prdi.ID_PROGDIDAC FROM EVA_PROGDIDAC prdi WHERE prdi.X_MATERIAOMG = matofe.X_MATERIAOMG AND prdi.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG  " +
			"AND prdi.X_CENTRO = ofc.X_CENTRO AND prdi.NU_ANNO = :anyo AND prdi.X_NIVEADAP IS NULL) AND doc.id_progdidac = eva.id_progdidac AND his.id_documento = doc.id_documento  " +
			"AND adj.id_historial = his.id_historial order by his.fh_registro desc) where rownum = 1) nombreFichero  " +
			"from tlperiodosomc pomc, tlofematrcen ofc, tlmatofematrcen matofec, tlmatofematrg matofe,  tlmateriascurso mc, tlcursoorg cuo, tlcursomoda curModa, tletapas e,  " +
			"tletapas ciclo, tlcentros c " +
			"where ofc.x_ofertamatric=pomc.x_ofertamatric  " +
			"and (pomc.c_annopuedeterminar is null or pomc.c_annopuedeterminar > :anyo)  " +
			"and matofec.x_ofertamatric=ofc.x_ofertamatric  " +
			"and matofec.x_centro=ofc.x_centro  " +
			" AND (:estado = '-1' OR decode((select pd.lg_cerrada from eva_progdidac pd where pd.x_materiaomg=matofe.x_materiaomg AND  " +
			" pd.x_CENTRO= OFC.X_CENTRO AND PD.NU_ANNO = :anyo AND PD.X_NIVEADAP IS NULL),null,'No iniciada',0,'Iniciada',1,'Cerrada') = :estado )  " +
			"and matofe.x_materiaomg=matofec.x_materiaomg  " +
			"and exists (select 1 from tlrelcompesmat rcm where rcm.x_materiaomg=matofec.x_materiaomg)  " +
			"and c.x_centro = ofc.x_centro  " +
			"and c.c_codigo = :codigoCentro  " +
			"and mc.x_materiac=matofe.x_materiac  " +
			"AND (:editor = 0 OR (SELECT count(*) FROM EVA_EMPEDITPROGDID editor  " +
			"WHERE EDITOR.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG  " +
			"AND EDITOR.X_MATERIAOMG = MATOFE.X_MATERIAOMG  " +
			"AND editor.X_CENTRO = OFC.X_CENTRO  " +
			"AND editor.NU_ANNO = :anyo  " +
			"AND editor.LG_ACTIVO = 1) = 0)  " +
			"and curModa.x_Etapa=e.x_etapa  " +
			"and cuo.x_cursomod=curModa.x_Cursomod  " +
			"and ciclo.x_etapa=e.x_etapadependede  " +
			"and cuo.x_ofertamatrig=ofc.x_ofertamatrig " +
			"AND ('-1' = :idCurso OR ofc.X_OFERTAMATRIG = :idCurso)  " +
			"AND ('-1' = :idMateria OR matofe.X_MATERIAOMG = :idMateria)  " +
			"UNION ALL    " +
			"select matofe.x_materiaomg idMateriaOmg, mc.d_materiac || ' - ' || mc.T_ABREV materia, ofc.x_ofertamatric idcurso, ofc.x_ofertamatrig idOfertaMatrig,  " +
			"matofec.X_DEPARTCEN idDepartamento , " +
			"(SELECT depden.D_DEPARTAMENTO FROM TLDEPDENGEN depden, tldepartcen dep  WHERE depden.X_DEPDENGEN = dep.X_DEPDENGEN AND dep.X_DEPARTCEN = matofec.X_DEPARTCEN  AND dep.X_CENTRO = c.x_centro  ) nombreDepartamento, " +
			"(select ofg.d_ofertamatrig from tlofematrgen ofg where ofg.x_ofertamatrig=ofc.x_ofertamatrig) curso, mc.T_ABREV abreviatura,   " +
			"(SELECT mc.T_ABREV FROM tlmateriascurso mc WHERE X_MATERIAC = (SELECT mat.X_MATERIAC FROM EVA_PROGDIDAC prdi INNER JOIN TLMATOFEMATRG mat ON mat.X_MATERIAOMG = prdi.X_MATERIAOMGADAP WHERE prdi.X_MATERIAOMG = matofe.X_MATERIAOMG AND prdi.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG AND prdi.X_CENTRO = ofc.X_CENTRO AND prdi.NU_ANNO = :anyo AND prdi.X_NIVEADAP = PROGDID.X_NIVEADAP)) abreviaturaAcnee,  " +
			"ciclo.x_etapa, ciclo.d_etapa ciclo_etapa, (select ofg2.d_ofertamatrig from tlofematrgen ofg2 where ofg2.x_ofertamatrig = PROGDID.X_NIVEADAP) nivelCurricular, PROGDID.X_NIVEADAP idNivelCurricular,  " +
			"decode((select DISTINCT pd.lg_cerrada from eva_progdidac pd where pd.x_materiaomg=matofe.x_materiaomg and pd.x_CENTRO= OFC.X_CENTRO AND PD.NU_ANNO = :anyo AND pd.X_NIVEADAP = PROGDID.X_NIVEADAP),null,'No iniciada',0,'Iniciada',1,'Cerrada') Estado,  " +
			"(SELECT prdi.ID_PROGDIDAC FROM EVA_PROGDIDAC prdi WHERE prdi.X_MATERIAOMG = matofe.X_MATERIAOMG AND prdi.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG AND prdi.X_CENTRO = ofc.X_CENTRO AND prdi.NU_ANNO = :anyo AND prdi.X_NIVEADAP = PROGDID.X_NIVEADAP) idProgramacionDidactica,  " +
			"(SELECT COUNT(*) FROM EVA_RELPROGUNIDAD WHERE ID_PROGDIDAC = (SELECT prdi.id_progdidac FROM eva_progdidac prdi WHERE prdi.x_materiaomg = matofe.x_materiaomg AND prdi.x_ofertamatrig = ofc.x_ofertamatrig AND prdi.x_centro = ofc.x_centro AND prdi.nu_anno = :anyo AND prdi.X_NIVEADAP = PROGDID.X_NIVEADAP)) AS countUnidadesProgramacion,  " +
			"(SELECT LISTAGG(uniprog.TX_NOMBRE , ', ' ON OVERFLOW TRUNCATE '...') WITHIN GROUP (ORDER BY uniprog.NU_ORDENPRES, uniprog.TX_ABREV, uniprog.TX_NOMBRE) FROM EVA_RELPROGUNIDAD rel, EVA_UNIDADPROG uniprog  WHERE rel.ID_UNIDADPROG = uniprog.ID_UNIDADPROG  AND rel.ID_PROGDIDAC = (SELECT prdi.id_progdidac FROM eva_progdidac prdi WHERE prdi.x_materiaomg = matofe.x_materiaomg AND prdi.x_ofertamatrig = ofc.x_ofertamatrig AND prdi.x_centro = ofc.x_centro AND prdi.nu_anno = :anyo AND prdi.X_NIVEADAP = PROGDID.X_NIVEADAP)) AS nombresUnidadesProgramacion,  " +
			"(SELECT usu.X_EMPLEADO FROM DELPHOS_SEGEDU.TLUSUARIOS usu WHERE usu.usuario = TO_CHAR((SELECT prdi.C_USUCREACION FROM eva_progdidac prdi WHERE prdi.x_materiaomg = matofe.x_materiaomg AND prdi.x_ofertamatrig = ofc.x_ofertamatrig AND prdi.x_centro = ofc.x_centro AND prdi.nu_anno = :anyo AND prdi.X_NIVEADAP = PROGDID.X_NIVEADAP))) AS idEmpleado,  " +
			"(SELECT e.nombre || ' ' || e.apellido1 || ' ' || e.apellido2  from tlempleados e WHERE X_EMPLEADO = (SELECT usu.X_EMPLEADO FROM DELPHOS_SEGEDU.TLUSUARIOS usu WHERE usu.usuario = TO_CHAR((SELECT prdi.C_USUCREACION FROM eva_progdidac prdi WHERE prdi.x_materiaomg = matofe.x_materiaomg AND prdi.x_ofertamatrig = ofc.x_ofertamatrig AND prdi.x_centro = ofc.x_centro AND prdi.nu_anno = :anyo AND prdi.X_NIVEADAP = PROGDID.X_NIVEADAP)))) AS nombreEmpleado,  " +
			"(SELECT eepd.X_EMPLEADO FROM EVA_EMPEDITPROGDID eepd WHERE eepd.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG AND eepd.X_MATERIAOMG = matofe.X_MATERIAOMG AND eepd.X_CENTRO = c.X_CENTRO AND eepd.NU_ANNO = DELPHOS.TLF_CURSO_ACADEMICO_CUADERNO_EVALUACION() AND eepd.LG_ACTIVO = 1 AND eepd.X_NIVEADAP = PROGDID.X_NIVEADAP) idEmpleadoResponsableActual, " +
			"(select ID_DOCHIS_RODAL FROM (select adj.ID_DOCHIS_RODAL from dgc_documentos doc, dgc_documento_historial his, dgc_historial_adjuntos adj, eva_progdidac eva  " +
			"where eva.id_progdidac = (SELECT prdi.ID_PROGDIDAC FROM EVA_PROGDIDAC prdi WHERE prdi.X_MATERIAOMG = matofe.X_MATERIAOMG AND prdi.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG  " +
			"AND prdi.X_CENTRO = ofc.X_CENTRO AND prdi.NU_ANNO = :anyo AND prdi.X_NIVEADAP = PROGDID.X_NIVEADAP) AND doc.id_progdidac = eva.id_progdidac AND his.id_documento = doc.id_documento  " +
			"AND adj.id_historial = his.id_historial order by his.fh_registro desc) where rownum = 1) idRodal,  " +
			"(select TX_DOCHIS_FICHERO FROM (select adj.TX_DOCHIS_FICHERO from dgc_documentos doc, dgc_documento_historial his, dgc_historial_adjuntos adj, eva_progdidac eva  " +
			"where eva.id_progdidac = (SELECT prdi.ID_PROGDIDAC FROM EVA_PROGDIDAC prdi WHERE prdi.X_MATERIAOMG = matofe.X_MATERIAOMG AND prdi.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG  " +
			"AND prdi.X_CENTRO = ofc.X_CENTRO AND prdi.NU_ANNO = :anyo AND prdi.X_NIVEADAP = PROGDID.X_NIVEADAP) AND doc.id_progdidac = eva.id_progdidac AND his.id_documento = doc.id_documento  " +
			"AND adj.id_historial = his.id_historial order by his.fh_registro desc) where rownum = 1) nombreFichero  " +
			"from tlperiodosomc pomc, tlofematrcen ofc, tlmatofematrcen matofec, tlmatofematrg matofe,  tlmateriascurso mc, tlcursoorg cuo, tlcursomoda curModa, tletapas e,  " +
			"tletapas ciclo, tlcentros c, EVA_PROGDIDAC PROGDID  " +
			"where ofc.x_ofertamatric=pomc.x_ofertamatric  " +
			"and (pomc.c_annopuedeterminar is null or pomc.c_annopuedeterminar > :anyo)  " +
			"and matofec.x_ofertamatric=ofc.x_ofertamatric  " +
			"and matofec.x_centro=ofc.x_centro  " +
			"AND (:estado = '-1' OR decode((select pd.lg_cerrada from eva_progdidac pd where pd.x_materiaomg=matofe.x_materiaomg and  " +
			"pd.x_CENTRO= OFC.X_CENTRO AND PD.NU_ANNO = :anyo AND pd.X_NIVEADAP = PROGDID.X_NIVEADAP),null,'No iniciada',0,'Iniciada',1,'Cerrada') = :estado)  " +
			"and matofe.x_materiaomg=matofec.x_materiaomg  " +
			"and exists (select 1 from tlrelcompesmat rcm where rcm.x_materiaomg=matofec.x_materiaomg)  " +
			"and c.x_centro = ofc.x_centro  " +
			"and c.c_codigo = :codigoCentro  " +
			"and mc.x_materiac=matofe.x_materiac  " +
			"AND (:editor = 0 OR (SELECT count(*) FROM EVA_EMPEDITPROGDID editor  " +
			"WHERE EDITOR.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG  " +
			"AND EDITOR.X_MATERIAOMG = MATOFE.X_MATERIAOMG  " +
			"AND editor.X_CENTRO = OFC.X_CENTRO  " +
			"AND editor.NU_ANNO = :anyo  " +
			"AND editor.LG_ACTIVO = 1) = 0)  " +
			"and curModa.x_Etapa=e.x_etapa  " +
			"and cuo.x_cursomod=curModa.x_Cursomod  " +
			"and ciclo.x_etapa=e.x_etapadependede  " +
			"and cuo.x_ofertamatrig=ofc.x_ofertamatrig    " +
			"AND PROGDID.X_MATERIAOMG = matofe.X_MATERIAOMG    " +
			"AND PROGDID.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG    " +
			"AND PROGDID.X_CENTRO = ofc.X_CENTRO    " +
			"AND PROGDID.NU_ANNO = :anyo    " +
			"AND PROGDID.X_NIVEADAP = PROGDID.X_NIVEADAP " +
			"AND ('-1' = :idCurso OR ofc.X_OFERTAMATRIG = :idCurso)  " +
			"AND ('-1' = :idMateria OR matofe.X_MATERIAOMG = :idMateria))  " +
			"order by idOfertaMatrig, materia, idProgramacionDidactica",
			countQuery = "SELECT SUM(total_count) AS total " +
					"FROM (select count(*) total_count " +
					" from tlperiodosomc pomc, tlofematrcen ofc, tlmatofematrcen matofec, tlmatofematrg matofe,  tlmateriascurso mc, tlcursoorg cuo, tlcursomoda curModa, tletapas e,  " +
					" tletapas ciclo, tlcentros c " +
					" where ofc.x_ofertamatric=pomc.x_ofertamatric  " +
					" and (pomc.c_annopuedeterminar is null or pomc.c_annopuedeterminar > :anyo)  " +
					" and matofec.x_ofertamatric=ofc.x_ofertamatric  " +
					" and matofec.x_centro=ofc.x_centro  " +
					" AND (:estado = '-1' OR decode((select pd.lg_cerrada from eva_progdidac pd where pd.x_materiaomg=matofe.x_materiaomg AND " +
					" pd.x_CENTRO= OFC.X_CENTRO AND PD.NU_ANNO = :anyo AND PD.X_NIVEADAP IS NULL),null,'No iniciada',0,'Iniciada',1,'Cerrada') = :estado ) " +
					" and matofe.x_materiaomg=matofec.x_materiaomg  " +
					" and exists (select 1 from tlrelcompesmat rcm where rcm.x_materiaomg=matofec.x_materiaomg)  " +
					" and c.x_centro = ofc.x_centro  " +
					" and c.c_codigo = :codigoCentro  " +
					" and mc.x_materiac=matofe.x_materiac  " +
					" AND (:editor = 0 OR (SELECT count(*) FROM EVA_EMPEDITPROGDID editor " +
					" WHERE EDITOR.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG " +
					" AND EDITOR.X_MATERIAOMG = MATOFE.X_MATERIAOMG " +
					" AND editor.X_CENTRO = OFC.X_CENTRO " +
					" AND editor.NU_ANNO = :anyo " +
					" AND editor.LG_ACTIVO = 1) = 0) " +
					" and curModa.x_Etapa=e.x_etapa  " +
					" and cuo.x_cursomod=curModa.x_Cursomod  " +
					" and ciclo.x_etapa=e.x_etapadependede  " +
					" and cuo.x_ofertamatrig=ofc.x_ofertamatrig  " +
					" AND ('-1' = :idCurso OR ofc.X_OFERTAMATRIG = :idCurso)  " +
					" AND ('-1' = :idMateria OR matofe.X_MATERIAOMG = :idMateria) " +
					"UNION ALL  " +
					"select count(*) total_count " +
					"from tlperiodosomc pomc, tlofematrcen ofc, tlmatofematrcen matofec, tlmatofematrg matofe,  tlmateriascurso mc, tlcursoorg cuo, tlcursomoda curModa, tletapas e,  " +
					" tletapas ciclo, tlcentros c, EVA_PROGDIDAC PROGDID   " +
					" where ofc.x_ofertamatric=pomc.x_ofertamatric  " +
					" and (pomc.c_annopuedeterminar is null or pomc.c_annopuedeterminar > :anyo)  " +
					" and matofec.x_ofertamatric=ofc.x_ofertamatric  " +
					" and matofec.x_centro=ofc.x_centro  " +
					" AND (:estado = '-1' OR decode((select pd.lg_cerrada from eva_progdidac pd where pd.x_materiaomg=matofe.x_materiaomg and " +
					" pd.x_CENTRO= OFC.X_CENTRO AND PD.NU_ANNO = :anyo AND pd.X_NIVEADAP = PROGDID.X_NIVEADAP),null,'No iniciada',0,'Iniciada',1,'Cerrada') = :estado) " +
					" and matofe.x_materiaomg=matofec.x_materiaomg  " +
					" and exists (select 1 from tlrelcompesmat rcm where rcm.x_materiaomg=matofec.x_materiaomg)  " +
					" and c.x_centro = ofc.x_centro  " +
					" and c.c_codigo = :codigoCentro  " +
					" and mc.x_materiac=matofe.x_materiac  " +
					" AND (:editor = 0 OR (SELECT count(*) FROM EVA_EMPEDITPROGDID editor " +
					" WHERE EDITOR.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG " +
					" AND EDITOR.X_MATERIAOMG = MATOFE.X_MATERIAOMG " +
					" AND editor.X_CENTRO = OFC.X_CENTRO " +
					" AND editor.NU_ANNO = :anyo " +
					" AND editor.LG_ACTIVO = 1) = 0) " +
					" and curModa.x_Etapa=e.x_etapa  " +
					" and cuo.x_cursomod=curModa.x_Cursomod  " +
					" and ciclo.x_etapa=e.x_etapadependede  " +
					" and cuo.x_ofertamatrig=ofc.x_ofertamatrig  " +
					" AND PROGDID.X_MATERIAOMG = matofe.X_MATERIAOMG " +
					" AND PROGDID.X_OFERTAMATRIG = ofc.X_OFERTAMATRIG " +
					" AND PROGDID.X_CENTRO = ofc.X_CENTRO " +
					" AND PROGDID.NU_ANNO = :anyo " +
					" AND PROGDID.X_NIVEADAP = PROGDID.X_NIVEADAP " +
					" AND ('-1' = :idCurso OR ofc.X_OFERTAMATRIG = :idCurso)  " +
					" AND ('-1' = :idMateria OR matofe.X_MATERIAOMG = :idMateria))", nativeQuery = true)
	Page<EvaMateriaProgramacionDidacticaProjection> findAllByCentroAnyoCursoAndMateria(@Param("codigoCentro") Long codigoCentro,
																					@Param("anyo") Integer anyo,
																					@Param("idCurso") Long idCurso,
																					@Param("idMateria") Long idMateria,
																				    @Param("editor") Long editor,
																					@Param("estado") String estado,
																					Pageable pageable);

	@Query(value = "SELECT DISTINCT didac.ID_PROGDIDAC idDidactica, PROGDIDPOND.X_PONDERACION idPonderacion, didac.X_OFERTAMATRIG idOferMatrigNivelCurricular, " +
			"ofer.S_OFERTAMATRIG descripcionNivelCurricular, 0 acneae, MATERIASCURSO.T_ABREV abrev  " +
			" FROM EVA_PROGDIDAC didac " +
			" INNER JOIN TLOFEMATRGEN ofer ON DIDAC.X_OFERTAMATRIG = ofer.X_OFERTAMATRIG " +
			" INNER JOIN EVA_RELPROGDIDPOND PROGDIDPOND ON PROGDIDPOND.ID_PROGDIDAC = DIDAC.ID_PROGDIDAC " +
			"  INNER JOIN TLMATOFEMATRG OFEMATRG ON OFEMATRG.X_MATERIAOMG = DIDAC.X_MATERIAOMG " +
			" INNER JOIN TLMATERIASCURSO MATERIASCURSO ON MATERIASCURSO.X_MATERIAC = OFEMATRG.X_MATERIAC " +
			" WHERE didac.X_OFERTAMATRIG  = :idOfermatrig " +
			" AND didac.X_MATERIAOMG = :idMateriaOmg " +
			" AND DIDAC.X_CENTRO = :idCentro " +
			" AND didac.LG_CERRADA = 1 " +
			" AND DIDAC.NU_ANNO = :anno " +
			" AND didac.X_NIVEADAP IS null " +
			"UNION ALL " +
			"SELECT DISTINCT didac.ID_PROGDIDAC idDidactica, PROGDIDPOND.X_PONDERACION idPonderacion, didac.X_NIVEADAP idOferMatrigNivelCurricular, " +
			"ofer.S_OFERTAMATRIG descripcionNivelCurricular, 1 acneae, MATERIASCURSO.T_ABREV abrev  " +
			" FROM EVA_PROGDIDAC didac " +
			" INNER JOIN TLOFEMATRGEN ofer ON DIDAC.X_NIVEADAP = ofer.X_OFERTAMATRIG   " +
			" INNER JOIN EVA_RELPROGDIDPOND PROGDIDPOND ON PROGDIDPOND.ID_PROGDIDAC = DIDAC.ID_PROGDIDAC " +
			" INNER JOIN TLMATOFEMATRG OFEMATRG ON OFEMATRG.X_MATERIAOMG = DIDAC.X_MATERIAOMGADAP  " +
			" INNER JOIN TLMATERIASCURSO MATERIASCURSO ON MATERIASCURSO.X_MATERIAC = OFEMATRG.X_MATERIAC " +
			" WHERE didac.X_OFERTAMATRIG  = :idOfermatrig     " +
			" AND didac.X_MATERIAOMG = :idMateriaOmg     " +
			" AND DIDAC.X_CENTRO = :idCentro " +
			" AND didac.LG_CERRADA = 1 " +
			" AND DIDAC.NU_ANNO = :anno " +
			" AND didac.X_NIVEADAP IS NOT NULL", nativeQuery = true)
	List<NivelCurricularProgramacionAulaProjection> getNivelCurricular(@Param("anno") Integer anno,
																	   @Param("idMateriaOmg") Long idMateriaOmg,
																	   @Param("idCentro") Long idCentro,
																	   @Param("idOfermatrig") Long idOfermatrig);

	@Query(value = "SELECT DISTINCT didac.ID_PROGDIDAC idDidactica, PROGDIDPOND.X_PONDERACION idPonderacion, didac.X_OFERTAMATRIG idOferMatrigNivelCurricular, " +
			"ofer.S_OFERTAMATRIG descripcionNivelCurricular, 0 acneae, MATERIASCURSO.T_ABREV abrev  " +
			" FROM EVA_PROGDIDAC didac " +
			" INNER JOIN TLOFEMATRGEN ofer ON DIDAC.X_OFERTAMATRIG = ofer.X_OFERTAMATRIG " +
			" INNER JOIN EVA_RELPROGDIDPOND PROGDIDPOND ON PROGDIDPOND.ID_PROGDIDAC = DIDAC.ID_PROGDIDAC " +
			"  INNER JOIN TLMATOFEMATRG OFEMATRG ON OFEMATRG.X_MATERIAOMG = DIDAC.X_MATERIAOMG " +
			" INNER JOIN TLMATERIASCURSO MATERIASCURSO ON MATERIASCURSO.X_MATERIAC = OFEMATRG.X_MATERIAC " +
			" WHERE didac.X_OFERTAMATRIG  = :idOfermatrig " +
			" AND didac.X_MATERIAOMG = :idMateriaOmg " +
			" AND DIDAC.X_CENTRO = :idCentro " +
			" AND didac.LG_CERRADA = 1 " +
			" AND DIDAC.NU_ANNO = :anno " +
			" AND didac.X_NIVEADAP IS null " +
			"UNION ALL " +
			"SELECT DISTINCT didac.ID_PROGDIDAC idDidactica, PROGDIDPOND.X_PONDERACION idPonderacion, didac.X_NIVEADAP idOferMatrigNivelCurricular, " +
			"ofer.S_OFERTAMATRIG descripcionNivelCurricular, 1 acneae, MATERIASCURSO.T_ABREV abrev  " +
			" FROM EVA_PROGDIDAC didac " +
			" INNER JOIN TLOFEMATRGEN ofer ON DIDAC.X_NIVEADAP = ofer.X_OFERTAMATRIG   " +
			" INNER JOIN EVA_RELPROGDIDPOND PROGDIDPOND ON PROGDIDPOND.ID_PROGDIDAC = DIDAC.ID_PROGDIDAC " +
			" INNER JOIN TLMATOFEMATRG OFEMATRG ON OFEMATRG.X_MATERIAOMG = DIDAC.X_MATERIAOMGADAP  " +
			" INNER JOIN TLMATERIASCURSO MATERIASCURSO ON MATERIASCURSO.X_MATERIAC = OFEMATRG.X_MATERIAC " +
			" INNER JOIN EVA_PROGAULA PROGAULA ON PROGAULA.ID_PROGDIDAC = DIDAC.ID_PROGDIDAC " +
			" INNER JOIN EVA_RELPROGAULAEMP PROGAULAEMP ON PROGAULAEMP.ID_PROGAULA = PROGAULA.ID_PROGAULA AND (:direccion = 1 OR PROGAULAEMP.X_EMPLEADO = :idEmpleado) " +
			" WHERE didac.X_OFERTAMATRIG  = :idOfermatrig     " +
			" AND didac.X_MATERIAOMG = :idMateriaOmg     " +
			" AND DIDAC.X_CENTRO = :idCentro " +
			" AND didac.LG_CERRADA = 1 " +
			" AND DIDAC.NU_ANNO = :anno " +
			" AND didac.X_NIVEADAP IS NOT NULL", nativeQuery = true)
	List<NivelCurricularProgramacionAulaProjection> getNivelCurricularValoracionCriterios(@Param("anno") Integer anno,
			@Param("idMateriaOmg") Long idMateriaOmg,
			@Param("idCentro") Long idCentro, 
			@Param("idOfermatrig") Long idOfermatrig,
			@Param("idEmpleado") Long idEmpleado,
			@Param("direccion") Long direccion);
}


