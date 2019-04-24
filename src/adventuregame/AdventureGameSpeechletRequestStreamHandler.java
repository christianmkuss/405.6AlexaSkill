package adventuregame;

import java.util.HashSet;
import java.util.Set;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public final class AdventureGameSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;

    static {
        supportedApplicationIds = new HashSet<String>();
        // Application ID is the ARN of the Skill from https://developer.amazon.com
        supportedApplicationIds.add("amzn1.ask.skill.23cae22d-59f0-48a3-bd69-adc43341802e");
    }

    public AdventureGameSpeechletRequestStreamHandler() {
        super(new AdventureGameSpeechlet(), supportedApplicationIds);
    }
}
