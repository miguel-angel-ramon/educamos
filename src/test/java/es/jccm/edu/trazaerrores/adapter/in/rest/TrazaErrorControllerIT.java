//package es.jccm.edu.trazaerrores.adapter.in.rest;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.assertj.core.util.DateUtil;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import es.jccm.edu.simulacion.application.services.jwtutils.JwtUtilsService;
//import es.jccm.edu.trazaerrores.adapter.in.rest.trazaErrorFrontEnd.model.ErrorClienteDTO;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class TrazaErrorControllerIT {
//	
//	@Autowired
//	private MockMvc mockMvc;
//
//	private static String jwtToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMzE1MjY2IiwidXNlcl9uYW1lIjoiMTMxNTI2NiIsInNjb3BlIjpbInJlYWQiLCJvcGVuaWQiXSwiZXhwIjoxNjc5Mzg4NjkxLCJnaXZlbl9uYW1lIjoiSmVzw7pzIiwiZmFtaWx5X25hbWUiOiJNYXJ0w61uIGRlIGxhIENydXoiLCJhdXRob3JpdGllcyI6eyJhdXRob3JpdGllcyI6WyJST0xFX0VEVV9GQU1JTElBIl19LCJqdGkiOiI1Y2E1YWI3ZC1mMjNlLTRmNDItYjc2OC1mZTUzMWM0OWM2MDIiLCJzaXN0ZW1hX2F1dGVudGljYWRvciI6IkxEQVBKY2NtIiwiZW1haWwiOm51bGwsImNsaWVudF9pZCI6ImNsaWVudCJ9.bODB4Tvd49MtCfYl8QUEuXVKqwIsNxirQyl5PhjyDRU";
//
//	@Autowired
//	JwtUtilsService revitalizadorTokens;
//	
//	@Test
//	public void testcreaerrorfrontend() throws Exception {
//		ErrorClienteDTO clienteDTO = new ErrorClienteDTO();
//		clienteDTO.setLogLevel("error");
//		clienteDTO.setStackTrace(null);
//		clienteDTO.setTimestamp(DateUtil.now());
//		clienteDTO.setModuleName("trazaerrores");
//		clienteDTO.setUserName("ozlem");
//		clienteDTO.setBrowser("chrome");
//		clienteDTO.setIp("1111");
//		clienteDTO.setParams(null);
//
//		ObjectMapper mapper = new ObjectMapper();
//
//		
//		String s1 = mapper.writeValueAsString(clienteDTO);
//		mockMvc.perform(post("/apieducamosclm/trazaerrores/creaerrorfrontend").content(s1).contentType(MediaType.APPLICATION_JSON)
//				.header("Authorization", "Bearer "+revitalizadorTokens.testingRevitalizarToken(jwtToken,30)))
//				.andExpect(status().isOk());
//	}
//
//	@Test
//	public void testValidacionCreaErrorFrontEnd() throws Exception {
//		ErrorClienteDTO clienteDTO = new ErrorClienteDTO();
//		clienteDTO.setLogLevel("unknown");
//		clienteDTO.setStackTrace(null);
//		clienteDTO.setTimestamp(DateUtil.now());
//		clienteDTO.setModuleName("trazaerrores");
//		clienteDTO.setUserName("ozlem");
//		clienteDTO.setBrowser("chrome");
//		clienteDTO.setIp("1111");
//		clienteDTO.setParams(null);
//
//		ObjectMapper mapper = new ObjectMapper();
//
//		String s1 = mapper.writeValueAsString(clienteDTO);
//		mockMvc.perform(post("/apieducamosclm/trazaerrores/creaerrorfrontend").content(s1).contentType(MediaType.APPLICATION_JSON)
//				.header("Authorization", "Bearer "+revitalizadorTokens.testingRevitalizarToken(jwtToken,30)))
//				.andExpect(status().is(409));
//	}
//}
