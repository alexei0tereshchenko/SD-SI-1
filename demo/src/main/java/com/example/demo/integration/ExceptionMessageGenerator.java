package com.example.demo.integration;

import org.springframework.stereotype.Component;

@Component
public abstract class ExceptionMessageGenerator {
    private String objType;

    public String getObjType(){
        return this.objType;
    }

    public void setObjType(String newType){
        this.objType = newType;
    }

    public String generateCreateLimitReached(Long accountId){
        return "Can't create " + objType + " connected for billing account with ID:" + accountId +
                ", because limit of " + objType + "s for this billing account has already been reached";
    }

    public String generateObjectNotFound(Long objectId){
        return objType + " with ID:" + objectId + " wasn't found";
    }

    public String generateAccountNotFound(Long accountId){
        return "Can't find " + objType + "s for account with ID:" + accountId;
    }

    public String generateDeleteDataAccessException(Long objectId){
        return "Can't delete " + objType + " with ID:" + objectId + " because of DataAccessException";
    }
}
