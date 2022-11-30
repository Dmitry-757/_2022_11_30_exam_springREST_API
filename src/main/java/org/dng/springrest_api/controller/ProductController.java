package org.dng.springrest_api.controller;

import org.dng.springrest_api.Service.ProductService;
import org.dng.springrest_api.model.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping(path = "ping")
    public String ping(){
        return "pong";
    }


    //Creates a new article
    @PostMapping("product")
    public ResponseEntity<Void> addItem(@RequestBody Product newItemInfo, UriComponentsBuilder builder) {
        Product item = new Product();
        BeanUtils.copyProperties(newItemInfo, item);
        boolean flag = service.saveOrUpdate(item);
        if (!flag) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/product/{id}").buildAndExpand(item.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //Fetches all
    @GetMapping(value="product")
    public ResponseEntity<List<Product>> getAllItems() {
        List<Product> responseItemList = new ArrayList<>();
        List<Product> itemList = service.getAll();
        for (int i = 0; i < itemList.size(); i++) {
            Product item = new Product();
            BeanUtils.copyProperties(itemList.get(i), item);
            responseItemList.add(item);
        }
        return new ResponseEntity<List<Product>>(responseItemList, HttpStatus.OK);
    }

    //Fetches item by id
    @GetMapping("product/{id}")
    public ResponseEntity<Product> getItemById(@PathVariable("id") Integer id) {
        Product item = new Product();
        BeanUtils.copyProperties(service.getById(id), item);
        return new ResponseEntity<Product>(item, HttpStatus.OK);
    }

    //Updates item
    @PutMapping("product")
    public ResponseEntity<Product> updateItem(@RequestBody Product itemInfo) {
//        Product editedItem = service.getById(itemInfo.getId());
//        BeanUtils.copyProperties(itemInfo, editedItem);
        service.saveOrUpdate(itemInfo);

        return new ResponseEntity<Product>(itemInfo, HttpStatus.OK);
    }

    //Deletes item by id
    @DeleteMapping("product/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
