package adventuregame;

import com.amazon.speech.speechlet.SpeechletResponse;

import java.util.Arrays;
import java.util.HashMap;

class Zorento {
    private static boolean KEY = false;
    private static boolean GUN = false;
    private static boolean ROBOT = false;

    private static final String EXPLOSION = "<audio src='https://s3.amazonaws.com/textbasedgame/bomb_exploding.mp3'/>";

    static HashMap<String, String> ZorentoSpeech = new HashMap<>();

    static {
        ZorentoSpeech.put(
                "SteelDoorIntent",
                "Upon entering through the the door, it immediately slams shut behind you. . . Ahead lies a building " +
                        "with a faint red glow inside. To the left you see a pile of what looks like broken laser " +
                        "weapons. To the right there are what appears to be robots rusted with age holding something " +
                        "mysterious in their hands. Do you go to the robots, the broken lasers, or the building? "
        );
        ZorentoSpeech.put(
                "BuildingIntent",
                !KEY && ROBOT ? "With the robot hot on you heels you stumble into the building and find the source of " +
                        "the light. A computer screen with a prompt open with two choices, activate or deactivate. " +
                        "Which one will you press? " :
                        "Inside the building you find the source of the light. A warning on a computer monitor about " +
                                "robots becoming dysfunctional. A prompt appears with two options, activate or deactivate " +
                                "Which one will you press? "
        );
        ZorentoSpeech.put(
                "RobotPileIntent",
                GUN ? "After going to the pile of robots, one activates and determines you are a threat. Do you shoot " +
                        "the robot or go to the building? " :
                        "As you look to see what the mechanical beings have in their grasp one of them activates and " +
                                "determines you are a threat. You decide it is probably wise to run away. You can " +
                                "continue straight ahead to the building with the red glow or inspect the robots on " +
                                "the right. Which will you choose? "
        );
        ZorentoSpeech.put(
                "KeyIntent",
                "You rush the key into the lock and it fits. You open the door and behind you more robots have come to " +
                        "avenge their friend. You run inside and lock the door behind you. Safe for now, you find a " +
                        "path ahead which leads to some form of hangar. As you search the hangar you observe the " +
                        "remnants of spacecraft that has been left to rust. One of these ships appears to be still " +
                        "intact and functional. You enter the ship and sit in the cockpit as it comes to life with " +
                        "different colored buttons. The controls seem like basic flight controls but you have no idea " +
                        "how to start the engines. You have three buttons to choose from, a turquoise, red, or violet " +
                        "button. Which will you press? "
        );
        ZorentoSpeech.put(
                "LaserPileIntent",
                ROBOT ? "After scrambling to the pile of broken technology, with the robot right on your heels, by a " +
                        "miracle you find a laser working with a couple shots left. Do you shoot the robot or run " +
                        "straight into the building with the red light?" :
                        "In the rubble of broken and useless junk you find a laser gun working with only a couple of " +
                                "shots left. You can continue straight ahead to the building with the red glow or " +
                                "inspect the robots on the right. "
        );
        ZorentoSpeech.put(
                "ShootRobotIntent",
                "You fall to your back as the recoil of the laser is too much for your measly, unworked muscles. The " +
                        "robot raises back to attack but you squeeze the trigger on the laser and let loose what energy " +
                        "is left in the weapon into the robots chest. As you watch the light die from its mechanical " +
                        "body it releases what was in its grasp. It seems to be a key of some sort. You enter the " +
                        "building and find yourself in a metal room with a computer that is off. Do you inspect the " +
                        "room? Yes or no?"
        );
        ZorentoSpeech.put(
                "ActivateIntent",
                !KEY && ROBOT ? "As you quickly click the activate button you realize the clanging of the robot chasing " +
                        "you has died. As you turn around, you see the robot as if it's frozen in time. You take what " +
                        "was in the robot's clutch and realize it's a key. Do you inspect the room? Yes or no? " :
                        "After messing with the terminal you go back to the robots to inspect their contents. The " +
                                "robots are beeping and you hurriedly grab what appears to be a key that was in its " +
                                "grasp as you fear the noise will draw something dangerous. You run straight into " +
                                "the building and find you are in a dark room. Do you inspect the room? Yes or no? "
        );
        ZorentoSpeech.put(
                "DeactivateIntent",
                "<speak>Upon pressing deactivate you realize that that was the wrong choice. The right choice, which " +
                        "was obviously activate, seems to taunt you. It taunts you so much that your whole life becomes " +
                        "obsessed with your one mistake. And while your friends and family continue on living happy " +
                        "lives you are left alone to contemplate your mistake and what would have happened if you had " +
                        "only pushed activate. This idiotic decision haunts you until you die. " +
                        AdventureGameSpeechlet.GAMEOVER + "</speak>"
        );
        ZorentoSpeech.put(
                "TurquoiseIntent",
                "<speak> As you press the button the engine malfunctions and explodes, leaving you in pieces over the " +
                        "hangar walls. " + EXPLOSION + AdventureGameSpeechlet.GAMEOVER + "</speak>"
        );
        ZorentoSpeech.put(
                "NoIntent",
                "<speak> As you decide not to try the key on the door robots come from every direction after you have " +
                        "killed their friend. You become surrounded and have no escape as your fate is sealed. " +
                        AdventureGameSpeechlet.GAMEOVER + "</speak>"
        );
        ZorentoSpeech.put(
                "RedIntent",
                "You wait in anticipation to see the result of your choice. You are relieved to hear the sound of the " +
                        "engines starting. Without wasting any time you take off and arrive into a bright, neon green " +
                        "palace. Do you want to wander or not wander?"
        );
        ZorentoSpeech.put(
                "VioletIntent",
                "<speak> Upon pressing the button you hear a beeping noise and rigged bomb explodes becoming your demise. " +
                        EXPLOSION + AdventureGameSpeechlet.GAMEOVER + "</speak>"
        );
        ZorentoSpeech.put(
                "RoomIntent",
                "After inspecting the room you see a locked door which requires some form of a key to unlock it. After " +
                        "closer inspecting the door you realize the key from the robot will open the door. Do you use " +
                        "the key or not use the key?"
        );
    }

    private static String[] tellResponses = {"VioletIntent", "TurquoiseIntent", "NoIntent", "DeactivateIntent"};

    static SpeechletResponse zorentoIntent(String intentName) {
        if(intentName.equals("RobotPileIntent")) ROBOT = true;
        if(intentName.equals("LaserPileIntent")) GUN = true;
        if(intentName.equals("ShootRobotIntent")) {
            ROBOT = false;
            KEY = true;
        }
        return Response.response(
                ZorentoSpeech.get(intentName),
                Arrays.asList(tellResponses).contains(intentName),
                Arrays.asList(tellResponses).contains(intentName)
        );
    }
}
