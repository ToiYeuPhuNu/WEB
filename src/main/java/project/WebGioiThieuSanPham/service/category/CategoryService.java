package project.WebGioiThieuSanPham.service.category;

import project.WebGioiThieuSanPham.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    public Category createCategory(Category category);
    public List<Category> getAllCategories();
    public Category updateCategory(UUID categoryId, Category updateCategory);
    public void deleteCategory(UUID categoryId);
}
