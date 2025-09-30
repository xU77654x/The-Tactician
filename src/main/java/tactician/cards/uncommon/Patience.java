package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.PatiencePower;
import tactician.util.CardStats;

public class Patience extends TacticianCard {
    public static final String ID = makeID(Patience.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public Patience() {
        super(ID, info);
        setMagic(6, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new ApplyPowerAction(p, p, new PatiencePower(this.magicNumber), this.magicNumber)); }

    @Override
    public AbstractCard makeCopy() { return new Patience(); }
}