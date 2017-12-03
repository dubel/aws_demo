import org.junit.Test;
import software.amazon.awssdk.core.client.builder.ClientHttpConfiguration;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheSdkHttpClientFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDBClient;

import java.time.Duration;

/**
 * Created by mike on 29.11.17.
 */
public class PluggableHttpLayer {


    @Test
    public void testPluggableHttpLayer() throws Exception {


        ApacheSdkHttpClientFactory apacheClientFactory =
                ApacheSdkHttpClientFactory.builder()
                        .socketTimeout(Duration.ofSeconds(10))
                        .connectionTimeout(Duration.ofMillis(750))
                        .build();


        SdkHttpClient sharedClient = apacheClientFactory.createHttpClient();

        DynamoDBClient clientOne =
                DynamoDBClient.builder()
                        .httpConfiguration(ClientHttpConfiguration.builder()
                                .httpClient(sharedClient)
                                .build())
                        .build();
        DynamoDBClient clientTwo =
                DynamoDBClient.builder()
                        .httpConfiguration(ClientHttpConfiguration.builder()
                                .httpClient(sharedClient)
                                .build())
                        .build();

    }
}
