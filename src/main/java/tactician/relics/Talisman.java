package tactician.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import java.util.Objects;
import static tactician.TacticianMod.makeID;

public class Talisman extends TacticianRelic {
    private static final String NAME = "Talisman";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int TEMPHP = 1;
    private static final int BLOCK = 1;

    public Talisman() { super(ID, NAME, RARITY, SOUND); }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + TEMPHP + this.DESCRIPTIONS[1] + BLOCK + this.DESCRIPTIONS[2]; }

    @Override
    public void playLandingSFX() { CardCrawlGame.sound.playV("tactician:LevelUpFE8", 0.80F); }

    @Override
    public void onExhaust(AbstractCard card) {
        if (!Objects.equals(card.cardID, Necronomicurse.ID)) {
            flash();
            addToBot(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, TEMPHP));
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
        }
    }

    @Override
    public AbstractRelic makeCopy() { return new Talisman(); }
}