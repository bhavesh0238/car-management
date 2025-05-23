package com.bhavesh.project1.controller;

import com.bhavesh.project1.model.product;
import com.bhavesh.project1.service.productservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class productcontroller {
    @Autowired
    private productservice service;
    @RequestMapping("/")
    public String greet()
    {
        return "hello world";
    }

    @GetMapping("/products")
    public ResponseEntity<List<product>> getallproducts()
    {
        return new ResponseEntity<>(service.getallproducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{pid}")
    public ResponseEntity<product> getproduct(@PathVariable int pid)
    {
        product product = service.getproductbyid(pid);
        if(product!=null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart product product,
                                        @RequestPart MultipartFile imageFile)
    {
        try {
            System.out.println(product);
            product p = service.addProduct(product,imageFile);
            return new ResponseEntity<>(p,HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{pid}/image")
    public ResponseEntity<byte[]> getimagebyproductid(@PathVariable int pid)
    {
        product p = service.getproductbyid(pid);
        byte[] imageFile = p.getImagedata();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(p.getImagetype()))
                .body(imageFile);
    }

    @PutMapping("/product/{pid}")
    public ResponseEntity<String> updateProduct(@PathVariable int pid,
                                                @RequestPart product product,
                                                @RequestPart MultipartFile imageFile)
    {
        product p = null;
        try {
            p = service.updateProduct(pid,product,imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("failed to update",HttpStatus.BAD_REQUEST);
        }

        if(p != null)
        {
            return new ResponseEntity<>("updated",HttpStatus.OK);
        }
        return new ResponseEntity<>("failed to update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{pid}")
    public ResponseEntity<String> deleteproduct(@PathVariable int pid)
    {
        product p = service.getproductbyid(pid);
        if(p!=null)
        {
            service.deleteproduct(pid);
            return new ResponseEntity<>("deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>("product not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<product>> searchproducts(@RequestParam String keyword)
    {
        System.out.println("searching with " + keyword);
        List<product> products = service.searchproducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
