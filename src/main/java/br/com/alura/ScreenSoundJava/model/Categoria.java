package br.com.alura.ScreenSoundJava.model;

public enum Categoria {
    SOLO("Solo"),
    DUPLA("Dupla"),
    BANDA("Banda");

    private String categoriaBanda;

    Categoria(String categoriaBanda) {
        this.categoriaBanda = categoriaBanda;
    }
}
