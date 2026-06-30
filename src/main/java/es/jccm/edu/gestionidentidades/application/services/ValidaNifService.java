package es.jccm.edu.gestionidentidades.application.services;
import org.springframework.stereotype.Service;

import es.jccm.edu.gestionidentidades.application.ports.in.ValidaNifServicePortIn;

import java.util.Random;

/**
 * Clase que permite validar nifs y nies (Personas Físicas)
 *
 * Realiza validaciones de Nifs lo más permisivas posibles, permitiendo validar
 * incluso nifs aún cuando no se meta la letra. Se traga incluso nifs con
 * caracteres extraños (puntos, comas espacios). 
 * Es capaz de validar nies extranjeros que empiecen por X, Y o Z, K, L y M
 *
 * FORMATOS:
 * DNI-D: (DNI GENERAL)
 * X-NUM-D, Y-NUM-D, Z-NUM-D: 
 * K-NUM-D: Menores de 14 años y residentes en España
 * L-NUM-D: Residentes en el extranjero
 * M-NUM-D: Que no dispongan de NIE, transitoria o definitivamente
 *
 * Permite detectar si metes el nif con el digito de control mal.
 *
 * Las dos funciones principales son validarNif, que simplemente retorna un
 * booleano, y limpiarNif, que formatea el nif para obtener el string sin espacios y 
 * letras extrañas y con la letra del dígito de control calculada
 *
 * Lo ideal para usar es: 1º) validar el dato introducido el usuario y 2º) limpiar
 * el dato para guardarlo en base de datos con formato estandar
 *
 * @author jmartind
 *
 *
 */
@Service
public class ValidaNifService implements ValidaNifServicePortIn {

    // Caracteres a mostrar en un nif: son 4 según la ley orgánica 3/2018
    public static final int CARACTERES_A_MOSTRAR = 4;

    /**
     * Indica si un nif es correcto. Solo comprueba si el dígito de control es correcto.
     * Esta función no indica si el nif tiene formato correcto. El nif puede tener
     * espacios o caracteres extraños. Si se quiere tratar el nif (guardar en base de datos
     * o enviar a otro sistema externo),  es aconsejable obtener el nif limpio
     * llamando a la funcion limpiarNif 
     *
     * @param nif nif a validar
     *
     * @return true si el dígito de control es correcto
     */
    public static boolean validarNif(String nif) {
        return calcularLetraNif(obtenerNumeroNif(nif)) == obtenerLetraNif(nif);
    }

    /**
     * Valida el nif/nie limpiándolo previamente. 
     * El nif solo es correcto si coincide exactamente con el nif limpio 
     * @param nif nif a validar
     *
     * @return true si el nif pasado está limpio y es válido.
     */
    public boolean validarSinLimpiar(String nif) {
        return ValidaNifService.limpiarNif(nif).equals(nif) && ValidaNifService.validarNif(nif);
    }

    /**
     * Elimina caracteres extraños de la cadena que se pasa 
     *
     * @param cadena cadena con posible nif
     *
     * @return un nif correctamente formado con el dígito de control correcto que le correspondería
     */
    public static String limpiarNif(String cadena) {
        String digitos = obtenerDigitos(cadena);
        char letra = ValidaNifService.obtenerLetraNif(cadena);
        String nif = digitos + letra;

        if (esTipoNifEspecial(cadena)) {
            nif = "" + getTipoNif(cadena) + nif.substring(1);
        }
        return nif;
    }

    /**
     *
     * Retorna la primera letra del nif si es de tipo especial
     *
     * @param cadena
     * @return
     * @deprecated usar getTipoNif
     */
    @Deprecated
    public static char getLetraExtranjero(String cadena) {
        return getTipoNif(cadena);
    }

    /**
     * Obtiene la primera letra del nif, sirve para obtener el tipo de nif, si es 
     * extranjero o especial
     *
     * @param cadena
     * @return
     */
    public static char getTipoNif(String cadena) {
        String cadenaTrim = cadena.trim();
        return Character.toUpperCase(cadenaTrim.charAt(0));
    }

    /**
     *
     * @param cadena
     * @return
     * @deprecated usar esTipoNifEspecial
     */
    @Deprecated
    public static boolean esNifExtranjero(String cadena) {
        return esTipoNifEspecial(cadena);
    }

    private static final String TIPOS_ESPECIALES[]= {"X","Y","Z","K","L","M"};

