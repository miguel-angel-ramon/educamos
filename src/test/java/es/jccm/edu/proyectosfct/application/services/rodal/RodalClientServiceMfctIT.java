package es.jccm.edu.proyectosfct.application.services.rodal;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.jccm.cstic.clientesws.clienterodal.InsertarDocFault;

import es.jccm.cstic.dm.rodal.documento.RespuestaInsertarDoc;
import es.jccm.edu.proyectosfct.application.domain.convenios.entities.ConveniosFct;
import es.jccm.edu.proyectosfct.application.ports.in.convenios.IConveniosFctService;
import es.jccm.edu.proyectosfct.application.ports.in.rodal.RodalExceptionMfct;
import es.jccm.edu.proyectosfct.application.services.rodal.RodalClientServiceMfct;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class RodalClientServiceMfctIT {

	private static final String modulo = "MFCT";
	@Autowired
	RodalClientServiceMfct sut;
	
	@Test
	public void soloParaModuloMfct() throws UnsupportedEncodingException, RodalExceptionMfct {
		MultipartFile file=new MockMultipartFile("eje.txt","contenido test".getBytes("UTF-8"));
		
		try {
			sut.insertaDoc(file, "DGC", null, null, null, null);
			fail();
		} catch (IllegalArgumentException e) {
			
		} catch (InsertarDocFault e) {
			fail();

		} catch (IOException e) {
			fail();

		}
	}
	
	@Test
	public void insertarDocNoFalla() throws Exception {
		MultipartFile file=new MockMultipartFile("eje.txt","eje.txt","text/plain","contenido test".getBytes("UTF-8"));

		String entidad="entidad";
		long idEntidad=getRandomIdEntidad();
		long anno=2023;
		long xCentroDelphos=824;
		
		
		RespuestaInsertarDoc resultado=sut.insertaDoc(file, modulo, entidad, idEntidad, anno, xCentroDelphos);
		String idDoc=resultado.getIdDoc();

		assertNotNull(idDoc);
	}
	
	@Test
	public void sePuedeInsertarYRecuperarDoc() throws Exception{
		byte[] contenidoBytes = "contenido test".getBytes("UTF-8");
		MultipartFile file=new MockMultipartFile("eje.txt","eje.txt","text/plain",contenidoBytes);

		String entidad="entidad";
		long idEntidad=getRandomIdEntidad();
		long anno=2023;
		long xCentroDelphos=824;
		
		
		RespuestaInsertarDoc resultado=sut.insertaDoc(file, modulo, entidad, idEntidad, anno, xCentroDelphos);
		String idDoc=resultado.getIdDoc();

		
		ByteArrayOutputStream doc=sut.recuperaDoc(idDoc, modulo);
		
		assertArrayEquals(contenidoBytes,doc.toByteArray());
	}
	
	String entidad="CF_"; // parece que por código solo permite 3 entidades, y según el tipo de entidad usa uno u otros parámetros
	long idEntidad=41; //en este caso el id de entidad es el id de "convenio", pero no tengo claro si es el mismo para cada entidad. La entidad es en realidad el tipo de entidad?

	@Autowired
	private IConveniosFctService conveniosFctService;
	
	@BeforeEach
	@AfterEach
	// para que la prueba de modificar documento sea repetible, es necesario borrar el documento introducido
	// el problema es que hay que pasar el id de rodal y eso solo se puede recuperar de otro servicio
	public void teardown(){
		try {
			ConveniosFct convenioUpdate = conveniosFctService.getConvenioById(idEntidad);
			if(convenioUpdate!=null) {
				Boolean res=sut.borrarDocumento( convenioUpdate.getIdConfirRodal(),modulo, entidad);
				log.info("res de borrar="+res);
			}
		} catch (RodalExceptionMfct e) {
			//si no puede borrar porque no existe no pasa nada
		}
	}
	

	
	@Test
	public void setPuedeModificarDoc()  throws Exception{
		
		
		/*byte[] contenidoBytes = "contenido test".getBytes("UTF-8");
		MultipartFile file=new MockMultipartFile("eje.txt","eje.txt","text/plain",contenidoBytes);

		long anno=2023;
		long xCentroDelphos=824;

		RespuestaInsertarDoc resultado=sut.insertaDoc(file, modulo, entidad, idEntidad, anno, xCentroDelphos); //TODO: prueba no repetible ya que no permite insertar dos documentos para un mismo identidad
		String idDoc=resultado.getIdDoc();

		byte[] nuevoContenido = "nuevo contenido test".getBytes("UTF-8");

		MultipartFile fileActualizado=new MockMultipartFile("eje.txt","eje.txt","text/plain",nuevoContenido);

		Boolean res=sut.actualizaDoc(fileActualizado,modulo,entidad, idEntidad);
		assertEquals(true,res);
	
		ByteArrayOutputStream doc=sut.recuperaDoc(idDoc, modulo);
		assertArrayEquals(nuevoContenido,doc.toByteArray());*/
	}
	
	private long getRandomIdEntidad() {
	    int leftLimit = 9800000;
	    int rightLimit = 9900000;
	    int generatedInteger = leftLimit + (int) (new Random().nextFloat() * (rightLimit - leftLimit));
	    return generatedInteger;
	}
	
}
