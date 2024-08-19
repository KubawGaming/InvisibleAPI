# InvisibleAPI
<bold>[![](https://jitpack.io/v/KubawGaming/InvisibleAPI.svg)](https://jitpack.io/#KubawGaming/InvisibleAPI)</bold> <strong>Its project version used in gradle/maven</strong>

<br>
A small and simple packet-based API that allows you to set invisibility to players in such a way as to control who sees this invisibility in a given player.

## Example of use:

At the very beginning you need to create an instance of the InvisibleAPI class. Note - make only one instance!

You will not have to save your instance. After creating it, you will have access to InvisibleAPI through the static method.

```java
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // We are creating InvisibleAPI instance and giving Main class (that extends JavaPlugin) as argument
        new InvisibleAPI(this);

        // After creating the instance you are able to get InvisibleAPI using:
        InvisibleAPI invisibleAPI = InvisibleAPI.getInstance();
    }

}
```

Below are examples of how to operate on the API:

```java
InvisibleAPI invisibleAPI = InvisibleAPI.getInstance();
Player player = ...;
List<Player> targets = ...;

// Setting invisibility (player will be invisible for targets)
invisibleAPI.setInvisible(player, targets);

// Unsetting invisibility
invisibleAPI.unsetInvisible(player, targets);

// Checking if player is invisible
// Note - this method only checks visibility in the API, not actually whether the player can see someone or not
invisibleAPI.canSee(player, targets.get(0));
```

## Gradle:

```gradle
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.KubawGaming:InvisibleAPI:VERSION_HERE'
}
```

## Maven:

```html
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.KubawGaming</groupId>
    <artifactId>InvisibleAPI</artifactId>
    <version>VERSION_HERE</version>
</dependency>
```
