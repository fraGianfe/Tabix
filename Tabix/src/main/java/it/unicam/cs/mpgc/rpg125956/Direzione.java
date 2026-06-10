package it.unicam.cs.mpgc.rpg125956;

public enum Direzione {
    NORD(0, -1), SUD(0, 1), EST(1, 0), OVEST(-1, 0);

    private final int deltaX;
    private final int deltaY;

    Direzione(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }
    public int getDeltaY() {
        return deltaY;
    }

}
