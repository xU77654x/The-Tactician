package tactician;

import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.interfaces.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.Falling;
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.events.exordium.GoldenWing;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.jetbrains.annotations.NotNull;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.events.*;
import tactician.potions.BasePotion;
import tactician.relics.BaseRelic;
import tactician.util.GeneralUtils;
import tactician.util.KeywordInfo;
import tactician.util.TextureLoader;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import tactician.util.WeaponTypeChart;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import static tactician.character.TacticianRobin.Meta.TACTICIAN;

@SuppressWarnings({"CallToPrintStackTrace", "ConstantValue", "StringConcatenationArgumentToLogCall"})
@SpireInitializer
public class TacticianMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditCharactersSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        AddAudioSubscriber,
        OnStartBattleSubscriber,
        StartGameSubscriber

{
    public static ModInfo info;
    public static String modID;
    static { loadModInfo(); }
    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID);
    public static String makeID(String id) { return modID + ":" + id; }

    public static Properties defaultSettings = new Properties();
    public static final String SKIP_TUTORIALS_SETTING = "Skip Tutorial";
    public static Boolean skipTutorialsPlaceholder = true;
    public static Boolean globalRelicsPlaceholder = true;
    public static Boolean tempStatPatchPlaceholder = true;
    public static ModLabeledToggleButton skipTutorials;
    public static ModLabeledToggleButton globalRelics;
    public static ModLabeledToggleButton tempStatPatch;
    public static SpireConfig modConfig = null;
    public static WeaponTypeChart weaponTriangleChart;

    // This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new TacticianMod();
        TacticianRobin.Meta.registerColor();
    }

    public TacticianMod() {
        BaseMod.subscribe(this); // This will make BaseMod trigger all the subscribers at their appropriate times.
		logger.info("{} subscribed to BaseMod.", modID); // logger.info(modID + " subscribed to BaseMod.");
        defaultSettings.setProperty("Skip Tutorial", "FALSE");
        try {
            SpireConfig config = new SpireConfig(modID, makeID("Config"), defaultSettings);
            skipTutorialsPlaceholder = config.getBool("Skip Tutorial");
        }
        catch (IOException e) { e.printStackTrace(); }
        defaultSettings.setProperty("Global Relics", "TRUE");
        try {
            SpireConfig config = new SpireConfig(modID, makeID("Config"), defaultSettings);
            globalRelicsPlaceholder = config.getBool("Global Relics");
        }
        catch (IOException e) { e.printStackTrace(); }
        defaultSettings.setProperty("Temp Stat Patch", "TRUE");
        try {
            SpireConfig config = new SpireConfig(modID, makeID("Config"), defaultSettings);
            tempStatPatchPlaceholder = config.getBool("Temp Stat Patch");
        }
        catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void receivePostInitialize() {
        weaponTriangleChart = new WeaponTypeChart();
        ModPanel settingsPanel = new ModPanel();
        Texture badgeTexture = TextureLoader.getTexture(imagePath("TacticianBadge.png")); //  Load the icon image in the Mods submenu.
        // Set up the mod information displayed in the in-game mods menu. The information used is taken from your pom.xml file.
        // If you want to set up a config panel, that will be done here. The Mod Badges page has a basic example of this, but setting up config is overall a bit complex.

        BaseMod.addEvent(new AddEventParams.Builder(GoldenWingTactician.ID, GoldenWingTactician.class).playerClass(TACTICIAN).overrideEvent(GoldenWing.ID).create());
        BaseMod.addEvent(new AddEventParams.Builder(VampiresTactician.ID, VampiresTactician.class).playerClass(TACTICIAN).overrideEvent(Vampires.ID).create());
        BaseMod.addEvent(new AddEventParams.Builder(GhostsTactician.ID, GhostsTactician.class).playerClass(TACTICIAN).overrideEvent(Ghosts.ID).create());
        BaseMod.addEvent(new AddEventParams.Builder(FallingTactician.ID, FallingTactician.class).playerClass(TACTICIAN).overrideEvent(Falling.ID).create());
        registerPotions(); // Custom events and potions here.

        skipTutorials = new ModLabeledToggleButton("Disable the Tutorial", 350.0F, 750.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, skipTutorialsPlaceholder, settingsPanel, label -> {}, button -> {
            skipTutorialsPlaceholder = button.enabled;
            try {
                SpireConfig config = new SpireConfig(modID, makeID("Config"), defaultSettings);
                config.setBool("Skip Tutorial", skipTutorialsPlaceholder);
                config.save();
            }
            catch (Exception e) { e.printStackTrace(); }
        });
        settingsPanel.addUIElement(skipTutorials);

        globalRelics = new ModLabeledToggleButton("All characters can obtain global relics. (Applies to current run; does not require title restart.)", 350.0F, 700.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, globalRelicsPlaceholder, settingsPanel, label -> {}, button -> {
            globalRelicsPlaceholder = button.enabled;
            try {
                SpireConfig config = new SpireConfig(modID, makeID("Config"), defaultSettings);
                config.setBool("Global Relics", globalRelicsPlaceholder);
                config.save();
            }
            catch (Exception e) { e.printStackTrace(); }
        });
        settingsPanel.addUIElement(globalRelics);

        tempStatPatch = new ModLabeledToggleButton("Enable custom sprites for temp. Strength / Dexterity on all characters.", 350.0F, 650.0F, Settings.CREAM_COLOR, FontHelper.charDescFont, globalRelicsPlaceholder, settingsPanel, label -> {}, button -> {
            tempStatPatchPlaceholder = button.enabled;
            try {
                SpireConfig config = new SpireConfig(modID, makeID("Config"), defaultSettings);
                config.setBool("Temp Stat Patch", tempStatPatchPlaceholder);
                config.save();
            }
            catch (Exception e) { e.printStackTrace(); }
        });
        settingsPanel.addUIElement(tempStatPatch);

        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, settingsPanel);
    }

    @Override
    public void receiveStartGame() {
        BaseMod.removeTopPanelItem(weaponTriangleChart);
        if (AbstractDungeon.player.chosenClass == TACTICIAN) {
            BaseMod.addTopPanelItem(weaponTriangleChart);
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        // if (AbstractDungeon.ascensionLevel == 0 && floorNum == 1 && AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.ftue = new TutorialTactician(); }
        if (!skipTutorials.toggle.enabled && AbstractDungeon.player instanceof TacticianRobin) {
            AbstractDungeon.ftue = new TutorialTactician();
            skipTutorials.toggle.toggle();
        }
    }

    /*----------Localization----------*/

    // This is used to load the appropriate localization files based on language.
    private static String getLangString() { return Settings.language.name().toLowerCase(); }
    private static final String defaultLanguage = "eng";
    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        // First, load the default localization. Then, if the current language is different, attempt to load localization for that language.
        // This results in the default localization being used for anything that might be missing. The same process is used to load keywords slightly below.
        loadLocalization(defaultLanguage); // There is no exception catching for default localization.
        if (!defaultLanguage.equals(getLangString())) {
            try { loadLocalization(getLangString()); }
            catch (GdxRuntimeException e) { e.printStackTrace(); }
        }
    }

    public static void registerPotions() {
        new AutoAdd(modID).packageFilter(BasePotion.class).any(BasePotion.class, (info, potion) -> BaseMod.addPotion(potion.getClass(), null, null, null, potion.ID, potion.playerClass));
        // This code runs for any classes that extend this class.
        // These three null parameters are a deprecated way to ste potion colors. If they're not null, they'll overwrite the color set in the potions themselves.
        // playerClass will make a potion character-specific. By default, it's null and will do nothing.
    }

    private void loadLocalization(String lang) {
        // While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        BaseMod.loadCustomStringsFile(CardStrings.class, localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class, localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class, localizationPath(lang, "EventStrings.json"));
        // BaseMod.loadCustomStringsFile(OrbStrings.class, localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class, localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class, localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class, localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(TutorialStrings.class, localizationPath(lang, "TutorialStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class, localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e) { logger.warn(modID + " does not support " + getLangString() + " keywords."); }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty()) { keywords.put(info.ID, info); }
    }

    // These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) { return resourcesFolder + "/localization/" + lang + "/" + file; }
    public static String imagePath(String file) { return resourcesFolder + "/images/" + file; }
    public static String characterPath(String file) { return resourcesFolder + "/images/character/" + file; }
    public static String powerPath(String file) { return resourcesFolder + "/images/powers/" + file; }
    public static String relicPath(String file) { return resourcesFolder + "/images/relics/" + file; }
    // public static String musicPath(String file) { return resourcesFolder + "/audio/music/" + file; }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = TacticianMod.class.getName(); // getPackage can be iffy with patching, so the class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0) { name = name.substring(0, separator); }

        FileHandle resources = getFileHandle(name);
        if (!resources.child("images").exists()) { throw new RuntimeException("\n\tFailed to find the 'images' folder in the mod's 'resources/" + name + "' folder; Make sure the images folder is in the correct location."); }
        if (!resources.child("localization").exists()) { throw new RuntimeException("\n\tFailed to find the 'localization' folder in the mod's 'resources/" + name + "' folder; Make sure the localization folder is in the correct location."); }
        return name;
    }

    private static @NotNull FileHandle getFileHandle(String name) {
        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);

        if (!resources.exists()) {
            throw new RuntimeException("\n\tFailed to find resources folder; expected it to be named \"" + name + "\"." +
                " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                "\tat the top of the " + TacticianMod.class.getSimpleName() + " java file.");
        }
        return resources;
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    @SuppressWarnings("UrlHashCode")
	private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null) { return false; }
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(TacticianMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else { throw new RuntimeException("Failed to determine mod info/ID based on initializer."); }
    }

    @Override
    public void receiveEditCharacters() { TacticianRobin.Meta.registerCharacter(); }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID) // Loads files from this mod
            .packageFilter(TacticianCard.class) // In the same package as this class
            .setDefaultSeen(true) // And marks them as seen in the compendium
            .cards(); // Adds the cards
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID).any(BaseRelic.class, (info, relic) -> { // Loads files from this mod. Run this code for any classes that extend this class.
            if (relic.pool != null) { BaseMod.addRelicToCustomPool(relic, relic.pool); } // Register a custom character specific relic
            else { BaseMod.addRelic(relic, relic.relicType); } // Register a shared or base game character specific relic.
            UnlockTracker.markRelicAsSeen(relic.relicId);
            // If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
        });
    }

    @Override
    public void receiveAddAudio() {
        // CardCrawlGame.sound.playAndLoop(add here)

        // Basic Cards
        BaseMod.addAudio("tactician:Strike_Strong", "tactician/audio/effect/Strike_Strong.ogg");
        BaseMod.addAudio("tactician:Strike_Neutral", "tactician/audio/effect/Strike_Neutral.ogg");
        BaseMod.addAudio("tactician:Strike_Weak", "tactician/audio/effect/Strike_Weak.ogg");
        BaseMod.addAudio("tactician:Defend_Strong", "tactician/audio/effect/Defend_Strong.ogg");
        BaseMod.addAudio("tactician:Defend_Neutral", "tactician/audio/effect/Defend_Neutral.ogg");
        BaseMod.addAudio("tactician:Defend_Weak", "tactician/audio/effect/Defend_Weak.ogg");
        BaseMod.addAudio("tactician:Solidarity", "tactician/audio/effect/Solidarity.ogg");
        BaseMod.addAudio("tactician:Veteran", "tactician/audio/effect/Veteran.ogg");

        // Common Cards
        BaseMod.addAudio("tactician:WrathStrike", "tactician/audio/effect/WrathStrike.ogg");
        BaseMod.addAudio("tactician:TempestLance", "tactician/audio/effect/TempestLance.ogg");
        BaseMod.addAudio("tactician:Smash", "tactician/audio/effect/Smash.ogg");
        BaseMod.addAudio("tactician:CurvedShot", "tactician/audio/effect/CurvedShot.ogg");
        BaseMod.addAudio("tactician:Elwind", "tactician/audio/effect/Elwind.ogg");
        BaseMod.addAudio("tactician:Arcfire_Cast", "tactician/audio/effect/Arcfire_Cast.ogg");
        BaseMod.addAudio("tactician:Arcfire_Hit", "tactician/audio/effect/Arcfire_Hit.ogg");
        BaseMod.addAudio("tactician:Thunder", "tactician/audio/effect/Thunder.ogg");
        BaseMod.addAudio("tactician:Flux", "tactician/audio/effect/Flux.ogg");
        BaseMod.addAudio("tactician:Shove", "tactician/audio/effect/Shove.ogg");
        BaseMod.addAudio("tactician:Reposition", "tactician/audio/effect/Reposition.ogg");
        BaseMod.addAudio("tactician:Discipline", "tactician/audio/effect/Discipline.ogg");
        BaseMod.addAudio("tactician:Zeal", "tactician/audio/effect/Zeal.ogg");
        BaseMod.addAudio("tactician:AlertStance", "tactician/audio/effect/AlertStance.ogg");
        BaseMod.addAudio("tactician:Tantivy", "tactician/audio/effect/Tantivy.ogg");
        BaseMod.addAudio("tactician:Blossom", "tactician/audio/effect/Blossom.ogg");
        BaseMod.addAudio("tactician:Armsthrift", "tactician/audio/effect/Armsthrift.ogg");
        BaseMod.addAudio("tactician:OutdoorFighter", "tactician/audio/effect/OutdoorFighter.ogg");
        BaseMod.addAudio("tactician:IndoorFighter", "tactician/audio/effect/IndoorFighter.ogg");

        // Uncommon Cards
        BaseMod.addAudio("tactician:CrosswiseCut1", "tactician/audio/effect/CrosswiseCut1.ogg");
        BaseMod.addAudio("tactician:CrosswiseCut2", "tactician/audio/effect/CrosswiseCut2.ogg");
        BaseMod.addAudio("tactician:FrozenLance", "tactician/audio/effect/FrozenLance.ogg");
        BaseMod.addAudio("tactician:WildAbandon", "tactician/audio/effect/WildAbandon.ogg");
        BaseMod.addAudio("tactician:WaningShot_Draw", "tactician/audio/effect/WaningShot_Draw.ogg");
        BaseMod.addAudio("tactician:WaningShot_Hit", "tactician/audio/effect/WaningShot_Hit.ogg");
        BaseMod.addAudio("tactician:CuttingGale", "tactician/audio/effect/CuttingGale.ogg");
        BaseMod.addAudio("tactician:DyingBlaze", "tactician/audio/effect/DyingBlaze.ogg");
        BaseMod.addAudio("tactician:Bolting", "tactician/audio/effect/Bolting.ogg");
        BaseMod.addAudio("tactician:Nosferatu", "tactician/audio/effect/Nosferatu.ogg");
        BaseMod.addAudio("tactician:LevinSword", "tactician/audio/effect/LevinSword.ogg");
        BaseMod.addAudio("tactician:FlameLance", "tactician/audio/effect/FlameLance.ogg");
        BaseMod.addAudio("tactician:HurricaneAxe", "tactician/audio/effect/HurricaneAxe.ogg");
        BaseMod.addAudio("tactician:PlegianBow", "tactician/audio/effect/PlegianBow.ogg");
        BaseMod.addAudio("tactician:FlashSparrow", "tactician/audio/effect/FlashSparrow.ogg");
        BaseMod.addAudio("tactician:Relief", "tactician/audio/effect/Relief.ogg");
        BaseMod.addAudio("tactician:PaviseAegis", "tactician/audio/effect/PaviseAegis.ogg");
        BaseMod.addAudio("tactician:Charm", "tactician/audio/effect/Charm.ogg");
        BaseMod.addAudio("tactician:Healtouch", "tactician/audio/effect/Healtouch.ogg");
        BaseMod.addAudio("tactician:EvenRhythm", "tactician/audio/effect/EvenOddRhythm_Even.ogg");
        BaseMod.addAudio("tactician:OddRhythm", "tactician/audio/effect/EvenOddRhythm_Odd.ogg");
        BaseMod.addAudio("tactician:SurpriseAttack", "tactician/audio/effect/SurpriseAttack.ogg");
        BaseMod.addAudio("tactician:Pass", "tactician/audio/effect/Pass.ogg");
        BaseMod.addAudio("tactician:Locktouch", "tactician/audio/effect/Locktouch.ogg");
        BaseMod.addAudio("tactician:Perceptive", "tactician/audio/effect/Perceptive.ogg");
        BaseMod.addAudio("tactician:RallySpectrum", "tactician/audio/effect/RallySpectrum.ogg");
        BaseMod.addAudio("tactician:SpecialDance", "tactician/audio/effect/SpecialDance.ogg");
        BaseMod.addAudio("tactician:Renewal", "tactician/audio/effect/Renewal.ogg");
        BaseMod.addAudio("tactician:Acrobat", "tactician/audio/effect/Acrobat.ogg");
        BaseMod.addAudio("tactician:MastersTactics", "tactician/audio/effect/MastersTactics.ogg");
        BaseMod.addAudio("tactician:Prescience", "tactician/audio/effect/Prescience.ogg");
        BaseMod.addAudio("tactician:Patience", "tactician/audio/effect/Patience.ogg");
        BaseMod.addAudio("tactician:Vantage", "tactician/audio/effect/Vantage.ogg");
        BaseMod.addAudio("tactician:Expiration", "tactician/audio/effect/Expiration.ogg");
        BaseMod.addAudio("tactician:CreationPulse", "tactician/audio/effect/CreationPulse.ogg");
        BaseMod.addAudio("tactician:StatIncreaseFE", "tactician/audio/effect/StatIncreaseFE.ogg");

        // Rare Cards
        BaseMod.addAudio("tactician:Astra_Hit1", "tactician/audio/effect/Astra_Hit1.ogg");
        BaseMod.addAudio("tactician:Astra_Hit2", "tactician/audio/effect/Astra_Hit2.ogg");
        BaseMod.addAudio("tactician:Astra_Hit3", "tactician/audio/effect/Astra_Hit3.ogg");
        BaseMod.addAudio("tactician:Astra_Hit4", "tactician/audio/effect/Astra_Hit4.ogg");
        BaseMod.addAudio("tactician:Astra_Hit5", "tactician/audio/effect/Astra_Hit5.ogg");
        BaseMod.addAudio("tactician:Astra_Hit1", "tactician/audio/effect/Astra_Hit1.ogg");
        BaseMod.addAudio("tactician:SwiftStrikes_Hit1", "tactician/audio/effect/SwiftStrikes_Hit1.ogg");
        BaseMod.addAudio("tactician:SwiftStrikes_Hit2", "tactician/audio/effect/SwiftStrikes_Hit2.ogg");
        BaseMod.addAudio("tactician:ExhaustiveStrike_Hit1", "tactician/audio/effect/ExhaustiveStrike_Hit1.ogg");
        BaseMod.addAudio("tactician:ExhaustiveStrike_Hit2", "tactician/audio/effect/ExhaustiveStrike_Hit2.ogg");
        BaseMod.addAudio("tactician:HuntersVolley_Hit1", "tactician/audio/effect/HuntersVolley_Hit1.ogg");
        BaseMod.addAudio("tactician:HuntersVolley_Hit2", "tactician/audio/effect/HuntersVolley_Hit2.ogg");
        BaseMod.addAudio("tactician:Excalibur_Cast", "tactician/audio/effect/Excalibur_Cast.ogg");
        BaseMod.addAudio("tactician:Excalibur_Hit", "tactician/audio/effect/Excalibur_Hit.ogg");
        BaseMod.addAudio("tactician:Bolganone", "tactician/audio/effect/Bolganone.ogg");
        BaseMod.addAudio("tactician:Thoron_Cast", "tactician/audio/effect/Thoron_Cast.ogg");
        BaseMod.addAudio("tactician:Thoron_Glint", "tactician/audio/effect/Thoron_Glint.ogg");
        BaseMod.addAudio("tactician:Goetia", "tactician/audio/effect/Goetia.ogg");
        BaseMod.addAudio("tactician:Luna_KillingEdgeGain", "tactician/audio/effect/Luna_KillingEdgeGain.ogg");
        BaseMod.addAudio("tactician:Ignis", "tactician/audio/effect/Ignis.ogg");
        BaseMod.addAudio("tactician:StatDecreaseFE", "tactician/audio/effect/StatDecreaseFE.ogg");
        BaseMod.addAudio("tactician:MasterSeal", "tactician/audio/effect/MasterSeal.ogg");
        BaseMod.addAudio("tactician:TipTheScales", "tactician/audio/effect/TipTheScales.ogg");
        BaseMod.addAudio("tactician:Despoil", "tactician/audio/effect/Despoil.ogg");
        BaseMod.addAudio("tactician:PartOfThePlan", "tactician/audio/effect/PartOfThePlan.ogg");
        BaseMod.addAudio("tactician:ChaosStyle", "tactician/audio/effect/ChaosStyle.ogg");
        BaseMod.addAudio("tactician:QuickBurn", "tactician/audio/effect/QuickBurn.ogg");
        BaseMod.addAudio("tactician:GrandmasterForm", "tactician/audio/effect/GrandmasterForm.ogg");

        // Other Cards and Powers
        BaseMod.addAudio("tactician:Hex", "tactician/audio/effect/Hex.ogg");
        BaseMod.addAudio("tactician:Anathema", "tactician/audio/effect/Anathema.ogg");
        BaseMod.addAudio("tactician:TacticianSelect", "tactician/audio/effect/Tactician_Select.ogg");
        BaseMod.addAudio("tactician:TacticianEnding", "tactician/audio/effect/Tactician_Ending.ogg");
        BaseMod.addAudio("tactician:LevelUpFE8", "tactician/audio/effect/LevelUpFE8.ogg");
        BaseMod.addAudio("tactician:CriticalHitFE8", "tactician/audio/effect/CriticalHitFE8.ogg");
        BaseMod.addAudio("tactician:WeaponSelect", "tactician/audio/effect/WeaponSelect.ogg");
        BaseMod.addAudio("tactician:DeflectGain", "tactician/audio/effect/DeflectGain.ogg");
        BaseMod.addAudio("tactician:DeflectReceiveHit", "tactician/audio/effect/DeflectReceiveHit.ogg");

        // Male Voice
        BaseMod.addAudio("tactician:Male_CA_Sword", "tactician/audio/voice/Male_CA_Sword.ogg");
        BaseMod.addAudio("tactician:Male_CA_Lance", "tactician/audio/voice/Male_CA_Lance.ogg");
        BaseMod.addAudio("tactician:Male_CA_Axe", "tactician/audio/voice/Male_CA_Axe.ogg");
        BaseMod.addAudio("tactician:Male_CA_Bow", "tactician/audio/voice/Male_CA_Bow.ogg");
        BaseMod.addAudio("tactician:Male_CA_MiscMagic", "tactician/audio/voice/Male_CA_MiscMagic.ogg");
        BaseMod.addAudio("tactician:Male_Elwind", "tactician/audio/voice/Male_Elwind.ogg");
        BaseMod.addAudio("tactician:Male_Arcfire", "tactician/audio/voice/Male_Arcfire.ogg");
        BaseMod.addAudio("tactician:Male_Thunder", "tactician/audio/voice/Male_Thunder.ogg");
        BaseMod.addAudio("tactician:Male_Flux", "tactician/audio/voice/Male_Flux.ogg");
        BaseMod.addAudio("tactician:Male_Nosferatu", "tactician/audio/voice/Male_Nosferatu.ogg");
        BaseMod.addAudio("tactician:Male_Bolganone", "tactician/audio/voice/Male_Bolganone.ogg");
        BaseMod.addAudio("tactician:Male_Thoron", "tactician/audio/voice/Male_Thoron.ogg");
        BaseMod.addAudio("tactician:Male_Luna", "tactician/audio/voice/Male_Luna.ogg");
        BaseMod.addAudio("tactician:Male_TipTheScales", "tactician/audio/voice/Male_TipTheScales.ogg");
        BaseMod.addAudio("tactician:Male_QuickBurn", "tactician/audio/voice/Male_QuickBurn.ogg");
        BaseMod.addAudio("tactician:Male_GrandmasterForm", "tactician/audio/voice/Male_GrandmasterForm.ogg");
    }
}