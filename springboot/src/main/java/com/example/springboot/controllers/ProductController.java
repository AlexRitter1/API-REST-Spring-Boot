package com.example.springboot.controllers;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductsModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    //este metodo recebe dados de um cliente e envia os dados para um banco de dados
    @PostMapping("/products") //metodo http
    public ResponseEntity<ProductsModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductsModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductsModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") UUID id){
        Optional<ProductsModel> product = productRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable (value="id") UUID id,
                                                @RequestBody @Valid ProductRecordDto productRecordDto){
        Optional<ProductsModel> product = productRepository.findById(id);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found");
        }
        var productModel = product.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id){
        Optional<ProductsModel> productsModel = productRepository.findById(id);
        productRepository.delete(productsModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }

}