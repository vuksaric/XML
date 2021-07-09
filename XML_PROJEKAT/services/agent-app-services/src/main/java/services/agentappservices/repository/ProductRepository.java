package services.agentappservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import services.agentappservices.model.Product;
import services.agentappservices.model.dto.ProductDTO;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product save(Product product);
    List<Product> findAllByAgentId(int agentId);
}
