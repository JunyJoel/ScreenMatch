package com.alura.screenmatch.excepcion;

public class ErrorEnConversionDuracionException extends RuntimeException {
    private String mensaje;

    public ErrorEnConversionDuracionException(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getMessage() {
        return this.mensaje;
    }
}
