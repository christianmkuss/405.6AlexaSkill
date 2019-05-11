package adventuregame;

import com.amazon.speech.speechlet.SpeechletResponse;

import java.util.Arrays;
import java.util.HashMap;

class Agoroth {
    static boolean FLEX = false;

    private static String[] tellResponses = {"PizzaIntent", "ProceedLeftIntent", "ProceedRightIntent", "GradeIntent", "RejectSoupIntent"};
    private static String[] ssmlResponses = {
            "WoodDoorIntent",
            "RejectSoupIntent",
            "GoblinKingIntent",
            "BlasterIntent",
            "SwordIntent",
            "FlexMoreIntent",
            "StayPutIntent",
            "PizzaIntent"
    };

    static HashMap<String, String> AgorothSpeech = new HashMap<>();

    static {
        final String surrounding = "As the creatures surround you, the sounds of hoofs, the smell of horses and " +
                "rainbows, and the sight of glowing horns and rainbows made you realize that you are surrounded by a herd " +
                "of Glownicorns, the most majestic of all Warnicorn races. The largest Glownicorn approaches you. <prosody " +
                "pitch=high> ? </prosody><speak>";

        final String lost = "<speak> You decide to not know where you are going. This was a dumb move. So dumb " +
                "that your brain sent out a distress signal throughout the dimensions to ask for help. So dumb in fact that " +
                "your ancestors felt that same signal that echoed throughout space time Causing one of your primordial " +
                "ancestors to take a break from creating the World Tree to stop you from what you are about to do. A " +
                "glowing green portal opens up from the ceiling above you, and from it, not just any unicorn centaur " +
                "hybrid, but a unitaur with wings. You couldn't believe your eyes as the legendary Unitarsus tells you to " +
                "read the sign and give the writers a break from having to write another story line where you don't know " +
                "where you're going. BTW you're dead now. " + AdventureGameSpeechlet.GAMEOVER + "</speak>";

        final String wander = "You wander the endless halls. After what seems like hours, you see A sign saying This way for hot mayo " +
                "soup. Since you do like yourself some hot mayo soup, you follow and you find yourself in a " +
                "familiar classroom. An open computer on the corner is logged into PowerSchool. Do you grade or " +
                "not grade?";

        AgorothSpeech.put(
                "WoodDoorIntent",
                "<speak>You creak open the wooden door. The hinges groan as the untouched overgrowth on the other side " +
                        "becomes disrupted by your actions. Past the door, a blinding light encompasses you, causing " +
                        "you to pass out. You wake up again to the sound of chanting. " +
                        "<audio src='https://s3.amazonaws.com/textbasedgame/HookedOnAFeelingOoga.mp3'/> Around your " +
                        "body, a thin vine surrounds you, binding you and rendering you immobile. You think you can " +
                        "break the vines. Would you try to escape or stay put? </speak>"
        );
        AgorothSpeech.put(
                "FlexIntent",
                "You flex your muscles but it proves useless. Do you give up or flex some more? "
        );
        AgorothSpeech.put(
                "FlexMoreIntent",
                "<speak>You flex some more. You realize that flexing your measly muscles isn't going to break even the " +
                        "thinnest of strings. You should have pumped more iron when you had the chance. Your rustling " +
                        "alerts the chanting creatures to your presence and they surround you. " + surrounding
        );
        AgorothSpeech.put(
                "StayPutIntent",
                FLEX ? "<speak>You're just not that buff. But your past movements alerted the chanting creatures to your " +
                        "presence and they surround you. " + surrounding :
                        "<speak>You decide to stay put. Who knows, the vines look really frail and you might have been able to " +
                                "break it with you ginormous muscles. An hour later one of the chanting creatures check " +
                                "on you. Realizing that you are awake, the creatures surround you. " + surrounding
        );
        AgorothSpeech.put(
                "PizzaIntent",
                "<speak> Ah, I see that you are the dragonborn, fluent in the tri-language of man, dragon, and " +
                        "Warnicorn. We have waited 405.6 million years for you to come, Come with me and fulfill your " +
                        "destiny. Riding upon the unnamed Glownicorns back, he takes you to a familiar classroom filled " +
                        "with computers. It is time to do what must be done said the glowing, rainbow colored Warnicorn. " +
                        "You nod in agreement as you sit down and start grading homework for once. You hear a faint " +
                        "whisper, <amazon:effect name=\"whispered\"> good job Mr. Booth, </amazon:effect> as you log " +
                        "into PowerSchool to give some students their grades. </speak>"
        );
        AgorothSpeech.put(
                "CatchIntent",
                "You try to catch the glowing dot with your bare hands. However, the wisp proves to be too small and " +
                        "quick to be caught by your sluggish arms anytime soon. You should really just follow it, who " +
                        "knows, some fairy queen might have been using her magic to guide you. You decide to follow the " +
                        "glowing red wisp. You wander through the caverns below, you notice that the number 5/5 is " +
                        "written throughout the walls. The red wisp leads you to a fork leading to two caves. A crooked " +
                        "sign stands in the middle. Do you inspect sign, proceed left, or proceed right? "
        );
        AgorothSpeech.put(
                "FollowIntent",
                "You decide to follow the glowing red wisp. You wander through the caverns below, you notice that the " +
                        "number 5/5 is written throughout the walls. The red wisp leads you to a fork leading to two " +
                        "caves. A crooked sign stands in the middle. Do you inspect sign, proceed left, or proceed right? "
        );
        AgorothSpeech.put("ProceedLeftIntent", lost);
        AgorothSpeech.put("ProceedRightIntent", lost);
        AgorothSpeech.put(
                "InspectSignIntent",
                "You decide to actually know where you're going. The sign pointing left reads Democratic Republic of " +
                        "the Goblin King while the sign right states Domain of the Rice Beast. Do you go to the goblin " +
                        "king or the rice beast?"
        );
        AgorothSpeech.put(
                "RiceBeastIntent",
                "<speak> You go to the path leading to the Rice beast. In a swamp in the middle of nowhere, you see a " +
                        "single Rice Mill in the middle of this murky nowhere. All of a sudden, a black figure comes " +
                        "out of the mill and quickly encompasses you. It's long curly mane chokes you out as the beast " +
                        "encompasses you. <prosody rate='x-slow'> <prosody pitch='x-low'> Why are you here? </prosody>" +
                        "</prosody> the monster asks. You respond: I have no home, no control, and I can't find escape " +
                        "anywhere. The beast makes a sly grin as it says, <prosody rate='x-slow'>" +
                        "<prosody pitch='x-low'> then you better get a new Keyboard. </prosody></prosody> That was " +
                        "terrible. You decide to kill the beast. You look around and see a pair of twin blasters on the " +
                        "floor. You also see a sword stuck in the mud. Which one do you pick? The sword or the blasters? " +
                        "</speak>"
        );
        AgorothSpeech.put(
                "SwordIntent",
                "<speak> You roll across the Rice Mill  in hopes to get the sword. You grip the hilt and, with little " +
                        "effort, the blade frees itself from the mud. You swing at the beast with a mighty blow, " +
                        "cutting off its left arm. <prosody rate='x-slow'> <prosody pitch='x-low'> Don't worry, I'm " +
                        "alright </prosody></prosody> the beast says as he runs away. You level up, it made you realize " +
                        "that the writer has to end the story quick and you quickly teleport into a bright, neon green " +
                        "palace. Do you wander or not wander? </speak>"
        );
        AgorothSpeech.put(
                "BlasterIntent",
                "<speak> In a desperate attempt, you dash for the blasters." +
                        "<audio src='https://s3.amazonaws.com/textbasedgame/Laser_Gun.mp3'/>" +
                        "<prosody rate='x-slow'> <prosody pitch='x-low'> I see you have your arms at your side " +
                        "</prosody></prosody> the monster says, and you feel no remorse shooting at the Rice beast " +
                        "until it disappears, fleeing into the horizon. " +
                        "<audio src='https://s3.amazonaws.com/textbasedgame/Laser_Gun.mp3'/>" +
                        "You level up. It made you realize that the writer has to end the story quick and you quickly " +
                        "teleport into a bright neon green palace. Do you wander or not wander? </speak>"
        );
        AgorothSpeech.put("WanderIntent", wander);
        AgorothSpeech.put(
                "NotWanderIntent",
                "You wait around. Nothing happens. You decide to wander. " + wander
        );
        AgorothSpeech.put(
                "GradeIntent",
                "Welcome Alex. The computer beeps as you log in and grade some homework. Thanks for playing"
        );
        AgorothSpeech.put(
                "NotGradeIntent",
                "You go back to sleep. " + AdventureGameSpeechlet.StorySpeech.get("StartGameIntent")
        );
        AgorothSpeech.put(
                "GoblinKingIntent",
                "<speak>" +
                        "The idea of a Democratic Republic with an authoritarian figure makes you giggle. You walk up to" +
                        " the door of a towering tower. The next thing you find is the great Goblin King Liam staring " +
                        "you down. <prosody rate='slow'> <prosody pitch='low'> Do you like some hot mayo soup?" +
                        "</prosody></prosody> Do you accept the soup or reject the soup? </speak>"
        );
        AgorothSpeech.put(
                "AcceptSoupIntent",
                "You decide to accept the soup. As you make your way to a seat, you also decide to get some Hawaiian " +
                        "pizza, double the sauce, just the way you like it. Reality around you fades away as you " +
                        "realize you're just in your living room, pizza and soup in hand grading papers. You win."
        );
        AgorothSpeech.put(
                "RejectSoupIntent",
                "<speak><prosody rate='x-slow'> <prosody pitch='x-low'><prosody volume = 'x-loud'> What? </prosody>" +
                        "</prosody></prosody> The goblin king roars, he stomps his foot and an army of goblins seize " +
                        "you, cramming you in a familiar cryo chamber. You teer up a bit as you get carried back to a " +
                        "room. </speak>"
        );
    }

    static SpeechletResponse agorothIntent(String intentName) {
        if (intentName.equals("FlexIntent") || intentName.equals("StayPutIntent"))
            AdventureGameSpeechlet.PASSWORD = true;
        return Response.response(
                AgorothSpeech.get(intentName),
                Arrays.asList(ssmlResponses).contains(intentName),
                Arrays.asList(tellResponses).contains(intentName));
    }
}
