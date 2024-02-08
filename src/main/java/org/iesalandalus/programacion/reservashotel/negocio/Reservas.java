package org.iesalandalus.programacion.reservashotel.negocio;

import org.iesalandalus.programacion.reservashotel.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservas {
    private int capacidad;
    private int tamano;
    private Reserva[] coleccionReservas;

    public Reservas(int capacidad){
        if (capacidad < 1)
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");

        coleccionReservas = new Reserva[capacidad];
        this.capacidad = capacidad;
        this.tamano = 0;
    }

    public Reserva[] get(){
        return copiaProfundaReservas();
    }

    private Reserva[] copiaProfundaReservas(){
        if (coleccionReservas==null)
            throw new NullPointerException("La colecci�n no ha sido creada a�n");

        Reserva[] copia = new Reserva[this.capacidad];
        for (int i=0; i < tamano; i++){
            copia[i]=new Reserva(coleccionReservas[i]);
        }
        return copia;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getTamano() {
        return tamano;
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException{
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        if (buscarIndice(reserva) != -1)
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        if (capacidadSuperada(tamano)) //validado para negativo
            throw new OperationNotSupportedException("ERROR: No se aceptan m�s reservas.");

        coleccionReservas[tamano] = new Reserva(reserva);
        tamano++;
    }

    private int buscarIndice(Reserva reserva) {
        if (reserva == null)
            throw new NullPointerException("La reserva a buscar no puede ser negativa (buscarIndice)");

        int indice = -1;

        for (int i = 0; i < tamano; i++) {
            if (coleccionReservas[i].equals(reserva))
                indice = i;
        }

        return indice;
    }

    private boolean tamanoSuperado(int indice){
        if (indice < 0)
            throw new IllegalArgumentException("El �ndice no puede ser negativo(tama�o)");
        return (indice>tamano);
    }

    private boolean capacidadSuperada(int indice){
        if (indice < 0)
            throw new IllegalArgumentException("El �ndice no puede ser negativo(capacidad)");

        return (indice>=capacidad);
    }

    public Reserva buscar(Reserva reserva){
        if (reserva == null)
            throw new NullPointerException("La reserva a buscar es Nula");

        if (buscarIndice(reserva) != -1)
            return coleccionReservas[buscarIndice(reserva)];
        else
            return null;

    }

    public void borrar(Reserva reserva)throws OperationNotSupportedException{
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        if (buscarIndice(reserva) == -1)
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");

        int posicionBorrado = buscarIndice(reserva);

        coleccionReservas[posicionBorrado] = null;
        if (posicionBorrado != tamano-1){
            for (int i = posicionBorrado; i< tamano-1; i++)
                desplazarUnaPosicionHaciaIzquierda(i);
        }
        tamano--;


    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice){
        if (tamanoSuperado(indice)) //validado para negativo
            throw new IllegalArgumentException("El �ndice indicado supera el tama�o");

        coleccionReservas[indice] = new Reserva(coleccionReservas[indice+1]);
        coleccionReservas[indice+1] = null;

    }

    public Reserva[] getReservas(Huesped huesped){
        if (coleccionReservas==null)
            throw new NullPointerException("La colecci�n no ha sido creada a�n");
        if (huesped == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo.");

        Reserva[] copiaEspecial = new Reserva[this.capacidad];
        int posicionVacia=0;

        for (int i=0; i < tamano; i++){

            if (coleccionReservas[i].getHuesped().equals(huesped)) {

                copiaEspecial[posicionVacia] = new Reserva(coleccionReservas[i]);
                posicionVacia++;
            }
        }
        return copiaEspecial;

    }
    public Reserva[] getReservas(TipoHabitacion tipoHabitacion){
        if (coleccionReservas==null)
            throw new NullPointerException("La colecci�n no ha sido creada a�n");
        if (tipoHabitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitaci�n nula.");

        Reserva[] copiaEspecial = new Reserva[this.capacidad];
        int posicionVacia=0;

        for (int i=0; i < tamano; i++){
            if (coleccionReservas[i].getHabitacion().getTipoHabitacion().equals(tipoHabitacion)) {
                copiaEspecial[posicionVacia] = new Reserva(coleccionReservas[i]);
                posicionVacia++;
            }
        }
        return copiaEspecial;
    }
    public Reserva[] getReservasFuturas(Habitacion habitacion){
        if (coleccionReservas==null)
            throw new NullPointerException("La colecci�n no ha sido creada a�n");
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitaci�n nula.");

        Reserva[] copiaEspecial = new Reserva[this.capacidad];
        int posicionVacia=0;
        for (int i=0; i < tamano; i++){
            if (coleccionReservas[i].getFechaInicioReserva().isAfter(LocalDate.now()) && coleccionReservas[i].getHabitacion().equals(habitacion)) {
                copiaEspecial[posicionVacia] = new Reserva(coleccionReservas[i]);
                posicionVacia++;
            }
        }
        return copiaEspecial;
    }
}
