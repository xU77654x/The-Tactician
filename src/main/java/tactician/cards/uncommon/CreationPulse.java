package tactician.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.actions.PlaySoundAction;
import tactician.cards.TacticianCard;
import tactician.character.TacticianRobin;
import tactician.powers.CreationPulsePower;
import tactician.util.CardStats;

public class CreationPulse extends TacticianCard {
    public static final String ID = makeID(CreationPulse.class.getSimpleName());
    private static final CardStats info = new CardStats(
            TacticianRobin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public CreationPulse() {
        super(ID, info);
        setMagic(1, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PlaySoundAction("tactician:CreationPulse", 1.00f));
        addToBot(new ApplyPowerAction(p, p, new CreationPulsePower(this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new CreationPulse(); }
}