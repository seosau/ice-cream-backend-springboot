package com.project.icecream.services;

import com.project.icecream.dto.requests.ProductRequest;
import com.project.icecream.models.Products;
import com.project.icecream.repositories.ProductsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.project.icecream.utils.GenerateSlugUtil.convertToSlug;
import static com.project.icecream.utils.SaveImageBase64Util.saveBase64ImageToFile;

@Service
public interface ProductsService {
    public Products addProduct(ProductRequest requestBody) throws IOException;
    public Page<Products> getAllProducts(int page, int size);
    public Page<Products> getFilterProducts(int page, String sortBy, String order);
    public Optional<Products> getProductById(int id) throws IOException;
    public Products updateProduct(int id, ProductRequest requestBody) throws IOException;
    public void deleteProduct(int id) throws IOException;
}
