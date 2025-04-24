package org.aldousdev.teas.service.tea.category;

import org.aldousdev.teas.models.tea.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<Category> getCategories();
    Category getCategoryById(Long id);
    Category getCategoryByTitle(String title);
    Category updateCategory(Long id, Map<String, Object> categoryDto);
    Category createCategory(Category category);
    String deleteCategory(Long id);

}
