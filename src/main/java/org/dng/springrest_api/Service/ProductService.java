package org.dng.springrest_api.Service;

import org.dng.springrest_api.model.Product;
import org.dng.springrest_api.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return (List<Product>) productRepository.findAll();
    }

    public Product getById(long id) {
        Product item = productRepository.findById(id).get();
        return item;
    }


    public boolean saveOrUpdate(Product item) {
        if (item.getId() != null) {
            Optional<Product> optionalItem = productRepository.findById(item.getId());
            if (optionalItem.isPresent()) {
                Product editedItem = optionalItem.get();

                if (!editedItem.equals(item)) {
//                    editedItem.setName(item.getName());
                    BeanUtils.copyProperties(item, editedItem);

                    productRepository.save(editedItem);
                    return true;
                }
            }
        } else {
            productRepository.save(item);
            return true;
        }
        return false;
    }

    public void delete(Long id) {
        productRepository.findById(id).ifPresent(productRepository::delete);
    }

}
