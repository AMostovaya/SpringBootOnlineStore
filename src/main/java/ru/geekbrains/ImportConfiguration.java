package ru.geekbrains;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.jdbc.JdbcMessageHandler;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHandlingException;
import ru.geekbrains.model.Brand;


import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

@Configuration
public class ImportConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ImportConfiguration.class);


    @Autowired
    DataSource dataSource;

    @Value("${source.dir.path}")
    private String sourceDirectoryPath;
    @Value("${dest.dir.path}")
    private String destDirectoryPath;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring_shop_db?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC\n");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public MessageSource<File> sourceDirectory() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(sourceDirectoryPath));
        messageSource.setAutoCreateDirectory(true);
        return messageSource;
    }

    @Bean
    public MessageHandler destDirectory(){

       JdbcMessageHandler jdbcMessageHandler = new JdbcMessageHandler(dataSource,
                "INSERT INTO brands (id, name) VALUES (:payload)");
        jdbcMessageHandler.setPreparedStatementSetter((ps, m) -> {
            ps.setString(1, (String) m.getHeaders().get(FileHeaders.FILENAME));
            try (FileInputStream inputStream = new FileInputStream((File) m.getPayload()); ) {
                ps.setBlob(2, inputStream);
            }
            catch (Exception e) {
                throw new MessageHandlingException(m, e);
            }
            ps.setClob(3, new StringReader(m.getHeaders().get("description", String.class)));
        });
        return jdbcMessageHandler;
         /*FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(destDirectoryPath));
        handler.setExpectReply(false);
        handler.setDeleteSourceFiles(true);
        return  handler;*/
    }



    @Bean
    public IntegrationFlow fileMoveFlow() throws IOException, CsvValidationException {

        return IntegrationFlows.from(sourceDirectory(), conf->conf.poller(Pollers.fixedDelay(5000)))
                .filter(msg -> ((File) msg).getName().endsWith(".csv"))
                .transform(new FileToStringTransformer())
                .<String, String>transform(Brand::setId)
                .transform(Message.class, msg-> {
                    msg.getHeaders().forEach((key, value)->log.info("{} : {}", key, value));
                    return msg;
                })
                .handle(destDirectory())
                .get();

    }

}
