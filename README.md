# 405.6 Adventure Game Alexa Skill

#### Setup AWS Lambda
1. First, go to the AWS Console and click on the Lambda link.

2. Click on the Create a Lambda Function or Get Started Now button.

3. Skip the blueprint, you probably won't need it. 

4. Name the Lambda Function "Adventure-Game-Skill" Space geek is the original skill we...gutted for our game to work and ask prompts.

5. Select the runtime as Java 8.

6. Go to the the root directory containing pom.xml, and run 
```bash
mvn assembly:assembly -DdescriptorId=jar-with-dependencies package
```
This will generate a file named `adventuregame-kit-samples-1.0-jar-with-dependencies.jar` in the target directory.

7. Select Code entry type as "Upload a .ZIP file" and then upload the `adventuregame-1.0-jar-with-dependencies.jar` file from the target directory to Lambda

8. Set the Handler as `adventuregame.AdventureGameSpeechletRequestStreamHandler`

9. Create a basic execution role and click create.

10. Leave the Advanced settings as the defaults.

11. Click "Next" and review the settings then click "Create Function"

12. Click the "Event Sources" tab and select "Add event source"

13. Set the Event Source type as Alexa Skills kit and Enable it now. Click Submit.

14. Copy the ARN from the top right to be used later in the Alexa Skill Setup. 

###Alexa Skill Setup

1. Go to the the Alexa Console (https://developer.amazon.com) and click Add a New Skill.

2. Set "TextBasedAdventureGame" as the skill name and "four hundred five point six" as the invocation name, this is what is used to activate your skill. 
	For example you would say: "Alexa, ask four hundred five point six to start a new game."

3. Select the Lambda ARN for the skill Endpoint and paste the ARN copied from above. Click Next.

4. Copy the Intent Schema from the included IntentSchema.json.

5. Copy the Sample Utterances from the included SampleUtterances.txt. Click Next.

6. Go back to the skill Information tab and copy the appId. Paste the appId into the AdventureGameSpeechletRequestStreamHandler.java file for the variable supportedApplicationIds,
   then update the lambda source zip file with this change and upload to lambda again, this step makes sure the lambda function only serves request from authorized source.

7. You are now able to start testing your sample skill! You should be able to go to the Echo webpage (http://echo.amazon.com/#skills) and see your skill enabled.

8. In order to test it, try to say some of the Sample Utterances from the Examples section below.

9. Your skill is now saved and once you are finished testing you can continue to publish your skill.    
User: "Alexa, ask four hundred five point six to play a new game."
    Alexa: "You wake up in a cryo chamber..."