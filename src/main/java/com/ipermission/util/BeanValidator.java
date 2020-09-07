package com.ipermission.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.ipermission.exception.ParamException;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class BeanValidator {
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    public static <T> Map<String,String> validation(T t, Class... groups){
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> validateResut = validator.validate(t, groups);
        if(validateResut.isEmpty()){
            return Collections.emptyMap();
        }else{
            LinkedHashMap errors = Maps.newLinkedHashMap();
            Iterator<ConstraintViolation<T>> iterator = validateResut.iterator();
            while(iterator.hasNext()){
                ConstraintViolation<T> validation = iterator.next();
                errors.put(validation.getPropertyPath().toString(),validation.getMessage());
            }
            return errors;
        }
    }

    public static Map<String,String> validateList(Collection<?> collection){
        Preconditions.checkNotNull(collection);
        Iterator<?> iterator = collection.iterator();
        Map<String, String> errors;
        do{
            if(!iterator.hasNext()){
                return Collections.emptyMap();
            }
            Object o = iterator.next();
            errors = validation(o, new Class[0]);
        }while (errors.isEmpty());
        return errors;
    }

    public static Map<String,String> validateObject(Object first,Object... objects){
        if(objects!=null&&objects.length!=0){
            return validateList(Arrays.asList(first,objects));
        }else{
            return validation(first,new Class[0]);
        }

    }
    public static void check(Object param) throws ParamException {
        Map<String, String> map = BeanValidator.validateObject(param);
        if (MapUtils.isNotEmpty(map)) {
            throw new ParamException(map.toString());
        }
    }
}
