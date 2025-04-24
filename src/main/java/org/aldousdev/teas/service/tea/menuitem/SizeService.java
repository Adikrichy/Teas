package org.aldousdev.teas.service.tea.menuitem;

import org.aldousdev.teas.models.tea.Size;

import java.util.List;
import java.util.Map;


public interface SizeService {
    Size getSizeById(Long id);
    List<Size> getSize();

    Size createSize(Size size);
    Size updateSize(Long id, Map<String, Object> sizeDto);
    void deleteSize(Long id);
}
