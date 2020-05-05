package com.example.demo.integration.bill;

import com.example.demo.dto.bill.UpdateBillDto;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.model.Bill.Bill;
import com.example.demo.eav.model.object.Object;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillIntegrationService {
    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private EavBaseConverter eavBaseConverter;

    @Transactional
    public void createBill(Bill source) {
        Object customerDataObject = objectRepository.save(eavBaseConverter.convertToEav(source));
        customerDataObject.getParams().forEach(
                param -> {
                    param.setObject(customerDataObject);
                    paramRepository.saveParam(param.getValue(), param.getObject().getObjectId(), param.getAttribute().getAttributeId());
                });
    }

    @Transactional
    public void deleteBill(Long Id){
    }

    @Transactional
    public void updateBill(UpdateBillDto source){
    }

    @Transactional
    public Bill getBill(Long id){
        Bill b = new Bill();
        b.setSpecification("follow the rules");
        return b;
    }
}
