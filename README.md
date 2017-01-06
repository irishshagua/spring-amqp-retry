We want the Rabbit Consumer to conditionally retry based off of what exception is thrown.
To test we send four different messages.

1. Happy Path
    The below JSON is processed successfully by the app:
    
    ```json
        {
            "id": 1,
            "name": "Bob",
            "anyoneCare": false,
            "amount": 45.456
        }
     ```
     
2. Retry With Backoff
    The exception thrown by the below json should trigger the spring retry with an exponential backoff up to a max num retries:
    
    ```json
        {
            "id": 2,
            "name": "Bob",
            "anyoneCare": false,
            "amount": 45.456
        }
     ```
     
3. No retry
    The exception thrown by the below json should not triger any retries
    
    ```json
        {
            "id": 3,
            "name": "Bob",
            "anyoneCare": false,
            "amount": 45.456
        }
    ```
    
4. The below message is unparsable so it should blow up before our handling code and not cause a retry
    
    ```json
       blah blah blah
    ```
    
The logs from the execution are below:

    /usr/lib/jvm/jdk1.8.0_25/bin/java -Didea.launcher.port=7532 -Didea.launcher.bin.path=/home/brian/opt/idea-IC-163.7743.44/bin -Dfile.encoding=UTF-8 -classpath /usr/lib/jvm/jdk1.8.0_25/jre/lib/rt.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/jce.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/jsse.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/jfr.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/deploy.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/resources.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/management-agent.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/javaws.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/jfxswt.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/charsets.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/plugin.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/ext/cldrdata.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/ext/dnsns.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/ext/nashorn.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/ext/localedata.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/ext/sunjce_provider.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/ext/jfxrt.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/ext/zipfs.jar:/usr/lib/jvm/jdk1.8.0_25/jre/lib/ext/sunec.jar:/home/brian/dev/workspace/elixir/spingamqpretry/target/classes:/home/brian/.m2/repository/org/springframework/boot/spring-boot-starter-amqp/1.4.3.RELEASE/spring-boot-starter-amqp-1.4.3.RELEASE.jar:/home/brian/.m2/repository/org/springframework/boot/spring-boot-starter/1.4.3.RELEASE/spring-boot-starter-1.4.3.RELEASE.jar:/home/brian/.m2/repository/org/springframework/boot/spring-boot/1.4.3.RELEASE/spring-boot-1.4.3.RELEASE.jar:/home/brian/.m2/repository/org/springframework/boot/spring-boot-autoconfigure/1.4.3.RELEASE/spring-boot-autoconfigure-1.4.3.RELEASE.jar:/home/brian/.m2/repository/org/springframework/boot/spring-boot-starter-logging/1.4.3.RELEASE/spring-boot-starter-logging-1.4.3.RELEASE.jar:/home/brian/.m2/repository/ch/qos/logback/logback-classic/1.1.8/logback-classic-1.1.8.jar:/home/brian/.m2/repository/ch/qos/logback/logback-core/1.1.8/logback-core-1.1.8.jar:/home/brian/.m2/repository/org/slf4j/slf4j-api/1.7.22/slf4j-api-1.7.22.jar:/home/brian/.m2/repository/org/slf4j/jcl-over-slf4j/1.7.22/jcl-over-slf4j-1.7.22.jar:/home/brian/.m2/repository/org/slf4j/jul-to-slf4j/1.7.22/jul-to-slf4j-1.7.22.jar:/home/brian/.m2/repository/org/slf4j/log4j-over-slf4j/1.7.22/log4j-over-slf4j-1.7.22.jar:/home/brian/.m2/repository/org/springframework/spring-core/4.3.5.RELEASE/spring-core-4.3.5.RELEASE.jar:/home/brian/.m2/repository/org/yaml/snakeyaml/1.17/snakeyaml-1.17.jar:/home/brian/.m2/repository/org/springframework/spring-messaging/4.3.5.RELEASE/spring-messaging-4.3.5.RELEASE.jar:/home/brian/.m2/repository/org/springframework/spring-beans/4.3.5.RELEASE/spring-beans-4.3.5.RELEASE.jar:/home/brian/.m2/repository/org/springframework/spring-context/4.3.5.RELEASE/spring-context-4.3.5.RELEASE.jar:/home/brian/.m2/repository/org/springframework/spring-aop/4.3.5.RELEASE/spring-aop-4.3.5.RELEASE.jar:/home/brian/.m2/repository/org/springframework/spring-expression/4.3.5.RELEASE/spring-expression-4.3.5.RELEASE.jar:/home/brian/.m2/repository/org/springframework/amqp/spring-rabbit/1.6.6.RELEASE/spring-rabbit-1.6.6.RELEASE.jar:/home/brian/.m2/repository/org/springframework/spring-web/4.3.5.RELEASE/spring-web-4.3.5.RELEASE.jar:/home/brian/.m2/repository/org/springframework/amqp/spring-amqp/1.6.6.RELEASE/spring-amqp-1.6.6.RELEASE.jar:/home/brian/.m2/repository/org/springframework/spring-tx/4.3.5.RELEASE/spring-tx-4.3.5.RELEASE.jar:/home/brian/.m2/repository/org/springframework/retry/spring-retry/1.1.5.RELEASE/spring-retry-1.1.5.RELEASE.jar:/home/brian/.m2/repository/com/rabbitmq/http-client/1.0.0.RELEASE/http-client-1.0.0.RELEASE.jar:/home/brian/.m2/repository/org/apache/httpcomponents/httpclient/4.5.2/httpclient-4.5.2.jar:/home/brian/.m2/repository/org/apache/httpcomponents/httpcore/4.4.5/httpcore-4.4.5.jar:/home/brian/.m2/repository/commons-codec/commons-codec/1.10/commons-codec-1.10.jar:/home/brian/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.8.5/jackson-databind-2.8.5.jar:/home/brian/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.8.5/jackson-annotations-2.8.5.jar:/home/brian/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.8.5/jackson-core-2.8.5.jar:/home/brian/.m2/repository/com/rabbitmq/amqp-client/3.6.5/amqp-client-3.6.5.jar:/home/brian/.m2/repository/org/projectlombok/lombok/1.16.12/lombok-1.16.12.jar:/home/brian/opt/idea-IC-163.7743.44/lib/idea_rt.jar com.intellij.rt.execution.application.AppMain com.mooneyserver.springamqpretry.AmqpRetryApp
    
      .   ____          _            __ _ _
     /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
     =========|_|==============|___/=/_/_/_/
     :: Spring Boot ::        (v1.4.3.RELEASE)
    
    2017-01-06 22:58:03.848  INFO 21110 --- [           main] c.m.springamqpretry.AmqpRetryApp         : Starting AmqpRetryApp on brians-laptop with PID 21110 (/home/brian/dev/workspace/elixir/spingamqpretry/target/classes started by brian in /home/brian/dev/workspace/elixir/spingamqpretry)
    2017-01-06 22:58:03.852  INFO 21110 --- [           main] c.m.springamqpretry.AmqpRetryApp         : No active profile set, falling back to default profiles: default
    2017-01-06 22:58:03.911  INFO 21110 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@1817d444: startup date [Fri Jan 06 22:58:03 GMT 2017]; root of context hierarchy
    2017-01-06 22:58:04.688  INFO 21110 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.amqp.rabbit.annotation.RabbitBootstrapConfiguration' of type [class org.springframework.amqp.rabbit.annotation.RabbitBootstrapConfiguration$$EnhancerBySpringCGLIB$$a745a6bf] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
    2017-01-06 22:58:05.467  INFO 21110 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
    2017-01-06 22:58:05.475  INFO 21110 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Bean with name 'rabbitConnectionFactory' has been autodetected for JMX exposure
    2017-01-06 22:58:05.478  INFO 21110 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Located managed bean 'rabbitConnectionFactory': registering with JMX server as MBean [org.springframework.amqp.rabbit.connection:name=rabbitConnectionFactory,type=CachingConnectionFactory]
    2017-01-06 22:58:05.520  INFO 21110 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Starting beans in phase -2147482648
    2017-01-06 22:58:05.520  INFO 21110 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Starting beans in phase 2147483647
    2017-01-06 22:58:05.574  INFO 21110 --- [cTaskExecutor-1] o.s.a.r.c.CachingConnectionFactory       : Created new connection: SimpleConnection@208e5ad3 [delegate=amqp://guest@127.0.0.1:5672/, localPort= 34814]
    2017-01-06 22:58:05.609  INFO 21110 --- [           main] c.m.springamqpretry.AmqpRetryApp         : Started AmqpRetryApp in 2.385 seconds (JVM running for 2.803)
    2017-01-06 22:58:11.408  INFO 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : Received <SampleMessage(id=1, name=Bob, anyoneCare=false, amount=45.456)>
    2017-01-06 22:58:11.409  INFO 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : Happy to accept 1s
    2017-01-06 22:58:21.894  INFO 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : Received <SampleMessage(id=2, name=Bob, anyoneCare=false, amount=45.456)>
    2017-01-06 22:58:21.894  WARN 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : It's hard to process 2s, we want to retry
    2017-01-06 22:58:22.896  INFO 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : Received <SampleMessage(id=2, name=Bob, anyoneCare=false, amount=45.456)>
    2017-01-06 22:58:22.896  WARN 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : It's hard to process 2s, we want to retry
    2017-01-06 22:58:24.897  INFO 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : Received <SampleMessage(id=2, name=Bob, anyoneCare=false, amount=45.456)>
    2017-01-06 22:58:24.897  WARN 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : It's hard to process 2s, we want to retry
    2017-01-06 22:58:28.898  INFO 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : Received <SampleMessage(id=2, name=Bob, anyoneCare=false, amount=45.456)>
    2017-01-06 22:58:28.898  WARN 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : It's hard to process 2s, we want to retry
    2017-01-06 22:58:36.899  INFO 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : Received <SampleMessage(id=2, name=Bob, anyoneCare=false, amount=45.456)>
    2017-01-06 22:58:36.899  WARN 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : It's hard to process 2s, we want to retry
    2017-01-06 22:58:36.905  WARN 21110 --- [cTaskExecutor-1] essRetryOperationsInterceptorFactoryBean : Message dropped on recovery: (Body:'[B@4563f5ac(byte[110])' MessageProperties [headers={}, timestamp=null, messageId=null, userId=null, receivedUserId=null, appId=null, clusterId=null, type=null, correlationId=null, correlationIdString=null, replyTo=null, contentType=null, contentEncoding=null, contentLength=0, deliveryMode=null, receivedDeliveryMode=NON_PERSISTENT, expiration=null, priority=null, redelivered=false, receivedExchange=some-fanout-exchange, receivedRoutingKey=, receivedDelay=null, deliveryTag=2, messageCount=0, consumerTag=amq.ctag-1awkjXWgI7YPpHsuFMMeQA, consumerQueue=my-consuming-q])
    
    org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException: Listener method 'public void com.mooneyserver.springamqpretry.AmqpQueueConsumer.handleMessage(com.mooneyserver.springamqpretry.SampleMessage)' threw exception
    	at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:138) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.onMessage(MessagingMessageListenerAdapter.java:105) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.doInvokeListener(AbstractMessageListenerContainer.java:780) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.invokeListener(AbstractMessageListenerContainer.java:703) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.access$001(SimpleMessageListenerContainer.java:98) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$1.invokeListener(SimpleMessageListenerContainer.java:189) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_25]
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_25]
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_25]
    	at java.lang.reflect.Method.invoke(Method.java:483) ~[na:1.8.0_25]
    	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:333) ~[spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.retry.interceptor.RetryOperationsInterceptor$1.doWithRetry(RetryOperationsInterceptor.java:74) ~[spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.retry.support.RetryTemplate.doExecute(RetryTemplate.java:276) [spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.retry.support.RetryTemplate.execute(RetryTemplate.java:172) [spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.retry.interceptor.RetryOperationsInterceptor.invoke(RetryOperationsInterceptor.java:98) [spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:213) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at com.sun.proxy.$Proxy54.invokeListener(Unknown Source) [na:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.invokeListener(SimpleMessageListenerContainer.java:1236) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.executeListener(AbstractMessageListenerContainer.java:684) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.doReceiveAndExecute(SimpleMessageListenerContainer.java:1190) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.receiveAndExecute(SimpleMessageListenerContainer.java:1174) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.access$1200(SimpleMessageListenerContainer.java:98) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$AsyncMessageProcessingConsumer.run(SimpleMessageListenerContainer.java:1363) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at java.lang.Thread.run(Thread.java:745) [na:1.8.0_25]
    Caused by: java.lang.IllegalStateException: Should be retried
    	at com.mooneyserver.springamqpretry.AmqpQueueConsumer.handleMessage(AmqpQueueConsumer.java:24) ~[classes/:na]
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_25]
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_25]
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_25]
    	at java.lang.reflect.Method.invoke(Method.java:483) ~[na:1.8.0_25]
    	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:196) ~[spring-messaging-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:113) ~[spring-messaging-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.amqp.rabbit.listener.adapter.HandlerAdapter.invoke(HandlerAdapter.java:49) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:125) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	... 26 common frames omitted
    
    2017-01-06 22:58:44.817  INFO 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : Received <SampleMessage(id=3, name=Bob, anyoneCare=false, amount=45.456)>
    2017-01-06 22:58:44.819 ERROR 21110 --- [cTaskExecutor-1] c.m.springamqpretry.AmqpQueueConsumer    : 3s are cunts. No retry!
    2017-01-06 22:58:44.820  WARN 21110 --- [cTaskExecutor-1] essRetryOperationsInterceptorFactoryBean : Message dropped on recovery: (Body:'[B@5290ea8c(byte[110])' MessageProperties [headers={}, timestamp=null, messageId=null, userId=null, receivedUserId=null, appId=null, clusterId=null, type=null, correlationId=null, correlationIdString=null, replyTo=null, contentType=null, contentEncoding=null, contentLength=0, deliveryMode=null, receivedDeliveryMode=NON_PERSISTENT, expiration=null, priority=null, redelivered=false, receivedExchange=some-fanout-exchange, receivedRoutingKey=, receivedDelay=null, deliveryTag=3, messageCount=0, consumerTag=amq.ctag-1awkjXWgI7YPpHsuFMMeQA, consumerQueue=my-consuming-q])
    
    org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException: Listener method 'public void com.mooneyserver.springamqpretry.AmqpQueueConsumer.handleMessage(com.mooneyserver.springamqpretry.SampleMessage)' threw exception
    	at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:138) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.onMessage(MessagingMessageListenerAdapter.java:105) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.doInvokeListener(AbstractMessageListenerContainer.java:780) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.invokeListener(AbstractMessageListenerContainer.java:703) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.access$001(SimpleMessageListenerContainer.java:98) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$1.invokeListener(SimpleMessageListenerContainer.java:189) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_25]
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_25]
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_25]
    	at java.lang.reflect.Method.invoke(Method.java:483) ~[na:1.8.0_25]
    	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:333) ~[spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.retry.interceptor.RetryOperationsInterceptor$1.doWithRetry(RetryOperationsInterceptor.java:74) ~[spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.retry.support.RetryTemplate.doExecute(RetryTemplate.java:276) [spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.retry.support.RetryTemplate.execute(RetryTemplate.java:172) [spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.retry.interceptor.RetryOperationsInterceptor.invoke(RetryOperationsInterceptor.java:98) [spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:213) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at com.sun.proxy.$Proxy54.invokeListener(Unknown Source) [na:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.invokeListener(SimpleMessageListenerContainer.java:1236) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.executeListener(AbstractMessageListenerContainer.java:684) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.doReceiveAndExecute(SimpleMessageListenerContainer.java:1190) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.receiveAndExecute(SimpleMessageListenerContainer.java:1174) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.access$1200(SimpleMessageListenerContainer.java:98) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$AsyncMessageProcessingConsumer.run(SimpleMessageListenerContainer.java:1363) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at java.lang.Thread.run(Thread.java:745) [na:1.8.0_25]
    Caused by: java.lang.ArithmeticException: Death to 3s
    	at com.mooneyserver.springamqpretry.AmqpQueueConsumer.handleMessage(AmqpQueueConsumer.java:27) ~[classes/:na]
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_25]
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_25]
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_25]
    	at java.lang.reflect.Method.invoke(Method.java:483) ~[na:1.8.0_25]
    	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:196) ~[spring-messaging-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:113) ~[spring-messaging-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.amqp.rabbit.listener.adapter.HandlerAdapter.invoke(HandlerAdapter.java:49) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:125) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	... 26 common frames omitted
    
    2017-01-06 22:58:53.223  WARN 21110 --- [cTaskExecutor-1] essRetryOperationsInterceptorFactoryBean : Message dropped on recovery: (Body:'[B@361882e7(byte[14])' MessageProperties [headers={}, timestamp=null, messageId=null, userId=null, receivedUserId=null, appId=null, clusterId=null, type=null, correlationId=null, correlationIdString=null, replyTo=null, contentType=null, contentEncoding=null, contentLength=0, deliveryMode=null, receivedDeliveryMode=NON_PERSISTENT, expiration=null, priority=null, redelivered=false, receivedExchange=some-fanout-exchange, receivedRoutingKey=, receivedDelay=null, deliveryTag=4, messageCount=0, consumerTag=amq.ctag-1awkjXWgI7YPpHsuFMMeQA, consumerQueue=my-consuming-q])
    
    org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException: Listener method could not be invoked with the incoming message
    Endpoint handler details:
    Method [public void com.mooneyserver.springamqpretry.AmqpQueueConsumer.handleMessage(com.mooneyserver.springamqpretry.SampleMessage)]
    Bean [com.mooneyserver.springamqpretry.AmqpQueueConsumer@21d8bcbe]
    	at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:128) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.onMessage(MessagingMessageListenerAdapter.java:105) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.doInvokeListener(AbstractMessageListenerContainer.java:780) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.invokeListener(AbstractMessageListenerContainer.java:703) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.access$001(SimpleMessageListenerContainer.java:98) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$1.invokeListener(SimpleMessageListenerContainer.java:189) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_25]
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_25]
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_25]
    	at java.lang.reflect.Method.invoke(Method.java:483) ~[na:1.8.0_25]
    	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:333) ~[spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:190) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.retry.interceptor.RetryOperationsInterceptor$1.doWithRetry(RetryOperationsInterceptor.java:74) ~[spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.retry.support.RetryTemplate.doExecute(RetryTemplate.java:276) [spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.retry.support.RetryTemplate.execute(RetryTemplate.java:172) [spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.retry.interceptor.RetryOperationsInterceptor.invoke(RetryOperationsInterceptor.java:98) [spring-retry-1.1.5.RELEASE.jar:na]
    	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:179) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:213) [spring-aop-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at com.sun.proxy.$Proxy54.invokeListener(Unknown Source) [na:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.invokeListener(SimpleMessageListenerContainer.java:1236) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.executeListener(AbstractMessageListenerContainer.java:684) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.doReceiveAndExecute(SimpleMessageListenerContainer.java:1190) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.receiveAndExecute(SimpleMessageListenerContainer.java:1174) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.access$1200(SimpleMessageListenerContainer.java:98) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$AsyncMessageProcessingConsumer.run(SimpleMessageListenerContainer.java:1363) [spring-rabbit-1.6.6.RELEASE.jar:na]
    	at java.lang.Thread.run(Thread.java:745) [na:1.8.0_25]
    Caused by: org.springframework.amqp.support.converter.MessageConversionException: Cannot handle message
    	... 27 common frames omitted
    Caused by: org.springframework.messaging.converter.MessageConversionException: Could not read JSON: Unrecognized token 'blah': was expecting ('true', 'false' or 'null')
     at [Source: [B@361882e7; line: 1, column: 6]; nested exception is com.fasterxml.jackson.core.JsonParseException: Unrecognized token 'blah': was expecting ('true', 'false' or 'null')
     at [Source: [B@361882e7; line: 1, column: 6]
    	at org.springframework.messaging.converter.MappingJackson2MessageConverter.convertFromInternal(MappingJackson2MessageConverter.java:224) ~[spring-messaging-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.messaging.converter.AbstractMessageConverter.fromMessage(AbstractMessageConverter.java:175) ~[spring-messaging-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver.resolveArgument(PayloadArgumentResolver.java:135) ~[spring-messaging-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:112) ~[spring-messaging-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:137) ~[spring-messaging-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:108) ~[spring-messaging-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	at org.springframework.amqp.rabbit.listener.adapter.HandlerAdapter.invoke(HandlerAdapter.java:49) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	at org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:125) ~[spring-rabbit-1.6.6.RELEASE.jar:na]
    	... 26 common frames omitted
    Caused by: com.fasterxml.jackson.core.JsonParseException: Unrecognized token 'blah': was expecting ('true', 'false' or 'null')
     at [Source: [B@361882e7; line: 1, column: 6]
    	at com.fasterxml.jackson.core.JsonParser._constructError(JsonParser.java:1702) ~[jackson-core-2.8.5.jar:2.8.5]
    	at com.fasterxml.jackson.core.base.ParserMinimalBase._reportError(ParserMinimalBase.java:558) ~[jackson-core-2.8.5.jar:2.8.5]
    	at com.fasterxml.jackson.core.json.UTF8StreamJsonParser._reportInvalidToken(UTF8StreamJsonParser.java:3524) ~[jackson-core-2.8.5.jar:2.8.5]
    	at com.fasterxml.jackson.core.json.UTF8StreamJsonParser._handleUnexpectedValue(UTF8StreamJsonParser.java:2686) ~[jackson-core-2.8.5.jar:2.8.5]
    	at com.fasterxml.jackson.core.json.UTF8StreamJsonParser._nextTokenNotInObject(UTF8StreamJsonParser.java:878) ~[jackson-core-2.8.5.jar:2.8.5]
    	at com.fasterxml.jackson.core.json.UTF8StreamJsonParser.nextToken(UTF8StreamJsonParser.java:772) ~[jackson-core-2.8.5.jar:2.8.5]
    	at com.fasterxml.jackson.databind.ObjectMapper._initForReading(ObjectMapper.java:3834) ~[jackson-databind-2.8.5.jar:2.8.5]
    	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:3783) ~[jackson-databind-2.8.5.jar:2.8.5]
    	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2959) ~[jackson-databind-2.8.5.jar:2.8.5]
    	at org.springframework.messaging.converter.MappingJackson2MessageConverter.convertFromInternal(MappingJackson2MessageConverter.java:211) ~[spring-messaging-4.3.5.RELEASE.jar:4.3.5.RELEASE]
    	... 33 common frames omitted
    
