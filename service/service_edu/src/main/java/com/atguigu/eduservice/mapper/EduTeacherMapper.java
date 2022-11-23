package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author test java
 * @since 2022-11-19
 */
//@Mapper //添加该注解将其注入到容器中
public interface EduTeacherMapper extends BaseMapper<EduTeacher> {

    public List<EduTeacher> selectAll();
}
