# gumpframework
     用于java 网站开发，app接口，微信开发的服务端框架。  
     整合 spring boot ，spring data jpa ，并自定义持久层 查询技术，优雅的sql 查询 
     优雅的json转换，数据字典保存，公共工具类以及开发思想。不断更新中，敬请期待
# 运行介绍
1、在git的master分支为稳定版，迭代更新版在test上面。  
2、项目为core 和test两个项目，core为基础框架，test为实际开发框架。  
3、下载项目之后，需要将core用mvn clean install 命令将core基础框架安装到本地仓库。  
4、然后打开test项目启动web-back下的TestStart.java，运行基础frame.sql，并配置自己的数据库以及redis，test文件即可访问 
5、提示：此框架会不断更新，加入更多实用功能。现阶段只是基础版本，没有包含页面和代码生成器，之后会加入。希望大家耐心等待

#最新更新介绍  
  core包介绍  
  1、core包下的entity将封装 系统自用的entity，模块化，现在core源码中，大家可以按照自己的需求修改  
  2、core包下的repository是本框架的核心：里面已经封装好了 QlRepository 和 ORMRepository   
       QlRepository 主要是用于 sql 和 hql 编程用的实现，实现了参数动态绑定，sql结果集封装为List<map>等（不用再写繁多的entity来对应，并简化代码）  
       ORMRepository主要用户ORM方式的查询，提供面对对象的可读性极高的查询方式  
       这两个模块用户可以在service层中按照自己的需求随意调用  
    
  3、同时repository 项目模块还整合了spring data jpa，用户可以继承repository接口后，用spring data jpa的方法编程去读写数据库
   这三个模块相辅相成，不冲突，不耦合，用户可以自己选择自己喜欢的方式注入到service中开发。（如下）
    
    @Autowired
    private BaseQlRepository baseQlRepository;//用与sql和hql编程的模块，只需要传入sql或者hql以及动态参数，就可以返回结果
    @Autowired
    private BaseOrmRepository baseOrmRepository;//用户orm编程，只需要传入Filter之类的api就可以获取数据，可读性极高
    @Autowired
    private UserInfoRepository userInfoRepository;//spring data jpa的代理实现接口，可以在该接口中实现方法式编程，不用编写自己的实现
    
    QlRepository  编程
    @Cacheable(key = "#p0")
    @Override
    public List<Map<String,Object>> login(String name, String password){
        String sql = " SELECT a.name,a.password,a.id_ AS id from bs_user_info a where a.name_=:p1 and a.password_=:p2 ";
        return baseQlRepository.getListBySQL(sql,name,password);
    }
    
    ORMRepository 编程
    @Override
    public void save(T entity) {
        this.baseOrmRepository.save(entity);
    }

    
    
