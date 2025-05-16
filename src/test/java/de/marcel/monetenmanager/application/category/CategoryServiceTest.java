package de.marcel.monetenmanager.application.category;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.marcel.monetenmanager.domain.category.Category;
import de.marcel.monetenmanager.domain.category.CategoryRepository;
import de.marcel.monetenmanager.domain.category.CategoryType;

public class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    public void testCreateCategory_savesCategory() {
        UUID userId = UUID.randomUUID();
        String name = "Lebensmittel";
        CategoryType type = CategoryType.AUSGABE;
        String color = "grün";

        categoryService.createCategory(userId, name, type, color);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testGetCategoriesForUser_returnsList() {
        UUID userId = UUID.randomUUID();

        List<Category> expected = List.of(
            new Category(UUID.randomUUID(), userId, "Lebensmittel", CategoryType.AUSGABE, "grün")
        );

        when(categoryRepository.findByUserId(userId)).thenReturn(expected);

        List<Category> result = categoryService.getCategoriesForUser(userId);

        assertEquals(1, result.size());
        assertEquals("Lebensmittel", result.get(0).getName());
        assertEquals(CategoryType.AUSGABE, result.get(0).getType());

        verify(categoryRepository, times(1)).findByUserId(userId);
    }
}
