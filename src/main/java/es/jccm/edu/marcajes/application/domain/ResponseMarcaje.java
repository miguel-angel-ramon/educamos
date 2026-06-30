package es.jccm.edu.marcajes.application.domain;

import java.util.List;

import lombok.Data;

@Data
public class ResponseMarcaje {

	private String ok;
	private String id;
	private List<Marcaje> marcajes;
}
