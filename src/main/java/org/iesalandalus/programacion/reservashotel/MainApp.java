package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.dominio.*;
import org.iesalandalus.programacion.reservashotel.negocio.Habitaciones;
import org.iesalandalus.programacion.reservashotel.negocio.Huespedes;
import org.iesalandalus.programacion.reservashotel.negocio.Reservas;
import org.iesalandalus.programacion.reservashotel.vista.Consola;
import org.iesalandalus.programacion.reservashotel.vista.Opcion;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class MainApp {
    public final static int  CAPACIDAD=6;
    private static Habitaciones habitaciones = new Habitaciones(CAPACIDAD);
    private static Reservas reservas = new Reservas(CAPACIDAD);;
    public static Huespedes huespedes = new Huespedes(CAPACIDAD);
    private static boolean salir = false;

    private static void ejecutarOpcion(Opcion opcion){

        if (opcion == null)
            throw new IllegalArgumentException("Opción nula");
        switch (opcion){
            case BUSCAR_HABITACION -> buscarHabitacion();
            case BORRAR_HUESPED -> borrarHuesped();
            case INSERTAR_HUESPED -> insertarHuesped();
            case ANULAR_RESERVA -> anularReserva();
            case BUSCAR_HUESPED -> buscarHuesped();
            case INSERTAR_RESERVA -> insertarReserva();
            case MOSTRAR_RESERVAS -> mostrarReservas();
            case BORRAR_HABITACION -> borrarHabitacion();
            case MOSTRAR_HUESPEDES -> mostrarHuespedes();
            case INSERTAR_HABITACION -> insertarHabitacion();
            case MOSTRAR_HABITACIONES -> mostrarHabitaciones();
            case CONSULTAR_DISPONIBILIDAD -> System.out.println(consultarDisponibilidad(Consola.leerTipoHabitacion(), Consola.leerFecha(Entrada.cadena()), Consola.leerFecha(Entrada.cadena())));
            case SALIR -> salir=true;
            case DEBUG -> debug();
        }
    }

    private static void insertarHuesped(){
        try{
            huespedes.insertar(Consola.leerHuesped());
            System.out.println(" ");
            System.out.println("*****");
            System.out.println("Huésped insertado!!!");
            System.out.println("*****");
            System.out.println(" ");
        }catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }


    }

    private static void buscarHuesped(){
        System.out.println(huespedes.buscar(Consola.getHuespedPorDni()));
    }

    private static void borrarHuesped(){
        try{
            huespedes.borrar(Consola.getHuespedPorDni());
            System.out.println(" ");
            System.out.println("*****");
            System.out.println("Huesped borrado!!!");
            System.out.println("*****");
            System.out.println(" ");

        }catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }



    }

    private static void mostrarHuespedes(){
        Huesped [] lista;
        lista = huespedes.get();
        System.out.println(" ");
        System.out.println("*****");
        for (int i=0; i< huespedes.getTamano(); i++) {

            if (lista[i] != null)
                System.out.println(lista[i]);
        }
        System.out.println("*****");
        System.out.println(" ");
    }

    private static void insertarHabitacion(){
        try{
            habitaciones.insertar(Consola.leerHabitacion());
            System.out.println(" ");
            System.out.println("*****");
            System.out.println("Habitación insertada!!!");
            System.out.println("*****");
            System.out.println(" ");
        }catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }

    private static void buscarHabitacion(){
        try{
            System.out.println(habitaciones.buscar(Consola.leerHabitacionPorIdentificador()));

        }catch (NullPointerException|IllegalArgumentException e){
            System.out.println(e.getMessage());

        }
    }

    private static void borrarHabitacion(){
        try{
            habitaciones.borrar(Consola.leerHabitacionPorIdentificador());
            System.out.println(" ");
            System.out.println("*****");
            System.out.println("Habitación borrada!!!");
            System.out.println("*****");
            System.out.println(" ");
        }catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }

    }

    private static void mostrarHabitaciones(){
        Habitacion [] lista;
        lista = habitaciones.get();
        System.out.println(" ");
        System.out.println("*****");
        for (int i=0; i< habitaciones.getTamano(); i++) {

            if (lista[i] != null)
                System.out.println(lista[i]);
        }
        System.out.println("*****");
        System.out.println(" ");

    }
    private static void insertarReserva(){
        Reserva habitacionDeseada = Consola.leerReserva();
        if (consultarDisponibilidad(habitacionDeseada.getHabitacion().getTipoHabitacion(), habitacionDeseada.getFechaInicioReserva(), habitacionDeseada.getFechaFinReserva()) == null) {

            try {
                reservas.insertar(habitacionDeseada);
                System.out.println(" ");
                System.out.println("*****");
                System.out.println("Reserva insertada!!!");
                System.out.println("*****");
                System.out.println(" ");
            } catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e) {
                System.out.println(e.getMessage());
            }

        } else {
            System.out.println("No está disponible");
        }

    }

    private void listarReservas(Huesped huesped){
        Reserva [] lista;
        lista = reservas.getReservas(huesped);
        for (int i=0; i< reservas.getTamano(); i++) {

            if (lista[i] != null)
                System.out.println(lista[i]);
        }
    }

    private void listarReservas(TipoHabitacion tipoHabitacion){
        Reserva [] lista;
        lista = reservas.getReservas(tipoHabitacion);
        for (int i=0; i< reservas.getTamano(); i++) {

            if (lista[i] != null)
                System.out.println(lista[i]);
        }
    }

    private void listarReservas(LocalDate fecha){
        Reserva [] lista;
        lista = reservas.get();
        for (int i=0; i< reservas.getTamano(); i++) {

            if (fecha.isAfter(lista[i].getFechaInicioReserva()) && fecha.isBefore(lista[i].getFechaFinReserva()))
                System.out.println(lista[i]);
            else
                System.out.println("No hay reservas ese día");
        }
    }

    private static Reserva[] getReservasAnulables(Reserva[] reservasAAnular){

        int tamano = 0;
        for (Reserva reserva:reservasAAnular)
            if (reserva != null)
                tamano++;

        Reserva[] listaAnulables = new Reserva[tamano];

        int posicion=0;

        for (int i=0; i < listaAnulables.length; i++){
            if (reservasAAnular[i].getFechaInicioReserva().isAfter(LocalDate.now())) {

                listaAnulables[posicion] = new Reserva(reservasAAnular[i]);
                posicion++;
            }
        }
        //for (Reserva reserva:listaAnulables)
        //    System.out.println("debug: listaanulables"+reserva);
        return listaAnulables;
    }


    private static void debug(){
        Huesped huesped1 = new Huesped("pepe felices", "22222222J", "aaaa@aaaa.com", "950262626", LocalDate.of(1950,1,1));
        Huesped huesped2 = new Huesped("carlos salfredo", "11111111H", "bbbb@bbbb.com", "650151515", LocalDate.of(1975,1,1));
        Habitacion habitacion1 = new Habitacion(1,1,100,TipoHabitacion.TRIPLE);
        Habitacion habitacion2 = new Habitacion(1,2,100,TipoHabitacion.TRIPLE);
        LocalDate inicio1 = LocalDate.of(2024,2,15);
        LocalDate fin1 = LocalDate.of(2024,2,20);
        LocalDate inicio2 = LocalDate.of(2024,4,15);
        LocalDate fin2 = LocalDate.of(2024,4,20);
        Reserva reserva1 = new Reserva(huesped1, habitacion1, Regimen.MEDIA_PENSION, inicio1, fin1, 2);
        Reserva reserva2 = new Reserva(huesped2, habitacion2, Regimen.MEDIA_PENSION, inicio1, fin1, 2);
        Reserva reserva3 = new Reserva(huesped1, habitacion2, Regimen.MEDIA_PENSION, inicio2, fin2, 2);
        Reserva reserva4 = new Reserva(huesped2, habitacion1, Regimen.MEDIA_PENSION, inicio2, fin2, 2);

        try {
            huespedes.insertar(huesped1);
            huespedes.insertar(huesped2);
            habitaciones.insertar(habitacion1);
            habitaciones.insertar(habitacion2);
            reservas.insertar(reserva1);
            reservas.insertar(reserva2);
            reservas.insertar(reserva3);
            reservas.insertar(reserva4);
        }catch(OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
        System.out.println("***DATOS DE PRUEBA INSERTADOS***");
    }

    private static void anularReserva(){

        Huesped cliente = Consola.getHuespedPorDni();
        Reserva [] lista = reservas.getReservas(cliente);
        //System.out.println("DEBUG: length "+lista.length);

        //for (Reserva reserva:lista)
        //    System.out.println("DEBUG: anular"+reserva);

        lista = getReservasAnulables(lista);

        if (getNumElementosNoNulos(lista) == 0)
            System.out.println("No tiene reservas anulables");
        else if (getNumElementosNoNulos(lista) == 1) {
            String respuesta;

            do {
                System.out.println("Confirma que quiere eliminar la reserva? (Si/No)");
                respuesta = Entrada.cadena();
            } while (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no"));


            if (respuesta.equalsIgnoreCase("si"))
                try {
                    reservas.borrar(lista[0]);
                    System.out.println("**Reserva Eliminada!!!**");
                } catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e){
                    System.out.println(e.getMessage());
                }


        } else {
            //mostrar todas las posibilidades
            for  (int i = 0; i< getNumElementosNoNulos(lista) ; i++)
                System.out.println(i + " - " + lista[i]);

            //elegir option
            System.out.println("-------------");
            System.out.print("Elija cual desea borrar: ");
            int eleccion;
            do{
                eleccion = Entrada.entero();
            }while (eleccion <0 || eleccion > lista.length);

            //borrar reserva de la colección usando la posición de la lista nueva.
            try{
                reservas.borrar(lista[eleccion]);
                System.out.println("**Reserva Eliminada!!!**");
            }catch (NullPointerException|IllegalArgumentException|OperationNotSupportedException e){
                System.out.println(e.getMessage());
            }

        }
    }

    private static void mostrarReservas(){
        Reserva [] lista;
        lista = reservas.get();
        System.out.println(" ");
        System.out.println("*****");
        for (int i=0; i< reservas.getTamano(); i++) {

            if (lista[i] != null)
                System.out.println(lista[i]);
        }
        System.out.println("*****");
        System.out.println(" ");
    }

    private static Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva)
    {
        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        int numElementos=0;

        Habitacion[] habitacionesTipoSolicitado= habitaciones.get(tipoHabitacion);

        if (habitacionesTipoSolicitado==null)
            return habitacionDisponible;

        for (int i=0; i<habitacionesTipoSolicitado.length && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado[i]!=null)
            {
                Reserva[] reservasFuturas = reservas.getReservasFuturas(habitacionesTipoSolicitado[i]);
                numElementos=getNumElementosNoNulos(reservasFuturas);

                if (numElementos == 0)
                {
                    //Si la primera de las habitaciones encontradas del tipo solicitado no tiene reservas en el futuro,
                    // quiere decir que está disponible.
                    habitacionDisponible=new Habitacion(habitacionesTipoSolicitado[i]);
                    tipoHabitacionEncontrada=true;
                }
                else {

                    //Ordenamos de mayor a menor las reservas futuras encontradas por fecha de fin de la reserva.
                    // Si la fecha de inicio de la reserva es posterior a la mayor de las fechas de fin de las reservas
                    // (la reserva de la posición 0), quiere decir que la habitación está disponible en las fechas indicadas.

                    Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaFinReserva).reversed());

                    /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                    mostrar(reservasFuturas);*/

                    if (fechaInicioReserva.isAfter(reservasFuturas[0].getFechaFinReserva())) {
                        habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                        tipoHabitacionEncontrada = true;
                    }

                    if (!tipoHabitacionEncontrada)
                    {
                        //Ordenamos de menor a mayor las reservas futuras encontradas por fecha de inicio de la reserva.
                        // Si la fecha de fin de la reserva es anterior a la menor de las fechas de inicio de las reservas
                        // (la reserva de la posición 0), quiere decir que la habitación está disponible en las fechas indicadas.

                        Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaInicioReserva));

                        /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                        mostrar(reservasFuturas);*/

                        if (fechaFinReserva.isBefore(reservasFuturas[0].getFechaInicioReserva())) {
                            habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                            tipoHabitacionEncontrada = true;
                        }
                    }

                    //Recorremos el array de reservas futuras para ver si las fechas solicitadas están algún hueco existente entre las fechas reservadas
                    if (!tipoHabitacionEncontrada)
                    {
                        for(int j=1;j<reservasFuturas.length && !tipoHabitacionEncontrada;j++)
                        {
                            if (reservasFuturas[j]!=null && reservasFuturas[j-1]!=null)
                            {
                                if(fechaInicioReserva.isAfter(reservasFuturas[j-1].getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas[j].getFechaInicioReserva())) {

                                    habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }


                }
            }
        }

        return habitacionDisponible;
    }


    private static int getNumElementosNoNulos(Reserva[] reservas){
        int numero=0;
        for (int i=0; i<reservas.length; i++) {
            if (reservas[i] != null)
                numero++;
        }


        return numero;
    }
    
    public static void main(String[] args) {


        while(!salir) {
            try {
                Consola.mostrarMenu();
                ejecutarOpcion(Consola.elegirOpcion());

            }catch (IllegalArgumentException|NullPointerException|IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("\n***\nHasta luego!!!!\n***");
    }
}
