import org.junit.Test;
import software.amazon.awssdk.core.auth.ProfileCredentialsProvider;
import software.amazon.awssdk.core.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDBClient;

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
                        .profileName("my-profile")
                        .build())
                .build();

    }
}
