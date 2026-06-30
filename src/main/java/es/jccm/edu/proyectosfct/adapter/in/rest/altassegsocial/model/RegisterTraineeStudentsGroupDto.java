package es.jccm.edu.proyectosfct.adapter.in.rest.altassegsocial.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import es.jccm.edu.shared.adapter.in.rest.segsocial.model.RegisterTraineeStudentDto;

@Data
@Schema(name = "RegisterTraineeStudentsGroupDto", description = "Objeto auxiliar para recoger tres listados")
public class RegisterTraineeStudentsGroupDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<RegisterTraineeStudentDto> registerTraineeStudentDtos;

    private List<RegisterTraineeStudentDto> updateTraineeStudentDtos;

    private List<RegisterTraineeStudentDto> cancellTraineeStudentDtos;

}
