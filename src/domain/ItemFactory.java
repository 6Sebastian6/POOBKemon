package domain;

public class ItemFactory {
    public static Pocion crearPocion(TipoPocion tipo) {
        switch (tipo) {
            case POCION:
                return new Pocion("Poción", 20);
            case SUPERPOCION:
                return new Pocion("Superpoción", 50);
            case HIPERPOCION:
                return new Pocion("Hiperpocíon", 200);
            case POCMAX:
                return new Pocion("Poción Máxima", 999);
            default:
                throw new IllegalArgumentException("Tipo de poción no válido");
        }
    }
} 