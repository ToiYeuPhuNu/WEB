package project.WebGioiThieuSanPham.service.category;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.WebGioiThieuSanPham.dto.ApiListBaseRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.request.CategoryRequest;
import project.WebGioiThieuSanPham.dto.categoryDto.response.CategoryResponse;
import project.WebGioiThieuSanPham.dto.clothesDto.response.BasePage;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ClothesAvatarView;
import project.WebGioiThieuSanPham.exception.MasterException;
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
    private final CategoryRepository categoryRepository;
    private final ClothesRepository clothesRepository;

    private final CategoryMapper categoryMapper;


    private final ClothesMapper clothesMapper;

   private final SearchUtil searchUtil = new SearchUtil();


    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        String categoryName = categoryRequest.getName();
        Category existingCategory = categoryRepository.findByName(categoryName);
        if (existingCategory != null){
            throw new MasterException(HttpStatus.CONFLICT ,"Danh mục đã tồn tại!");
        }
        if (StringUtils.isBlank(categoryName)) {
            throw new MasterException(HttpStatus.BAD_REQUEST, "Tên danh mục không hợp lệ!");
        }
        Category newCategory = categoryMapper.categoryRequestToCategory(categoryRequest);
        categoryRepository.save(newCategory);
        return categoryMapper.categoryToCategoryResponse(newCategory);

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
        Category existingCategory = existingCategoryOptional.orElseThrow(()-> new MasterException(HttpStatus.NOT_FOUND,"Danh mục không tồn tại!"));
        String newName = updateCategoryRequest.getName();
        if (StringUtils.isBlank(newName)){
            throw new MasterException(HttpStatus.BAD_REQUEST, "Tên không hợp lệ!");
        }
        existingCategory.setName(newName);
        categoryRepository.save(existingCategory);
        return categoryMapper.categoryToCategoryResponse(existingCategory);

    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Objects.requireNonNull(categoryId, "ID của danh mục không được null!");
        if(!categoryRepository.existsById(categoryId)){
                throw new MasterException(HttpStatus.NOT_FOUND,"Danh mục không tồn tại ");
        }
        categoryRepository.deleteById(categoryId);
    }
    public BasePage<ClothesAvatarView> getClothesByCategory(ApiListBaseRequest apiListBaseRequest, UUID id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new MasterException(HttpStatus.NOT_FOUND, "Danh mục không tồn tại"));
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