    /**
     * @param cadena
     * @return true si la cadena tiene pinta de ser un nif especial
     */
    public static boolean esTipoNifEspecial(String cadena) {
        String cadenaTrim = cadena.trim().toUpperCase();

        for(String tipoEspecial:TIPOS_ESPECIALES) {
            if(cadenaTrim.startsWith(tipoEspecial)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Obtiene el dígito equivalente para el cálculo del digito de control de una letra
     * que identifica el tipo de nif
     *
     * @param letra
     * @return
     * @deprecated usar getDigitoEquivalenteDeLetraTipoNif
     *
     */
    @Deprecated
    public static int getDigitoEquivalenteDeLetraExtranjera(char letra) {
        return getDigitoEquivalenteDeLetraTipoNif(letra);
    }


    /**
     * Obtiene el dígito equivalente para el cálculo del digito de control de una letra
     * que identifica el tipo de nif
     *
     *  X -> 0 Y -> 1 Z -> 2
     *
     * @param letraTipoNif
     * @return
     */
    public static int getDigitoEquivalenteDeLetraTipoNif(char letraTipoNif) {
        switch (letraTipoNif) {
            case ('X'):
                return 0;
            case ('Y'):
                return 1;
            case ('Z'):
                return 2;
            default:
                return 0;
        }
    }

    /**
     * Obtiene el numero del nif.
     *
     * Si es extranjero obtiene el numero nif equivalente, sustituyendo la letra
     * de extranjero por su numero equivalente (getDigitoEquivalenteDeLetraTipoNif):
     *
     * @param nif
     * @return
     */
    public static long obtenerNumeroNif(String nie) {
        String num = obtenerDigitos(nie);

        if (esTipoNifEspecial(nie)) {
            int digitoEquivalente = getDigitoEquivalenteDeLetraTipoNif(getTipoNif(nie));
            num = "" + digitoEquivalente + num.substring(1);
        }

        try {
            return Long.parseLong(num);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     *
     * @param nif
     * @return una cadena sólo con los digitos. Si no tiene 9 digitos, rellena
     *         con ceros al principio
     */
    private static String obtenerDigitos(String cadena) {
        String num = new String();

        for (int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.charAt(i);

            if (Character.isDigit(caracter))
                num = num + caracter;
        }

        while (num.length() < 8)
            num = "0" + num;

        return num;
    }

    /**
     * Obtiene la letra (digito de control), que tiene como nif la cadena que 
     * se manda.
     * la letra del nif es la primera letra que se encuentra en la cadena.
     *
     * Si no aparece ninguna, la calcula a partir de los números.
     *
     * Esta función solo vale para nifs normales de tipo DNI 
     * (no vale para nies especiales extranjeros, que
     * tengan letra X, Y o Z por delante). Para estos casos habría que llamar
     * primero a la función obtenerNumeroNif("X334222J")->"0334222J", y entonces
     * llamar a esta función.
     *
     * @param nifSinLetraPorDelante
     * @return
     */
    public static char obtenerLetraNif(String nifSinLetraPorDelante) {

        for (int i = nifSinLetraPorDelante.length() - 1; i >= 0; i--) {
            char caracter = nifSinLetraPorDelante.charAt(i);

            if (Character.isLetter(caracter))
                return Character.toUpperCase(caracter);
            if (Character.isDigit(caracter))
                break;
        }

        // Si no ha puesto la letra, se calcula y se pone
        return ValidaNifService.calcularLetraNif(obtenerNumeroNif(nifSinLetraPorDelante));

    }

    /**
     *
     * @param numero
     *
     * @return el dígito de control que sirve para chequear que se ha escrito bien 
     */
    public static char calcularLetraNif(long numero) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";

        return letras.charAt((int) (numero % 23));
    }


    /**
     * Oculta parte del nif de manera aleatoria.
     *
     * Previsto para ser utilizado en pantallas y/o listados por lo que la salida será de logitud fija de 9 caracteres.
     *
     * Se mostrarán 4 caracteres aleatorios según la ley orgánica 3/2018, al resto del nif se le aplicará la máscara.
     *
     * @param nif
     *            DNI o NIE a ocultar (debe tener al menos 8 caracteres)
     * @param mascara
     *            carácter que se utilizará para ocultar parte del nif
     * @return nif con caracteres ocultos usando la máscara
     */
    public static String ocultarNifAleatorio(String nif, char mascara) {
        // Se hace un trim sobre el nif y se comprueba que al menos tenga 8 caracteres
        if (nif.trim().length() >= 8) {
            nif = nif.trim();
        } else {
            throw new IllegalArgumentException("El nif a ocultar debe tener al menos 8 caracteres.");
        }

        // Se inicializa el resultado con los caracteres de la máscara
        char[] nifResultado = new char[] { mascara, mascara, mascara, mascara, mascara, mascara, mascara, mascara };

        // Si el primer caracter no es un dígito (letra NIE) no debe ser mostrado, por lo que se avanza el índice de
        // caracteres a analizar en una posición.
        int index = 0;
        if (!Character.isDigit(nif.charAt(index))) {
            index++;
        }

        // Como semilla de aleatoriedad se va a utilizar el propio número de identificación para que siempre oculte las
        // mismas posiciones.
        Random aleatorio;
        try {
            aleatorio = new Random(Long.parseLong(nif.substring(index, 8)));
        } catch (Exception e) {
            throw new IllegalArgumentException("El nif a ocultar no tiene un formato válido.");
        }

        // Al tener que ocultar posiciones aleatorias es necesario evaluar para cada carácter
        for (int mostrados = 0; mostrados < ValidaNifService.CARACTERES_A_MOSTRAR && index < nifResultado.length; index++) {
            // Si el número de caracteres que quedan por mostrar es menor o igual a los que quedan por evaluar se
            // tendrán que mostrar todos los que quedan. Sino se decide aleatoriamente si mostrar o no ese carácter.
            if (ValidaNifService.CARACTERES_A_MOSTRAR - mostrados >= nifResultado.length - index
                    || aleatorio.nextBoolean()) {
                nifResultado[index] = nif.charAt(index);
                mostrados++;
            }
        }

        // Se devuelven los 8 caracteres calculados y en se añade la máscara ya que la letra final siempre se oculta
        return new String(nifResultado) + mascara;
    }

    /**
     * Oculta el identificador según la recomendación publicada por la Agencia Española de Protección de Datos del
     * 27/02/2019: Orientación para la aplicación provisional de la disposición adicional séptima de la LOPDGDD
     *
     * @author Emilio Guerrero Guerrero
     * @param nif
     *            DNI o NIE a ocultar
     * @param numeroInicial
     *            número del primer dígito que será visible (según sorteo realizado)
     * @param numeroVisibles
     *            cantidad de dígitos que serán visibles
     * @param mascara
     *            caracter con el que se enmascararán los caracteres no visibles
     * @return nif con caracteres ocultos usando la máscara
     */
    public static String ocultarNifAEPD(String nif, int numeroInicial, int numeroVisibles, char mascara) {
        // número de dígitos que contiene el identificador
        final int numeroDigitos = nif.replaceAll("[^0-9]", "").length();
        // número del primer dígito que debe ser visible
        final int numeroPrimerVisible = Math.min(numeroInicial, numeroDigitos - numeroVisibles + 1);
        // número del último dígito que debe ser visible
        final int numeroUltimoVisible = Math.min(numeroPrimerVisible + numeroVisibles - 1, numeroDigitos);

        StringBuffer resultado = new StringBuffer();
        int numeroActual = 0; // número del dígito actual
        for (final char caracter : nif.toCharArray()) // recorrer todos los caracteres
        {
            if (caracter >= '0' && caracter <= '9') // el carácter es un dígito
            {
                ++numeroActual; // actualizar la posición del dígito actual
                // añadir a resultado el dígito o la máscara
                resultado.append(numeroActual >= numeroPrimerVisible && numeroActual <= numeroUltimoVisible ? caracter
                        : mascara);
            } else // el carácter no es un dígito
            {
                resultado.append(mascara); // añadir a resultado la máscara
            }
        }

        // Los identificadores que no lleguen a 9 caracteres (posibles DNIs sin letra) se completan con la máscara, para
        // que la salida sea uniforme
        while (resultado.length() < 9) {
            resultado.append(mascara);
        }

        return resultado.toString();
    }

    /**
     * Oculta el nif en cumplimiento de la LOPDGDD
     *
     * @param nif
     *            DNI o NIE a ocultar (debe tener al menos 8 caracteres)
     * @param mascara
     *            carácter que se utilizará para ocultar parte del nif
     * @return nif con caracteres ocultos usando la máscara
     */
    public static String ocultarNif(String nif, char mascara) {

        // Para ocultar el nif utiliza la función recomendada por la AEPD
        // El número inicial a mostrar es el 4 que fue el resultante del sorteo realizado por la Agencia el 27/02/2019
        int numeroInicialSorteo = 4;
        return ValidaNifService.ocultarNifAEPD(nif, numeroInicialSorteo, CARACTERES_A_MOSTRAR, mascara);
    }

}