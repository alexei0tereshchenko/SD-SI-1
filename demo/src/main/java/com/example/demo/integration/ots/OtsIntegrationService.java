package com.example.demo.integration.ots;

import com.example.demo.dto.ots.UpdateOtsDto;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.model.ots.Ots;
import com.example.demo.eav.model.object.Object;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class OtsIntegrationService {
    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    @Qualifier("customer")
    private EavBaseConverter eavBaseConverter;

    @Transactional
    public void createOts(Ots source) {
        Object customerDataObject = objectRepository.save(eavBaseConverter.convertToEav(source));
        customerDataObject.getParams().forEach(
                param -> {
                    param.setObject(customerDataObject);
                    paramRepository.saveParam(param.getValue(), param.getObject().getObjectId(), param.getAttribute().getAttributeId());
                });
    }

    @Transactional
    public void deleteOts(Long id){
    }

    @Transactional
    public void updateOts(UpdateOtsDto source){
    }

    public Ots getOts(Long id) {
        Ots t = new Ots();
        t.setTerminationReason("Description here");
        return t;
    }
}
