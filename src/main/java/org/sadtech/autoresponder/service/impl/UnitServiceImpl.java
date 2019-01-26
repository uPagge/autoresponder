package org.sadtech.autoresponder.service.impl;

import org.apache.log4j.Logger;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.repository.UnitRepository;
import org.sadtech.autoresponder.service.UnitService;

import java.util.ArrayList;
import java.util.List;

public class UnitServiceImpl implements UnitService {

    private static final Logger log = Logger.getLogger(UnitServiceImpl.class);

    private List<UnitRepository> unitRepositories;

    public UnitServiceImpl() {
        unitRepositories = new ArrayList<>();
    }

    public UnitServiceImpl(List<UnitRepository> unitRepositories) {
        this.unitRepositories = unitRepositories;
    }

    @Override
    public List<Unit> menuUnit() {
        List<Unit> units = new ArrayList<>();
        for (UnitRepository unitRepository : unitRepositories) {
            units.addAll(unitRepository.menuUnits());
        }
        return units;
    }

    @Override
    public void addUnitRepository(UnitRepository unitRepository) {
        unitRepositories.add(unitRepository);
    }

    @Override
    public UnitRepository getUnitRepository(Class clazz) {
        for (UnitRepository unitRepository : unitRepositories) {
            if (unitRepository.getClass().equals(clazz)) {
                return unitRepository;
            }
        }
        return null;
    }


}

