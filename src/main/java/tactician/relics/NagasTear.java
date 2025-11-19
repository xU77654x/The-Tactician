package tactician.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.TacticianRobin;
import static tactician.TacticianMod.makeID;

public class NagasTear extends TacticianRelic {
    private static final String NAME = "NagasTear"; // Determines the filename and ID.
    public static final String ID = makeID(NAME); // Adds prefix to relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int MAXHP = 10;
    private static final int STAT = 1;

    public NagasTear() { super(ID, NAME, TacticianRobin.Meta.CARD_COLOR, RARITY, SOUND); }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + MAXHP + this.DESCRIPTIONS[1] + STAT + this.DESCRIPTIONS[2]; }

    @Override
    public void playLandingSFX() { CardCrawlGame.sound.playV("tactician:LevelUpFE8", 0.95F); }

    @Override
    public void onEquip() { AbstractDungeon.player.increaseMaxHp(MAXHP, true); }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STAT), STAT));
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, STAT), STAT));
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, STAT), STAT));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public AbstractRelic makeCopy() { return new NagasTear(); }
}