package domain.movimientos;

import domain.MovimientoEspecial;
import domain.Tipo;

public class MovimientosEspeciales {
    
    public static class LanzaLlamas extends MovimientoEspecial {
        public LanzaLlamas() {
            super("Lanzallamas", Tipo.FUEGO, 90, 100, 15);
        }
    }
    
    public static class RayoHielo extends MovimientoEspecial {
        public RayoHielo() {
            super("Rayo Hielo", Tipo.HIELO, 90, 100, 10);
        }
    }
    
    public static class PsiquicoAtaque extends MovimientoEspecial {
        public PsiquicoAtaque() {
            super("Psíquico", Tipo.PSIQUICO, 90, 100, 10);
        }
    }
    
    public static class RayoSolar extends MovimientoEspecial {
        public RayoSolar() {
            super("Rayo Solar", Tipo.PLANTA, 120, 100, 10);
        }
    }
    
    public static class Trueno extends MovimientoEspecial {
        public Trueno() {
            super("Trueno", Tipo.ELECTRICO, 110, 70, 10);
        }
    }
    
    public static class HidroBomba extends MovimientoEspecial {
        public HidroBomba() {
            super("Hidrobomba", Tipo.AGUA, 110, 80, 5);
        }
    }
} 