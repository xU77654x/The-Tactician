package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.FiendishBlowPower;
import tactician.powers.LoseFocusPower;
import tactician.util.CardStats;

public class FiendishBlow extends TacticianCard {
    public static final String ID = makeID(FiendishBlow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public FiendishBlow() {
        super(ID, info);
        setMagic(3, 0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FiendishBlowPower(this.magicNumber), this.magicNumber));
        if (this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new LoseFocusPower(this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new FiendishBlow(); }
}