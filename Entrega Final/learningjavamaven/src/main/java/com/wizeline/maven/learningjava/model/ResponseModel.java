package com.wizeline.maven.learningjava.model;

public class ResponseModel {
	
	private String status;
	private String code;
	
	private ErrorModel errors = new ErrorModel();
	
	
	private ResponseModel(ResponseDTOBuilder builder){
		this.status = builder.status;
		this.code = builder.code;
		this.errors = builder.errors;
		
	}

    public String getStatus() {
        return status;
    }

   /// public void setStatus(String status) {
     //   this.status = status;
    //}

    public String getCode() {
        return code;
    }

  //  public void setCode(String code) {
    //    this.code = code;
   // }

    public ErrorModel getErrors() {
        return errors;
    }

   // public void setErrors(ErrorDTO errors) {
    //    this.errors = errors;
   // }
    
    public static final class ResponseDTOBuilder {
    	
    	private String status;
    	private String code;
    	
    	private ErrorModel errors = new ErrorModel();
    	
    	
    public ResponseDTOBuilder() {}
    
    
    public ResponseDTOBuilder status(String status) {
    	this.status= status;
    	return this;
    }
    public ResponseDTOBuilder code(String code) {
    	this.code= code;
    	return this;
    }
    
    
    public ResponseDTOBuilder errors(ErrorModel errorModel) {
    	this.errors= errorModel;
    	return this;
    }
    
    
    //devuelve el objeto ya contruido
    public ResponseModel build() {
    	ResponseModel responseDTO =  new ResponseModel(this);
        validateUserObject(responseDTO);
        return responseDTO;
    }
    private void validateUserObject(ResponseModel responseDTO) {
        //aplicar validaciones...
       
    }
    	
    }
}