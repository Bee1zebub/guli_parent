package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author test java
 * @since 2022-11-19
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    /**
     * 查询讲师表中的所有数据
     */

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询所有讲师数据
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAddTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        //list.forEach(System.out::println);
        try {
            int a = 10/0;
        } catch (Exception e) {
            throw new GuliException(20001,"执行自定义处理异常...");
        }
        return R.ok().data("items", list);
    }

    /**
     * 测试mybatis的xml用法是否与MP相冲突
     */
/*    @ApiOperation(value = "所有讲师列表")
    @GetMapping("selectAll")
    public List<EduTeacher> selectAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        //list.forEach(System.out::println);
        return list;
    }*/

    //id值通过路径传递  ==》 th:href="@{/order/details(orderId=${o.id})}"
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("delete/{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                           @PathVariable("id") String id) {
        boolean flag = teacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

    /**
     * 使用MP分页插件进行分页查询
     */
    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageList(@ApiParam(name = "current", value = "当前页码")
                      @PathVariable("current") Long current,
                      @ApiParam(name = "limit", value = "每页记录数")
                      @PathVariable("limit") Long limit) {
        //创建分页对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //调用分页方法进行分页
        teacherService.page(pageTeacher, null);

        List<EduTeacher> teacherRecords = pageTeacher.getRecords();
        long teacherTotal = pageTeacher.getTotal();
        return R.ok().data("total", teacherTotal).data("rows", teacherRecords);
    }

    /**
     * 使用MP分页插件进行多条件查询
     * '@RequestBody':使用json传递数据，将json数据封装到对应对象中，需要使用post提交！
     * (required = false)表示参数值可以为空
     */
    @ApiOperation(value = "分页多条件查询讲师列表")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") Long current,
                                  @PathVariable("limit") Long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {

        //创建page对象
        Page<EduTeacher> teacherPage = new Page<>(current, limit);

        //使用Wrapper配置条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询，mybatis 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name); //模糊查询
        }
        if (Objects.nonNull(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            //第一个参数为数据库字段名！ >= 创建时间，
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            //第一个参数为数据库字段名！ <= 结束时间
            wrapper.le("gmt_create", end);
        }
        teacherService.page(teacherPage, wrapper);
        List<EduTeacher> records = teacherPage.getRecords();//结果集
        long total = teacherPage.getTotal();//总记录数
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 添加讲师方法
     */
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    /**
     * 根据id获取讲师方法
     */
    @ApiOperation(value = "按id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("items", eduTeacher);
    }

    /**
     * 修改讲师方法  @RequestBody和 @PostMapping一起使用
     * UPDATE edu_teacher SET name=?, intro=?, career=?, level=?, avatar=?, sort=?, gmt_modified=? WHERE id=? AND is_deleted=0
     */
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean update = teacherService.updateById(eduTeacher);
        return update ? R.ok() : R.error();
    }

    /**
     * 根据id修改讲师方法,id参数优先
     */
    @ApiOperation(value = "根据id修改讲师")
    @PutMapping("updateTeacherById/{id}")
    public R updateTeacherById(@PathVariable("id") String id,
                               @RequestBody EduTeacher eduTeacher) {
        eduTeacher.setId(id);
        boolean update = teacherService.updateById(eduTeacher);
        return update ? R.ok() : R.error();
    }


}

