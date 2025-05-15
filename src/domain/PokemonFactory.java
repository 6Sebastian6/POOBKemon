package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PokemonFactory {
    private static final Random random = new Random();
    
    // Lista predefinida de Pokémon con sus estadísticas base
    public static Pokemon crearPokemon(String nombre, int nivel) {
        switch (nombre.toLowerCase()) {
            case "pikachu":
                return new Pokemon("Pikachu", nivel, Tipo.ELECTRICO,
                    274, 229, 174, 218, 218, 306,
                    getMovimientosPikachu());
            case "charmander":
                return new Pokemon("Charmander", nivel, Tipo.FUEGO,
                    282, 223, 203, 240, 203, 251,
                    getMovimientosCharmander());
            case "bulbasaur":
                return new Pokemon("Bulbasaur", nivel, Tipo.PLANTA,
                    294, 216, 216, 251, 251, 196,
                    getMovimientosBulbasaur());
            case "squirtle":
                return new Pokemon("Squirtle", nivel, Tipo.AGUA,
                    292, 214, 251, 218, 249, 203,
                    getMovimientosSquirtle());
            case "geodude":
                return new Pokemon("Geodude", nivel, Tipo.ROCA,
                    284, 296, 351, 174, 174, 174,
                    getMovimientosGeodude());
            case "gastly":
                return new Pokemon("Gastly", nivel, Tipo.FANTASMA,
                    264, 174, 174, 351, 174, 262,
                    getMovimientosGastly());
            default:
                throw new IllegalArgumentException("Pokémon no encontrado: " + nombre);
        }
    }

    public static Pokemon crearPokemonAleatorio(int nivel) {
        String[] nombres = {"pikachu", "charmander", "bulbasaur", "squirtle", "geodude", "gastly"};
        return crearPokemon(nombres[random.nextInt(nombres.length)], nivel);
    }

    private static List<Movimiento> getMovimientosPikachu() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new MovimientoEspecial("Impactrueno", Tipo.ELECTRICO, 40, 100, 30));
        movimientos.add(new MovimientoEspecial("Rayo", Tipo.ELECTRICO, 90, 100, 15));
        movimientos.add(new MovimientoFisico("Placaje", Tipo.NORMAL, 40, 100, 35));
        movimientos.add(new MovimientoEstado("Gruñido", Tipo.NORMAL, 100, 40, TipoEfecto.BAJAR_ATAQUE, 100));
        return movimientos;
    }

    private static List<Movimiento> getMovimientosCharmander() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new MovimientoEspecial("Lanzallamas", Tipo.FUEGO, 90, 100, 15));
        movimientos.add(new MovimientoFisico("Arañazo", Tipo.NORMAL, 40, 100, 35));
        movimientos.add(new MovimientoEspecial("Ascuas", Tipo.FUEGO, 40, 100, 25));
        movimientos.add(new MovimientoEstado("Malicioso", Tipo.NORMAL, 100, 30, TipoEfecto.BAJAR_DEFENSA, 100));
        return movimientos;
    }

    private static List<Movimiento> getMovimientosBulbasaur() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new MovimientoEspecial("Látigo Cepa", Tipo.PLANTA, 45, 100, 25));
        movimientos.add(new MovimientoEspecial("Hoja Afilada", Tipo.PLANTA, 55, 95, 25));
        movimientos.add(new MovimientoFisico("Placaje", Tipo.NORMAL, 40, 100, 35));
        movimientos.add(new MovimientoEstado("Drenadoras", Tipo.PLANTA, 100, 30, TipoEfecto.DRENAR, 100));
        return movimientos;
    }

    private static List<Movimiento> getMovimientosSquirtle() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new MovimientoEspecial("Pistola Agua", Tipo.AGUA, 40, 100, 25));
        movimientos.add(new MovimientoEspecial("Burbuja", Tipo.AGUA, 40, 100, 30));
        movimientos.add(new MovimientoFisico("Placaje", Tipo.NORMAL, 40, 100, 35));
        movimientos.add(new MovimientoEstado("Refugio", Tipo.AGUA, 100, 40, TipoEfecto.SUBIR_DEFENSA, 100));
        return movimientos;
    }

    private static List<Movimiento> getMovimientosGeodude() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new MovimientoFisico("Lanzarrocas", Tipo.ROCA, 50, 90, 15));
        movimientos.add(new MovimientoFisico("Placaje", Tipo.NORMAL, 40, 100, 35));
        movimientos.add(new MovimientoFisico("Magnitud", Tipo.TIERRA, 70, 100, 30));
        movimientos.add(new MovimientoEstado("Defensa Férrea", Tipo.ROCA, 100, 30, TipoEfecto.SUBIR_DEFENSA, 100));
        return movimientos;
    }

    private static List<Movimiento> getMovimientosGastly() {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(new MovimientoEspecial("Bola Sombra", Tipo.FANTASMA, 80, 100, 15));
        movimientos.add(new MovimientoEspecial("Tinieblas", Tipo.FANTASMA, 40, 100, 30));
        movimientos.add(new MovimientoEstado("Hipnosis", Tipo.PSIQUICO, 60, 20, TipoEfecto.DORMIR, 100));
        movimientos.add(new MovimientoEstado("Rencor", Tipo.FANTASMA, 100, 30, TipoEfecto.BAJAR_ATAQUE, 100));
        return movimientos;
    }
} 