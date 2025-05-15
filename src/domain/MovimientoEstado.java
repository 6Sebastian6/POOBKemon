package domain;

public class MovimientoEstado extends Movimiento {
    protected TipoEfecto tipoEfecto;
    protected double probabilidadEfecto;
    
    public MovimientoEstado(String nombre, Tipo tipo, int precision, int ppMaximo, 
                           TipoEfecto tipoEfecto, double probabilidadEfecto) {
        super(nombre, tipo, 0, precision, false, ppMaximo);
        this.tipoEfecto = tipoEfecto;
        this.probabilidadEfecto = probabilidadEfecto;
    }

    @Override
    public void ejecutar(Pokemon atacante, Pokemon defensor) {
        if (!calculaAcierto()) {
            System.out.println("¡El ataque falló!");
            return;
        }

        consumirPP();
        
        // Verificar si el efecto se aplica según la probabilidad
        if (Math.random() * 100 <= probabilidadEfecto) {
            aplicarEfecto(defensor);
            System.out.println(atacante.getNombre() + " usó " + getNombre() + "!");
        } else {
            System.out.println("¡Pero no tuvo efecto!");
        }
    }

    protected void aplicarEfecto(Pokemon pokemon) {
        switch (tipoEfecto) {
            case BAJAR_ATAQUE:
                pokemon.modificarAtaque(0.67);
                break;
            case BAJAR_DEFENSA:
                pokemon.modificarDefensa(0.67);
                break;
            case BAJAR_ATAQUE_ESPECIAL:
                pokemon.modificarAtaqueEspecial(0.67);
                break;
            case BAJAR_DEFENSA_ESPECIAL:
                pokemon.modificarDefensaEspecial(0.67);
                break;
            case BAJAR_VELOCIDAD:
                pokemon.modificarVelocidad(0.67);
                break;
            case SUBIR_ATAQUE:
                pokemon.modificarAtaque(1.5);
                break;
            case SUBIR_DEFENSA:
                pokemon.modificarDefensa(1.5);
                break;
            case SUBIR_ATAQUE_ESPECIAL:
                pokemon.modificarAtaqueEspecial(1.5);
                break;
            case SUBIR_DEFENSA_ESPECIAL:
                pokemon.modificarDefensaEspecial(1.5);
                break;
            case SUBIR_VELOCIDAD:
                pokemon.modificarVelocidad(1.5);
                break;
            default:
                // Para otros efectos como estados alterados, se manejan en otra parte
                break;
        }
    }
}

// Enumeración para los tipos de efectos
enum TipoEfecto {
    PARALISIS,
    QUEMADURA,
    VENENO,
    DORMIR,
    CONGELAR,
    CONFUSION,
    BAJAR_ATAQUE,
    BAJAR_DEFENSA,
    BAJAR_ATAQUE_ESPECIAL,
    BAJAR_DEFENSA_ESPECIAL,
    BAJAR_VELOCIDAD,
    SUBIR_ATAQUE,
    SUBIR_DEFENSA,
    SUBIR_ATAQUE_ESPECIAL,
    SUBIR_DEFENSA_ESPECIAL,
    SUBIR_VELOCIDAD
}
