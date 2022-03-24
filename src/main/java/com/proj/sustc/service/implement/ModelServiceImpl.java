package com.proj.sustc.service.implement;

import com.proj.sustc.entity.Model;
import com.proj.sustc.entity.Product;
import com.proj.sustc.mapper.ModelMapper;
import com.proj.sustc.mapper.ProductMapper;
import com.proj.sustc.service.IModelService;
import com.proj.sustc.service.exception.InsertException;
import com.proj.sustc.vo.RespBean;
import com.proj.sustc.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.model.IModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ModelServiceImpl implements IModelService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void insertModel(Model model) {
        if (model == null || model.getModel() == null || model.getProductNumber() == null || model.getUnitPrice() == null)
            throw new InsertException("Model information should not be null");
        if (modelMapper.selectModelByModelNumber(model.getModel()) != null)
            throw new InsertException("Duplicated model [" + model.getModel() + "]");
        if (modelMapper.selectProductByNumber(model.getProductNumber()) == null)
            throw new InsertException("Invalid product number [" + model.getProductNumber() + "]");
        if (modelMapper.insertModel(model) != 1)
            throw new InsertException("Unknown error when insert Model");
    }

    @Override
    public Model selectModelByModelNumber(String number) {
        return modelMapper.selectModelByModelNumber(number);
    }

    @Override
    public void insertProduct(Product product) {
        if (product == null || product.getName() == null || product.getNumber() == null)
            throw new InsertException("Product information should not be null");
        if (modelMapper.selectProductByNumber(product.getNumber()) != null)
            throw new InsertException("Duplicated product number [" + product.getNumber() + "]");
        if (modelMapper.insertProduct(product) != 1)
            throw new InsertException("Unknown error when insert Product");
    }

    @Override
    public Product selectProductByProductNumber(String number) {
        return modelMapper.selectProductByNumber(number);
    }

    @Override
    public RespBean AddModel(HttpServletRequest request, HttpServletResponse response, String model, String product, Integer price){
        //首先要看商品存不存在，存在则失败
        Model model1=modelMapper.selectModelByModelNumber(model);
        Product product1=productMapper.SelectProductByNumber(product);
        if(model1!=null||product1==null){
            return RespBean.operation_error1(RespBeanEnum.OPERATION_ERROR1);
        }
        Model insertModel=new Model();
        insertModel.setModel(model);
        insertModel.setProductNumber(product);
        insertModel.setUnitPrice(price);
       // insertModel.setId(modelMapper.SelectMaxId()+1);
        modelMapper.insertModel(insertModel);
        return RespBean.operation_success(RespBeanEnum.OPERATION_SUCCESS);
    }

}
