package com.ciroiencom.tfg.modelo.DTO;

import com.ciroiencom.tfg.modelo.Cliente;
import com.ciroiencom.tfg.modelo.Mascota;
import com.ciroiencom.tfg.modelo.Tener;

public class TenerDTO {
    private Cliente cliente;
    private MascotaDTO mascota;

    public TenerDTO() {
    }

    public TenerDTO(Cliente cliente, MascotaDTO mascota) {
        this.cliente = cliente;
        this.mascota = mascota;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public MascotaDTO getMascota() {
        return mascota;
    }

    public void setMascota(MascotaDTO mascota) {
        this.mascota = mascota;
    }

    public static TenerDTO tenerATenerDTO(Tener tener) {
        TenerDTO tenerDTO = new TenerDTO();
            tenerDTO.setCliente(tener.getDNI_Cliente());
            tenerDTO.setMascota(MascotaDTO.mascotaAMascotaDTO(tener.getDNI_Mascota()));
        return tenerDTO;
    }

}
