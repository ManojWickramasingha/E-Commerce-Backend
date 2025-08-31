package com.sliat.crm.ecommerce.controller;

import com.sliat.crm.ecommerce.dto.ProductDto;
import com.sliat.crm.ecommerce.entity.ImageModel;
import com.sliat.crm.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/admin/product")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('admin')")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDto> createNewProduct(@RequestPart("product") ProductDto productData, @RequestPart("images") MultipartFile[] file) {
        try {
            Set<ImageModel> imageModels = uploadImages(file);
            productData.setProductImages(imageModels);
            ProductDto product = productService.createNewProduct(productData);
            if (product != null)
                return ResponseEntity.ok(product);


        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    private Set<ImageModel> uploadImages(MultipartFile[] file) throws Exception {
        Set<ImageModel> images = new HashSet<>();

        for (MultipartFile f : file) {
            ImageModel imageModel =
                    new ImageModel(f.getOriginalFilename()
                            , f.getContentType(),
                            f.getBytes());

            images.add(imageModel);
        }

        return images;
    }


    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProduct(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "") String searchKey) {
        List<ProductDto> allProduct = productService.getAllProduct(pageNumber, searchKey);
        if (allProduct != null)
            return ResponseEntity.ok(allProduct);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/deleteProductDetail/{productId}")
    public ResponseEntity<Boolean> deleteProductDetails(@PathVariable("productId") Integer id) {

        if (productService.deleteProductDetails(id)) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.badRequest().body(false);
    }


    @GetMapping("getProductDetailById/{productId}")
    public ProductDto getProductDetailById(@PathVariable("productId") Integer productId) {
        ProductDto productDto = productService.getProductDetailById(productId);
        if (productDto != null)
            return productDto;

        return null;
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping("/getProductDetail/{isSingleProductCheckOut}/{productId}")
    public ResponseEntity<List<ProductDto>> getProductDetail(@PathVariable(name = "isSingleProductCheckOut") boolean isSingleProductCheckOut, @PathVariable(name = "productId") Integer productId) {
        List<ProductDto> list = productService.getProductDetail(isSingleProductCheckOut, productId);
        if (!list.isEmpty())
            return ResponseEntity.ok(list);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}
