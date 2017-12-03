import org.junit.Test;
import software.amazon.awssdk.core.auth.ProfileCredentialsProvider;
import software.amazon.awssdk.core.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDBAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDBClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by mike on 26.11.17.
 */
public class JUGDemo {


    @Test
    public void SDKtesting() throws Exception {

        DynamoDBClient dynamoDBClient = DynamoDBClient.create();

        ListTablesRequest request = ListTablesRequest.builder()
                .limit(5)
                .build();

        ListTablesResponse listTablesResponse = dynamoDBClient.listTables(request);

        listTablesResponse.tableNames().forEach(System.out::print);


    }


}
