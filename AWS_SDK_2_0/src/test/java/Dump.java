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
 * Created by mike on 29.11.17.
 */
public class Dump {

    @Test
    public void testSDK() throws Exception {


        DynamoDBClient client = DynamoDBClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.builder()
                        .profileName("Michal_Dubel")
                        .build())
                .build();


        ListTablesRequest listTablesRequest = ListTablesRequest.builder().limit(5).build();
        ListTablesResponse listTablesResponse = client.listTables(listTablesRequest);

        listTablesResponse.tableNames().forEach(System.out::print);

        DynamoDBAsyncClient dynamoDBAsyncClient = DynamoDBAsyncClient.create();

        CompletableFuture<ListTablesResponse> listTablesResponseCompletableFuture = dynamoDBAsyncClient.listTables(listTablesRequest);

        CompletableFuture<List<String>> response = listTablesResponseCompletableFuture.thenApply(ListTablesResponse::tableNames);

        response.whenComplete((tables, throwable) -> {

            try {
                System.out.println("");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }

        });
    }
}
