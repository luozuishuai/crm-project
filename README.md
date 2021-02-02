# crm-project
第三阶段工程

CRM后台管理项目
后台基于springboot
前台vue element-ui
实现了基础的增删改查、图片上传、树形分类、动态菜单、token认证、Redis缓存

封装抽取了Base类
JSON字符串返回工具类
自定义响应码 封装返回数据
封装自定义valid注解实现自定义验证规则
前端使用element-ui验证
封装了Redis缓存工具类
Redis关键字池类
SpringBean类从SpringIOC容器中获取对象
使用单例模式封装了AsyncManage类 AsyncFactory创建多线程任务
封装了TokenService类 创建token 获取token 验证token 从token中获取数据
封装了UploadService类 上传文件到阿里云
封装了UCaptchaCacheUtils类缓存验证到数据库 和从数据库获取验证码
封装了JSONUtils 对象与JSON快速转换
封装了ReflectionUtils 反射类工具
封装了ServletUtils类 获取request请求  response响应对象 session对象
封装了TemplateUtils 将数据加载到html模板中
封装了TreeDataUtils 生成树形菜单工具类


数据库多表设计 - 后台结构搭建 - 结构Base类抽取 - 数据封装工具类抽取 
树形数据菜单完成 - 树形数据工具类抽取 - 员工信息管理 - element-ui数据折叠展示 - 后台数据校验@Valid - 全局异常处理 - 文件上传 - 表单验证
密码密文加密BCryptPasswordEncoder - 邮件发送JavaMailSender（工具类抽取封装） - 制作邮件html渲染数据 - 封装反射工具类处理对象数据 - （单例模式）封装多线程工具类实现多线程开发@EnableAsync -使用EasyExcel工具类实现数据导出excel
@Valid(group = )实现分组校验 - 多表操作业务处理 - 树形结构数据回显
登录用户封装 验证 - easy-captcha实现验证码 - 缓存验证码和用户信息到Redis - jtw生成token保存uuid - 前端请求头设置token - 配置拦截器验证登录状态
根据登录用户拥有的权限动态生成对应的菜单 - 获取树形动态菜单数据 - 前端回调渲染
