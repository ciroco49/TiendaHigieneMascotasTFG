package com.example.tiendahigienemascotas.BBDD.entidades;

public class Imagen {
    private int ID;
    private byte[] imagenBytes;

    public Imagen() {
    }

    public Imagen(byte[] imagenBytes) {
        this.imagenBytes = imagenBytes;
    }

    public Imagen(int ID, byte[] imagenBytes) {
        this.ID = ID;
        this.imagenBytes = imagenBytes;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public byte[] getImagenBytes() {
        return imagenBytes;
    }

    public void setImagenBytes(byte[] imagenBytes) {
        this.imagenBytes = imagenBytes;
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "imagenBytes='" + this.imagenBytes + '\'' +
                '}';
    }

}
