package services.agentappservices.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import services.agentappservices.model.Product;
import services.agentappservices.model.dto.ProductDTO;
import services.agentappservices.repository.ProductRepository;
import services.agentappservices.service.IProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final FileLocationService fileLocationService;

    public ProductService(ProductRepository productRepository, FileLocationService fileLocationService) {
        this.productRepository = productRepository;
        this.fileLocationService = fileLocationService;
    }

    @Override
    public int save(MultipartFile multipartFile, int price, int quantity, int agentId, String name) throws Exception {
        int pictureId = fileLocationService.save(multipartFile.getBytes(), multipartFile.getOriginalFilename());
        Product product = new Product(pictureId, price, quantity, agentId, name);
        return productRepository.save(product).getId();
    }

    @Override
    public List<ProductDTO> findAllByAgentId(int agentId) {
        List<ProductDTO> result = new ArrayList<>();
        for(Product product : productRepository.findAllByAgentId(agentId)){
            result.add(new ProductDTO(product.getId(), product.getPrice(), product.getCount(),
                    fileLocationService.getLocationById(product.getPictureId()), product.getName()));
        }
        return result;
    }

    @Override
    public boolean edit(ProductDTO productDTO) {
        Product product = productRepository.getById(productDTO.getId());
        product.setCount(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());
        Product edited_product = productRepository.save(product);
        if(edited_product==null)
            return false;
        else
            return true;
    }

    @Override
    public int editPicture(MultipartFile multipartFile, int productId) throws Exception {
        Product product = productRepository.getById(productId);
        int pictureId = fileLocationService.save(multipartFile.getBytes(), multipartFile.getOriginalFilename());
        product.setPictureId(pictureId);
        return productRepository.save(product).getId();
    }

    @Override
    public boolean delete(int productId) {
        productRepository.delete(productRepository.getById(productId));
        if(productRepository.getById(productId)==null)
            return true;
        else
            return false;
    }

    @Override
    public ProductDTO findById(int productId) {
        Product product = productRepository.getById(productId);
        return new ProductDTO( product.getName(),
                fileLocationService.getLocationById(product.getPictureId()),product.getPrice());
    }

    @Override
    public boolean buyProduct(int productId) {
        Product product = productRepository.getById(productId);
        if(product.getCount()>0){
            product.setCount(product.getCount()-1);
            productRepository.save(product);
            return true;
        }
        else
            return false;
    }
}
