package org.sadtech.autoresponder;

import lombok.extern.log4j.Log4j;
import org.sadtech.autoresponder.entity.Person;
import org.sadtech.autoresponder.entity.Unit;
import org.sadtech.autoresponder.entity.compare.UnitPriorityComparator;
import org.sadtech.autoresponder.service.PersonService;
import org.sadtech.autoresponder.service.UnitService;
import org.sadtech.autoresponder.submodule.parser.Parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j
public class Autoresponder {

    private UnitService unitService;
    private PersonService personService;

    public Autoresponder(UnitService unitService, PersonService personService) {
        this.unitService = unitService;
        this.personService = personService;
    }

    public Unit answer(Integer idPerson, String message) {
        Person person = checkAndAddPerson(idPerson);
        Unit unit;
        if (person.getUnit() == null) {
            unit = nextUnit(unitService.menuUnit(), message);
        } else {
            unit = nextUnit(person.getUnit().getNextUnits(), message);
        }
        person.setUnit(unit);
        return unit;
    }

    private Person checkAndAddPerson(Integer idPerson) {
        Person person;
        if (personService.checkPerson(idPerson)) {
            person = personService.getPersonById(idPerson);
        } else {
            person = new Person(idPerson);
            personService.addPerson(person);
        }
        return person;
    }

    private Unit nextUnit(List<Unit> nextUnits, String message) {
        if (nextUnits.size() > 0) {
            Parser parser = new Parser();
            parser.setText(message);
            parser.parse();
            return nextUnits.stream().filter(nextUnit -> textPercentageMatch(nextUnit, parser.getWords()) >= nextUnit.getMatchThreshold()).max(new UnitPriorityComparator()).get();
        } else {
            return null;
        }
    }

    private Double textPercentageMatch(Unit unit, Set<String> words) {
        Set<String> temp = new HashSet<>(unit.getKeyWords());
        temp.retainAll(words);
        log.info((temp.size() / unit.getKeyWords().size()) * 100);
        return (double) (temp.size() / unit.getKeyWords().size()) * 100;
    }

}
