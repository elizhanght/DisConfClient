# DisConfClient

##  1. 在项目的 resource 文件夹下添加 application.properties 文件，内容如下：
    dis_conf_path=http://{host}:{port}/api/property/
  
##  2. PropertySource 的使用方式
    @PropertySource(name="xhy-wifi",value="application",factory=HttpDisPropertySourceFactory.class) 
    public class AuthServiceImpl implements IFatApService {
      @Value("${type}")
      private String type;
    }
##  3. 替换 XML 配置文件，PropertyPlaceholderConfigurer
    在 xml 配置文件中如果想要替换 jdbc 、redis 配置信息需要使用下面的配置信息
~~~
  <bean class="com.client.source.DisPropertyPlaceholderConfigurer">
		<property name="locations">
			<list>
				<value>http://{host}:{port}/api/property/{project_name}</value>
        <value>http://{host}:{port}/api/property/{project_name}/{file_name}</value>
			</list>
		</property>
	</bean>
~~~
