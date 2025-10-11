package tactician.character;

import basemod.BaseMod;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import tactician.cards.basic.*;
import tactician.cards.basic.defends.*;
import tactician.cards.basic.strikes.*;
import tactician.relics.SecretBook;
import java.util.ArrayList;
import java.util.List;
import static tactician.TacticianMod.characterPath;
import static tactician.TacticianMod.makeID;

public class TacticianRobin extends CustomPlayer {
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 73;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 2;

    private static final String ID = makeID("Tactician"); // This should match whatever you have in the CharacterStrings.json file
    private static String[] getNames() { return CardCrawlGame.languagePack.getCharacterString(ID).NAMES; }
    private static String[] getText() { return CardCrawlGame.languagePack.getCharacterString(ID).TEXT; }

    // This static class is necessary to avoid certain quirks of Java classloading when registering the character.
    public static class Meta {
        // These are used to identify your character, as well as your character's card color.
        // Library color is basically the same as card color, but you need both because that's how the game was made.
        @SpireEnum
        public static PlayerClass TACTICIAN;
        @SpireEnum(name = "TACTICIAN_COLOR") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "TACTICIAN_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;

        // Character select images.
        private static final String CHAR_SELECT_BUTTON = characterPath("select/TacticianButton.png"); // Credit to bat.nick for this art.
        private static final String CHAR_SELECT_PORTRAIT = characterPath("select/TacticianSelect.png");

        // Character card images.
        private static final String BG_ATTACK = characterPath("cardback/bg_attack.png");
        private static final String BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
        private static final String BG_SKILL = characterPath("cardback/bg_skill.png");
        private static final String BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
        private static final String BG_POWER = characterPath("cardback/bg_power.png");
        private static final String BG_POWER_P = characterPath("cardback/bg_power_p.png");
        private static final String BG_CURSE = characterPath("cardback/bg_curse.png"); // Unable to access within a card.
        private static final String BG_CURSE_P = characterPath("cardback/bg_curse_p.png"); // Unable to access within a card.
        private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
        private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
        private static final String SMALL_ORB = characterPath("cardback/small_orb.png");

        // This is used to color *some* images, but NOT the actual cards. For that, edit the images in the cardback folder!
        private static final Color cardColor = new Color(Color.PURPLE);

        // Methods that will be used in the main mod file.
        public static void registerColor() {
            BaseMod.addColor(CARD_COLOR, cardColor, BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB, BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P, SMALL_ORB);
        }

        public static void registerCharacter() { BaseMod.addCharacter(new TacticianRobin(), CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT); }
    }

    // In-game images.
    private static final String SHOULDER_1 = characterPath("Tactician_RestSite1.png"); // Shoulder 1 and 2 are used at rest sites.
    private static final String SHOULDER_2 = characterPath("Tactician_RestSite2.png");
    private static final String CORPSE = characterPath("Tactician_Corpse.png"); // When you die (couldn't be me).

    // Textures to satiate BaseMod's stubborn energy orb implementation.
    private static final String[] orbTextures = {
        characterPath("energyorb/Tactician_ColorLarge.png"), // When you have energy.
        characterPath("energyorb/lol.png"),
        characterPath("energyorb/lol.png"),
        characterPath("energyorb/lol.png"),
        characterPath("energyorb/lol.png"),
        characterPath("energyorb/lol.png"), // The image that matters.
        characterPath("energyorb/Tactician_GreyLarge.png"), // When you don't have energy.
        characterPath("energyorb/lol.png"),
        characterPath("energyorb/lol.png"),
        characterPath("energyorb/lol.png"),
        characterPath("energyorb/lol.png")
    };

