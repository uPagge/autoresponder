package org.sadtech.autoresponder.repository;

import org.sadtech.autoresponder.entity.Unit;

import java.util.Collection;
import java.util.List;

public interface UnitRepository<T extends Unit> {

    void addUnit(T unit);

    void addUnits(Collection<T> units);

    List<T> menuUnits();

}
