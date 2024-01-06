package br.com.alura.ScreenSoundJava.model;

public enum Categoria {
    SOLO("Solo"),
    DUPLA("Dupla"),
    BANDA("Banda");

    private String categoriaBanda;

    Categoria(String categoriaBanda) {
        this.categoriaBanda = categoriaBanda;
    }

    public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaBanda.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
