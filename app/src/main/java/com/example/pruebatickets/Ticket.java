package com.example.pruebatickets;

public class Ticket {
    private String idTicket;
    private String tituloTicket;
    private String fechaCreacion;
    private String nombreResponsable;
    private String equipoResponsable;
    private String tipoIncidencia;
    private String gravedad;
    private String versionSoftware;
    private String descripcion;

    private int estatus;

    public Ticket() {
    }

    public Ticket(String idTicket, String tituloTicket, String fechaCreacion, String nombreResponsable, String equipoResponsable, String tipoIncidencia, String gravedad, String versionSoftware, String descripcion, int estatus) {
        this.idTicket = idTicket;
        this.tituloTicket = tituloTicket;
        this.fechaCreacion = fechaCreacion;
        this.nombreResponsable = nombreResponsable;
        this.equipoResponsable = equipoResponsable;
        this.tipoIncidencia = tipoIncidencia;
        this.gravedad = gravedad;
        this.versionSoftware = versionSoftware;
        this.descripcion = descripcion;
        this.estatus = estatus;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    public String getTituloTicket() {
        return tituloTicket;
    }

    public void setTituloTicket(String tituloTicket) {
        this.tituloTicket = tituloTicket;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getEquipoResponsable() {
        return equipoResponsable;
    }

    public void setEquipoResponsable(String equipoResponsable) {
        this.equipoResponsable = equipoResponsable;
    }

    public String getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(String tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }

    public String getVersionSoftware() {
        return versionSoftware;
    }

    public void setVersionSoftware(String versionSoftware) {
        this.versionSoftware = versionSoftware;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "idTicket='" + idTicket + '\'' +
                ", tituloTicket='" + tituloTicket + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", nombreResponsable='" + nombreResponsable + '\'' +
                ", equipoResponsable='" + equipoResponsable + '\'' +
                ", tipoIncidencia='" + tipoIncidencia + '\'' +
                ", gravedad='" + gravedad + '\'' +
                ", versionSoftware='" + versionSoftware + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estatus='" + estatus + '\'' +
                '}';
    }
}
