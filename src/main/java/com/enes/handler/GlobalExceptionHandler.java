package com.enes.handler;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.enes.exception.BaseException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = BaseException.class)
	public ResponseEntity<ApiError<String>> handleBaseException(BaseException ex, WebRequest request) {
		return ResponseEntity.badRequest().body(createApiError(ex.getMessage(), request));
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
		System.out.println("I am in the handler");
		Map<String, List<String>> map = new HashMap<>();
		for (ObjectError objError : ex.getBindingResult().getAllErrors()) {
			String fieldName= ((FieldError) objError).getField();
			if(map.containsKey(fieldName)) {
				map.put(fieldName, addValue(map.get(fieldName), objError.getDefaultMessage()));
			}else {
				map.put(fieldName, addValue(new ArrayList<>(), objError.getDefaultMessage()));
			}
		}
		return ResponseEntity.badRequest().body(createApiError(map, request));
		
	}
	
	private List<String> addValue(List<String> list, String newValue){
		list.add(newValue);
		return list;
	}
	
	
	private String getHostName()  {
		try {
			return Inet4Address.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public <E> ApiError<E> createApiError(E message, WebRequest request) {
		ApiError<E> apiError= new ApiError<>();
		apiError.setStatus(HttpStatus.BAD_REQUEST.value());
		
		Exception<E> exception = new Exception<>();
		exception.setPath(request.getDescription(false).substring(4));
		exception.setCreateTime(new Date());
		exception.setMessage(message);
		exception.setHostName(getHostName());
		
		
		apiError.setException(exception);
		return apiError;
	}

	
	 @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity<ApiError<String>> handleInvalidFormatException(HttpMessageNotReadableException ex, WebRequest request) {
	        String originalMessage = ex.getMostSpecificCause().getMessage();

	        // Define common type-related messages
	        Map<String, String> typeErrorMessages = new HashMap<>();
	        typeErrorMessages.put("java.lang.Integer", "must be a number.");
	        typeErrorMessages.put("java.lang.Double", "must be a decimal number.");
	        typeErrorMessages.put("java.lang.Boolean", "must be true or false.");
	        typeErrorMessages.put("java.time.LocalDate", "must be in YYYY-MM-DD format.");
	        typeErrorMessages.put("java.util.UUID", "must be a valid UUID format.");
	        
	        // Extract field name and type from the exception message
	        String fieldName = extractFieldName(originalMessage);
	        String message = "Invalid request body: Ensure all fields have correct data types."+originalMessage;

	        for (Map.Entry<String, String> entry : typeErrorMessages.entrySet()) {
	            if (originalMessage.contains(entry.getKey())) {
	                message = String.format("Invalid input: Field '%s' %s", fieldName, entry.getValue());
	                break;
	            }
	        }

	        return ResponseEntity.badRequest().body(createApiError(message, request));
	    }

	    private String extractFieldName(String errorMessage) {
	        // Attempt to extract the field name from the message
	        int startIndex = errorMessage.indexOf("[\"") + 2;
	        int endIndex = errorMessage.indexOf("\"]");

	        if (startIndex > 1 && endIndex > startIndex) {
	            return errorMessage.substring(startIndex, endIndex);
	        }
	        return "Unknown field";
	    }
	    
	    @ExceptionHandler(DataAccessException.class)
	    public  ResponseEntity<ApiError<String>> handleDatabaseException(DataAccessException e, WebRequest request) {
	    	StringBuilder errorMessage= new StringBuilder();
	    	errorMessage.append("Database Error: ");
	    	errorMessage.append(e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createApiError( errorMessage.toString(), request));
	    }
	    
	    @ExceptionHandler(UsernameNotFoundException.class)
	    public ResponseEntity<ApiError<String>> usernameNotFoundExceptionHandler(UsernameNotFoundException e, WebRequest request){
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createApiError( e.getMessage(), request));
	    }
	    
} 
