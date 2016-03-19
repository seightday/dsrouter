#参考
https://github.com/afedulov/routing-data-source

#spring配置
	<!-- 使用annotation定义事务 -->
	<tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true" order="30"/>
    <bean id="routeConnectionInterceptor" class="com.seightday.dbroute.aspect.RouteConnectionInterceptor">
    	<property name="order" value="10"></property>
    </bean>
    <aop:aspectj-autoproxy />

	
    <bean id="dataSource" class="com.seightday.dbroute.datasource.RoutingDataSource">
        <property name="targetDataSources">
            <map>
                <entry key="ds1" value-ref="dataSource1"/>
                <entry key="ds2" value-ref="dataSource2"/>
                ......
            </map>
        </property>
        <property name="defaultTargetDataSource" ref="dataSource1"/>
    </bean>
	<bean id="dataSourceParent" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close" abstract="true"> 
	    <!-- 数据源驱动类可不写，Druid默认会自动根据URL识别DriverClass -->
	    <property name="driverClassName" value="${jdbc.driver}" />
	    
		<!-- 配置初始化大小、最小、最大 -->
		<property name="initialSize" value="${jdbc.pool.init}" />
		<property name="minIdle" value="${jdbc.pool.minIdle}" /> 
		<property name="maxActive" value="${jdbc.pool.maxActive}" />
		
		<!-- 配置获取连接等待超时的时间 -->
		<property name="maxWait" value="60000" />
		
		<!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
		<property name="timeBetweenEvictionRunsMillis" value="60000" />
		
		<!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
		<property name="minEvictableIdleTimeMillis" value="300000" />
		
		<property name="validationQuery" value="${jdbc.testSql}" />
		<property name="testWhileIdle" value="true" />
		<property name="testOnBorrow" value="false" />
		<property name="testOnReturn" value="false" />
		
		<!-- 配置监控统计拦截的filters -->
	   <property name="filters" value="stat" /> 
	</bean>
	
	<bean id="dataSource1" parent="dataSourceParent"> 
		<!-- 基本属性 url、user、password -->
		<property name="url" value="${jdbc.ds1.url}" />
		<property name="username" value="${jdbc.ds1.username}" />
		<property name="password" value="${jdbc.ds1.password}" />
	</bean>
	<bean id="dataSource2" parent="dataSourceParent"> 
		<!-- 基本属性 url、user、password -->
		<property name="url" value="${jdbc.ds2.url}" />
		<property name="username" value="${jdbc.ds2.username}" />
		<property name="password" value="${jdbc.ds2.password}" />
	</bean>