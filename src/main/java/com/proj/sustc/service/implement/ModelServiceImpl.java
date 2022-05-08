package com.proj.sustc.service.implement;

import com.proj.sustc.entity.Model;
import com.proj.sustc.entity.Product;
import com.proj.sustc.mapper.ModelMapper;
import com.proj.sustc.service.IModelService;
import com.proj.sustc.service.exception.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements IModelService {
    @Autowired
    private ModelMapper modelMapper;

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
}
