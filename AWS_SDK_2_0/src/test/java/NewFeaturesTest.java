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
public class NewFeaturesTest {


    @Test
    public void dynamoDbExample_001() throws Exception {

        /*
        The region and credentials provider are for demonstration purposes. Feel free to use whatever region and credentials
        are appropriate for you, or load them from the environment (See http://docs.aws.amazon.com/sdk-for-java/v2/developer-guide/setup-credentials.html)
        */
        DynamoDBClient client = DynamoDBClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.builder()
                        .profileName("Michal_Dubel")
                        .build())
                .build();

        ListTablesResponse response = client.listTables(ListTablesRequest.builder()
                .limit(5)
                .build());
        response.tableNames().forEach(System.out::println);

    }

    @Test
    public void nonBlocking() throws Exception {

        // Creates a default async client with credentials and regions loaded from the environment
        DynamoDBAsyncClient client = DynamoDBAsyncClient.create();
        CompletableFuture<ListTablesResponse> response = client.listTables(ListTablesRequest.builder()
                .limit(5)
                .build());
        // Map the response to another CompletableFuture containing just the table names
        CompletableFuture<List<String>> tableNames = response.thenApply(ListTablesResponse::tableNames);
        // When future is complete (either successfully or in error) handle the response
        tableNames.whenComplete((tables, err) -> {
            if (tables != null) {
                tables.forEach(System.out::println);
            } else {
                // Handle error
                err.printStackTrace();
            }
        });

    }


    @Test
    public void nonBlockingStreaming() throws Exception {
        

    }
}
