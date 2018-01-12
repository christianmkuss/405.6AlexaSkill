/**
 * Created by Christian Kuss, William Tablan, Anthony Corigliano, and Cole Pobanz. All rights belong to Central Kitsap High School, Silverdale, WA. 2017
 * Special thanks to Alex Booth of Central Kitsap High School for knowledge of Java and for purchase of AMAZON Echo.
 * This code was built upon the spacegeek sample provided by AMAZON developer. While built upon "SpaceGeek" many more skills were utlized such as SSML.
 *
 * */
package adventuregame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.ui.OutputSpeech;
import com.amazon.speech.ui.SsmlOutputSpeech;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class AdventureGameSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(AdventureGameSpeechlet.class);

    //Array containing the different prompts
    private static boolean KEY = false;
    private static boolean STEEL;
    private static boolean WOOD;
    private static boolean GUN = false;
    private static boolean FLEX = false;
    private static boolean BUILDING = false;
    private static boolean ROBOT = false;

    private static final String GAMEOVER = "<audio src='https://s3.amazonaws.com/textbasedgame/Game_Over_sound_effect%5BMp3Converter.net%5D.mp3'/>";
    private static final String EXPLOSION = "<audio src='https://s3.amazonaws.com/textbasedgame/Bomb_Exploding-Sound_Explorer-68256487.mp3'/>";

    private static final String[] STORY = new String[] {
     /*0*/       "You wake up from your cryo chamber. As you feel your aching muscles light your body afire, " +
                        "you look over and realize its been a long time, " +
                        "405.6 million years ago, according to the green digital numbers on the chambers shiny, chrome clock . . . " +
                        "You look over to a table and see a pill. You vaguely remember the scientists telling you to take the pill after you awaken. " +
                        "But then again that was 405.6 million years ago. You also see two doors, one large steel door to the left and one creaky wooden door, barely standing by its hinges. " +
                        "Do you take the pill, go through the steel door, or go through the creaky wooden door? ",
     /*1*/       "as the pill takes into effect , you slightly remember what each door leads to and feel a little better. " +
                        "The wooden door leads outside to the mystical and mysterious forest of Agoroth. " +
                        "The steel door leads to the cybernetic city of Zorento. " +
                        "You also remember being told that the password is pizza. Whatever that means. Do you go through the steel door or the wooden door? ",
     /*2*/       "you enter the mysterious forest of Agoroth. ",
     /*3*/       "you enter the futuristic sci-fi city of Zorento. "
    };

    private static final String[] ZORENTO = new String[] {
     /*0*/       "Upon entering through the the door, it immediately slams shut behind you. . . Ahead lies a building with a faint red glow inside. " +
                        "To the left you see a pile of what looks like broken laser weapons. To the right there are what appears to be robots " +
                        "rusted with age holding something mysterious in their hands. Do you go to the robots, the broken lasers, or the building? ",
     /*1*/       "With the robot hot on your heels you stumble into the building and find the source of the light. A computer screen with a prompt open with two choices. " +
                        "Activate or deactivate. Which one will you press? ",
     /*2*/       "As you quickly click the activate button you realize the clanging of the robot chasing you has died. As you turn around, you see the robot as if it's frozen in time. " +
                        "You take what was in the robot's clutch and realize it's a key. Do you inspect the room? ",
     /*3*/       "You rush the key into the lock and it fits. You open the door and behind you more robots have come to avenge their friend. You run inside and lock the door behind you. " +
                        "Safe for now, you find a path ahead which leads to some form of hangar. As you search the hangar you observe the remnants of spacecraft that has been left to rust. " +
                        "One of these ships appears to be still intact and functional. You enter the ship and sit in the cockpit as it comes to life with different colored buttons. " +
                        "The controls seem like basic flight controls but you have no idea how to start the engines. You have three buttons to choose from, a turquoise, red, or violet button. " +
                        "Which will you press? ",
     /*4*/       "<speak>" +
                        "as you press the button the engine malfunctions and explodes, leaving you in pieces over the hangar walls. " +
                        EXPLOSION +
                        GAMEOVER +
                        "</speak>",
     /*5*/       "You wait in anticipation to see the result of your choice. You are relieved to hear the sound of the engines starting. Without wasting any time you take off and " +
                        "arrive into a bright, neon green palace. Do you want to wander or not wander? ",
     /*6*/       "<speak>" +
                        "upon pressing the button you hear a beeping noise and rigged bomb explodes becoming your demise. " +
                        EXPLOSION +
                        GAMEOVER +
                        "</speak>",
     /*7*/       "<speak>" +
                        "as you decide not to try the key on the door robots come from every direction after you have killed their friend. You become surrounded and have no escape as your fate is sealed. GAME OVER" +
                        GAMEOVER +
                        "</speak>",
     /*8*/       "Inside the building you find the source of the light. A warning on a computer monitor about robots becoming dysfunctional. A prompt appears with two options: " +
                        "activate or deactivate. " +
                        "Which will you click? ",
     /*9*/       "upon pressing deactivate you realize that that was the wrong choice. The right choice, which was obviously activate, seems to taunt you. It taunts you so much that " +
                        "your whole life becomes obsessed with your one mistake. And while your friends and family continue on living happy lives you are left alone to contemplate your mistake and " +
                        "what would have happened if you had only pushed activate. This idiotic decision haunts you until you die. " + GAMEOVER,
     /*10*/       "After messing with the terminal you go back to the robots to inspect their contents. The robots are beeping and you hurriedly grab what appears to be a key that was in its grasp as you fear the noise will draw " +
                        "something dangerous. You can run straight into the building and inspect the room. Do you inspect the room? ",
     /*11*/       "after inspecting the room you see a locked door which requires some form of a key to unlock it. After closer inspecting the door you realize the key from the robot " +
                        "will open the door. Do you use the key or not use the key?",
     /*12*/       "You enter the building and find yourself in a metal room with a computer that is off. Do you inspect the room? Yes or no? ",
     /*13*/       "As you look to see what the mechanical beings have in their grasp one of them activates and determines you are a threat. " +
                        "You decide it is probably wise to run away. Do you run to the broken pile of lasers on the left or go to the building with the red glow " +
                        "straight ahead. ",
     /*14*/       "After scrambling to the pile of broken technology, with the robot right on your heels, by a miracle you find a laser working with a couple shots left. " +
                        "Do you shoot the robot or run straight into the building with the red light? ",
     /*15*/       "You fall to your back as the recoil of the laser is too much for your measly, unworked muscles. The robot raises back to attack but you squeeze the trigger on the laser and let "+
                        "loose what energy is left in the weapon into the robots chest. As you watch the light die from its mechanical body it releases what was in its grasp. "+
                        "It seems to be a key of some sort. ",
     /*16*/       "After going to the pile of robots, one activates and determines you are a threat. Do you shoot the robot or go to the building? ",
     /*17*/       "In the rubble of broken and useless junk you find a laser gun working with only a couple of shots left. You can continue straight ahead to the building with the red glow " +
                        "or inspect the robots on the right. "
    };


    private static final String[] AGOROTH = new String[]{
    /*0*/        "you creak open the wooden door. The hinges groan as the untouched overgrowth on the other side becomes disrupted by your actions. " +
                        "Past the door, a blinding light encompasses you, causing you to pass out. " +
                        "You wake up again to the sound of chanting. ",
     /*1*/       "Around your body, a thin vine surrounds you, binding you and rendering you immobile. " +
                        "You think you can break the vines, would you try to escape or stay put?",
     /*2*/       "You flex your muscles but it proves useless. Do you give up or flex some more? ",
     /*3*/       "You flex some more. You realize that flexing your measly muscles isn't going to break even the thinnest of strings. " +
                        "You should have pumped more iron when you had the chance. Your rustling alerts the chanting creatures to your presence and they surround you. ",
     /*4 */      "Your just not that buff. But your past movements alerted the chanting creatures to your presence and they surround you. ",
     /*5*/       "You decide to stay put. Who knows, the vines look really frail and you might have been able to break it with you ginormous muscles. " +
                        "An hour later one of the chanting creatures check on you. Realizing that you are awake, the creatures surround you. ",
     /*6 */      "As the creatures surround you, the sounds of hoofs, the smell of horses and rainbows, and the sight of glowing horns and rainbows made you realize " +
                        "that you are surrounded by a herd of Glownicorns, the most majestic of all Warnicorn races. The largest Glownicorn approaches you. " +
                        "What is the password?",
     /*7*/       "<speak>" +
                        "Ah, I see that you are the dragonborn, fluent in the tri-language of man, dragon, and Warnicorn. We have waited 405.6 million years for you to come, " +
                        "Come with me and fulfill your destiny. " +
                        "Riding upon the unnamed Glownicorns back, he takes you to a familiar classroom filled with computers. " +
                        "It is time to do what must be done said the glowing, rainbow colored Warnicorn. " +
                        "You nod in agreement as you sit down and start grading homework for once. " +
                        "You hear a faint whisper, <amazon:effect name=\"whispered\"> good job Mr. Booth, </amazon:effect> as you log into PowerSchool to give some students their grades. " +
                        "</speak>",
     /*8*/       "You are no hero, said the glownicorn, he stomps his hoof and the ground beneath you opens, engulfing you in darkness as you fall into the deep abyss. " +
                        "After what seems like eternity, the falling sensation just stops and you see a bright red light, barely bigger than a grain of orcish rice, " +
                        "which is like normal rice but half as big since those greedy orcs try to upend you. " +
                        "What do you want to do? Follow or catch? ",
    /*9*/        "You try to catch the glowing dot with your bare hands. However, the wisp proves to be too small and quick to be caught by your sluggish arms anytime soon. " +
                        "You should really just follow it, who knows, some fairy queen might have been using her magic to guide you. ",
    /*10*/        "You decide to follow the glowing red wisp. You wander through the caverns below, you notice that the number 5/5 is written throughout the walls. " +
                        "The red wisp leads you to a fork leading to two caves. A crooked sign stands in the middle. Do you inspect sign, proceed left, or proceed right? ",
     /*11 */      "<speak>" +
                        "you decide to not know where you are going. This was a dumb move. So dumb that your brain sent out a distress signal throughout the dimensions to ask for help. " +
                        "So dumb in fact that your ancestors felt that same signal that echoed throughout space time Causing one of your primordial ancestors to take a break from " +
                        "creating the World Tree to stop you from what you are about to do. An glowing green portal opens up from the ceiling above you, and from it, not just any " +
                        "unicorn centaur hybrid, but a unitaur with wings. You couldn't believe your eyes as the legendary Unitarsus tells you to read the sign and give the writers a " +
                        "break from having to write another story line where you don't know where you're going. BTW youre dead now. " +
                        GAMEOVER +
                        "</speak>",
     /*12 */      "you decide to actually know where you're going. The sign pointing left reads Democratic Republic of the Goblin King while the sign right states Domain of the Rice Beast. " +
                        "Do you go to the goblin king or the rice beast?",
     /*13*/       "<speak>" +
                        "You go to the path leading to the Rice beast. In a swamp in the middle of nowhere, you see a single Rice Mill in the middle of this murky nowhere. " +
                        "All of a sudden, a black figure comes out of the mill and quickly encompasses you. It's long curly mane chokes you out as the beast encompasses you. " +
                        "<prosody rate='x-slow'> <prosody pitch='x-low'> Why are you here? </prosody></prosody> the monster asks. " +
                        "You respond: I have no home, no control, and I can't find escape anywhere. " +
                        "The beast makes a sly grin as it says, <prosody rate='x-slow'> <prosody pitch='x-low'> then you better get a new Keyboard. </prosody></prosody> " +
                        "That was terrible. You decide to kill the beast. You look around and see a pair of twin blasters on the floor. You also see a sword stuck in the mud. Which one do you pick? " +
                        "</speak>",
     /*14*/       "<speak>" +
                        "You roll across the Rice Mill  in hopes to get the sword. You grip the hilt and, with little effort, the blade frees itself from the mud. " +
                        "You swing at the beast with a mighty blow, cutting off its left arm. " +
                        "<prosody rate='x-slow'> <prosody pitch='x-low'> Don't worry, I'm all right </prosody></prosody> the beast says as he runs away. " +
                        "You level up, it made you realize that the writer has to end the story quick and you quickly teleport into a bright, neon green palace. Do you wander? " +
                        "</speak>",
     /*15*/       "<speak>" +
                        "in a desperate attempt, you dash for the blasters. " +
                        "<audio src='https://s3.amazonaws.com/textbasedgame/Laser_Gun-Mike_Koenig-1975537935.mp3'/>" +
                        "<prosody rate='x-slow'> <prosody pitch='x-low'> I see you have your arms at your side </prosody></prosody> the monster says, and you feel no remorse shooting at the Rice beast until it disappears, fleeing into the horizon. " +
                        "<audio src='https://s3.amazonaws.com/textbasedgame/Laser_Gun-Mike_Koenig-1975537935.mp3'/>" +
                        "You level up. It made you realize that the writer has to end the story quick and you quickly teleport into a bright neon green palace. Do you wander or not wander?" +
                        "</speak>",
     /*16*/       "You wait some more. Nothing happens, do you want to wander now? ",
     /*17*/       "you wander the endless halls. After what seems like hours, you see A sign saying This way for hot mayo soup. Since you do like yourself some hot mayo soup, " +
                        "you follow and you find yourself in a familiar classroom. An open computer on the corner is logged into PowerSchool. Do you grade or not grade?",
     /*18*/       "Welcome Alex. The computer beeps as you log in and grade some homework. Thanks for playing. ",
     /*19*/       "You go back to sleep. ",
     /*20*/       "<speak>" +
                        "The idea of a Democratic Republic with an authoritarian figure makes you giggle. You walk up to thye door of a towering tower. " +
                        "The next thing you find is the great Goblin King Liam staring you down. <prosody rate='slow'> <prosody pitch='low'> Do you like some hot mayo soup? </prosody></prosody>" +
                        "Do you accept soup or reject soup?" +
                        "</speak>",
     /*21*/       "You decide to accept the soup. As you make your way to a seat, you also decide to get some Hawaiian pizza, double the sauce, just the way you like it. " +
                        "Reality around you fades away as you realize you're just in your living room, pizza and soup in hand grading papers. You win. ",
     /*22*/       "<prosody rate='x-slow'> <prosody pitch='x-low'><prosody volume = 'x-loud'> What? </prosody></prosody></prosody>" +
                        "The goblin king roars, he stomps his foot and an army of goblins seize you, cramming you in a familiar cryo chamber. You teer up a bit as you get carried back to a room. "
    };

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any initialization logic goes here
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return startNewGameResponse();
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("StartGameIntent".equals(intentName)) {
            return startNewGameResponse();
        }else if("TakePillIntent".equals(intentName)) {
            return pillResponse();
        } else if("ChristianIntent".equals(intentName)){
            return christianResponse();
        } else if("BoofIntent".equals(intentName)){
            return boofResponse();
        }
            //Zorento

         if("SteelDoorIntent".equals(intentName)){
            STEEL = true;
            WOOD = false;
            return steelDoorResponse();
        }
        while(STEEL == true){
            if("BuildingIntent".equals(intentName)) {
                return buildingResponse();
            }else if("RobotPileIntent".equals(intentName)){
                return robotPileResponse();
            } else if("KeyIntent".equals(intentName)){
                return keyResponse();
            } else if("LaserPileIntent".equals(intentName)){
                return laserPileResponse();
            } else if("ShootRobotIntent".equals(intentName)){
                return shootRobotResponse();
            } else if("ActivateIntent".equals(intentName)){
                return activateResponse();
            } else if("DeactivateIntent".equals(intentName)){
                return deactivateResponse();
            } else if("TurquoiseIntent".equals(intentName)){
                return turquoiseResponse();
            } else if("NoIntent".equals(intentName)){
                return noResponse();
            } else if("RedIntent".equals(intentName)){
                return redResponse();
            } else if("VioletIntent".equals(intentName)){
                return violetResponse();
            } else if("RoomIntent".equals(intentName)){
                return roomResponse();
            }
        }


        //Agoroth

         if("WoodDoorIntent".equals(intentName)){
            WOOD = true;
            STEEL = false;
            return woodDoorResponse();
        }
        while(WOOD == true){
            if("FlexIntent".equals(intentName)) {
                FLEX = true;
                return flexResponse();
            }else if("FlexMoreIntent".equals(intentName)){
                return flexMoreResponse();
            } else if("StayPutIntent".equals(intentName)){
                return stayPutResponse();
            } else if("PizzaIntent".equals(intentName)) {
                    return pizzaResponse();
            } else if("PasswordIntent".equals(intentName)){
                    return passwordResponse();
            }else if("CatchIntent".equals(intentName)){
                return catchResponse();
            } else if("FollowIntent".equals(intentName)){
                return followResponse();
            } else if("ProceedLeftIntent".equals(intentName)){
                return proceedLeftResponse();
            }  else if("InspectSignIntent".equals(intentName)){
                return inspectSignResponse();
            } else if("RiceBeastIntent".equals(intentName)){
                return riceBeastResponse();
            } else if("SwordIntent".equals(intentName)){
                return swordResponse();
            } else if("BlasterIntent".equals(intentName)){
                return blasterResponse();
            } else if("WanderIntent".equals(intentName)){
                return wanderResponse();
            } else if("NotWanderIntent".equals(intentName)) {
                return notWanderResponse();
            }else if("GradeIntent".equals(intentName)){
                return gradeResponse();
            } else if("NotGradeIntent".equals(intentName)){
                return notGradeResponse();
            } else if("GoblinKingIntent".equals(intentName)){
                return goblingKingResponse();
            } else if("AcceptSoupIntent".equals(intentName)){
                return acceptSoupResponse();
            } else if("RejectSoupIntent".equals(intentName)){
                return rejectSoupResponse();
            }
        }


        if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } else if ("AMAZON.StopIntent".equals(intentName)) {
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("Thanks for playing");
            return SpeechletResponse.newTellResponse(outputSpeech);
        } else if ("AMAZON.CancelIntent".equals(intentName)) {
            PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
            outputSpeech.setText("Thanks for playing");
            return SpeechletResponse.newTellResponse(outputSpeech);
        } else {
            PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
            speech.setText("I am sorry I did not catch that.");
            Reprompt reprompt = new Reprompt();
            return SpeechletResponse.newAskResponse(speech, reprompt);
            /*throw new SpeechletException("Invalid Intent");*/
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any cleanup logic goes here
    }

    private SpeechletResponse startNewGameResponse() {
        String speechText = STORY[0];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse pillResponse(){
        String speechText = STORY[1];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse boofResponse(){
        String[] assignments = new String[] {"Picture Lab", "Bugs", "Card game version 1", "card game version 2", "guessing game", "graphics"};
        int rand = (int)(Math.random() * (assignments.length+1));
        String assignment = assignments[rand];
        String speechText = "Oh hey Mr Booth! Fancy seeing you here. Have you graded " + assignment + " yet? ";
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newTellResponse(speech);
    }

    //Zorento Responses

    private SpeechletResponse steelDoorResponse(){
        String speechText = STORY[3] + ZORENTO[0];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse buildingResponse(){
        String speechText;
        if(KEY == false && GUN == false && ROBOT == false){
            speechText = ZORENTO[8];
        }
        else if(GUN == true && KEY == false && ROBOT == false){
            speechText = ZORENTO[8];
        }
        else if(GUN == false && KEY == false && ROBOT == true){
            speechText = ZORENTO[1];
        }
        else if(GUN == true && ROBOT== true && KEY == false){
            speechText = ZORENTO[1];
        } else{
            speechText = ZORENTO[8];
        }
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse shootRobotResponse(){
        ROBOT = false;
        KEY = true;
        String speechText = ZORENTO[15] + ZORENTO[12];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse activateResponse(){
        String speechText;
        if(KEY == false && ROBOT == true){
            speechText = ZORENTO[2];
        } else{
            speechText = ZORENTO[10];
        }
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse deactivateResponse(){
        String speechText = "<speak>" + ZORENTO[9] + "</speak>";
        return newTellResponse(speechText, true);
    }

    private SpeechletResponse turquoiseResponse(){
        String speechText = ZORENTO[4];
        return newTellResponse(speechText, true);
    }

    private SpeechletResponse redResponse(){
        STEEL = false;
        WOOD = true;
        String speechText = ZORENTO[5];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse violetResponse(){
        String speechText = ZORENTO[6];
        return newTellResponse(speechText, true);
    }

    private SpeechletResponse roomResponse(){
        String speechText = ZORENTO[11];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse noResponse(){
        String speechText = ZORENTO[7];
        return newTellResponse(speechText, true);
    }

    private SpeechletResponse laserPileResponse(){
        GUN = true;
        String speechText;
        if(ROBOT == true){
            speechText = ZORENTO[14];
        }else{
            speechText = ZORENTO[17];
        }
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse keyResponse(){
        String speechText = ZORENTO[3];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse robotPileResponse(){
        ROBOT = true;
        String speechText;
        if(GUN == true){
            speechText = ZORENTO[16];
        } else{
            speechText = ZORENTO[13];
        }
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }


    //Agoroth Responses

    private SpeechletResponse woodDoorResponse(){
        String speechOutput = "<speak>"
                + AGOROTH[0]
                + "<audio src='https://s3.amazonaws.com/textbasedgame/HookedOnAFeelingOoga.mp3'/>"
                + AGOROTH[1]
                + "</speak>";
        String repromptText = "";
        return newAskResponse(speechOutput, true, repromptText, false);
    }

    private SpeechletResponse flexResponse(){
        String speechText = AGOROTH[2];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse flexMoreResponse(){
        String speechText = AGOROTH[3] + AGOROTH[6];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse stayPutResponse(){
        String speechText;
        if(FLEX == false) {
            speechText = AGOROTH[5] + AGOROTH[6];
        } else{
            speechText = AGOROTH[4] + AGOROTH[6];
        }
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse pizzaResponse(){
        String speechText = AGOROTH[7];
        return newTellResponse(speechText, true);
    }

    private SpeechletResponse passwordResponse(){
        String speechText = AGOROTH[8];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse followResponse(){
        String speechText = AGOROTH[10];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse catchResponse(){
        String speechText = AGOROTH[9] + AGOROTH[10];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse inspectSignResponse(){
        String speechText = AGOROTH[12];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse proceedLeftResponse(){
        String speechText = AGOROTH[11];
        return newTellResponse(speechText, true);
    }

    private SpeechletResponse riceBeastResponse(){
        String speechText = AGOROTH[13];
        String reprompt = "";
        return newAskResponse(speechText, true, reprompt, false);
    }

    private SpeechletResponse goblingKingResponse(){
        String speechText = AGOROTH[20];
        return newAskResponse(speechText, true, "", false);
    }

    private SpeechletResponse swordResponse(){
        String speechText = AGOROTH[14];
        return newAskResponse(speechText, true, "", false);
    }

    private SpeechletResponse blasterResponse(){
        String speechText = AGOROTH[15];
        return newAskResponse(speechText, true, "", false);
    }

    private SpeechletResponse wanderResponse(){
        String speechText = AGOROTH[17];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse notWanderResponse(){
        String speechText = AGOROTH[16];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse gradeResponse(){
        String speechText = AGOROTH[18];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        return SpeechletResponse.newTellResponse(speech);
    }

    private SpeechletResponse notGradeResponse(){
        String speechText = AGOROTH[19] + STORY[0];
        STEEL = false;
        WOOD = false;
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        Reprompt reprompt = new Reprompt();
        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse rejectSoupResponse(){
        String speech = "<speak>" + AGOROTH[22] + STORY[0] + "</speak>";
        return newAskResponse(speech, true, "", false);
    }

    private SpeechletResponse acceptSoupResponse(){
        String speechText = AGOROTH[21];
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);
        return SpeechletResponse.newTellResponse(speech);
    }

    private SpeechletResponse christianResponse(){
        String speechText = "<speak>"
                            + "<audio src='https://s3.amazonaws.com/myskill/mp3/iceicebaby.mp3'/>"
                            + "</speak>";
        return newTellResponse(speechText, true);
    }

    private SpeechletResponse getHelpResponse() {
        String speechText = "This is a test based adventure game using me, Alexa. At the end of each prompt I will ask you a question" +
                        "and you can respond or choose to say stop.";

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt);
    }

    private SpeechletResponse newAskResponse(String stringOutput, boolean isOutputSsml,
                                             String repromptText, boolean isRepromptSsml) {
        OutputSpeech outputSpeech, repromptOutputSpeech;
        if (isOutputSsml) {
            outputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) outputSpeech).setSsml(stringOutput);
        } else {
            outputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) outputSpeech).setText(stringOutput);
        }

        if (isRepromptSsml) {
            repromptOutputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) repromptOutputSpeech).setSsml(stringOutput);
        } else {
            repromptOutputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) repromptOutputSpeech).setText(repromptText);
        }

        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptOutputSpeech);
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }

    private SpeechletResponse newTellResponse(String stringOutput, boolean isOutputSsml) {
        OutputSpeech outputSpeech;
        if (isOutputSsml) {
            outputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) outputSpeech).setSsml(stringOutput);
        } else {
            outputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) outputSpeech).setText(stringOutput);
        }
        return SpeechletResponse.newTellResponse(outputSpeech);
    }
}
