package tactician.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.util.Wiz;
import static tactician.TacticianMod.makeID;

public class Dracoshield extends TacticianRelic {
    private static final String NAME = "Dracoshield"; // Determines the filename and ID.
    public static final String ID = makeID(NAME); // Adds prefix to relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int BLUR = 1;
    private static final int CARDS = 3;

    public Dracoshield() { super(ID, NAME, RARITY, SOUND); }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + CARDS + this.DESCRIPTIONS[1] + BLUR + this.DESCRIPTIONS[2]; }

    @Override
    public void playLandingSFX() { CardCrawlGame.sound.playV("tactician:LevelUpFE8", 0.80F); }

    @Override
    public void onEquip() {
        if (AbstractDungeon.currMapNode == null) { this.counter = -1; }
        else {
            if (Wiz.isInCombatRelic()) { this.counter = 0; }
            else { this.counter = -1; }
        }
    }

    @Override
    public void atTurnStart() { this.counter = 0; }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.counter++;
            if (this.counter == CARDS) {
                this.counter = 0;
                flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BlurPower(AbstractDungeon.player, BLUR), BLUR));
            }
        }
    }

    @Override
    public void onVictory() { this.counter = -1; }

    @Override
    public AbstractRelic makeCopy() { return new Dracoshield(); }
}