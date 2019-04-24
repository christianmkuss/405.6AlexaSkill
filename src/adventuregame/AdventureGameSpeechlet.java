/*
 * Created by Christian Kuss, William Tablan, Anthony Corigliano, and Cole Pobanz
 */
package adventuregame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;

import java.util.Arrays;
import java.util.HashMap;

public class AdventureGameSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(AdventureGameSpeechlet.class);

    static boolean PASSWORD = false;

    static final String GAMEOVER = "<audio src='https://s3.amazonaws.com/textbasedgame/Game_Over_Sound_effect.mp3'/>";

    static HashMap<String, String> StorySpeech = new HashMap<>();

    private static String[] tellResponses = {"AMAZON.CancelIntent", "AMAZON.StopIntent"};

    static {
        StorySpeech.put(
                "StartGameIntent",
                "You wake up from your cryo chamber. As you feel your aching muscles light your body afire, " +
                        "you look over and realize its been a long time, 405.6 million years ago, according to the, " +
                        "green digital numbers on the chambers shiny, chrome clock . . . " +
                        "You look over to a table and see a pill. You vaguely remember the scientists telling you to " +
                        "take the pill after you awaken. But then again that was 405.6 million years ago. You also see " +
                        "two doors, one large steel door to the left and one creaky wooden door, barely standing by " +
                        "its hinges. Do you take the pill, go through the steel door, or go through the creaky wooden door? "
        );
        StorySpeech.put(
                "TakePillIntent",
                "As the pill takes into effect , you slightly remember what each door leads to and feel a little better. " +
                        "The wooden door leads outside to the mystical and mysterious forest of Agoroth. The steel door " +
                        "leads to the cybernetic city of Zorento. You also remember being told that the password is " +
                        "pizza. Whatever that means. Do you go through the steel door or the wooden door? "
        );
        StorySpeech.put("AMAZON.CancelIntent", "Thank you for playing");
        StorySpeech.put("AMAZON.StopIntent", "Thank you for playing");
        StorySpeech.put(
                "AMAZON.HelpIntent",
                "This is a test based adventure game using me, Alexa. At the end of each prompt I will ask you a question" +
                        "and you can respond or choose to say stop. "
        );
        StorySpeech.put("ChristianIntent", "<speak><audio src='https://s3.amazonaws.com/textbasedgame/iceicebaby.mp3'/></speak>");
        StorySpeech.put(
                "AMAZON.FallbackIntent",
                PASSWORD ? "You are no hero, said the glownicorn, he stomps his hoof and the ground beneath you opens, " +
                        "engulfing you in darkness as you fall into the deep abyss. After what seems like eternity, the " +
                        "falling sensation just stops and you see a bright red light, barely bigger than a grain of orcish rice, " +
                        "which is like normal rice but half as big since those greedy orcs try to upend you. Do you follow or try to catch? " :
                        "I am sorry I did not understand what you said."
                );
    }

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return Response.response(StorySpeech.get("StartGameIntent"), false, false);
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : "";

        if (Agoroth.AgorothSpeech.containsKey(intentName)) {
            return Agoroth.agorothIntent(intentName);
        } else if (Zorento.ZorentoSpeech.containsKey(intentName)) {
            return Zorento.zorentoIntent(intentName);
        } else {
            return Response.response(StorySpeech.get(intentName), false, Arrays.asList(tellResponses).contains(intentName));
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
    }
}
