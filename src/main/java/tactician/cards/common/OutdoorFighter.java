package tactician.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.DeflectPower;
import tactician.util.CardStats;

public class OutdoorFighter extends TacticianCard {
    public static final String ID = makeID(OutdoorFighter.class.getSimpleName());
    private static final CardStats info = new CardStats(
        TacticianRobin.Meta.CARD_COLOR,
        CardType.SKILL,
        CardRarity.COMMON,
        CardTarget.SELF,
        1
    );

    public OutdoorFighter() {
        super(ID, info);
        setMagic(2, 1);
        setCustomVar("magicDeflect", 4, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlaySoundAction("tactician:OutdoorFighter", 1.50f));
        addToBot(new DrawCardAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DeflectPower(customVar("magicDeflect"))));
    }

    @Override
    public AbstractCard makeCopy() { return new OutdoorFighter(); }
}