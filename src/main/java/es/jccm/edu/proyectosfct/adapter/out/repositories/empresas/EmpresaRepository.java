package es.jccm.edu.proyectosfct.adapter.out.repositories.empresas;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.proyectosfct.application.domain.empresas.Empresa;
import es.jccm.edu.proyectosfct.application.domain.empresas.QEmpresa;
import es.jccm.edu.proyectosfct.application.domain.empresas.projection.EmpresaEmpleadosProjection;
import es.jccm.edu.proyectosfct.application.domain.empresas.projection.EmpresaProjection;
import es.jccm.edu.proyectosfct.application.domain.empresas.projection.TipoEmpresaProjection;
import es.jccm.edu.proyectosfct.application.domain.programas.projection.FamiliaProjection;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EmpresaRepository extends AbstractRepository<Empresa, Long, QEmpresa>{
 
 @Modifying
 @Query(value= "SELECT emp.X_EMPRESA as id, emp.L_TIPIDE as tipoIdentificador, emp.C_NUMIDE as numIde, emp.D_EMPRESA as nombreEmpresa, se.T_DOMICILIO as domicilio, se.T_NUMERO as numero, "
   + "se.T_ESCALERA as escalera, se.T_PISO as piso, se.C_POSTAL as codigoPostal, se.N_TELEFONO as telefono, se.N_TELEFONO2 as otroTelefono, se.N_FAX as fax, "
   + "se.T_CORREO as correo, emp.L_ACTIVA as empresaActiva, emp.N_TELEFONOS as telefonoContacto, emp.T_OBSERVACIONES as observaciones, "
   + "se.T_LETRA as letra, emp.X_SECTOR as idSectorProductivo, emp.T_WEB as paginaWeb, se.T_LOCALIDADEXT as localidadExtranjera, emp.L_PUBLICO  as empresaPublica, "
   + "(SELECT D_TIPOVIA FROM TLTIPOVIAS WHERE se.X_TIPOVIA = X_TIPOVIA) as dTipoVia, "
   + "(SELECT D_LOCALIDAD  FROM TLLOCALIDADES WHERE se.X_LOCALIDAD = X_LOCALIDAD ) as dLocalidad, "
   + "(SELECT D_MUNICIPIO  FROM TLMUNICIPIOS WHERE se.C_MUNICIPIO = C_MUNICIPIO AND se.C_PROVINCIA = C_PROVINCIA ) as dMunicipio, "
   + "(SELECT D_PROVINCIA  FROM TLPROVINCIAS  WHERE se.C_PROVINCIA = C_PROVINCIA) as dProvincia, "
   + "(SELECT D_PAIS  FROM TLPAIS WHERE se.C_PAIS = C_PAIS) as dPais, "
   + "(SELECT SUBSTR(MAX(familiaProfesional),3) familias "
   + "FROM ( "
   + "SELECT SYS_CONNECT_BY_PATH(d_familia,'/ ') familiaProfesional "
   + "FROM ( "   
   + "select rownum fila, d_familia "
   + " from ( "  
   + "select distinct fam.d_familia "
   + "from   FCT_CONVENIOS conv, FCT_CONV_PROG conp, FCT_PROGRAMAS pro, TLFAMILIAS fam "
   + " where  conv.id_convenio = conp.id_convenio "
   + " and  conp.id_programa = pro.id_programa "
   + " and  pro.x_familia = fam.x_familia "
   + " and  conv.x_empresa = emp.X_EMPRESA "
   + " union   "
   + " select distinct fam.d_familia "
   + " from  FCT_CONVENIOS conv, FCT_CONV_PROY conp, FCT_PROYECTOS pro, TLFAMILIAS fam "
   + " where conv.id_convenio = conp.id_convenio  "
   + " and conp.id_proyecto = pro.id_proyecto  "
   + " and pro.x_familia = fam.x_familia  "
   + " and conv.x_empresa = emp.X_EMPRESA "                   
   + " order  by 1 "   
   + " ) "   
   + ") "
   + " START WITH fila = 1 "
   + "CONNECT BY PRIOR fila = fila - 1 "
   + ")) familiaProfesional FROM TLEMPRESAS emp,EMP_SEDEMP se,TLEMPTIPEMP ete, TLTIPOEMPRESA tip "
   + "WHERE emp.l_activa = 'S' " 
   + "AND emp.x_empresa = se.x_empresa (+) "
   + "AND emp.x_empresa = ete.x_empresa "
   + "AND ete.x_tipoempresa = tip.x_tipoempresa "
   + "AND tip.c_tipoempresa = 'F' "
   + "AND se.lg_principal = 1 "
   + "AND ete.l_vigente = 'S' "
   + "AND  emp.l_activa = 'S' order by 1 desc ",nativeQuery = true)
 List<EmpresaProjection> findAllEmpresas();
 
 @Query(value= "SELECT id, tipoIdentificador,numIde,nombreEmpresa,domicilio,numero,escalera,piso,codigoPostal,telefono,otroTelefono,fax,correo,empresaActiva,telefonoContacto,observaciones, "
   +"letra,idSectorProductivo,paginaWeb,localidadExtranjera,empresaPublica, sesCam ,dTipoVia,dLocalidad,dMunicipio,dProvincia,familiaProfesional,dPais from ( "
         +"SELECT emp.X_EMPRESA as id, emp.L_TIPIDE as tipoIdentificador, emp.C_NUMIDE as numIde, emp.D_EMPRESA as nombreEmpresa, se.T_DOMICILIO as domicilio, se.T_NUMERO as numero, "
   +"se.T_ESCALERA as escalera, se.T_PISO as piso, se.C_POSTAL as codigoPostal, se.N_TELEFONO as telefono, se.N_TELEFONO2 as otroTelefono, se.N_FAX as fax, "
   +"se.T_CORREO as correo, emp.L_ACTIVA as empresaActiva, emp.N_TELEFONOS as telefonoContacto, emp.T_OBSERVACIONES as observaciones, "
   +"se.T_LETRA as letra, emp.X_SECTOR as idSectorProductivo, emp.T_WEB as paginaWeb, se.T_LOCALIDADEXT as localidadExtranjera, emp.L_PUBLICO  as empresaPublica, emp.L_SESCAM  as sesCam, "
   +"(SELECT D_TIPOVIA FROM TLTIPOVIAS WHERE se.X_TIPOVIA = X_TIPOVIA) as dTipoVia, "
   +"(SELECT D_LOCALIDAD  FROM TLLOCALIDADES WHERE se.X_LOCALIDAD = X_LOCALIDAD ) as dLocalidad, "
   +"(SELECT D_MUNICIPIO  FROM TLMUNICIPIOS WHERE se.C_MUNICIPIO = C_MUNICIPIO AND se.C_PROVINCIA = C_PROVINCIA ) as dMunicipio, "
   +"(SELECT D_PROVINCIA  FROM TLPROVINCIAS  WHERE se.C_PROVINCIA = C_PROVINCIA) as dProvincia, "
   +"(SELECT D_PAIS  FROM TLPAIS WHERE se.C_PAIS = C_PAIS) as dPais, "
   +"(SELECT SUBSTR(MAX(familiaProfesional),3) familias "
   +"FROM ( "
   +"SELECT SYS_CONNECT_BY_PATH(d_familia,'/ ') familiaProfesional "
   +"FROM ( "   
   +"select rownum fila, d_familia  "
   +"from (  "
   +"select distinct fam.d_familia  "
   +"from   FCT_CONVENIOS conv, FCT_CONV_PROG conp, FCT_PROGRAMAS pro, TLFAMILIAS fam  "
   +"where  (?1 = -1 OR (?1 != -1 AND  pro.x_familia = ?1))  "
   +"and    conv.id_convenio = conp.id_convenio  "
   +"and    conp.id_programa = pro.id_programa  "
   +"and    pro.x_familia = fam.x_familia  "
   +"and    conv.x_empresa = emp.X_EMPRESA  "
   +"union "
   +"select distinct fam.d_familia  " 
   +"from   FCT_CONVENIOS conv, FCT_CONV_PROY conp, FCT_PROYECTOS pro, TLFAMILIAS fam  "
   +"where  (?1 = -1 OR (?1 != -1 AND  pro.x_familia = ?1))  "
   +"and    conv.id_convenio = conp.id_convenio  "
   +"and    conp.id_proyecto = pro.id_proyecto  "
   +"and    pro.x_familia = fam.x_familia  "
   +"and    conv.x_empresa = emp.X_EMPRESA "
   +"order  by 1  "
   +" )  "   
   +"    ) "
   +"    START WITH fila = 1 "
   +"    CONNECT BY PRIOR fila = fila - 1 "
   +" )) familiaProfesional     FROM TLEMPRESAS emp,EMP_SEDEMP se,TLEMPTIPEMP ete, TLTIPOEMPRESA tip "
   +" WHERE emp.l_activa = 'S' " 
   +" AND (?2 = -1  OR (?2 != -1 AND se.c_provincia = ?2) OR ?3 != '034') "
   +" AND (?3 = -1 OR (?3 != -1 AND se.c_pais = ?3)) "
   +" AND emp.x_empresa = se.x_empresa (+) "
   +" AND emp.x_empresa = ete.x_empresa "
   +" AND ete.x_tipoempresa = tip.x_tipoempresa "
   +" AND tip.c_tipoempresa = 'F' "
   +" AND se.lg_principal = 1 "
   +" AND ete.l_vigente = 'S' "
   +" AND (CASE WHEN ?4 = 0 THEN 1 WHEN ?4 = 1 AND EXISTS (SELECT 1 FROM fct_convenios WHERE x_empresa = emp.x_empresa) THEN 1 ELSE 0 END = 1) "
   +" AND  emp.l_activa = 'S') WHERE (?1 = -1 OR (?1 !=-1 AND familiaProfesional is not null)) order by id desc ",nativeQuery = true)
 List<EmpresaProjection> findAllEmpresasProvincia(Long idFamilia,Long idProvincia,String dPais,Integer conConvenio);
 
 List<Empresa> findByEmpresaActiva(String empresaActiva);
 
 //Page<Empresa> findByEmpresaActiva(String empresaActiva, Pageable pageable);
 
 Page<Empresa> findByNombreEmpresaContainingIgnoreCaseAndEmpresaActiva(String nombre, String empresaActiva, Pageable pageable);
 
 @Modifying
 @Query("update Empresa emp set emp.empresaActiva='N' where emp.id = :idEmpresa")
 void deleteEmpresaById(Long idEmpresa);
 
 @Query(value= "select count(*)   "
   +"from TLEMPRESAS emp,EMP_SEDEMP se,TLEMPTIPEMP ete, TLTIPOEMPRESA tip "
   +"where emp.l_activa = 'S' "
   +"and emp.x_empresa = se.x_empresa (+) "
   +"and emp.x_empresa = ete.x_empresa "
   +"and ete.x_tipoempresa = tip.x_tipoempresa "
   +"and tip.c_tipoempresa = 'F' "
   +"and se.lg_principal = 1 "
   +"and ete.l_vigente = 'S' "
   +"and  emp.l_activa = 'S' ", nativeQuery = true)
 Long countByEmpresas();

 @Modifying
 @Query(value= "select distinct fam.X_FAMILIA AS id, fam.S_FAMILIA AS descripcionCorta, fam.d_familia AS descripcionLarga "
   + "from FCT_CONVENIOS conv, FCT_CONV_PROG conp, FCT_PROGRAMAS pro, TLFAMILIAS fam "
   + "where conv.id_convenio = conp.id_convenio "
   + "and conp.id_programa = pro.id_programa "
   + "and pro.x_familia = fam.x_familia "
   + "and conv.x_empresa = :idEmpresa "
   + "order  by 1", nativeQuery = true)
 List<FamiliaProjection> getFamiliasEmpresa(Long idEmpresa);
 
 @Modifying
 @Query(value= "select tip.x_tipoempresa AS id, tip.d_tipoempresa AS descripcionTipo from  TLEMPTIPEMP ete , TLTIPOEMPRESA tip "
   + "where ete.x_empresa =  :idEmpresa  "
   + "and tip.x_tipoempresa =  ete.x_tipoempresa "
   + "and ete.l_vigente = 'S' ", nativeQuery = true)
 List<TipoEmpresaProjection> getTiposEmpresas(Long idEmpresa);
 
 @Query(value= "select tra.id_traemp AS ID, "
   +"emp.tx_apellido1 || ' ' || emp.tx_apellido2 || ', ' || emp.tx_nombre AS NOMBRE, "
   +"emp.c_numide DNI, "
   +"tra.ds_departamento DEPARTAMENTO, "
   +"decode(tra.lg_repemp,1,'Sí','No') REPRESENTANTE, "
   +"decode(tra.lg_resfct,1,'Sí','No') RESPONSABLE "
   +"from EMP_TRAEMP tra, EMP_EMPLEADOS emp "
   +"where tra.id_empleado = emp.id_empleado "
   +"and tra.x_empresa = ?1 "
   +"order  by 2 ", nativeQuery = true)
 List<EmpresaEmpleadosProjection> findAllEmpleadosEmpresa(Long idEmpresa);

 @Query(value= "select emp.x_empresa AS id, emp.d_empresa as nombreEmpresa "
   +"from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa, "
   +"FCT_CONVENIOS con, TLEMPRESAS emp "
   +"where (-1 = ?1 OR  tut.id_tutorfctdual = ?1) "
   + " and (tut.id_tutorfctdual = pro.id_tutorfctdual or pro.id_tutorfctdual in ( SELECT DISTINCT TUT.ID_TUTORFCTDUAL    "           
   + "        FROM TLPTOTRAEMP PTO, TLUSUARIOS USU, FCT_TUTORFCTDUAL TUT    "                                                                           
   + "        WHERE  USU.X_USUARIO = ?5                          "
   + "       AND PTO.X_EMPLEADO = USU.X_EMPLEADO    "
   + "       AND PTO.X_CENTRO = ?2 "
   + "       AND PTO.F_TOMAPOSREA <= SYSDATE " 
   + "       AND (PTO.F_CESE >= SYSDATE OR PTO.F_CESE IS NULL) "
   + "       AND TUT.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE))  "
   +"and pro.id_programa = cp.id_programa "
   +"and cp.id_conv_prog = cpa.id_conv_prog "
   +"and cp.id_convenio = con.id_convenio "
   +"and con.x_empresa = emp.x_empresa "
   +"and con.lg_lofp = 0 "
   +"and pro.x_centro = ?2 "
   +"and ?3 between pro.c_anno_desde and pro.c_anno_hasta "
   +"and ?4 in (-1,1) "
   +"union "
   +"select emp.x_empresa, emp.d_empresa empresa "
   +"from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa, "
   +"FCT_CONVENIOS con, TLEMPRESAS emp "
   +"where (-1 = ?1 OR  tut.id_tutorfctdual = ?1) "
   + " and (tut.id_tutorfctdual = pro.id_tutorfctdual or pro.id_tutorfctdual in ( SELECT DISTINCT TUT.ID_TUTORFCTDUAL    "           
   + "        FROM TLPTOTRAEMP PTO, TLUSUARIOS USU, FCT_TUTORFCTDUAL TUT    "                                                                           
   + "        WHERE  USU.X_USUARIO = ?5                          "
   + "       AND PTO.X_EMPLEADO = USU.X_EMPLEADO    "
   + "       AND PTO.X_CENTRO = ?2 "
   + "       AND PTO.F_TOMAPOSREA <= SYSDATE " 
   + "       AND (PTO.F_CESE >= SYSDATE OR PTO.F_CESE IS NULL) "
   + "       AND TUT.X_EMPLEADO = PTO.X_EMPLEADO_SUSTITUYE))  "
   +"and pro.id_proyecto = cp.id_proyecto "
   +"and cp.id_conv_proy = cpa.id_conv_proy "
   +"and cp.id_convenio = con.id_convenio "
   +"and con.x_empresa = emp.x_empresa "
   +"and con.lg_lofp = 0 "
   +"and pro.x_centro = ?2 "
   +"and ?3 between pro.c_anno_desde and pro.c_anno_hasta "
   +"and ?4 in (-1,2) "
   +"order by nombreEmpresa ", nativeQuery = true)
 List<EmpresaProjection> findAllEmpresasEmpleadoCentro(Long id_tutorfctdual, Long idCentro, Integer cAnno, Integer tipoEmpresa, Long idUsuario);
 
 
 
  @Query(value= " select emp.x_empresa AS id, emp.d_empresa as nombreEmpresa "
      + "   from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa, "
      + "   FCT_CONVENIOS con, TLEMPRESAS emp "
      + "   where (-1 = :id_tutorfctdual OR  tut.id_tutorfctdual = :id_tutorfctdual) "
      + "   and tut.id_tutorfctdual = pro.id_tutorfctdual "
      + "   and pro.id_programa = cp.id_programa "
      + "   and cp.id_conv_prog = cpa.id_conv_prog "
      + "   and cp.id_convenio = con.id_convenio "
      + "   and con.x_empresa = emp.x_empresa "
      + "   and pro.x_centro IN (SELECT distinct dcen1.x_centro id "
      + "                                 from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv , "
      + "                                TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv  "
      + "                       WHERE u.x_usuario = :idUsuario "
      + "                       AND pop.x_perfil = :idPerfil "
      + "                       AND pto.x_centro = :idCentro "
      + "                                AND pto.x_empleado=u.x_empleado "
      + "                       AND pop.x_empleado=pto.x_empleado "
      + "                       AND pop.f_tomapos = pto.f_tomapos "
      + "                                AND pto.x_centro = dcen.x_centro "
      + "                       AND dcen.c_provincia = prv.c_provincia  "
      + "                       AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) "
      + "                       AND dcen1.c_provincia = prv.c_provincia "
      + "                                AND dcen1.l_vigente = 'S' "
      + "                       AND cen.x_centro = dcen1.x_centro "
      + "                       AND cen.l_delegacion = 'N' "
      + "                       AND cen.l_extranjero = 'N') "
      + "            and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo) "
      + "   and :cAnno between pro.c_anno_desde and pro.c_anno_hasta "
      + "   and :tipoEmpresa in (-1,1) "
      + "   union "
      + "   select emp.x_empresa, emp.d_empresa empresa "
      + "   from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa, "
      + "   FCT_CONVENIOS con, TLEMPRESAS emp "
      + "   where (-1 = :id_tutorfctdual OR  tut.id_tutorfctdual = :id_tutorfctdual)  "
      + "   and tut.id_tutorfctdual = pro.id_tutorfctdual "
      + "   and pro.id_proyecto = cp.id_proyecto "
      + "   and cp.id_conv_proy = cpa.id_conv_proy "
      + "   and cp.id_convenio = con.id_convenio "
      + "   and con.x_empresa = emp.x_empresa "
      + "   and con.lg_lofp = 0 "
      + "   and pro.x_centro IN (SELECT distinct dcen1.x_centro id "
      + "                                 from TLUSUARIOS u, TLPTOTRAEMP pto, TLPUEORIPER pop, TLDATOSCEN dcen, TLPROVINCIAS prv , "
      + "                                TLDATOSCEN dcen1, TLCENTROS cen, FCT_CONVENIOS conv  "
      + "                       WHERE u.x_usuario = :idUsuario "
      + "                       AND pop.x_perfil = :idPerfil "
      + "                       AND pto.x_centro = :idCentro "
      + "                                AND pto.x_empleado=u.x_empleado "
      + "                       AND pop.x_empleado=pto.x_empleado "
      + "                       AND pop.f_tomapos = pto.f_tomapos "
      + "                                AND pto.x_centro = dcen.x_centro "
      + "                       AND dcen.c_provincia = prv.c_provincia  "
      + "                       AND (pto.f_cese is null or sysdate between pto.f_tomapos and pto.f_cese) "
      + "                       AND dcen1.c_provincia = prv.c_provincia "
      + "                                AND dcen1.l_vigente = 'S' "
      + "                       AND cen.x_centro = dcen1.x_centro "
      + "                       AND cen.l_delegacion = 'N' "
      + "                       AND cen.l_extranjero = 'N') "
      + "            and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)                     "
      + "   and :cAnno between pro.c_anno_desde and pro.c_anno_hasta "
      + "   and :tipoEmpresa in (-1,2) "
      + "   order by nombreEmpresa ", nativeQuery = true)
 List<EmpresaProjection> findAllEmpresasEmpleadoDelegacion(Long id_tutorfctdual, 
                 Long idCentro, 
                 Integer cAnno,
                 Integer tipoEmpresa, 
                 Long idCentroCombo, 
                 Long idUsuario,
                 Long idPerfil);
  
     @Query(value= " select emp.x_empresa AS id, emp.d_empresa as nombreEmpresa"
          + "         from FCT_TUTORFCTDUAL tut, FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,"
          + "         FCT_CONVENIOS con, TLEMPRESAS emp"
          + "         where (-1 = :id_tutorfctdual OR  tut.id_tutorfctdual = :id_tutorfctdual)"
          + "         and tut.id_tutorfctdual = pro.id_tutorfctdual"
          + "         and pro.id_programa = cp.id_programa"
          + "         and cp.id_conv_prog = cpa.id_conv_prog"
          + "         and cp.id_convenio = con.id_convenio"
          + "         and con.x_empresa = emp.x_empresa"
          + "         and pro.x_centro IN (SELECT distinct dcen.x_centro id"
          + "                              FROM TLDATOSCEN dcen, TLPROVINCIAS prv ,"
          + "                                           TLCENTROS cen, FCT_CONVENIOS conv "
          + "                             WHERE (-1 = :idProvincia OR dcen.c_provincia = :idProvincia)                          "
          + "                             AND dcen.c_provincia = prv.c_provincia "
          + "                             AND dcen.l_vigente = 'S'"
          + "                             AND cen.x_centro = dcen.x_centro"
          + "                             AND cen.l_delegacion = 'N'"
          + "                             AND cen.l_extranjero = 'N')"
          + "                  and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)"
          + "         and :cAnno between pro.c_anno_desde and pro.c_anno_hasta"
          + "         and :tipoEmpresa in (-1,1)"
          + "         union"
          + "         select emp.x_empresa, emp.d_empresa empresa"
          + "         from FCT_TUTORFCTDUAL tut, FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,"
          + "         FCT_CONVENIOS con, TLEMPRESAS emp"
          + "         where (-1 = :id_tutorfctdual OR  tut.id_tutorfctdual = :id_tutorfctdual) "
          + "         and tut.id_tutorfctdual = pro.id_tutorfctdual"
          + "         and pro.id_proyecto = cp.id_proyecto"
          + "         and cp.id_conv_proy = cpa.id_conv_proy"
          + "         and cp.id_convenio = con.id_convenio"
          + "         and con.x_empresa = emp.x_empresa"
          + "         and con.lg_lofp = 0 "
          + "         and pro.x_centro IN (SELECT distinct dcen.x_centro id"
          + "                              FROM    TLDATOSCEN dcen, TLPROVINCIAS prv ,"
          + "                                      TLCENTROS cen, FCT_CONVENIOS conv "
          + "                             WHERE (-1 = :idProvincia OR dcen.c_provincia = :idProvincia)"
          + "                             AND dcen.c_provincia = prv.c_provincia "
          + "                             AND dcen.l_vigente = 'S'"
          + "                             AND cen.x_centro = dcen.x_centro"
          + "                             AND cen.l_delegacion = 'N'"
          + "                             AND cen.l_extranjero = 'N')"
          + "                  and (-1 = :idCentroCombo OR pro.x_centro = :idCentroCombo)                    "
          + "         and :cAnno between pro.c_anno_desde and pro.c_anno_hasta"
          + "         and :tipoEmpresa in (-1,2)"
          + "         order by nombreEmpresa ", nativeQuery = true)
 List<EmpresaProjection> findAllEmpresasEmpleadoDelegacionProvincias(Long id_tutorfctdual, 
                  Long idCentro, 
                  Integer cAnno,
                  Integer tipoEmpresa, 
                  Long idCentroCombo,
                  Long idProvincia);
 
 @Query(value="SELECT DISTINCT EMP.X_EMPRESA AS id, EMP.D_EMPRESA AS nombreEmpresa, EMP.C_NUMIDE as numIde," + 
   "              (SELECT D_TIPOVIA FROM TLTIPOVIAS WHERE se.X_TIPOVIA = X_TIPOVIA) as dTipoVia," + 
   "   (SELECT D_LOCALIDAD  FROM TLLOCALIDADES WHERE se.X_LOCALIDAD = X_LOCALIDAD ) as dLocalidad," + 
   "   (SELECT D_MUNICIPIO  FROM TLMUNICIPIOS WHERE se.C_MUNICIPIO = C_MUNICIPIO AND se.C_PROVINCIA = C_PROVINCIA ) as dMunicipio," + 
   "   (SELECT D_PROVINCIA  FROM TLPROVINCIAS  WHERE se.C_PROVINCIA = C_PROVINCIA) as dProvincia," + 
   "   (SELECT D_PAIS  FROM TLPAIS WHERE se.C_PAIS = C_PAIS) as dPais" + 
   "    FROM TLEMPRESAS EMP,EMP_SEDEMP SE,TLEMPTIPEMP ETE,TLTIPOEMPRESA TIP" + 
   "    WHERE EMP.L_ACTIVA = 'S' " + 
   "    AND EMP.X_EMPRESA = SE.X_EMPRESA (+)" + 
   "    AND EMP.X_EMPRESA = ETE.X_EMPRESA" + 
   "    AND ETE.X_TIPOEMPRESA = TIP.X_TIPOEMPRESA" + 
   "    AND ETE.X_TIPOEMPRESA = 3" + 
   "    AND SE.LG_PRINCIPAL = 1" + 
   "    AND ETE.L_VIGENTE = 'S'" + 
   "    AND EMP.L_ACTIVA = 'S'" + 
   "    ORDER BY EMP.D_EMPRESA ASC " ,nativeQuery = true)
 List<EmpresaProjection>FindAllEmpresasListado();
 
 @Query(value= "select emp.x_empresa AS id, emp.d_empresa as nombreEmpresa    "
   + "    from delphos_segedu.tlempleados empl, tlalumnos alu, tlmatalu mat,  "
   + "                  FCT_PROGRAMAS pro, FCT_CONV_PROG cp, FCT_CONVPROG_ALU cpa,    "
   + "         FCT_CONVENIOS con, TLEMPRESAS emp    "
   + "    where empl.x_empleado = :idEmpleadoComunica "
   + "            and alu.c_numide = empl.c_numide "
   + "            and mat.x_alumno = alu.x_alumno "
   + "            and mat.c_anno = :cAnno "
   + "            and mat.x_centro = :idCentro "
   + "            and cpa.x_matricula = mat.x_matricula             "
   + "            and pro.id_programa = cp.id_programa    "
   + "      and cp.id_conv_prog = cpa.id_conv_prog    "
   + "    and cp.id_convenio = con.id_convenio    "
   + "    and con.x_empresa = emp.x_empresa    "
   + "    and pro.x_centro = :idCentro    "
   + "    and :cAnno between pro.c_anno_desde and pro.c_anno_hasta  "
   + "             and :tipoEmpresa in (-1,1) "
   + "    union    "
   + "    select emp.x_empresa, emp.d_empresa empresa    "
   + "    from  delphos_segedu.tlempleados empl, tlalumnos alu, tlmatalu mat,  "
   + "                   FCT_PROYECTOS pro, FCT_CONV_PROY cp, FCT_CONVPROY_ALU cpa,    "
   + "    FCT_CONVENIOS con, TLEMPRESAS emp    "
   + "    where empl.x_empleado = :idEmpleadoComunica "
   + "             and alu.c_numide = empl.c_numide "
   + "             and mat.x_alumno = alu.x_alumno "
   + "             and mat.c_anno = :cAnno "
   + "             and mat.x_centro = :idCentro "
   + "             and cpa.x_matricula = mat.x_matricula  "
   + "    and pro.id_proyecto = cp.id_proyecto    "
   + "    and cp.id_conv_proy = cpa.id_conv_proy    "
   + "    and cp.id_convenio = con.id_convenio    "
   + "    and con.x_empresa = emp.x_empresa    "
   + "    and pro.x_centro = :idCentro    "
   + "    and con.lg_lofp = 0 "
   + "    and :cAnno between pro.c_anno_desde and pro.c_anno_hasta  "
   + "             and :tipoEmpresa in (-1,2) "
   + "    order by nombreEmpresa ", nativeQuery = true)
 List<EmpresaProjection> findAllEmpresasEmpleadoCentroAlumno(Long idCentro, Integer cAnno, Integer tipoEmpresa, Long idEmpleadoComunica);


 

}
