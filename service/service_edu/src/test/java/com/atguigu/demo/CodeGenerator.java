package com.atguigu.demo;

import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Scanner;

/**
 * @author
 * @since 2018/12/13
 */
public class CodeGenerator {

    /**
     * E:\workplace\gulixueyuan_space\guli_parent\service\service_edu
     */
    @Test
    public void testPath(){
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
    }

    @Test
    public void testTimeType(){
        Date date = new Date();
        System.out.println(date);
    }

    @Test
    public void testNull(){
        TeacherQuery teacherQuery = new TeacherQuery();
        System.out.println(teacherQuery);
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help);
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


    @Test
    public void run() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        // 配置输出路径
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");

        gc.setAuthor("CMY TestJava");
        gc.setOpen(false);  //生成后是否打开资源管理器
        gc.setFileOverride(false); //生成时是否覆盖原同名文件

        //UserService
        //各层文件名称方式，例如： %sAction 生成 UserAction %s 为占位符
        gc.setServiceName("%sService");	//去掉Service接口的首字母I

        gc.setIdType(IdType.ID_WORKER_STR); //主键策略(MP3.5不同)
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);


        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/guli?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        //包  com.atguigu
        pc.setParent("com.atguigu");

        //包  com.atguigu.eduservice
        //模块名!!!
        pc.setModuleName(CodeGenerator.scanner("模块名"));

        //包  com.atguigu.eduservice.controller
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); //数据库表 映射到实体的命名策略,驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段 映射到实体的命名策略，未指定按照 naming 执行

        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作（返回该对象）
        strategy.setRestControllerStyle(true); //restful api风格控制器

        //设置对应表的名称!!!
        strategy.setInclude(CodeGenerator.scanner("表名，多个英文逗号分割").split(","));

        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

        //url中驼峰转连字符，驼峰转连字符
        //@RequestMapping("/managerUserActionHistory") -> @RequestMapping("/manager-user-action-history")
        strategy.setControllerMappingHyphenStyle(true);

        mpg.setStrategy(strategy);


        // 6、执行
        mpg.execute();
    }
}
