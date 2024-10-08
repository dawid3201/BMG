package BMG.BookManamgnet.Security.HelperUserInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;

public interface HelperInterface {
    default void checkBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errorList = bindingResult.getAllErrors();
            var errorMap = new HashMap<String, String>();

            for (org.springframework.validation.ObjectError objectError : errorList) {
                var error = (FieldError) objectError;
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            ResponseEntity.badRequest().body(errorMap);
        }
    }


}
