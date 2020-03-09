package pub.izumi.coolqs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pub.izumi.coolqs.core.config.CqConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("pub.izumi.coolqs.core.mapper")
public class CoolqsApplication {

    public static void main(String[] args) {
        CqConfig.init();
        SpringApplication.run(CoolqsApplication.class, args);
    }
}
