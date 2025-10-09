package tactician.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.actions.EasyModalChoiceAction;
import tactician.actions.PlaySoundAction;
import tactician.cards.cardchoice.*;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.powers.weapons.*;
import java.util.ArrayList;
import static tactician.TacticianMod.makeID;

public class Speedwing extends BaseRelic {
    private static final String NAME = "Speedwing"; // Determines the filename and ID.
    public static final String ID = makeID(NAME); // Adds prefix to relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int DEFLECT = 8; // This is used rather than a hard-coded value due to the description.

    public Speedwing() { super(ID, NAME, TacticianRobin.Meta.CARD_COLOR, RARITY, SOUND); }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + DEFLECT + this.DESCRIPTIONS[1]; }

    @Override
    public void atBattleStart() { this.counter = 0; }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (!this.grayscale) { this.counter++; }
        if (this.counter != -1) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.counter = -1;
            this.grayscale = true;
            AbstractPlayer p = AbstractDungeon.player;
            if (AbstractDungeon.player instanceof TacticianRobin) {
                ArrayList<AbstractCard> easyCardList = new ArrayList<>();
                easyCardList.add(new Weapon1Sword(() -> { if (!p.hasPower(Weapon1SwordPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon1SwordPower(p))); } }));
                easyCardList.add(new Weapon2Lance(() -> { if (!p.hasPower(Weapon2LancePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon2LancePower(p))); } }));
                easyCardList.add(new Weapon3Axe(() -> { if (!p.hasPower(Weapon3AxePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon3AxePower(p))); } }));
                easyCardList.add(new Weapon4Bow(() -> { if (!p.hasPower(Weapon4BowPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon4BowPower(p))); } }));
                easyCardList.add(new Weapon5Wind(() -> { if (!p.hasPower(Weapon5WindPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon5WindPower(p))); } }));
                easyCardList.add(new Weapon6Fire(() -> { if (!p.hasPower(Weapon6FirePower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon6FirePower(p))); } }));
                easyCardList.add(new Weapon7Thunder(() -> { if (!p.hasPower(Weapon7ThunderPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon7ThunderPower(p))); } }));
                easyCardList.add(new Weapon8Dark(() -> { if (!p.hasPower(Weapon8DarkPower.POWER_ID)) { addToBot(new ApplyPowerAction(p, p, new Weapon8DarkPower(p))); } }));
                addToBot(new PlaySoundAction("tactician:WeaponSelect", 1.50F));
                addToBot(new EasyModalChoiceAction(easyCardList));
            }
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DeflectPower(DEFLECT), DEFLECT));
        }
    }

    @Override
    public void onVictory() {
        this.counter = 0;
        this.grayscale = false;
    }

    @Override
    public void playLandingSFX() { CardCrawlGame.sound.play("tactician:LevelUpFE8"); }

    @Override
    public AbstractRelic makeCopy() { return new Speedwing(); }
}