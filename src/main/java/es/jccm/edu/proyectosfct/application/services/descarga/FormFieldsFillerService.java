package es.jccm.edu.proyectosfct.application.services.descarga;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.jccm.edu.proyectosfct.application.domain.descarga.PrintField;
import es.jccm.edu.proyectosfct.application.domain.descarga.PrintForm;
import es.jccm.edu.proyectosfct.application.ports.in.descarga.IFormFielsFillerService;
import es.jccm.edu.proyectosfct.application.ports.in.descarga.IPrintFieldService;
import es.jccm.edu.proyectosfct.application.ports.in.descarga.IPrintFormService;

@Service
public class FormFieldsFillerService implements IFormFielsFillerService {
	
	@Autowired
	private IPrintFormService printFormService;
	
	@Autowired
	private IPrintFieldService printFieldService;
	
	@Override
	public <T> Map<String, String> execute(T entity, String printFormName) {
		
		Assert.notNull(entity, "La entidad es una parametro obligatorio");
		Assert.notNull(printFormName, "La propiedad es un parametro obligatorio");
		
		Map<String, String> res = new HashMap<>();
		
		final PrintForm printForm = printFormService.findPrintFormByAlias(printFormName);
		Assert.notNull(printForm, "No se pudo recuperar el ojeto PrintForm, revise el parametro printFormName enviado");
		
		final List<PrintField> printFields = printFieldService.findByPrintFormId(printForm.getId());
		
		for(PrintField printField : printFields) {
			try {
				String value = parser(entity, printField);
				res.put(printField.getCampoArchivo(), !StringUtils.isEmpty(value) ? value : "");
			} catch (Exception e) {
				System.out.println(printField.getId());
			}
		}
		return res;
	}
	
	public <T> String parser (T entity, PrintField printField) {
		Object valor = getValorCampo(entity, printField);
		return valor != null ? String.valueOf(valor) : "";
	}
	
	/**
	 * Metodo que diferencia si solo buscamos 1 propiedad o si se está buscando
	 * en una propiedad que es FK. Si la FK (propiedad2) contiene un . se itera hasta devolver
	 * el resultado deseado, ya que estaríamos buscando en la FK de la FK
	 * 
	 * @param entity
	 * @param printField
	 * @return
	 */
	private static <T, F> F getValorCampo (T entity, PrintField printField) {
		String propertyName = printField.getPropiedad();
		String subProperty = printField.getPriopiedad2();
		F tipoFinal = getValorDesdeGetter(entity, propertyName);
		if (subProperty != null && tipoFinal != null) {
			if(!subProperty.contains(".")) {
				tipoFinal = getValorDesdeGetter(tipoFinal, subProperty);
			} else {
				String[] subProperties = subProperty.split("\\.");
				for (String s : subProperties) {
					tipoFinal = getValorDesdeGetter(tipoFinal, s);
				}
			}
		}
		return tipoFinal;
	}
	
	/**
	 * Metodo que devuelve el valor de un campo desde su getter. 
	 * 
	 * @param entity
	 * @param propertyName
	 * @return
	 */
	private static <T, F> F getValorDesdeGetter(T entity, String propertyName) {
		F resultado = null;
		try {
			Method get = entity.getClass().getMethod("get".concat(StringUtils.capitalize(propertyName)));
			resultado = (F) get.invoke(entity);
			if(resultado instanceof HibernateProxy) {
				resultado = (F) ((HibernateProxy) get.invoke(entity)).getHibernateLazyInitializer().getImplementation();
			}
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			System.out.println("Error al recuperar el campo " + propertyName);
		}
		return resultado;
	}

}
