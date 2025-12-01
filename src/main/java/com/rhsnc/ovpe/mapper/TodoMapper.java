package com.rhsnc.ovpe.mapper;

import com.rhsnc.ovpe.domain.Todo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface TodoMapper {
    List<Todo> findAll();
} 