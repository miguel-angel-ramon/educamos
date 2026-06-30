package es.jccm.edu.comunicaciones.application.services.mensajes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import es.jccm.edu.comunicaciones.application.domain.mensajes.Miembro;

@Service
public class MensajesServiceDataSource {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcTemplate jdbcTemplateSegedu;

    @Autowired
    public MensajesServiceDataSource(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate,
                           @Qualifier("jdbcTemplateSegedu") JdbcTemplate jdbcTemplateSegedu) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplateSegedu = jdbcTemplateSegedu;
        testConnection();
    }

    // Método que usa jdbcTemplate para la base de datos principal
    public List<Miembro> getMiembrosFromPrimaryDB(String query, List<Object> params) {
        return jdbcTemplate.query(query, params.toArray(), new MiembrosRowMapper());
    }

    // Método que usa jdbcTemplateSegedu para la base de datos Segedu
    public List<Miembro> getMiembrosFromSegeduDB(String query, List<Object> params) {
    	testConnection();
        return jdbcTemplateSegedu.query(query, params.toArray(), new MiembrosRowMapper());
    }
    
	private static class MiembrosRowMapper implements RowMapper<Miembro> {
	    @Override
	    public Miembro mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Miembro miembro = new Miembro();
	        miembro.setIndice(rs.getString("indice"));
	        miembro.setDescripcion(rs.getString("descripcion"));	     
	        return miembro;
	    }
	}
	
	public List<Miembro> ejecutarQuery(String query) {
	    return jdbcTemplateSegedu.query(query, new MiembrosRowMapper());

	}

    public void testConnection() {
        try {
            String result = jdbcTemplate.queryForObject("SELECT 'Conexión exitosa' FROM DUAL", String.class);
            System.out.println("Base de datos principal: " + result);

            String resultSegedu = jdbcTemplateSegedu.queryForObject("SELECT 'Conexión exitosa' FROM DUAL", String.class);
            System.out.println("Base de datos Segedu: " + resultSegedu);
        } catch (Exception e) {
            System.err.println("Error en la conexión: " + e.getMessage());
        }
    }
}

