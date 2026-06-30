package es.jccm.edu.gestionidentidades.application.domain;


public class ResultadoValidacionCredencialesAcceso {
    
    private boolean valido;
    
    private MotivoNoValidezCredencialesAcceso motivoNoValido;
    
    public ResultadoValidacionCredencialesAcceso(boolean valido, MotivoNoValidezCredencialesAcceso motivoRechazo) {
        this.valido=valido;
        this.motivoNoValido=motivoRechazo;
    }
    
    public boolean isValido() {
        return this.valido;
    }
    
    public MotivoNoValidezCredencialesAcceso getMotivoNoValido() {
        return this.motivoNoValido;
    }
    
    public String toString() {
        if(valido) {
            return "Credenciales válidas";
        }else {
            return "Credenciales no validas. "+motivoNoValido.toString();
        }
    }

}
