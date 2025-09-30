package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.PresciencePower;
import tactician.util.CardStats;

public class Prescience extends TacticianCard {
    public static final String ID = makeID(Prescience.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public Prescience() {
        super(ID, info);
        setMagic(4, 0);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new ApplyPowerAction(p, p, new PresciencePower(this.magicNumber), this.magicNumber)); }

    @Override
    public AbstractCard makeCopy() {
        return new Prescience();
    }
}