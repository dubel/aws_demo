import org.junit.Test;
import software.amazon.awssdk.core.async.AsyncResponseHandler;
import software.amazon.awssdk.core.auth.ProfileCredentialsProvider;
import software.amazon.awssdk.core.regions.Region;
import software.amazon.awssdk.core.sync.StreamingResponseHandler;
import software.amazon.awssdk.services.dynamodb.DynamoDBAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDBClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.utils.FunctionalUtils;

import java.nio.file.Paths;
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

        ListTablesRequest originalRequest = ListTablesRequest.builder()
                .limit(5)
                .build();
        ListTablesResponse response = client.listTables(originalRequest);

        response.tableNames().forEach(System.out::println);


        // Immutable POJOs
        ListTablesRequest newOneBaby = originalRequest.toBuilder()
                .exclusiveStartTableName(response.lastEvaluatedTableName())
                .build();

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
    public void blockingStreaming() throws Exception {
        S3Client client = S3Client.create();
        client.getObject(GetObjectRequest.builder()
                        .bucket("mdubel-test-bucket")
                        .key("lorem_ipsum.txt")
                        .build(),
                StreamingResponseHandler.toFile(Paths.get("myfile.out")));

    }


    @Test
    public void nonBlockingStreaming() throws Exception {
        S3AsyncClient client = S3AsyncClient.create();
        final CompletableFuture<GetObjectResponse> future = client.getObject(
                GetObjectRequest.builder()
                        .bucket("mdubel-test-bucket")
                        .key("lorem_ipsum.txt")
                        .build(),
                AsyncResponseHandler.toFile(Paths.get("myfile.out")));
        future.whenComplete((resp, err) -> {
            try {
                if (resp != null) {
                    System.out.println(resp);
                } else {
                    // Handle error
                    err.printStackTrace();
                }
            } finally {
                // Lets the application shut down. Only close the client when you are completely done with it
                FunctionalUtils.invokeSafely(client::close);
            }
        });



    }

}
