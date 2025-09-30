package tactician.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.ChaosStylePower;
import tactician.util.CardStats;

public class ChaosStyle extends TacticianCard {
    public static final String ID = makeID(ChaosStyle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public ChaosStyle() {
        super(ID, info);
        setMagic(3, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new ApplyPowerAction(p, p, new ChaosStylePower(this.magicNumber), this.magicNumber)); }

    @Override
    public AbstractCard makeCopy() {
        return new ChaosStyle();
    }
}