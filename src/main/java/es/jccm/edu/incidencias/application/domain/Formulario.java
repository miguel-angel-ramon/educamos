package es.jccm.edu.incidencias.application.domain;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;



@Data
public class Formulario {
    private String email;
    private String phone;
    private String category;
    private String subCategory;  
    private String itemCategory;
    private String subject;
    private int priority;
    private int status;
    private int source;
    private String description;
    // Archivos recibidos
    private List<MultipartFile> attachaments;
    private Map<String, String> customFields;

    // Getters y Setters
}