package services.agentappservices.service;

import org.springframework.web.multipart.MultipartFile;
import services.agentappservices.model.dto.ProductDTO;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    int save(MultipartFile multipartFile,int price ,int quantity ,int agentId, String name) throws Exception;
    List<ProductDTO> findAllByAgentId(int agentId);
    boolean edit(ProductDTO productDTO);
    int editPicture(MultipartFile multipartFile,int productId ) throws Exception;
    boolean delete(int productId);
    ProductDTO findById(int productId);
    boolean buyProduct(int productId);
}
