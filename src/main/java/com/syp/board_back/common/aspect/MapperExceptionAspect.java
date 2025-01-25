package com.syp.board_back.common.aspect;

import com.syp.board_back.common.exception.DatabaseException;
import com.syp.board_back.dto.common.response.ResponseCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MapperExceptionAspect {

    @Around("execution(* com.syp.board_back.mapper..*(..))")
    public Object handleMapperExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (DuplicateKeyException dke) {
            throw new DatabaseException(ResponseCode.DB_DUPLICATE_ERROR);
        } catch (DataAccessException de) {

            throw new DatabaseException(ResponseCode.DB_SERVER_ERROR);
        }
    }
}
