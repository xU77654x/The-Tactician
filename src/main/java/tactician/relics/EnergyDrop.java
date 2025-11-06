package tactician.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import static tactician.TacticianMod.makeID;

public class EnergyDrop extends BaseRelic {
    private static final String NAME = "EnergyDrop";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int DEFLECT = 2;

    public EnergyDrop() { super(ID, NAME, TacticianRobin.Meta.CARD_COLOR, RARITY, SOUND); }

    @Override
    public String getUpdatedDescription() { return this.DESCRIPTIONS[0] + DEFLECT + this.DESCRIPTIONS[1]; }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DeflectPower(DEFLECT), DEFLECT));
        }
    }

    @Override
    public void playLandingSFX() { CardCrawlGame.sound.play("tactician:LevelUpFE8"); }

    @Override
    public AbstractRelic makeCopy() { return new EnergyDrop(); }
}