import org.junit.Test;
import software.amazon.awssdk.core.async.AsyncRequestProvider;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.utils.FunctionalUtils;

import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

/**
 * Created by mike on 29.11.17.
 */
public class S3ClientTest {

    @Test
    public void testAsyncUpload() throws Exception {


        S3AsyncClient client = S3AsyncClient.create();
        CompletableFuture<PutObjectResponse> future = client.putObject(
                PutObjectRequest.builder()
                        .bucket("mdubel-test-bucket")
                        .key("test_up.txt")
                        .build(),
                AsyncRequestProvider.fromFile(Paths.get("lorem_ipsum.txt"))
        );
        future.whenComplete((resp, err) -> {
            try {
                if (resp != null) {
                    System.out.println(resp);
                } else {
                    // Handle error
                    err.printStackTrace();
                }
            } finally {
                // Lets the application shut down. Only close the client when you are completely done with it.
                FunctionalUtils.invokeSafely(client::close);
            }
        });

        Thread.sleep(5000);

    }
}
