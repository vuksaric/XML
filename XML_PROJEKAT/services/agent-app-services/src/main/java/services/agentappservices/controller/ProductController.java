package services.agentappservices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.agentappservices.model.dto.ProductDTO;
import services.agentappservices.model.dto.RegistrationDTO;
import services.agentappservices.service.IProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService){
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestParam("file") MultipartFile multipartFile, @RequestParam("price") int price, @RequestParam("quantity") int quantity, @RequestParam("agentId") int agentId, @RequestParam("name") String name){
        try{
            return new ResponseEntity(productService.save(multipartFile, price, quantity, agentId, name), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getByAgent/{agentId}")
    public ResponseEntity findByAgentId(@PathVariable("agentId") int agentId){
        try{
            return new ResponseEntity(productService.findAllByAgentId(agentId), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity edit(@RequestBody ProductDTO productDTO){
        try{
            return new ResponseEntity(productService.edit(productDTO), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editPicture")
    public ResponseEntity editPicture(@RequestParam("file") MultipartFile multipartFile, @RequestParam("productId") int productId){
        try{
            return new ResponseEntity(productService.editPicture(multipartFile, productId), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping ("/delete/{productId}")
    public ResponseEntity delete(@PathVariable int productId){
        try{
            return new ResponseEntity(productService.delete(productId), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{productId}")
    public ResponseEntity findById(@PathVariable("productId") int productId){
        try{
            return new ResponseEntity(productService.findById(productId), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/buyProduct/{productId}")
    public ResponseEntity buyProduct(@PathVariable("productId") int productId){
        try{
            return new ResponseEntity(productService.buyProduct(productId), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
