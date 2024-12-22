package mk.ukim.finki.wp.exam.example.service.impl;

import mk.ukim.finki.wp.exam.example.model.Category;
import mk.ukim.finki.wp.exam.example.model.Product;
import mk.ukim.finki.wp.exam.example.model.exceptions.InvalidCategoryIdException;
import mk.ukim.finki.wp.exam.example.model.exceptions.InvalidProductIdException;
import mk.ukim.finki.wp.exam.example.repository.CategoryRepository;
import mk.ukim.finki.wp.exam.example.repository.ProductRepository;
import mk.ukim.finki.wp.exam.example.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    //    bidekji treba da se  vcitaat kategorii spored id
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(InvalidProductIdException::new);

    }

    @Override
    public Product create(String name, Double price, Integer quantity, List<Long> categoryIds) {
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        Product product = new Product(name, price, quantity, categories);
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, String name, Double price, Integer quantity, List<Long> categoryIds) {
        Product product = findById(id);
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        product.setCategories(categories);
        return productRepository.save(product);
    }

    @Override
    public Product delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
        return product;
    }

    @Override
    public List<Product> listProductsByNameAndCategory(String name, Long categoryId) {
        if (name == null && categoryId == null) {
            return productRepository.findAll();
        }
        if (name == null) {
            return productRepository.findAllByCategories_Id(categoryId);
        }
        if (categoryId == null) {
            return productRepository.findALLByNameLike("%"+name+"%");
        }
        Category category = categoryRepository.findById(categoryId).orElse((Category) null);
        return productRepository.findAllByNameLikeAndCategoriesContainingIgnoreCase("%"+name+"%",category);
    }
}
