package com.tch.common.conf;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.tch.common.filter.HTTPBasicAuthorizeAttribute;
import com.tch.common.filter.HTTPBearerAuthorizeAttribute;
import com.tch.common.filter.IllegalCharacterFilter;
import com.tch.common.shiro.MyExceptionResolver;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shz on 2017/8/21.
 */
@Configuration
public class CommonConf extends WebMvcConfigurerAdapter {

    final Logger logger = LogManager.getLogger(this.getClass());
    //配置fastjson数据转化器
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        logger.info("fastjson data transaction");
        super.configureMessageConverters(converters);
        //1,需要定义一个自定义转换器converter
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2,添加fastjson的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //3,处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //4,在converter中添加配置信息
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        //5.将convert添加到converters当中.
        converters.add(fastJsonHttpMessageConverter);
    }

    //配置默认访问页面
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/404"));
                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/500"));
            }
        };
    }
    //项目运行起来之后调用此方法
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        WebInitial webInitial = new WebInitial();
//        try {
//            webInitial.init();
//        } catch (ServletException e) {
//            e.printStackTrace();
//        }
//    }

    //配置kaptcha图片验证码框架提供的Servlet,,这是个坑,很多人忘记注册(注意)
    @Bean
    public ServletRegistrationBean kaptchaServlet(){
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(), "/kaptcha.jpg");
        servlet.addInitParameter(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_CONFIG_KEY,
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);//session key
        servlet.addInitParameter(com.google.code.kaptcha.Constants.KAPTCHA_IMAGE_HEIGHT,"60");//高度
        servlet.addInitParameter(com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE,"50");//字体大小
        //可以设置很多属性,具体看com.google.code.kaptcha.Constants
//		kaptcha.border  是否有边框  默认为true  我们可以自己设置yes，no
//		kaptcha.border.color   边框颜色   默认为Color.BLACK
//		kaptcha.border.thickness  边框粗细度  默认为1
//		kaptcha.producer.impl   验证码生成器  默认为DefaultKaptcha
//		kaptcha.textproducer.impl   验证码文本生成器  默认为DefaultTextCreator
//		kaptcha.textproducer.char.string   验证码文本字符内容范围  默认为abcde2345678gfynmnpwx
//		kaptcha.textproducer.char.length   验证码文本字符长度  默认为5
//		kaptcha.textproducer.font.names    验证码文本字体样式  默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
//		kaptcha.textproducer.font.size   验证码文本字符大小  默认为40
//		kaptcha.textproducer.font.color  验证码文本字符颜色  默认为Color.BLACK
//		kaptcha.textproducer.char.space  验证码文本字符间距  默认为2
//		kaptcha.noise.impl    验证码噪点生成对象  默认为DefaultNoise
//		kaptcha.noise.color   验证码噪点颜色   默认为Color.BLACK
//		kaptcha.obscurificator.impl   验证码样式引擎  默认为WaterRipple
//		kaptcha.word.impl   验证码文本字符渲染   默认为DefaultWordRenderer
//		kaptcha.background.impl   验证码背景生成器   默认为DefaultBackground
//		kaptcha.background.clear.from   验证码背景颜色渐进   默认为Color.LIGHT_GRAY
//		kaptcha.background.clear.to   验证码背景颜色渐进   默认为Color.WHITE
//		kaptcha.image.width   验证码图片宽度  默认为200
//		kaptcha.image.height  验证码图片高度  默认为50
        return servlet;
    }

    //注入异常处理类
    @Bean
    public MyExceptionResolver myExceptionResolver(){
        return new MyExceptionResolver();
    }
//    //注册请求方法过滤器
//    @Bean()
//    public Filter AuthFilter() {
//        return new MHttpServletRequest();
//    }
    //添加参数过滤拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IllegalCharacterFilter())
                .addPathPatterns("/**");
    }
    //外部接口过滤器,走basic认证
    @Bean
    public FilterRegistrationBean basicFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HTTPBasicAuthorizeAttribute httpBasicFilter = new HTTPBasicAuthorizeAttribute();
        registrationBean.setFilter(httpBasicFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/user/getuser");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
    //外部接口过滤器，走bearer认证
    @Bean
    public FilterRegistrationBean jwtFilterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HTTPBearerAuthorizeAttribute httpBearerFilter = new HTTPBearerAuthorizeAttribute();
        registrationBean.setFilter(httpBearerFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/app/getusers");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

}
