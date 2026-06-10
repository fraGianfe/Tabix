package it.unicam.cs.mpgc.rpg125956;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GestoreEventi {

    private final Map<Class<?>, List<Consumer<Object>>> ascoltatori = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> void iscriviti(Class<T> tipoEvento, Consumer<T> ascoltatore) {
        ascoltatori.computeIfAbsent(tipoEvento, k -> new ArrayList<>())
                .add((Consumer<Object>) ascoltatore);
    }

    public <T> void pubblica(T evento) {
        List<Consumer<Object>> lista = ascoltatori.get(evento.getClass());
        if (lista != null) lista.forEach(a -> a.accept(evento));
    }
}
