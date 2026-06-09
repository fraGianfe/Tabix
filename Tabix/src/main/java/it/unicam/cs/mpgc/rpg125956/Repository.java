package it.unicam.cs.mpgc.rpg125956;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    Optional<T> trovaPerId(ID id);
    List<T> trovaTutti();
    void salva(T entita);
    void elimina(ID id);
}