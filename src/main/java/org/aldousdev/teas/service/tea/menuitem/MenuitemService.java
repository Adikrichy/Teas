package org.aldousdev.teas.service.tea.menuitem;

import org.aldousdev.teas.dto.request.MenuitemDto;
import org.aldousdev.teas.models.tea.Menuitem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MenuitemService {
    List<Menuitem> getMenuitems();
    List<Menuitem> getMenuitemsByCategoryId(Long categoryId);
//    List<BestSellerDto> getBestsellers();

    Menuitem getMenuitemById(Long id);

    Menuitem createMenuitem(MenuitemDto menuitemDto);
    Menuitem updateMenuitem(Long id, Map<String, Object> menuitemDto);
    void deleteMenuitem(Long id);

    String saveImage(MultipartFile file) throws IOException;

    void toggleActiveMenuitem(Long id);
}
