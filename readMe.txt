在eclipse上实现热部署
	1、~/conf/Catalina/ssspm.xml
		<?xml version="1.0" encoding="UTF-8"?> 
		<Context docBase="F:\tz2017\ssspm\WebContent" reloadable="true" />
	2、配置Tomcat
		Server location选Use Tomcat Installation
		Deploy path是项目的webContent路径	F:\tz2017\ssspm\WebContent
		Server Options下勾选了Serve modules without publishing。
		publishing勾选automatically publish when resources change
		将JSTL标签库的jar包加入到TomCat的lib下
		
	3、存在找不到配置文件的情况，eclipse不会把配置文件和编译好的class文件放到/WEB-INFO下
	4、JDK1.8中，局部内部类访问的局部变量不必用final修饰。与1.7不同
环境搭建
	Struts2.3.16+Spring4.2.1
	参考阶段一整合内容

项目原型：直观的了解我们项目的整体情况和实现的功能点
	* 用户管理
	* 角色管理
	* 菜单管理
	* 区域管理
	* 部门管理（机构管理和区域管理有时候可以放在一起）
	* 字典管理
	* 日志管理
	
项目简介：
	* 权限模快 是所有企业级应用或后台管理系统	必不可少的一部分
	*　通用性很强
	* 权限实现的方式和组件有很多，但是基本的理念的一样，都是通过配置和控制来达到用户
		权限的限制
	* 权限系统的目标：什么样的用户/角色能够访问或操作哪些资源(菜单，按钮，链接)
	
项目表结构分析：建库，导入建表脚本以及表的初始化数据	
	* 用户表  用户角色对应表
	* 角色表
	* 菜单表   角色菜单对应表
	* 区域表
	* 部门表  
	* 字典表  
	* 日志表
	
项目用到的前台组件
	* bootstrap
	* jquery
	* jqGrid  显示网格数据的jquery插件（表格）
	* jquery-jbox  基于jquery的多功能对话框插件
	* jquery-select2  基于动态构建下拉选择框的插件
	* jquery-validation 基于jquery的前台验证插件
	* jqeury-ztree 基于jquery的树形插件	
	* My97DataPicker 日期选择控件
	* superSlide 基于jquery的切换特效插件
	* treeTable  基于jquery的树形表格
	
项目用到的后台框架/组件
	struts2.3+spring4.2+springJdbcTemplate
	权限控制框架shiro
	缓存组件 Ehcache

项目的搭建和部署
	1：将我们的系统框架（注解的方式）搭建成功
	2：tomcat热部署方式(参考eclipse热部署)

项目后台代码包结构以及前台页面js/css/img文件夹路径
	* 后台包结构
		com.company.projectName.moduleName.action
		com.company.projectName.moduleName.service
		com.company.projectName.moduleName.dao
		com.company.projectName.util
		com.company.projectName.common 	
	* 前台页面结构
		webroot/web-inf/pages/moduleName/function	
		webroot/js
		webroot/css
		webroot/img   
		(webroot/jsAndCss/)	
		
