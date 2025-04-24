package org.aldousdev.teas.service.tea.milk;

import org.aldousdev.teas.models.tea.Milk;

import java.util.List;
import java.util.Map;

public interface MilkService {

    Milk getMilkById(Long id);
    Milk getMilkByTitle(String title);
    List<Milk> getMilks();

    Milk updateMilk(Long id, Map<String, Object> milkDto);

    Milk createMilk(Milk milk);
    void deleteMilk(Long id) ;
}
