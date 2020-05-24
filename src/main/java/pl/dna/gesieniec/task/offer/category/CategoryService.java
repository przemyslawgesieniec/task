package pl.dna.gesieniec.task.offer.category;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    public static final String CANNOT_ADD_JOB_OFFER_TO_NON_EXISTING_CATEGORY = "Cannot add job offer to non existing category";

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity getCategoryEntityByCategory(String categoryName) throws CategoryException {
        return categoryRepository.findAll().stream()
                .filter(e -> categoryName.equals(e.getName()))
                .findAny()
                .orElseThrow(() -> new CategoryException(CANNOT_ADD_JOB_OFFER_TO_NON_EXISTING_CATEGORY));
    }
}
