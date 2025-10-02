package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.effects.cards.CharmEffect;
import tactician.util.CardStats;

public class Charm extends TacticianCard {
    public static final String ID = makeID(Charm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Charm() {
        super(ID, info);
        setBlock(4, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new CharmEffect("tactician:Charm", 1.25f)));
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new PlaySoundAction("tactician:Charm", 1.25f));
        addToTop(new GainEnergyAction(1));
        Charm c = new Charm();
        addToTop(new MakeTempCardInDrawPileAction(c, 1, true, true));
    }

    @Override
    public AbstractCard makeCopy() { return new Charm(); }
}