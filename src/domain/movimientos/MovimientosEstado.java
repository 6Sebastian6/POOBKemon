package domain.movimientos;

import domain.MovimientoEstado;
import domain.Pokemon;
import domain.Tipo;
import domain.TipoEfecto;
import domain.estados.*;

public class MovimientosEstado {
    
    public static class Paralizar extends MovimientoEstado {
        public Paralizar() {
            super("Onda Trueno", Tipo.ELECTRICO, 90, 20, TipoEfecto.PARALISIS, 100);
        }
        
        @Override
        protected void aplicarEfecto(Pokemon atacante, Pokemon defensor) {
            defensor.aplicarEstado(new EstadoParalisis());
        }
    }
    
    public static class Quemar extends MovimientoEstado {
        public Quemar() {
            super("Ascuas", Tipo.FUEGO, 95, 15, TipoEfecto.QUEMADURA, 30);
        }
        
        @Override
        protected void aplicarEfecto(Pokemon atacante, Pokemon defensor) {
            defensor.aplicarEstado(new EstadoQuemadura());
        }
    }
    
    public static class Envenenar extends MovimientoEstado {
        public Envenenar() {
            super("Polvo Veneno", Tipo.VENENO, 75, 35, TipoEfecto.VENENO, 75);
        }
        
        @Override
        protected void aplicarEfecto(Pokemon atacante, Pokemon defensor) {
            defensor.aplicarEstado(new EstadoVeneno());
        }
    }
    
    public static class Dormir extends MovimientoEstado {
        public Dormir() {
            super("Somnífero", Tipo.PLANTA, 75, 15, TipoEfecto.DORMIR, 100);
        }
        
        @Override
        protected void aplicarEfecto(Pokemon atacante, Pokemon defensor) {
            defensor.aplicarEstado(new EstadoDormido());
        }
    }
    
    public static class Congelar extends MovimientoEstado {
        public Congelar() {
            super("Rayo Hielo", Tipo.HIELO, 95, 10, TipoEfecto.CONGELAR, 10);
        }
        
        @Override
        protected void aplicarEfecto(Pokemon atacante, Pokemon defensor) {
            defensor.aplicarEstado(new EstadoCongelado());
        }
    }
    
    public static class BajarAtaque extends MovimientoEstado {
        public BajarAtaque() {
            super("Gruñido", Tipo.NORMAL, 100, 40, TipoEfecto.BAJAR_ATAQUE, 100);
        }
        
        @Override
        protected void aplicarEfecto(Pokemon atacante, Pokemon defensor) {
            defensor.modificarAtaque(0.67); // Reduce el ataque en un 33%
        }
    }
    
    public static class BajarDefensa extends MovimientoEstado {
        public BajarDefensa() {
            super("Malicioso", Tipo.NORMAL, 100, 30, TipoEfecto.BAJAR_DEFENSA, 100);
        }
        
        @Override
        protected void aplicarEfecto(Pokemon atacante, Pokemon defensor) {
            defensor.modificarDefensa(0.67); // Reduce la defensa en un 33%
        }
    }
    
    public static class SubirAtaque extends MovimientoEstado {
        public SubirAtaque() {
            super("Afilar", Tipo.NORMAL, 100, 30, TipoEfecto.SUBIR_ATAQUE, 100);
        }
        
        @Override
        protected void aplicarEfecto(Pokemon atacante, Pokemon defensor) {
            atacante.modificarAtaque(1.5); // Aumenta el ataque en un 50%
        }
    }
    
    public static class SubirDefensa extends MovimientoEstado {
        public SubirDefensa() {
            super("Defensa Férrea", Tipo.ACERO, 100, 15, TipoEfecto.SUBIR_DEFENSA, 100);
        }
        
        @Override
        protected void aplicarEfecto(Pokemon atacante, Pokemon defensor) {
            atacante.modificarDefensa(1.5); // Aumenta la defensa en un 50%
        }
    }
    
    public static class SubirVelocidad extends MovimientoEstado {
        public SubirVelocidad() {
            super("Agilidad", Tipo.PSIQUICO, 100, 30, TipoEfecto.SUBIR_VELOCIDAD, 100);
        }
        
        @Override
        protected void aplicarEfecto(Pokemon atacante, Pokemon defensor) {
            atacante.modificarVelocidad(2.0); // Duplica la velocidad
        }
    }
} 