项目功能制作
	1、用户登陆功能
		* 登陆页面的跳转流程，做一个假页面，请求到正式登陆页	
		* 接收参数
		* 服务层进行对象查询、显示密码加密比对
		* 正确则进入主页面
		加密算法	common-codec包提供的几种加密算法
			不可逆加密算法
				* MD5
				* SHA1(项目使用)
			可逆加密算法
				* BASE64
				* HEX(项目使用)
	
	2、修改密码
		* 判断前台输入的原密码是否正确(根据id查询对象)
		* 更新密码
	
	3、用户信息修改
		* 填充用户信息(根据id查询DTO对象) GROUP_CONCAT(roleName)  将多行的roleName并为一行，用逗号隔开
		* 文本填充、输入框填充、下拉菜单填充....
		* 去除输入框的默认值
		* 保存用户修改操作
		* 属性驱动/对象驱动/模型驱动
		* service填充对象其他数据后执行update操作
		
	4、字典分页查询
		* 屏蔽数据库的分页查询的差异
		* 书写一个分页查询类，封装查询必备参数(当前页、总页数、总记录数...)
		* 生成前台分页条的工具代码类
		* 书写查询Action,Service,Dao
		
	5、权限管理
	5.1 权限管理的原理和基本概念 
		*　权限管理实现用户对系统访问的控制，按照既定的规则或者策略控制用户访问被授权的资源
		* 权限管理包括用户认证和用户授权两个部分 

	权限管理 -> 用户认证
		概念：用户访问系统，需要验证用户的正确性和合法性
		认证的几种方式
			* 用户名和密码
			* 证书
			* 指纹
			
		认证的流程 用户名和密码的认证为例
			* 访问系统资源 （如果是匿名访问资源可以省略验证）
			* 验证用户是否存在
			* 验证密码是否正确
			* 验证通过就可以访问系统资源
			
		用户认证的几个基本的对象概念
			* subject 主体 可以理解为用户也可以是其他系统或者访问系统的其他对象
			* principle :身份信息，就是主体的标识或者编码	
			* credential :凭证信息 ，比如密码，证书，指纹

	权限管理 -> 用户授权
		概念：简单理解为就是访问控制，用户认证通过后，系统对资源进行授权给用户，用户才能访问
		授权的流程：
			* 分配权限
			* 用户认证通过以后，即将访问系统资源
			* 查询该用户是否有访问该资源的权限
			* 如果有则访问，无则拒绝
			
		授权的两种类型
			* 基于角色访问授权控制
				if(user.hasRole("system"))
				 	I  can do all things
				if(user.hasRole("manager"))
				 	I can do many things	
				if(user.hasRole("member"))
					I can do a little things 	
			注意：当新增角色或者修改角色是，需要更改相关业务代码，比如创建一个副经理角色，他和经理有交叉的权限。这个时候需要修改授权业务代码，不利于扩展
				
			* 基于资源的访问授权控制
				if(user.hasPermission("查看工资"))
					do it
				if(user.hasPermission("发布公告"))
					do it	
		总结：因为系统中的资源变化是最小的，而且如果资源有变化可以通过授权分配给用户，不需要修改业务代码
			所以一般权限管理都是基于资源的访问进行授权控制。
		
	权限管理实现 -> 解决方案
		1：粗粒度权限管理（按钮级别的权限管理）	
			* 概念：对资源类型的权限管理 资源类型比如：菜单，url，方法 ，按钮（url请求）	
			* 实现：用基于url的拦截方式进行系统级别的处理
				比如：filter ,interceptor
			* 基于url的拦截方式用拦截器来实现（需要两个拦截器 ：认证拦截器，授权拦截器）	
				认证拦截器：loginInterceptor
					a: 定义身份信息对象priciple
					b: 在用户确认登陆以后，将用户身份信息放到session
					c: 编写登陆拦截器，遇到除了匿名访问地址之外，判断session里面是否存在用户身份对象信息
						如果有则认证成功 ，放行，否则跳转到登陆页面
					d：配置拦截器
						
				授权拦截器 AuthorizationInterceptor	
					a: 在登陆的LoginAction,在用户身份验证通过以后，从数据库获取该用户的授权信息
					b: 编写授权拦截器，通过认证拦截器以后，进入授权拦截器，从session去除用户授权资源信息，查询此次访问的资源是否在用户的权限
						范围之内，如果是，否则转到拒绝访问页面
					c: 配置拦截器
		
		2：细粒度权限管理
			* 概念：不仅对资源类型进行权限管理，还对资源实例（数据）进行管理，比如本部门的员工只能查看本部门信息
					不能查看其它部门的信息
			* 数据级别的权限管理，因为没有共性，所以不能进行系统级别的处理，只能放在业务逻辑中单独处理		
		
		总结：基于url的权限管理方式,实现简单，而且不依赖于任何框架，如果不用struts2的拦截器，我们用web的filter也可以实现
			但是有一个确定：需要将所有访问的url配置到数据库中，繁琐且不容易维护，也不规范。

	5.2 shiro简介
		shiro是apache下面的一个开源框架，他实现了用户身份认证，权限授权 ，加密等功能，组成了一个系统级的安全认证框架
		
	shiro的优势
		shiro是将安全认证相关的功能抽取出来组成一个框架，使用简单，灵活，可以非常快速的完成认证，授权等功能开发，降低系统
		集成难度，使用广泛，不仅可以运行在web应用，非web应用也可以，而且还支持分布式集群方式
	
	shiro的构成以及相关的概念	
		* Subject:主体，它在shiro里面是一个接口，外部系统通过subject进行认证授权
			subject其实是通过Security Manager来完成身份认证的操作
			
		* SecurityManager:安全管理器，是shiro的核心，管理所有的subject
			它是通过Authenticator进行身份认证，通过Authorizer进行授权，通过sessionManager进行会话管理
			SecurityManger也是一个接口 ，继承了	Authenticator，Authorizer，sessionManager三个接口
		
		* Authenticator 认证器，用来对用户身份进行认证，他也是一个接口
		
		* Authorizer 	 授权器，用户通过认证器认证以后，在访问资源时，需要通过授权器判断此用户是否有操作权限
		
		* SessionManager 会话管理，不依赖于web容器的session,所以shiro能在非web系统中应用	
		
		* realm  :认证和授权需要通过realm获取用户的权限数据，相当于数据源
		
		* CacheManager:缓存管理，将用户的权限数据存储在缓存，提高系统性能(Ehcache)
	
	shiro的开发环境搭建	
		* shiro的核心包		   	shiro-core-1.2.3.jar
		* shiro与web整合的包	   	shiro-web-1.2.3.jar
		* shiro与spring整合的包       	shiro-spring-1.2.3.jar
		* shiro与quartz整合的包       	quartz-1.6.1.jar 
								shiro-quartz-1.2.3.jar
		* shiro与ehcache整合的包      ehcache-core-2.5.0.jar
								shiro-ehcache-1.2.3.jar
	
	shiro的身份认证流程
		// 1、构建SecurityManager对象SecurityManagerFactory
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:com/zt/ssspm/test/studyshiro/shiro.ini");
		SecurityManager manager = factory.getInstance();
		// 2、将SecurityManager对象设置到运行环境
		SecurityUtils.setSecurityManager(manager);
		//--------------------------------------------------------------
		// 1、获取当前正在执行的主体对象，它基于与当前线程或传入请求相关联的用户数据获取subject。
		Subject subject = SecurityUtils.getSubject();
		// 2、根据用户信息创建一个身份令牌，记录了身份和凭证
		UsernamePasswordToken token = new UsernamePasswordToken("admin", "123");
		// 3、进行身份认证(身份仍证+凭证认证)
		try {
			// 在项目集成时subuject.login最终是由SecurityManager通过Authticator调用realm来完成身份认证
			subject.login(token);
			
		} catch (UnknownAccountException e) {
			// 身份信息错误
			System.out.println("用户身份信息错误错误");
		}catch (IncorrectCredentialsException e) {
			// 凭证信息错误
			System.out.println("用户凭证信息错误");
		}catch ( LockedAccountException lae ) {
		    //account for that username is locked - can't login.
			System.out.println("当天登陆用户已被锁定");
		}catch (Exception e) {
			System.out.println("其他异常");
			e.printStackTrace();
		}
		// 4、判断身份认证是否通过
		boolean flag = false;
		flag = subject.isAuthenticated();
		System.out.println("用户认证状态"+flag);
		// 测试登出操作
		subject.logout();
		flag = subject.isAuthenticated();
		System.out.println("用户认证状态"+flag);
	shiro的身份认证 自定义realm        
		public class UserRealm extends AuthorizingRealm{
			@Override
			// 认证方法
			protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
				// 从token中取出身份信息
				String userName = (String) token.getPrincipal();
				// 模拟数据库的user信息
				User user = new User();
				user.setLoginName("admin"); 
				user.setPassword("123456");
				if(userName.equals(user.getLoginName())) {
					// 身份认证通过，开始凭证认证			身份信息	正确的凭证信息			当前类的类名
					SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, user.getPassword(), this.getName());
					return info;
				}
				return null;
			}
			
			@Override
			// 授权方法,只需将查询到的权限信息返回即可
			protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principle) {
		//		String userName = (String) principle.getPrimaryPrincipal();
				// 模拟一些权限信息
				List<String> permissions = new ArrayList<String>();
				permissions.add("user:add");
				permissions.add("user:delete");
				permissions.add("user:update");
				// 将得到的权限信息放入simpleAuthorizationInfo对象保存
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				for (String permission : permissions) {
					info.addStringPermission(permission);
				}
				return info;
			}
		}
	shiro的授权流程
		// 基于角色的权限判断
		System.out.println("当前用户拥有role1权限======"+subject.hasRole("role1"));
		System.out.println("用户是否拥有role1,role2角色==="+subject.hasAllRoles(Arrays.asList("role1","role2")));
		// 基于资源的权限判断
		System.out.println("用户是否拥有user:add的权限====="+subject.isPermitted("user:add"));
		System.out.println("用户是否拥有user:add,user:update的权限==="+subject.isPermittedAll("user:add","user:update"));
		System.out.println("用户是否拥有user:delete的权限====="+subject.isPermitted("user:delete"));
	认证成功后授权
	5.3 shiro集成web项目
	1：加入shiro与项目整的jar包	
		* shiro的核心包		   	shiro-core-1.2.3.jar
		* shiro与web整合的包		shiro-web-1.2.3.jar
		* shiro与spring整合的包      	shiro-spring-1.2.3.jar
		* shiro与quartz整合的包       	quartz-1.6.1.jar shiro-quartz-1.2.3.jar
		* shiro与ehcahe整合的包      	ehcache-core-2.5.0.jar  shiro-ehcache-1.2.3.jar

	2：在web.xml文件里面添加shiro的过滤器	
		<!--shiro的过滤器  DelegatingFilterProxy 他是一个代理，他会从spring容器中取找shiroFilter-->
		 <filter>
			  <filter-name>shiroFilter</filter-name>
			  <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class> 
			 	<!--这个参数的值为true代表由web容器来控制这个filter的生命周期  -->
			 <init-param>
			 	<param-name>targetFilterLifecycle</param-name>
			 	<param-value>true</param-value>
			 </init-param>
			<!--默认寻找的spring容器中的过滤器名称为shiroFilter  -->
			<init-param>
				  <param-name>targetBeanName</param-name>
				<param-value>shiroFilter</param-value>
			 </init-param>
		 </filter>
		 <filter-mapping>
			 <filter-name>shiroFilter</filter-name>
			 <url-pattern>/*</url-pattern>
		 </filter-mapping>
		  
	3：在spring文件里面配置shiroFilter SecurityManager realm SessionManager......
		<!-- 配置shiro的过滤器 -->
		<bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
			<property name="securityManager" ref="securityManager"></property>
			<property name="loginUrl" value="/login.action"></property>
			<property name="successUrl" value="/main.action"></property>
		</bean>
		<!-- 注册安全管理器 -->
		<bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
			<property name="realm" ref="userRealm"></property>
		</bean>
		<!-- 注册userRealm -->
		<bean id="userRealm" class="com.zt.ssspm.security.UserRealm"></bean>
	
	4、shiro的认证流程
		* 主页需要认证 —> 进入认证流程
		* —>认证成功—>返回上一次访问的页面(main.action)
		* —>认证失败—>login.action的失败返回页面(/WEB-INF/pages/login.jsp)

		*	<!-- 配置无权限访问时的页面 -->
			<property name="unauthorizedUrl" value="/refuse.jsp"></property>
			<!-- 配置处理结果的Filter -->
			<property name="filters">
				<map>
					<entry key="authc" value-ref="loginFormAuthFilter" />
				</map>
			</property>
		
	5、配置页面不同的过滤器
		* anon(匿名访问),authc(需要权限访问),authcBasic,user(rememberMe登陆时)是第一组认证过滤器
		* perms(指定权限),port,rest,roles(指定角色),ssl是第二组授权过滤器
		*	<!-- 定义访问资源的权限类型，对不同的资源设置不同的过滤器 -->
			<property name="filterChainDefinitions">
				<value>
					<!--对静态资源进行匿名访问  -->
				 	/jsAndCss/** = anon
				 	/toLogin.action = anon
				 	/logout.action = logout
				 	<!--/** = authc 代表所有的url必须经过认证才可以访问  -->
				 	/** = authc
				</value>
			</property>
	
	6、指定访问权限的两种方式
		a: 在配置文件filterChainDefinitions中配置
				/sysmgr/getDictList.action = perms[dict:query]
			需要shiroFilter中配置一个无权限访问失败的提示页面
				<property name="unauthorizedUrl" value="/refuse.jsp"></property>
			此url访问时 被PermissionAuthorizationFilter 拦截,调用UserRealm的doGetAuthorizationInfo判断此用户是否有此权限，有则通过，无则返回拒绝页面

经过A步骤发现并不能正常的指定URL的权限,模拟用户没有权限时，依然可以使用该功能。所以基本确定是配置文件中配置路径的权限没有生效。(与Action是否交由Spring管理无关)
所以使用注解指定权限。
	注解后，发现可以正常的进入授权方法。授权通过，一切正常；授权不通过，后台抛出异常，但是页面并没有跳到我们配置的失败提示页面。
		就在Struts.xml中配置了全局的异常处理
			<global-exception-mappings>
				<exception-mapping result="refusePage" exception="org.apache.shiro.authz.UnauthorizedException"></exception-mapping>
			</global-exception-mappings>
	处理后再次测试时，发现授权失败时，异常被成功捕获，但是页面却仍然是提交时的页面。同样没有跳到我们struts.xml中配置的失败提示页面。
	指定访问权限没有问题、访问失败的异常捕获没有问题 ----> 返回页面的结果有问题
		测试是否是AJAX的问题
		'error' : function(XMLHttpRequest, textStatus, errorThrown){
			top.$.jBox.closeTip();
			alert("你当前没有访问这个功能的权限，请联系管理员");
			console.log(textStatus); // parsererror json解析异常,没有权限时，后台无返回结果
			console.log(errorThrown);// SyntaxError: Unexpected token < in JSON at position 0
		}
		添加后测试可以正常的关闭js提示，并且可以给出一些js提示信息。
		
		测试使用Form表单提交，结果可以跳转到提示页面。
		
		后台是HTTP转发请求，而不是返回json数据，我们可以在前台进行转发页面。或者对AJAX进行扩展
		
		b: 开启Spring的AOP类代理和shiro注解
			<!-- 开启AOP代理 -->
			<aop:config proxy-target-class="true"></aop:config>
			<!-- 开启shiro注解 -->
			<bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
				<property name="securityManager" ref="securityManager"></property>
			</bean>
			在对应的Action方法上配置@RequiresPermissions("dict:query")
		
		c: 在页面使用shiro标签
			<shiro:hasPermission name="sys:dict:query">
				<input id="btnSubmit" onclick="dictMgr.queryDictList(1,10);" class="btn btn-primary" type="button" value="查询" />
			</shiro:hasPermission>
	
	7、shiro缓存ehcache
		shiro提供了对认证信息和授权信息的缓存，认证信息的缓存默认是关闭。对于授权信息的缓存默认是开启的
		用户第一次授权：调用relam查询数据库，第二次访问的时候 ，会直接从缓存去授权信息。
		
		* 添加缓存需要的jar包（ehcahe-core ,shiro-ecache）
		* 配置文件引用缓存cachemanager(securityMangae)
		* 定义cacheManager ,查找ehcahe的配置文件 (ehcache.xml ehcahe.xsd)
			
			<!-- 配置ehcacheManager -->
			<bean id="ehcacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager">
				<property name="cacheManagerConfigFile" value="classpath:ehcache.xml"></property>
			</bean>
		
		如果用户正常退出，shiro的缓存会自动清空
		如果用户非正常退出，shiro的缓存也会自动清空
		如果修改了用户的权限以后 ，用户没有退出系统，修改的权限不会生效，怎么避免
			* 修改权限的方法中主动调用清除缓存的方法(clearCache())
	
	8、注入sessionManager
		项目与shiro整合以后 ，我们不需要使用web容器的session,使用shiro的session来管理会话
			* 在配置文件securityManager里面引入sessionManager
			* 设置sessionManager的失效时间和定时清理间隔时间	
		<!-- 定义sessionManager -->
		<bean id="sessionManager" class="org.apache.shiro.web.session.mgt.DefaultWebSessionManager">
			<!-- 配置session的失效时间,单位是ms 1h -->
			<property name="globalSessionTimeout" value="36000000"></property>
			<!-- 定时清理失效的会话  30min -->
			<property name="sessionValidationInterval" value="1800000"></property>
		</bean>	
			* 在securityManager中注入sessionManager和cacheManager
				<bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
					<property name="realm" ref="userRealm"></property>
					<property name="cacheManager" ref="ehcacheManager"></property>
					<property name="sessionManager" ref="sessionManager"></property>
				</bean>		
	
	6、菜单管理功能	
	分析此功能与原有功能有点不同
	(没有查询按钮 进入功能就默认查询所有的记录 ，/树形表格的方式显示数据)
		* 页面引入
			要有一定的规范
			页面名称：menuEdit.jsp	menuList.jsp
			对应的Action名称：
				/sysmgr/menuEdit.action?editFlag=1	方法：menuEdit
				/sysmgr/menuList.action				方法：menuList
		* 进入功能页面的struts配置
		* 页面切换的调整
		* 修改/增加的判断
			添加菜单的链接
				/sysmgr/menuEdit.action?editFlag=1
				/sysmgr/menuEdit.action?parentId=${menu.parentId}&&editFlag=1
			修改菜单的链接
				/sysmgr/menuEdit.action?id=${menu.id}&&editFlag=2
			删除菜单的方法(使用AJAX)
				javascript:menuMgr.delMenu(${menu.id})
			提交修改的请求
				/sysmgr/saveMenu.action	带一个json对象格式的字符串和editFlag				
		* 主页链接的修改
			
	查询出所有菜单列表以属性表格的形式展示
		* 查询当前访问用户有权限的所有菜单
			关联user_role、role_menu、menu三张表查询
		* 为了treeTable的正常显示，用递归排序将菜单依次排列
			使用forEach标签展示页面
		
	增加功能
		* 引入zTree的系统标签，查询后台所有当前用户菜单的节点
			
		a: 在taglib.jsp标签页面里面引入自定义的标签库(前缀可以随便命名)
			<%@taglib prefix="sys" tagdir="/WEB-INF/tags/" %>
			
		b: 在sturts配置文件里面配置点击文本框弹出事件的返回页面
			<!-- 标签请求数据的action -->
			<action name="getMenuTree" class="menuAction" method="getMenuTree">
				<result type="json">
					<param name="includeProperties">jsonObj</param>
				</result>
			</action>
			<!-- 标签请求页面的Action -->
			<action name="treeSelect" class="com.zt.ssspm.sysmanage.action.TagAction" method="treeSelect">
				<result>/WEB-INF/pages/common/treeSelect.jsp</result>
			</action>
			<action name="iconSelect" class="com.zt.ssspm.sysmanage.action.TagAction" method="iconSelect">
				<result>/WEB-INF/pages/common/iconSelect.jsp</result>
			</action>
			
		c: 弹出页面的数据源是List<Map<String,Object>的JSON对象的字符串格式
			// 获取zTree菜单
			public String getMenuTree() {
				List<Map<String,Object>> mapList  = new ArrayList<Map<String,Object>>();
				List<Menu> menuList = this.menuService.queryMenuListByUserId(UserUtils.getCurrentUserId());
				for(Menu menu:menuList){	
					if(extId != null || !menu.getId().equals(extId)){
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("id", menu.getId());
						map.put("pId", menu.getParentId());
						map.put("name", menu.getName());
						mapList.add(map);
					}
				}
				this.jsonObj = JSONArray.fromObject(mapList).toString();
				return SUCCESS;
			}
		
		d: 页面使用自定义的标签
			<sys:treeSelect 
					url="/sysmgr/getMenuTree.action" id="parent" value="${menu.parentId }" 
					labelName="parentName" labelValue="${menu.parentName}" title="菜单" name="parentId" 
					extId="${not empty menu.id ? menu.id : 0}" cssClass="required" >
			</sys:treeSelect>
			<sys:iconselect name="icon" value="${not empty menu.icon? menu.icon:'' }" id="icon"></sys:iconselect>
	
	菜单功能优化和BUG修改
	
		zTree下拉列表不用自定义标签实现，使用js,并且自动展开两级,选中后马上赋值并关闭选项框
			<div class="control-group">
	            <label class="control-label">上级菜单:</label>
	            <div class="controls">
	               	<div class="input-append">
						<input id="parentId" name="parentId" class="required" type="hidden" value="${menu.parentId}"/>
						<input id="parentName" name="parenName" readonly="readonly" type="text" value="${menu.parentName}" class="required" />
						<a id="menuButton" href="javascript:showMenu();" class="btn" >&nbsp;<i class="icon-search" ></i>&nbsp;</a>&nbsp;&nbsp;
					</div>
	            </div>
	        </div>
			<div id="menuContent" class="menuContent" style="display:none; position: absolute;background: #f0f6e4;">
				<ul id="menuTree" class="ztree" style="margin-top:0; width:160px;"></ul>
			</div>
			更改两个方法：点击事件进行赋值，页面加载时进入后台获取值
				// 点击树时返回值给页面
				function onClick(e, treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("menuTree"),
						nodes = zTree.getSelectedNodes();
					// 从nodes中获取表单需要的值
					var nodeName = nodes[0].name;
					var nodeId = nodes[0].id;
					$("#parentName").attr("value", nodeName);
					$("#parentId").attr("value", nodeId);
					// 选中节点后，隐藏选框
					hideMenu();
				}
				$(document).ready(function(){
					// 从后台请求数据显示
					$.get('${ctx}/sysmgr/getMenuTree.action',function(data){
						// 初始化后台数据
						var jsonObj = $.parseJSON(data.jsonObj);
						var tree = $.fn.zTree.init($("#menuTree"), setting, jsonObj);
						// 设置一个默认的展开级别
						var nodes = tree.getNodesByParam('level',2);
						for(var i=0,length=nodes.length;i<length;i++){
							tree.expandNode(nodes[i],true,false,false);
						}
						
					});
				});
			
		修改菜单时，自动选中父级菜单同时不显示本身的所有子节点
		更改查询方法
			// 如果是添加下级菜单我们可以在页面上锁定上级菜单的选择，不允许更改，否则需要在这里查询,不显示下级菜单同修改相同
			if(id != null) {
				// 进入了修改页面    查询出当前所有不是menuI=id的子菜单    没有与user对象进行关联
				menuList = this.menuService.queryOtherMenuById(id);
				// 关联的做法，可以查询出当前菜单的所有子菜单的id，与权限关联查询出的菜单集合对比，去掉当前菜单的所有子菜单的id
			}else {
				// 进入了添加页面
				menuList = this.menuService.queryMenuListByUserId(UserUtils.getCurrentUserId());
			}
			
		查询所有的子节点(递归)
			@Override
			public void getChildrenIds(Long menuId, List<Long> listIds) {
				// 查询第一级
				List<Long> list = getChildrenIds(menuId);
				if(list != null && list.size()>0){
					// 有下一及菜单
					listIds.addAll(list);
					// 遍历下一及菜单
					for (Long id : list) {
						if(hasChildMenu(id)){
							// 存在子菜单，迭代
							getChildrenIds(id,listIds);
						}else {
							continue;
						}
					}
				}else{
					return;
				}
			}
		
		最顶级的父级节点无法修改(外连接解决)
			buffer.append(" SELECT c.id, c.parent_id, d.name AS parent_name,c.name, c.sort, c.href, c.target, c.icon, ");
			buffer.append(" c.is_show, c.permission, c.create_by,c.create_date,c.update_by,c.update_date,c.remarks  ");
			buffer.append(" FROM pm_sys_menu c LEFT JOIN pm_sys_menu d  ");
			buffer.append(" ON c.parent_id=d.id WHERE c.id=?  AND c.del_flag=0 ");
		删除节点时，在后台判断返回
			查询是否存在子节点
			// 删除方法
			public String delMenu() {
				boolean flag = false;
				try {
					flag = menuService.queryHasChildMenu(id);
					if(flag) {
						// 存在子节点
						this.msg = "此菜单下面还有子菜单,请确定所有的子菜单被删除以后再进行此操作";
					}else {// 不存在子节点
						flag = menuService.delMenu(id);
						if(flag)
							this.msg = "删除菜单成功";
						else 
							this.msg = "删除菜单失败";
					}
				} catch (Exception e) {
					this.msg = "操作失败";
				}
				
				return SUCCESS;
			}
	
	7、部门管理功能
		* 引入页面，做好各个页面的跳转
		* 查询并显示
		* 查询树形菜单
			问题：每次查询一种树形菜单都要写一个对应的排序方法
				每次都要构造一个List<Map<String,Object>>对象，繁琐、不够灵活
				淘汰节点使用纯SQL解决使用的方法过多,需要频繁的查询数据库.
					能不能将对象进行筛选，而不是直接从数据库迭代查询
		* 保存
		* 解决方法
			设计entity的超类TreeDto
			设计TreeDto通用的排序方法
			设计查询所有TreeDto子节点的方法
				返回一个List集合
				返回一个Map集合
				
		* 具体实现
			a：定义TreeDto对象
				private Long id;
				private Long parentId;
				private String name;
				
			b：编写工具类 TreeUtils,设计排序方法，查询所有子节点的方法
				public static <T> void sortTreeDtoList (List<T> returnTreeList,List<T> treeList,Long parentId) {
			    	// 轮询所有菜单
			        for (int i = 0; i < treeList.size(); i++) {
			        	TreeDto m = (TreeDto) treeList.get(i);
			            // 找到第一级菜单
			            if (m.getParentId() != null&& m.getParentId().equals(parentId)) {
			            	returnTreeList.add( (T) m);             
			                for (T child : treeList) {
			                	// 准备递归
			                    if (((TreeDto) child).getParentId() != null&& ((TreeDto) child).getParentId().equals(parentId)) {
			                        // 做递归
			                    	sortTreeDtoList(returnTreeList, treeList, m.getId());
			                        break;
			                    }
			                }
			                
			            }
			        }
			    }
				public static void getAllChildrenList(List<TreeDto> childrenList,List<TreeDto> treeList,long parentId) {
					for(TreeDto tree:treeList){
			    		//遍历出父id等于parentId的所有子节点
			    		if(tree.getParentId().longValue() == parentId){
			    			childrenList.add(tree);
			    			getAllChildrenList(childrenList,treeList,tree.getId());
			    		}
			    	}
				}				
				public static void getAllChildrenMap(Map<Long,TreeDto> childrenMap,List<TreeDto> treeList,long parentId) {
					for(TreeDto tree:treeList) {
						if(tree.getParentId().longValue() == parentId) {
							childrenMap.put(tree.getId(), tree);
							getAllChildrenMap(childrenMap,treeList,tree.getId());
						}
					}
				}		
			
			c:注意淘汰节点的写法 ，不要用for() 用迭代器 Iterator
				// 查询树形部门菜单
				public String getDeptTree() {
					// 1、构建TreeList对象
					List<Dept> deptList = this.deptService.queryAllDept();
					List<TreeDto> treeList = new ArrayList<>();
					for(Dept dept:deptList) {
						TreeDto tree = new TreeDto();
						tree.setId(dept.getId());tree.setName(dept.getName());tree.setParentId(dept.getParentId());
						treeList.add(tree);
					}
					// 2、查询出所有的子节点(修改节点信息时才进行查询)采用key(id) --- value(dto)存储
					if(this.deptId != null) {
						Map<Long,TreeDto> treeMap = new HashMap<>();
						if(treeList!=null && treeList.size()>0) {
							TreeUtils.getAllChildrenMap(treeMap, treeList, deptId);
						}
						// 3、从TreList中移除子节点/传入的节点(不能使用for)
						Iterator<TreeDto> it = treeList.iterator();
						while(it.hasNext()) {
							TreeDto t = it.next();
							// 判断是否存在与map集合中
							if(treeMap.get(t.getId()) != null) {
								// 存在
								it.remove();
							}
							// 移除本身
							if(t.getId().equals(deptId)) {
								it.remove();
							}
						}
					}
					// 4、将TreeList发送到前台页面
					this.jsonObj = JSONArray.fromObject(treeList).toString();
					return SUCCESS;
				}			
			
			d:界面上zTree的setting设置成id，parentId，name模式
				var setting = {
					view: {
						dblClickExpand: false
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "parentId",
							rootPId: 0
						}
					},
					callback: {
						onClick: onClick
					}
				};			
		
		* 每种排序 都需要提供一个util方法，是否可以考虑用TreeUtil工具类来封装
			a:每个包含tree属性的对象继承TreeDto
			b：用泛型的封装TreeUtils.sortTreeList()-->采用递归
	
	8、区域管理功能
		同部门管理功能
	
	9、动态菜单的实现
		* 要结合实现菜单的组件 ,js代码的控制 ，后台数据的输出
		* a: 菜单有几级
		* b: 前台是什么组件实现，要符合组件的css结构才能控制
		* c: js代码怎么控制数据的加载，是ajax异步还是全部一次性加载
		* d: 在登陆页面进入主页的时候，要加载到哪一级 	
			项目使用的为3级菜单，前端组件使用的是bootStrap。
			实现思路：
				* 进入主页授权成功后显示第一级菜单
				
				* 根据权限 第一级菜单会向后台请求二三级菜单，并构建页面
	
		问题：怎么将我的面板放在第一位
			* 查询时按照parentId和sort排序
	10、角色管理
		* 完成最基本的增删改查
		* 难点：添加页面时显示菜单、部门、区域树
			// 显示菜单资源树
			var menuSetting = {
				check:{enable:true,nocheckInherit:true},
				view:{selectedMulti:false},
				data:{simpleData:{enable:true}},
				callback:{beforeClick:function(id, node){
					menuTree.checkNode(node, !node.checked, true, true);
					return false;
					}
				}
			};
			var menuZNodes=[
				<c:forEach items="${menuList}" var="menu">
					{
						id: "${menu.id}",
						pId: "${menu.parentId}", 
						name: "${menu.name}"
					},
				</c:forEach>
		    ];
			// 初始化树结构
			var menuTree = $.fn.zTree.init($("#menuTree"), menuSetting, menuZNodes);
			//默认展开全部节点
			menuTree.expandAll(true);
				
		* 难点：保存角色信息时，对应的保存角色部门，角色菜单，角色区域对应表
			// 获取角色的信息
			var json = {};
			var fromData = $("#roleEditForm").serializeArray();
			$.each(fromData, function(index,item){
				json[item.name] = item.value;
			});
			var obj = JSON.stringify(json);// 将json对象转为字符串数据发送到后端
			// 获取已选中的菜单信息
			var menuObj = {};
			var menuNodes = menuTree.getCheckedNodes(true);
			$.each(menuNodes, function(index, item){
		 		menuObj[item.id] = item.id;
			});
			var menuIds = JSON.stringify(menuObj);
			// 获取已选中的部门信息
			var deptObj = {};
			var deptNodes = deptTree.getCheckedNodes(true);
			$.each(deptNodes,function(index, item){
				deptObj[item.id] = item.id;
			});
			var deptIds = JSON.stringify(deptObj);
			// 获取已选中的区域信息
			var areaObj = {};
			var areaNodes = areaTree.getCheckedNodes(true);
			$.each(areaNodes,function(index, item){
				areaObj[item.id] = item.id;
			});
			var areaIds = JSON.stringify(areaObj);
		// 后台
			// 点击保存按钮
			public String saveRole() {
				// 把前台的obj字符串数据转为Role对象
				JSONObject roleJson = JSONObject.fromObject(obj);
				JSONObject menuJson = JSONObject.fromObject(menuIds);
				JSONObject deptJson = JSONObject.fromObject(deptIds);
				JSONObject areaJson = JSONObject.fromObject(areaIds);
				Role editRole = (Role) JSONObject.toBean(roleJson,Role.class);
				boolean flag = false;
				if(editFlag == 1) {
					// 新增角色
					flag = roleService.saveRole(editRole,menuJson,deptJson,areaJson);
					if(flag)
						 this.msg = "新增角色信息成功";
					 else 
						 this.msg = "新增角色信息失败";
				}else {
					// 保存已经修改的角色
					flag = roleService.updateRole(editRole,menuJson,deptJson,areaJson);
					if(flag)
						 this.msg = "修改角色信息成功";
					 else 
						 this.msg = "修改角色信息失败";
				}
				return SUCCESS;
			}
			保存新增的角色信息时，通常返回角色的id
				public Long saveRole(Role role) {
					StringBuffer buffer = new StringBuffer();
					buffer.append(" INSERT INTO pm_sys_role(name,create_by,create_date,update_by,update_date,remarks) ");
					buffer.append(" VALUES(?,?,?,?,?,?) ");
					
					KeyHolder holder = new GeneratedKeyHolder();
					this.template.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
							PreparedStatement ps = connection.prepareStatement(buffer.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
							ps.setObject(1, role.getName());
							ps.setObject(2, role.getCreateBy());
							ps.setObject(3, role.getCreateDate());
							ps.setObject(4, role.getUpdateBy());
							ps.setObject(5, role.getUpdateDate());
							ps.setObject(6, role.getRemarks());
							
							return ps;
						}
					}, holder);
					return holder.getKey().longValue();
				}
			构建中间表的对象，批量插入数据
				// 构造角色菜单表对象
				List<RoleToMenu> roleMenuList = new ArrayList<>();
				Iterator<Long> itMenu = menuJson.keys();
				RoleToMenu roleToMenu;
				while(itMenu.hasNext()) {
					roleToMenu = new RoleToMenu();
					roleToMenu.setRoleId(roleId);
					roleToMenu.setMenuId(new Long(menuJson.get(itMenu.next()).toString()));
					roleMenuList.add(roleToMenu);
				}
				flag = roleDao.addRoleToMenuBatch(roleMenuList);
				public boolean addRoleToMenuBatch(List<RoleToMenu> roleMenuList) {
					boolean flag = true;
					try {
						String sql = "INSERT INTO PM_SYS_ROLE_MENU(ROLE_ID,MENU_ID) VALUES(?,?) ";
						template.batchUpdate(sql, new BatchPreparedStatementSetter() {
							
							@Override
							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setObject(1, roleMenuList.get(i).getRoleId());
								ps.setObject(2, roleMenuList.get(i).getMenuId());
							}
							
							@Override
							public int getBatchSize() {
								return roleMenuList.size();
							}
						});
					}catch(Exception e) {
						e.printStackTrace();
						flag = false;
					}
					return flag;
				}
				
		* 难点：修改角色信息时，查询对应的菜单、部门、区域并在页面选中
				保存时特别注意对角色部门，角色菜单，角色区域对应表应该先删除原有的数据，再重新查询新数据
			1、修改页面	   从数据库中查询对应的权限信息
				this.role = roleService.queryRoleById(roleId);
				this.roleMenuList = roleService.queryRoleMenuListByRoleId(roleId);
				this.roleDeptList = roleService.queryRoleDeptListByRoleId(roleId);
				this.roleAreaList = roleService.queryRoleAreaListByRoleId(roleId);
			2、页面选中
				// 初始化树结构
				var menuTree = $.fn.zTree.init($("#menuTree"), menuSetting, menuZNodes);
				//进入修改页面的时候 ，选中已经拥有资源的节点	
				<c:forEach items="${roleMenuList}" var="roleMenu">
					var node = menuTree.getNodeByParam("id", ${roleMenu.menuId});
					menuTree.checkNode(node,true,false);
				</c:forEach>
				//默认展开全部节点
				menuTree.expandAll(true);	
			3、服务层操作
				// 第一：先删除与角色有关的中间表的数据
				flag = this.roleDao.delRoleAreaByRoleId(role.getId());
				flag = this.roleDao.delRoleDeptByRoleId(role.getId());
				flag = this.roleDao.delRoleMenuByRoleId(role.getId());
				
				// 第二：保存时在插入与角色有关的中间表的数据
				// 2.1构造角色菜单表对象
				List<RoleToMenu> roleMenuList = new ArrayList<>();
				Iterator<Long> itMenu = menuJson.keys();
				RoleToMenu roleToMenu;
				while(itMenu.hasNext()) {
					roleToMenu = new RoleToMenu();
					roleToMenu.setRoleId(role.getId());
					roleToMenu.setMenuId(new Long(menuJson.get(itMenu.next()).toString()));
					roleMenuList.add(roleToMenu);
				}
				// 2.2构造角色部门表对象
				List<RoleToDept> roleDeptList = new ArrayList<>();
				Iterator<Long> itDept = deptJson.keys();
				RoleToDept roleToDept;
				while(itDept.hasNext()) {
					roleToDept = new RoleToDept();
					roleToDept.setRoleId(role.getId());
					roleToDept.setDeptId(new Long(deptJson.get(itDept.next()).toString()));
					roleDeptList.add(roleToDept);
				}
				// 2.3构造角色部门表对象
				List<RoleToArea> roleAreaList = new ArrayList<>();
				Iterator<Long> itArea = areaJson.keys();
				RoleToArea roleToArea;
				while(itArea.hasNext()) {
					roleToArea = new RoleToArea();
					roleToArea.setRoleId(role.getId());
					roleToArea.setAreaId(new Long(areaJson.get(itArea.next()).toString()));
					roleAreaList.add(roleToArea);
				}
				// 2.4批量插入中间表数据
				flag = roleDao.addRoleToMenuBatch(roleMenuList);
				flag = roleDao.addRoleToDeptBatch(roleDeptList);
				flag = roleDao.addRoleToAreaBatch(roleAreaList);	
				
		* 难点：删除角色信息时，必须删除对应表的所有相关信息
			public boolean deleteRole(Long roleId) {
				boolean flag = false;
				// 删除相关表的所有数据     先删除从表，最后删除主表
				flag = roleDao.delRoleMenuByRoleId(roleId);
				flag = roleDao.delRoleDeptByRoleId(roleId);
				flag = roleDao.delRoleAreaByRoleId(roleId);
				
				flag = roleDao.deleteRole(roleId);
				return flag;
			}
11、用户管理
	* 查询页面生成部门树		
	* 多表、条件、模糊查询dto,并在页面显示	
	* 进入添加页面时
		加载部门树
		加载角色复选框	
		添加：添加用户表，用户角色表信息
	* 进入修改页面
		加载部门树(选中本身的部门)
		加载角色复选框(选中本身的角色框)
		保存：删除用户角色表  ---> 修改角色表   -----> 添加用户角色表
	* 删除同时删除用户角色表	
		
12、shiro授权遗留问题完善
	查询
		* 用户登录，加载此用户所有的菜单
		* 机构查询 只能查询当前用户有权限的机构
		* 区域查询 只能查询当前用户有权限的区域
		* 所有树形节点的查询，都是查询当前用户操作所拥有的资源
	新增
		* 新增菜单，部门，区域的时候，都要默认给超级管理员的角色在相应的映射表中增加一条对应的记录
			（role_menu,role_dept,role_area）	
		  如果不新增此次记录，谁来给大家授权，所以这种系统一定要注意超级管理员角色的灵活运用	
		*　新增用户，菜单，部门，区域等跟系统权限相关的资源时，都要注意清理跟shiro集成的缓存组件
	修改	 
		*　修改用户，菜单，部门，区域等跟系统权限相关的资源时，都要注意清理跟shiro集成的缓存组件
	删除
		* 删除菜单，部门，区域 都要删除对应的映射表里面的记录，以免造成垃圾数据（可以忽略）
	操作日志(Aop的后置通知/拦截器可以做)
		* 什么人 ，什么时候，做了什么事情，造成了什么后果