    // You spiiiiiiiiiin!. Negative is backwards.
    private static final float[] layerSpeeds = new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F };

    // Actual character class code is below this point.
    public TacticianRobin() { super(getNames()[0], Meta.TACTICIAN,
        new CustomEnergyOrb(orbTextures, characterPath("energyorb/lol.png"), layerSpeeds), // Energy Orb
        new AbstractAnimation() {
            @Override
            public Type type() { return Type.NONE; }
        }); // A NONE animation results in the image given in initializeClass being used.

        // Character hitbox. States the x/y position, then width and height.
        initializeClass(characterPath("Tactician_StaticFlipped.png"), SHOULDER_2, SHOULDER_1, CORPSE, getLoadout(), 0.0F, 0.0F, 229.0F, 250.0F, new EnergyManager(ENERGY_PER_TURN));

        // Location for text bubbles. You can adjust it as necessary. For most characters, these values are fine.
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike1Sword.ID);
        retVal.add(Strike2Lance.ID);
        retVal.add(Strike3Axe.ID);
        retVal.add(Strike4Bow.ID);
        retVal.add(Defend5Wind.ID);
        retVal.add(Defend6Fire.ID);
        retVal.add(Defend7Thunder.ID);
        retVal.add(Defend8Dark.ID);
        retVal.add(Solidarity.ID);
        retVal.add(Veteran.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(SecretBook.ID);
        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() { return new Solidarity(); }

    // Below this is a series of methods that you should *probably* adjust, but don't have to.
    @Override
    public int getAscensionMaxHPLoss() { return 5; } // Max HP reduction at Ascension 14+.

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        // These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.SMASH, // TODO: Wind attack effect.
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.LIGHTNING,
                AbstractGameAction.AttackEffect.POISON // TODO: Dark attack effect.
                // TODO: Use Sword, Lance, Axe, Bow, Wind, Fire, Thunder, and Dark in that order once these effects are decided.
        };
    }

    // @Override
    // public Texture getCutsceneBg() { return ImageMaster.loadImage(characterPath("Tactician_Ending.png")); }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel(characterPath("Tactician_Ending.png"), "tactician:TacticianEnding"));
        return panels;
    }


    public void receiveStartGame() {

    }

    private final Color cardRenderColor = Color.PURPLE.cpy(); // Used for some vfx on moving cards (sometimes) (maybe)
    private final Color cardTrailColor = Color.LIGHT_GRAY.cpy(); // Used for card trail vfx during gameplay.
    private final Color slashAttackColor = Color.LIGHT_GRAY.cpy(); // Used for a screen tint effect when you attack the heart.

    @Override
    public Color getCardRenderColor() { return cardRenderColor; }

    @Override
    public Color getCardTrailColor() { return cardTrailColor; }

    @Override
    public Color getSlashAttackColor() { return slashAttackColor; }

    @Override
    public BitmapFont getEnergyNumFont() {
        // Font used to display your current energy.
        // energyNumFontRed, Blue, Green, and Purple are used by the basegame characters.
        // It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        // See SoundMaster for a full list of existing sound effects, or look at BaseMod's wiki for adding custom audio.
        CardCrawlGame.sound.playA("tactician:TacticianSelect", MathUtils.random(0F, 0F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() { return "tactician:TacticianSelect"; }

    // Don't adjust these four directly, adjust the contents of the CharacterStrings.json file.
    @Override
    public String getLocalizedCharacterName() { return getNames()[0]; }

    @Override
    public String getTitle(PlayerClass playerClass) { return getNames()[1]; }

    @Override
    public String getSpireHeartText() { return getText()[1]; }

    @Override
    public String getVampireText() { return getText()[2]; }

    public String getSensoryStoneText() { return getText()[3]; }

    // You shouldn't need to edit any of the following methods.
    // This is used to display the character's information on the character selection screen.
    @Override
    public CharSelectInfo getLoadout() { return new CharSelectInfo(getNames()[0], getText()[0], MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(), getStartingDeck(), false); }

    @Override
    public AbstractCard.CardColor getCardColor() { return Meta.CARD_COLOR; }

    @Override
    public AbstractPlayer newInstance() { return new TacticianRobin(); } // Makes a new instance of your character class.

    @Override
    public void loadPrefs() {
        if (this.prefs == null) {
            this.prefs = SaveHelper.getPrefs(this.chosenClass.name());
            if (this.prefs.getInteger("ASCENSION_LEVEL", 1) <= 9) {
                this.prefs.putInteger("ASCENSION_LEVEL", 10);
                this.prefs.putInteger("WIN_COUNT", 1);
            }
        }
    }
}