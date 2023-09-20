package project.WebGioiThieuSanPham.service.category;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import project.WebGioiThieuSanPham.dto.ApiListBaseRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.dto.clothesDto.response.BasePage;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.mapper.CategoryMapper;
import project.WebGioiThieuSanPham.mapper.ClothesMapper;
import project.WebGioiThieuSanPham.models.Category;
import project.WebGioiThieuSanPham.models.Clothes;
import project.WebGioiThieuSanPham.repository.CategoryRepository;
import project.WebGioiThieuSanPham.repository.ClothesRepository;
import project.WebGioiThieuSanPham.utils.FilterDataUtil;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ClothesRepository clothesRepository;

    private final CategoryMapper categoryMapper;


    private final ClothesMapper clothesMapper;

   private final SearchUtil searchUtil = new SearchUtil();


    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        String categoryName = categoryRequest.getName();
        Category existingCategory = categoryRepository.findByName(categoryName);
        if (existingCategory != null){
            throw new RuntimeException("Danh mục đã tồn tại!");
        } else if (StringUtils.isBlank(categoryName)) {
            throw new RuntimeException("Tên không hợp lệ!");
        } else {
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            categoryRepository.save(newCategory);
            return categoryMapper.categoryToCategoryResponse(newCategory);
        }
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::categoryToCategoryResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategory(UUID categoryId, CategoryRequest updateCategoryRequest) {
        Objects.requireNonNull(categoryId, "ID của danh mục không được null.");
        //kiểm tra xem danh mục có tồn tại hay không
        Optional<Category> existingCategoryOptional = categoryRepository.findById(categoryId);
        Category existingCategory = existingCategoryOptional.orElseThrow(()-> new RuntimeException("Danh mục không tồn tại!"));
        String newName = updateCategoryRequest.getName();
        if (newName!=null && StringUtils.isBlank(newName)){
            throw new RuntimeException("Tên không hợp lệ!");
        }
        // Cập nhật thông tin danh mục
        existingCategory.setName(updateCategoryRequest.getName());
        categoryRepository.save(existingCategory);
        return categoryMapper.categoryToCategoryResponse(existingCategory);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Objects.requireNonNull(categoryId, "ID của danh mục không được null!");
        Optional<Category> existingCategoryOptional = categoryRepository.findById(categoryId);
        Category existingCategory = existingCategoryOptional.orElseThrow(()-> new RuntimeException("Danh mục không tồn tại "));
        categoryRepository.deleteById(categoryId);
    }
    public BasePage<ClothesAvatarView> getClothesByCategory(ApiListBaseRequest apiListBaseRequest, UUID id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        Page<Clothes> page = searchUtil.findClothesByCategory(category)
                .build(apiListBaseRequest);
        return this.map(page);
    }
    protected BasePage<ClothesAvatarView> map(Page<Clothes> page) {
        BasePage<ClothesAvatarView> rPage = new BasePage<>();
        rPage.setData(clothesMapper.toListDao(page.getContent()));
        rPage.setTotalPage(page.getTotalPages());
        rPage.setTotalRecord( page.getTotalElements());
        rPage.setPage(page.getPageable().getPageNumber());
        return rPage;
    }

    public class SearchUtil {
        private final List<Specification<Clothes>> specifications = new ArrayList<>();
        private final List<Predicate> tempPredicates = new ArrayList<>();
        ClothesRepository repository = CategoryServiceImpl.this.clothesRepository;

        public SearchUtil findClothesByCategory(Category category){
                specifications.add(
                        (Specification<Clothes>) (root, query, criteriaBuilder) -> {
                            return criteriaBuilder.like(root.get("category_id"), category.getId().toString());
                        });
                tempPredicates.clear();
            return this;
        }

        public Page<Clothes> build(ApiListBaseRequest filter) {
            Page<Clothes> data;
            if (!specifications.isEmpty()) {
                data = repository.findAll(specifications.stream().reduce(Specification::and).orElseThrow(),
                        FilterDataUtil.buildPageRequest(filter));
                specifications.clear();
            } else {
                data = repository.findAll(FilterDataUtil.buildPageRequest(filter));
            }
            return data;
        }
    }
}
