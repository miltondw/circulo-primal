package services;

import java.util.Scanner;
import exceptions.NombreInvalidoException;
import exceptions.OpcionInvalidaException;
import ui.UIService;

public class InputService {
    private final Scanner sc;
    private final UIService uiService;

    public InputService(Scanner sc) {
        this.sc = sc;
        this.uiService = new UIService();
    }

    public int seleccionarTipoJuego() throws OpcionInvalidaException {
        while (true) {
            try {
                uiService.mostrarMenuPrincipal();
                int opcion = Integer.parseInt(sc.nextLine());
                
                if (opcion != 1 && opcion != 2) {
                    throw new OpcionInvalidaException("Debes elegir 1 o 2");
                }
                return opcion;
            } catch (NumberFormatException e) {
                uiService.mostrarError("Debes ingresar un número (1 o 2)");
            } catch (OpcionInvalidaException e) {
                uiService.mostrarError(e.getMessage());
            }
        }
    }

    public String obtenerNombreJugador(String mensaje) throws NombreInvalidoException {
        while (true) {
            try {
                System.out.print(mensaje);
                String nombre = sc.nextLine();
                
                if (nombre == null || nombre.trim().isEmpty()) {
                    throw new NombreInvalidoException("El nombre no puede estar vacío");
                }
                
                if (nombre.trim().length() < 2) {
                    throw new NombreInvalidoException("El nombre debe tener al menos 2 caracteres");
                }
                
                return nombre.trim();
            } catch (NombreInvalidoException e) {
                uiService.mostrarError(e.getMessage());
            }
        }
    }

    public int obtenerNumeroRondas(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int rondas = Integer.parseInt(sc.nextLine());
                
                if (rondas <= 0) {
                    uiService.mostrarError("El número de rondas debe ser mayor a 0");
                    continue;
                }
                
                if (rondas > 100) {
                    uiService.mostrarError("El número de rondas no puede ser mayor a 100");
                    continue;
                }
                
                return rondas;
            } catch (NumberFormatException e) {
                uiService.mostrarError("Debes ingresar un número válido");
            }
        }
    }

    public int obtenerOpcionElemento(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int opcion = Integer.parseInt(sc.nextLine());
                
                if (opcion < 1 || opcion > 4) {
                    uiService.mostrarError("Debes seleccionar un número entre 1 y 4");
                    continue;
                }
                
                return opcion;
            } catch (NumberFormatException e) {
                uiService.mostrarError("Debes ingresar un número válido (1-4)");
            }
        }
    }

    public boolean preguntarParaVolverAJugar() {
        while (true) {
            try {
                uiService.mostrarMenuVolverAJugar();
                int opcion = Integer.parseInt(sc.nextLine());
                
                if (opcion == 1) {
                    uiService.limpiarPantalla();
                    return true;
                } else if (opcion == 2) {
                    return false;
                } else {
                    uiService.mostrarError("Debes elegir 1 o 2");
                }
            } catch (NumberFormatException e) {
                uiService.mostrarError("Debes ingresar un número válido (1 o 2)");
            }
        }
    }

    public void limpiarPantalla() {
        uiService.limpiarPantalla();
    }

    public UIService getUIService() {
        return uiService;
    }
}
