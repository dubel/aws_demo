import software.amazon.awssdk.core.async.AsyncResponseHandler;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.utils.FunctionalUtils;

import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

/**
 * Created by mike on 29.11.17.
 */
public class NonBlocking {


    public static void main(String[] args) {
        S3AsyncClient client = S3AsyncClient.create();
        final CompletableFuture<GetObjectResponse> future = client.getObject(
                GetObjectRequest.builder()
                        .bucket("mdbubel-test-bucket")
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
