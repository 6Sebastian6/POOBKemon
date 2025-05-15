package domain;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pokemon {
    private String nombre;
    private int nivel;
    private Tipo tipo;
    private int psActual, psMaximo;
    private int ataque, defensa, ataqueEspecial, defensaEspecial, velocidad;
    private List<Movimiento> movimientos;
    private EstadoPokemon estado;
    private Map<String, Double> modificadoresEstadisticas;

    public static final Map<Tipo, List<Tipo>> SUPER_EFECTIVO = new HashMap<>();
    public static final Map<Tipo, List<Tipo>> NO_ES_MUY_EFECTIVO = new HashMap<>();
    public static final Map<Tipo, List<Tipo>> NADA_EFECTIVO = new HashMap<>();

    static {
        // Tipos efectivos
        SUPER_EFECTIVO.put(Tipo.ACERO, Arrays.asList(Tipo.HADA, Tipo.HIELO, Tipo.ROCA));
        SUPER_EFECTIVO.put(Tipo.AGUA, Arrays.asList(Tipo.FUEGO, Tipo.ROCA, Tipo.TIERRA));
        SUPER_EFECTIVO.put(Tipo.BICHO, Arrays.asList(Tipo.PLANTA, Tipo.PSIQUICO, Tipo.SINIESTRO));
        SUPER_EFECTIVO.put(Tipo.DRAGON, Arrays.asList(Tipo.DRAGON));
        SUPER_EFECTIVO.put(Tipo.ELECTRICO, Arrays.asList(Tipo.AGUA, Tipo.VOLADOR));
        SUPER_EFECTIVO.put(Tipo.FANTASMA, Arrays.asList(Tipo.FANTASMA, Tipo.PSIQUICO));
        SUPER_EFECTIVO.put(Tipo.FUEGO, Arrays.asList(Tipo.ACERO,Tipo.PLANTA, Tipo.BICHO, Tipo.HIELO));
        SUPER_EFECTIVO.put(Tipo.HADA, Arrays.asList(Tipo.DRAGON, Tipo.LUCHA, Tipo.SINIESTRO));
        SUPER_EFECTIVO.put(Tipo.HIELO, Arrays.asList(Tipo.DRAGON, Tipo.PLANTA, Tipo.TIERRA, Tipo.VOLADOR));
        SUPER_EFECTIVO.put(Tipo.LUCHA, Arrays.asList(Tipo.ACERO, Tipo.HIELO, Tipo.NORMAL, Tipo.ROCA, Tipo.SINIESTRO));
        SUPER_EFECTIVO.put(Tipo.PLANTA, Arrays.asList(Tipo.AGUA,Tipo.ROCA, Tipo.TIERRA));
        SUPER_EFECTIVO.put(Tipo.PSIQUICO, Arrays.asList(Tipo.LUCHA, Tipo.VENENO));
        SUPER_EFECTIVO.put(Tipo.ROCA, Arrays.asList(Tipo.BICHO, Tipo.FUEGO, Tipo.HIELO, Tipo.VOLADOR)); 
        SUPER_EFECTIVO.put(Tipo.SINIESTRO, Arrays.asList(Tipo.FANTASMA, Tipo.PSIQUICO));
        SUPER_EFECTIVO.put(Tipo.TIERRA, Arrays.asList(Tipo.ACERO, Tipo.ELECTRICO, Tipo.FUEGO, Tipo.ROCA));
        SUPER_EFECTIVO.put(Tipo.VENENO, Arrays.asList(Tipo.HADA, Tipo.PLANTA));
        SUPER_EFECTIVO.put(Tipo.VOLADOR, Arrays.asList(Tipo.BICHO, Tipo.LUCHA, Tipo.PLANTA));

        // NO_ES_MUY_EFECTIVO
        NO_ES_MUY_EFECTIVO.put(Tipo.ACERO, Arrays.asList(Tipo.ACERO, Tipo.AGUA, Tipo.ELECTRICO, Tipo.FUEGO));
        NO_ES_MUY_EFECTIVO.put(Tipo.AGUA, Arrays.asList(Tipo.AGUA, Tipo.DRAGON, Tipo.PLANTA));
        NO_ES_MUY_EFECTIVO.put(Tipo.BICHO, Arrays.asList(Tipo.ACERO, Tipo.FANTASMA, Tipo.FUEGO,Tipo.HADA, Tipo.LUCHA,Tipo.VENENO, Tipo.VOLADOR));
        NO_ES_MUY_EFECTIVO.put(Tipo.DRAGON, Arrays.asList(Tipo.ACERO));
        NO_ES_MUY_EFECTIVO.put(Tipo.ELECTRICO, Arrays.asList(Tipo.DRAGON, Tipo.ELECTRICO, Tipo.PLANTA));
        NO_ES_MUY_EFECTIVO.put(Tipo.FANTASMA, Arrays.asList(Tipo.SINIESTRO));
        NO_ES_MUY_EFECTIVO.put(Tipo.FUEGO, Arrays.asList(Tipo.AGUA,Tipo.DRAGON, Tipo.FUEGO, Tipo.ROCA));
        NO_ES_MUY_EFECTIVO.put(Tipo.HADA, Arrays.asList(Tipo.ACERO, Tipo.FUEGO, Tipo.VENENO));
        NO_ES_MUY_EFECTIVO.put(Tipo.HIELO, Arrays.asList(Tipo.ACERO, Tipo.AGUA, Tipo.FUEGO, Tipo.HIELO));
        NO_ES_MUY_EFECTIVO.put(Tipo.LUCHA, Arrays.asList(Tipo.BICHO, Tipo.HADA, Tipo.PSIQUICO, Tipo.VENENO, Tipo.VOLADOR));
        NO_ES_MUY_EFECTIVO.put(Tipo.NORMAL, Arrays.asList(Tipo.ACERO, Tipo.ROCA ));
        NO_ES_MUY_EFECTIVO.put(Tipo.PLANTA, Arrays.asList(Tipo.ACERO,Tipo.BICHO, Tipo.DRAGON, Tipo.FUEGO, Tipo.PLANTA, Tipo.VENENO, Tipo.VOLADOR));/////////
        NO_ES_MUY_EFECTIVO.put(Tipo.PSIQUICO, Arrays.asList(Tipo.ACERO, Tipo.PSIQUICO));
        NO_ES_MUY_EFECTIVO.put(Tipo.ROCA, Arrays.asList(Tipo.ACERO, Tipo.LUCHA, Tipo.TIERRA)); 
        NO_ES_MUY_EFECTIVO.put(Tipo.SINIESTRO, Arrays.asList(Tipo.HADA, Tipo.LUCHA, Tipo.SINIESTRO));
        NO_ES_MUY_EFECTIVO.put(Tipo.TIERRA, Arrays.asList(Tipo.BICHO, Tipo.PLANTA));
        NO_ES_MUY_EFECTIVO.put(Tipo.VENENO, Arrays.asList(Tipo.FANTASMA, Tipo.ROCA, Tipo.TIERRA, Tipo.VENENO));
        NO_ES_MUY_EFECTIVO.put(Tipo.VOLADOR, Arrays.asList(Tipo.ACERO, Tipo.ELECTRICO, Tipo.ROCA));

         // Tipos nada efectivos
        NADA_EFECTIVO.put(Tipo.DRAGON, Arrays.asList(Tipo.HADA));
        NADA_EFECTIVO.put(Tipo.ELECTRICO, Arrays.asList(Tipo.TIERRA));
        NADA_EFECTIVO.put(Tipo.FANTASMA, Arrays.asList(Tipo.NORMAL));
        NADA_EFECTIVO.put(Tipo.LUCHA, Arrays.asList(Tipo.FANTASMA));
        NADA_EFECTIVO.put(Tipo.NORMAL, Arrays.asList(Tipo.FANTASMA));
        NADA_EFECTIVO.put(Tipo.PSIQUICO, Arrays.asList(Tipo.SINIESTRO));
        NADA_EFECTIVO.put(Tipo.TIERRA, Arrays.asList(Tipo.VOLADOR));
        NADA_EFECTIVO.put(Tipo.VENENO, Arrays.asList(Tipo.ACERO));
    }
    

    public Pokemon(String nombre, int nivel, Tipo tipo, int psMaximo, int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad, List<Movimiento> movimientos) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipo = tipo;
        this.psMaximo = psMaximo;
        this.psActual = psMaximo;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.velocidad = velocidad;
        this.movimientos = movimientos;
        this.modificadoresEstadisticas = new HashMap<>();
        inicializarModificadores();
    }

    private void inicializarModificadores() {
        modificadoresEstadisticas.put("ataque", 1.0);
        modificadoresEstadisticas.put("defensa", 1.0);
        modificadoresEstadisticas.put("ataqueEspecial", 1.0);
        modificadoresEstadisticas.put("defensaEspecial", 1.0);
        modificadoresEstadisticas.put("velocidad", 1.0);
    }

    public void aplicarEstado(EstadoPokemon nuevoEstado) {
        if (this.estado != null) {
            System.out.println(nombre + " ya tiene un estado alterado!");
            return;
        }
        this.estado = nuevoEstado;
        System.out.println(nombre + " ha sido " + nuevoEstado.getNombre() + "!");
    }

    public void curarEstado() {
        if (estado != null) {
            System.out.println(nombre + " se ha curado de " + estado.getNombre() + "!");
            this.estado = null;
            inicializarModificadores();
        }
    }

    public boolean puedeActuar() {
        return estado == null || estado.puedeActuar();
    }

    public void procesarEstadoInicioDeTurno() {
        if (estado != null) {
            estado.aplicarEfecto(this);
            if (estado.actualizarDuracion()) {
                curarEstado();
            }
        }
    }

    public void modificarAtaque(double multiplicador) {
        modificadoresEstadisticas.put("ataque", multiplicador);
    }

    public void modificarDefensa(double multiplicador) {
        modificadoresEstadisticas.put("defensa", multiplicador);
    }

    public void modificarAtaqueEspecial(double multiplicador) {
        modificadoresEstadisticas.put("ataqueEspecial", multiplicador);
    }

    public void modificarDefensaEspecial(double multiplicador) {
        modificadoresEstadisticas.put("defensaEspecial", multiplicador);
    }

    public void modificarVelocidad(double multiplicador) {
        modificadoresEstadisticas.put("velocidad", multiplicador);
    }

    public int getAtaqueActual() {
        return (int)(ataque * modificadoresEstadisticas.get("ataque"));
    }

    public int getDefensaActual() {
        return (int)(defensa * modificadoresEstadisticas.get("defensa"));
    }

    public int getAtaqueEspecialActual() {
        return (int)(ataqueEspecial * modificadoresEstadisticas.get("ataqueEspecial"));
    }

    public int getDefensaEspecialActual() {
        return (int)(defensaEspecial * modificadoresEstadisticas.get("defensaEspecial"));
    }

    public int getVelocidadActual() {
        return (int)(velocidad * modificadoresEstadisticas.get("velocidad"));
    }

    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getAtaqueEspecial() { return ataqueEspecial; }
    public int getDefensaEspecial() { return defensaEspecial; }
    public int getVelocidad() { return velocidad; }
    public String getNombre() { return nombre; }
    public int getNivel() { return nivel; }
    public Tipo getTipo() { return tipo; }
    public int getPsActual() { return psActual; }
    public int getPsMaximo() { return psMaximo; }

    public EstadoPokemon getEstado() {
        return estado;
    }

    public void recibirDanio(int cantidad) {
        psActual = Math.max(psActual - cantidad, 0);
    }

    public void curar(int cantidad) {
        psActual = Math.min(psActual + cantidad, psMaximo);
    }

    public boolean estaDebilitado() {
        return psActual <= 0;
    }

    public Movimiento getMovimiento(int index) {
        return movimientos.get(index);
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public boolean estaDerrotado() {
        return psActual <= 0;
    }

    public void revivir() {
        if (estaDerrotado()) {
            psActual = psMaximo / 2;
        }
    }
}


/*
 * public String calcularEfectividadContra(Tipo otro) {
    if (MUY_EFECTIVO.containsKey(tipo) && MUY_EFECTIVO.get(tipo).contains(otro)) {
        return "Muy efectivo";
    } else if (SUPER_EFECTIVO.containsKey(tipo) && SUPER_EFECTIVO.get(tipo).contains(otro)) {
        return "Efectivo";
    } else if (NADA_EFECTIVO.containsKey(tipo) && NADA_EFECTIVO.get(tipo).contains(otro)) {
        return "Nada efectivo";
    } else {
        return "Neutral";
    }
}
 */