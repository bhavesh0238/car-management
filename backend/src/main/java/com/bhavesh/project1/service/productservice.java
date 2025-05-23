package com.bhavesh.project1.service;

import com.bhavesh.project1.model.product;
import com.bhavesh.project1.repository.productrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class productservice {
    @Autowired
    private productrepo repo;
    public List<product> getallproducts() {
        return repo.findAll();
    }

    public product getproductbyid(int pid) {
        return repo.findById(pid).orElse(null);
    }

    public product addProduct(product product, MultipartFile imagefile) throws IOException {
        product.setImagename(imagefile.getOriginalFilename());
        product.setImagetype(imagefile.getContentType());
        product.setImagedata(imagefile.getBytes());
        return repo.save(product);
    }

    public product updateProduct(int pid, product product, MultipartFile imageFile) throws IOException {
        product.setImagedata(imageFile.getBytes());
        product.setImagename(imageFile.getOriginalFilename());
        product.setImagetype(imageFile.getContentType());
        return repo.save(product);
    }

    public void deleteproduct(int pid) {
        repo.deleteById(pid);
    }

    public List<product> searchproducts(String keyword) {
        return repo.searchproducts(keyword);
    }
}
