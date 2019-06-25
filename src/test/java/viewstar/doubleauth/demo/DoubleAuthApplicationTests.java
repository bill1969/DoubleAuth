package viewstar.doubleauth.demo;

import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.asciidoctor.cli.AsciidoctorInvoker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DoubleAuthApplicationTests {

    @Test
    public void generateAsciiDocs() throws Exception {
        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withGeneratedExamples()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .build();
        System.out.println("test!");
        Swagger2MarkupConverter.from(new URL("http://localhost:8004/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("src/main/docs/asciidoc/generated/all"));
    }
    @Test
    public void generatePDF() {
        //样式
        String style = "pdf-style=D:\\data\\themes\\base-theme.yml";
        //字体
        String fontsdir = "pdf-fontsdir=D:\\data\\fonts";
        //需要指定adoc文件位置
        String adocPath = "src/main/docs/asciidoc/generated/all.adoc";
        //AsciidoctorInvoker.main(new String[]{"-a",style,"-a",fontsdir,"-b","pdf",adocPath});
    }
}
