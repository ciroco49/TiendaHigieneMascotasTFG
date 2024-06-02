//package com.ciroiencom.tfg.modelo.DTO;
//
//import com.ciroiencom.tfg.modelo.Cuenta;
//import jakarta.persistence.Column;
//import jakarta.persistence.Lob;
//
//import java.util.Base64;
//
//public class CuentaDTO {
//    private String correo;
//    private String contrasenha;
//    private byte[] imagen;
//
//    public CuentaDTO() {
//    }
//
//    public CuentaDTO(String correo, String contrasenha, byte[] imagen) {
//        this.correo = correo;
//        this.contrasenha = contrasenha;
//        this.imagen = imagen;
//    }
//
//    public String getCorreo() {
//        return correo;
//    }
//
//    public void setCorreo(String correo) {
//        this.correo = correo;
//    }
//
//    public String getContrasenha() {
//        return contrasenha;
//    }
//
//    public void setContrasenha(String contrasenha) {
//        this.contrasenha = contrasenha;
//    }
//
//    public byte[] getImagen() {
//        return imagen;
//    }
//
//    public void setImagen(byte[] imagen) {
//        this.imagen = imagen;
//    }
//
//    public static Cuenta cuentaDTOaCuenta(CuentaDTO cuentaDTO) {
//        Cuenta cuenta = new Cuenta();
//            cuenta.setCorreo(cuentaDTO.getCorreo());
//            cuenta.setContrasenha(cuentaDTO.getContrasenha());
//            //Convierto el array de bytes de la imagen a una String
//            cuenta.setImagen(Base64.getEncoder().encodeToString(cuentaDTO.getImagen()));
//
//        return cuenta;
//    }
//
//    public static CuentaDTO cuentaACuentaDTO(Cuenta cuenta) {
//        CuentaDTO cuentaDTO = new CuentaDTO();
//            cuentaDTO.setCorreo(cuenta.getCorreo());
//            cuentaDTO.setContrasenha(cuenta.getContrasenha());
//            //Convierto la String de la imagen a un array de bytes si la imagen no es nula en la BD
//            if(cuenta.getImagen() != null) {
//                cuentaDTO.setImagen(Base64.getDecoder().decode(cuentaDTO.getImagen()));
//            } else {
//                cuentaDTO.setImagen(null);
//            }
//
//        return cuentaDTO;
//    }
//
//}
