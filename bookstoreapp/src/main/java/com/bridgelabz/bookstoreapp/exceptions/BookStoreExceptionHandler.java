package com.bridgelabz.bookstoreapp.exceptions;

import com.bridgelabz.bookstoreapp.constant.CommonMessage;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class BookStoreExceptionHandler {

    /**
     * Purpose : To handle the exception when duplicate user gets inserted
     *
     * @param exception DataIntegrityViolationException
     * @return proper message that user exist
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        ResponseDTO responseDTO = new ResponseDTO(CommonMessage.USER_ALREADY_EXIST.getMessage(), exception.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.LOCKED);
    }

    /**
     * Purpose : To handle exception when user enter invalid fields
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO>handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<String> errorMessage = errorList.stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());
        ResponseDTO responseDTO = new ResponseDTO(CommonMessage.REST_REQUEST_EXCEPTION.getMessage(), errorMessage);
        return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
    }

    /**
     * Purpose : To handle the runtime exceptions
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(BookStoreException.class)
    public ResponseEntity<ResponseDTO> handleUserServiceException(BookStoreException exception){
        ResponseDTO responseDTO = new ResponseDTO(CommonMessage.REST_REQUEST_EXCEPTION.getMessage(), exception.getMessage());
        return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
    }

}
